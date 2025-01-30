from flask import Flask, request,send_file ,  jsonify
from tensorflow.keras.models import load_model
from pymongo import MongoClient
import numpy as np
import matplotlib
matplotlib.use('Agg')  # Use non-GUI backend to avoid threading issues on macOS
import matplotlib.pyplot as plt
import io
from datetime import datetime
from flask import send_file
from flask_cors import CORS


from fpdf import FPDF
import tempfile






app = Flask(__name__)
CORS(app, resources={r"/*": {"origins": "*"}})

def generate_receipt(payment_data):
    # Initialize PDF
    pdf = FPDF()
    pdf.set_auto_page_break(auto=True, margin=15)
    pdf.add_page()
    
    # Title
    pdf.set_font("Arial", style='B', size=16)
    pdf.cell(0, 10, "Payment Receipt", align='C', ln=True)
    pdf.ln(10)
    
    # Set up columns for Payment and Farmer Details
    pdf.set_font("Arial", style='B', size=14)
    pdf.cell(90, 10, "Payment Details", align='L')
    pdf.cell(90, 10, "Farmer Details", align='R', ln=True)
    pdf.ln(5)
    
    # Payment Details on left
    pdf.set_font("Arial", size=12)
    payment_details = [
        f"Payment ID: {payment_data['paymentId']}",
        f"Amount: {payment_data['amount']}Rs",
        f"Payment Type: {payment_data['paymentType']}",
        f"Payment Date: {payment_data['paymentDate']}",
        f"Payment Status: {payment_data['paymentStatus']}"
    ]
    farmer = payment_data["order"]["product"]["farmerDTO"]
    farmer_details = [
        f"Farmer Name: {farmer['name']}",
        f"Farm Location: {farmer['farmLocation']}",
        f"Farm Type: {farmer['farmType']}"
    ]
    
    # Display details side by side
    for pay, farm in zip(payment_details, farmer_details):
        pdf.cell(90, 10, pay, align='L')
        pdf.cell(0, 10, farm, align='R', ln=True)
    pdf.ln(10)
    
    # Product and Order Details Table
    pdf.set_font("Arial", style='B', size=14)
    pdf.cell(0, 10, "Order Details", ln=True)
    
    # Check if there's enough space, if not, add a new page
    if pdf.get_y() > 250:  # Assuming 250 is near the bottom of the page
        pdf.add_page()
    
    pdf.set_font("Arial", style='B', size=12)
    headers = ["Product Name", "Amount (Rs.)", "Quantity", "Total Price (Rs.)"]
    widths = [60, 30, 30, 30]  # Adjusted widths to fit better
    
    for header, width in zip(headers, widths):
        pdf.cell(width, 10, header, border=1, align='C')
    pdf.ln()
    
    pdf.set_font("Arial", size=12)
    product = payment_data["order"]["product"]
    order_details = [
        product["prod_Name"],
        str(payment_data['amount']),
        str(payment_data['order']['quantity']),
        str(payment_data['order']['total_Price'])
    ]
    
    for detail, width in zip(order_details, widths):
        pdf.cell(width, 10, detail, border=1, align='C')
    pdf.ln(10)
    
    # Closing message
    pdf.cell(0, 10, "Thank you for your payment!", align='C', ln=True)
    
    # Save PDF to a temporary file
    temp_pdf = tempfile.NamedTemporaryFile(delete=False, suffix=".pdf")
    pdf.output(temp_pdf.name)
    
    return temp_pdf.name
# Load the trained model
model_path = "price_recommendation_model.keras"
price_model = load_model(model_path)

# MongoDB connection
client = MongoClient("mongodb+srv://manvith:manvith123@agritrading.dcnef.mongodb.net/")
db = client.Agritrading  # Replace with your database name
collection = db.commodity_prices  # Replace with your collection name

@app.route("/")
def home():
    """
    Root route to test if the API is running.
    """
    return "Welcome to the Price Recommendation API!"

@app.route("/recommend_price", methods=["POST"])
def recommend_price():
    """
    Endpoint to recommend prices based on input data.
    Input: JSON with features like 'state', 'district', 'market', 'commodity', 'month', etc.
    Output: Predicted price
    """
    try:
        
        # Parse JSON input
        data = request.get_json()
        required_fields = ['state', 'district', 'market', 'commodity',  'month']
        
        state = data.get("state")
        district = data.get("district")
        market = data.get("market")
        commodity = data.get("commodity")
        month = int(data.get("month"))
        
        # Validate input
        for field in required_fields:
            if field not in data:
                return jsonify({"error": f"Missing required field: {field}"}), 400
        
        
        state_doc = db.state.find_one({"state": state})
        district_doc = db.district.find_one({"district": district})
        market_doc = db.market.find_one({"market": market})
        commodity_doc = db.commodity.find_one({"commodity": commodity})

        # Validate if the documents exist
        if not state_doc:
            return jsonify({"error": f"State '{state}' not found in the database."}), 404
        if not district_doc:
            return jsonify({"error": f"District '{district}' not found in the database."}), 404
        if not market_doc:
            return jsonify({"error": f"Market '{market}' not found in the database."}), 404
        if not commodity_doc:
            return jsonify({"error": f"Commodity '{commodity}' not found in the database."}), 404

        # Extract encoded values
        state_encoded = state_doc.get("state_encoded")
        district_encoded = district_doc.get("district_encoded")
        market_encoded = market_doc.get("market_encoded")
        commodity_encoded = commodity_doc.get("commodity_encoded")
        # Extract query parameters from the payload
        filter={
        'commodity': commodity_encoded, 
        'month': month
        }
        prices = client['Agritrading']['commodity_prices'].find(
        filter=filter
        )
        

        

        # Fetch min_price and max_price for the given filters
        # prices = collection.find(query, {"min_price": 1, "max_price": 1, "_id": 0})
        # print(prices)
        prices = list(prices)
        
        # for i in prices:
        #     for j in i:
        #         print(i , end="")
        #     print("\n")        

        if not prices:
            return jsonify({"error": f"No data found for the given filters: {filter}"}), 404

        # Calculate min_price and max_price
        min_price = min(p["min_price"] for p in prices)
        max_price = max(p["max_price"] for p in prices)

        # Prepare features for prediction
        features = [
            min_price,
            max_price,
            max_price-min_price,
            state_encoded,
            district_encoded,
            market_encoded,
            commodity_encoded,
            max_price/(min_price *1e-5),
            month
        ]
        features_array = np.array(features).reshape(1, 1, len(features))

        # Make prediction
        predicted_price = price_model.predict(features_array)[0][0]
        actual_price = min_price + (predicted_price * (max_price - min_price))

        # Return the prediction
        return jsonify({
            "Normalised_output_by_model": float(predicted_price),
            "predicted_price": float(actual_price),
            "min_price": float(min_price),
            "max_price": float(max_price),
            "filters_used": filter
        })

    except Exception as e:
        # Handle errors
        return jsonify({"error": str(e)}), 500

