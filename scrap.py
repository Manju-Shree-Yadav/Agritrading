import requests
import pandas as pd

# Define the URL and payload
url = "https://enam.gov.in/web/Ajax_ctrl/trade_data_list"
payload = {
    "language": "en",
    "stateName": "-- All --",
    "apmcName": "-- Select APMCs --",
    "commodityName": "-- Select Commodity --",
    "fromDate": "2024-11-27",
    "toDate": "2024-11-27",
}
headers = {
    "Content-Type": "application/x-www-form-urlencoded",
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36",
}

# Send the POST request
response = requests.post(url, data=payload, headers=headers)

# Check if the request was successful
if response.status_code == 200:
    # Parse the JSON response
    data = response.json().get("data", [])
    
    if data:
        # Convert the data to a pandas DataFrame
        df = pd.DataFrame(data)
        
        # Save the DataFrame to a CSV file
        output_file = "trade_data.csv"
        df.to_csv(output_file, index=False)
        print(f"Data saved successfully to {output_file}")
    else:
        print("No data found in the API response.")
else:
    print(f"Failed to fetch data. Status code: {response.status_code}")
