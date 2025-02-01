# AgriTrade - AI-Powered E-Commerce for Farmers and Traders

<p align="center">
  <img src="https://github.com/user-attachments/assets/bdb9fb5e-464f-42a2-a659-7598e52baf87" width="300">
</p>

## Introduction

Artificial Intelligence (AI) and Machine Learning (ML) are transforming the agricultural sector by improving efficiency, transparency, and decision-making. Traditional agricultural trading systems struggle with price fluctuations, lack of real-time market insights, and dependency on intermediaries. **AgriTrade** aims to solve these challenges by providing a fair and transparent trading platform for farmers and traders.

## Features

- **AI-Powered Crop Price Prediction:** Uses ML models (LSTM, Random Forest, XGBoost) to forecast prices.
- **Real-Time Market Insights:** Provides farmers and traders with dynamic price updates and trends.
- **Seamless E-Commerce Platform:** Allows farmers to list and sell their produce directly to buyers.
- **Secure Transactions:** Ensures safe and transparent payments.
- **User-Friendly Interface:** Built with React.js and Bulma CSS for an intuitive experience.
- **Data-Driven Analytics:** Offers insights on market trends, demand, and pricing using MongoDB and PostgreSQL.

---

## Technologies Used

### Frontend

- **React.js** - For a dynamic and responsive UI.
- **HTML5 & CSS3** - For structured and styled webpages.
- **Bulma CSS Framework** - Ensures consistent styling and faster development.

### Backend

- **Java (Spring Boot)** - For API development and server-side operations.
- **Python (Flask)** - For deploying trained ML models.

### Databases

- **PostgreSQL** - For structured data like user profiles, product details, and transactions.
- **MongoDB** - For handling real-time analytics and ML outputs.

### AI/ML Tools

- **TensorFlow / PyTorch** - For training and deploying ML models.
- **Pandas & NumPy** - For data preprocessing and manipulation.
- **Scikit-learn** - For implementing ML algorithms like Random Forest and XGBoost.

### API Testing & Deployment

- **Postman** - For API testing and debugging.
- **Docker** - To containerize the project and simplify deployment.

---

## Setup Instructions

### Prerequisites

Ensure you have the following installed on your system:

- **Node.js & npm** (for frontend)
- **Java JDK 11+** (for Spring Boot backend)
- **Python 3.8+** (for ML models)
- **PostgreSQL & MongoDB** (for databases)


### 1. Clone the Repository

```bash
git clone https://github.com/ManvithLB/Agritrading
cd Agritrading
```

### 2. Setting Up the Backend

#### A. Spring Boot API (Java)

```bash
cd agritrading_backend
./mvnw spring-boot:run
```
Note : Easier to run in Intellij



#### B. Flask API (ML Models)

```bash
cd AI
pip install -r requirements.txt
python app.py
```
#### C. Testing API (postman)

- Import the Postman Collection  **Agritrading.postman_collection.json** 
- Create a env_variable auth_token

### 3. Setting Up the Frontend

```bash
cd Agri-Trade_Frontend
npm install
npm start
```

### 4. Database Configuration

#### PostgreSQL

Create a database and configure the **application.properties** file:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/agritrade
spring.datasource.username=your_username
spring.datasource.password=your_password
```

#### MongoDB

Ensure MongoDB is running:

```bash
mongod --dbpath /your/db/path
```

### 5. Running the Project with Docker (Optional)

```bash
docker-compose up --build
```

---

## Project Structure

```
agritrade/
├── agritrading_backend/            # Spring Boot API
├── Agri-Trade_Frontend/           # React.js Frontend
├── AI/             # Flask ML Model API
├── database/           # PostgreSQL & MongoDB Config
├── docker-compose.yml  # Docker Configuration
├── README.md           # Project Documentation
```

---

## Contribution Guidelines

1. Fork the repository and create a new branch.
2. Make your changes and commit with a meaningful message.
3. Push to your fork and create a pull request.

---