@app.route("/generate_graph", methods=["GET"])
def generate_graph():
    """
    Endpoint to generate a graph for a given commodity and month.
    Input: Query parameters 'commodity' and 'month'
    Output: Graph as an image
    """
    try:
        # Get query parameters
        commoditystring = request.args.get("commodity")
        month = request.args.get("month")
        commodity_doc = db.commodity.find_one({"commodity": commoditystring})
         
      
        
        if not commodity_doc:
            return jsonify({"error": f"Commodity '{commoditystring}' not found in the database."}), 404

        # Extract encoded values
        
        commodity = commodity_doc.get("commodity_encoded")

        if not commodity or not month:
            return jsonify({"error": "Missing required query parameters: 'commodity' and 'month'"}), 400
        commodity = int(commodity)  # Assuming commodity is an integer
        month = 6
        
        # Fetch prices from MongoDB
        filter = {'commodity': commodity, 'month': month}
        data = client['Agritrading']['commodity_prices'].find(filter=filter)

        # Convert the cursor to a list
        data = list(data)

        if not data:
            return jsonify({"error": f"No data found for the given filters: {filter}"}), 404

        # Debugging: print data content
        print("data:", data)

        # Extract prices and assign unique x-coordinates
        prices = [item["modal_price"] for item in data]
        x_coords = list(range(len(prices)))  # Create unique x-coordinates for each point

        # Debugging: print x-coordinates and prices
        print("x_coords:", x_coords)
        print("prices:", prices)

        # Generate graph
        plt.figure(figsize=(10, 6))
        plt.plot(x_coords, prices, marker="o", label=f"{commodity}")
        plt.title(f"Price Trends for Commodity {commodity} this Month")
        # plt.xlabel("Data Points")
        plt.ylabel("Price")
        plt.grid(True)
        plt.legend()

        # Customize x-axis to avoid overlap
        plt.xticks([], [])  # Remove x-axis ticks and labels
      # Ensure unique x-coordinates for each data point
        plt.axhline(y=min(prices), color="red", linestyle="--", label=f"Min Price: {min(prices)}")
        plt.axhline(y=max(prices), color="green", linestyle="--", label=f"Max Price: {max(prices)}")

# Add legend for the lines
        plt.legend()
        # Save graph to a BytesIO object
        img = io.BytesIO()
        plt.savefig(img, format="png")
        img.seek(0)

        # Close the plot
        plt.close()

        # Return the image as a response
        return send_file(img, mimetype="image/png")

    except Exception as e:
        # Handle errors
        return jsonify({"error": str(e)}), 500

@app.route("/fetch_data", methods=["GET"])
def fetch_data():
    try:
        # Fetch all documents from the collections
        states = db.state.find({}, {"_id": 0, "state": 1})  # Retrieve state names
        districts = db.district.find({}, {"_id": 0, "district": 1})  # Retrieve district names
        markets = db.market.find({}, {"_id": 0, "market": 1})  # Retrieve market names
        commodities = db.commodity.find({}, {"_id": 0, "commodity": 1})  # Retrieve commodity names

        # Convert cursor objects to lists
        states_list = [doc["state"] for doc in states]
        districts_list = [doc["district"] for doc in districts]
        markets_list = [doc["market"] for doc in markets]
        commodities_list = [doc["commodity"] for doc in commodities]

        # Return the results as a JSON response
        return jsonify({
            "states": states_list,
            "districts": districts_list,
            "markets": markets_list,
            "commodities": commodities_list
        }), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.route("/favicon.ico")
def favicon():
    """
    Handle the browser's favicon.ico request.
    """
    return "", 204


@app.route("/generate-receipt", methods=['POST'])
def generate_receipt_api():
    

    try:
        print(request.headers) 
        data = request.get_json()
        if not data or "payment" not in data or "payment" not in data["payment"]:
            return jsonify({"error": "Invalid payment data"}), 400
        
        # Extract the correct payment object
        payment = data["payment"]["payment"]
        pdf_path = generate_receipt(payment)
        return send_file(pdf_path, as_attachment=True, download_name="payment_receipt.pdf", mimetype="application/pdf")
    except Exception as e:
        print(f"An error occurred: {str(e)}")
        return jsonify({"error": "An error occurred while generating the receipt"}), 500

if __name__ == "__main__":
    # Run the Flask app
    app.run(debug=True)
