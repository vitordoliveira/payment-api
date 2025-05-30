## 💳 Payment API
  A Simplified Payment System Using Spring Boot
  
  ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-6DB33F?style=for-the-badge&logo=spring-boot)
  ![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk)
  ![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
  ![JWT](https://img.shields.io/badge/JWT-Auth-000000?style=for-the-badge&logo=json-web-tokens)
  ![Swagger](https://img.shields.io/badge/Swagger-API_Docs-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

## 📋 About the Project

Payment API is a comprehensive solution for managing financial transactions between users, built with Spring Boot and Java 17. The system provides RESTful endpoints for basic banking operations such as deposits, withdrawals, and transfers, along with robust JWT-based authentication.

This API focuses on:
- **Security**: JWT authentication, refresh tokens, and thorough validations  
- **Scalability**: Layered and decoupled architecture  
- **Documentation**: Fully documented API with OpenAPI/Swagger  
- **Maintainability**: Clean code and SOLID principles  

## 🚀 Features

### 👤 Authentication and Users
- New user registration  
- Secure login with JWT tokens  
- Session renewal using refresh tokens  
- Full CRUD for user management  

### 💰 Accounts and Transactions
- Account creation and management  
- Real-time balance inquiries  
- Deposits and withdrawals  
- Transfers between accounts  
- Detailed transaction history  

### 🔒 Security
- Token-based authentication (JWT)  
- Role-based authorization  
- Comprehensive data validation  
- Protection against common attacks  

## 🛠️ Technologies Used

| Technology      | Version | Purpose                                 |
|-----------------|---------|-----------------------------------------|
| Spring Boot     | 3.2.3   | Main framework                          |
| Spring Security | 3.2.3   | Authentication and authorization         |
| Spring Data JPA | 3.2.3   | Data persistence                        |
| MySQL           | 8.0     | Database                                |
| Swagger/OpenAPI | 2.0.2   | API documentation                       |
| JWT             | 0.11.5  | Token-based authentication              |
| Maven           | 3.6+    | Dependency management                   |
| Java            | 17      | Programming language                    |

## ⚙️ Requirements

- JDK 17 or later  
- Maven 3.6+  
- MySQL 8.0+  
- Java IDE (recommended: VS Code, IntelliJ IDEA, or Eclipse)  

## 📦 Installation and Execution

### 1. Clone the Repository

```bash
git clone https://github.com/vitordoliveira/payment-api.git
cd payment-api
```

### 2. Configure the Database
Create a MySQL database:
```sql
CREATE DATABASE payment_api_db;
```
Then set your credentials in the src/main/resources/application.properties file:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/payment_api_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Build and Run
```bash
# Compile the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```
The API will be available at: [http://localhost:8080](http://localhost:8080)

## 📚 API Documentation
A comprehensive, interactive documentation is provided through Swagger UI:

• [Swagger UI](http://localhost:8080/swagger-ui/index.html)

## 🧩 Project Structure
```
PAYMENT-API/
├── src/main/java/com/vitoroliveira/paymentapi/
│   ├── config/         # Configuration (Security, OpenAPI)
│   ├── controller/     # REST API endpoints
│   ├── dto/            # Data Transfer Objects
│   ├── exception/      # Exception handling
│   ├── model/          # JPA entities / Data models
│   ├── repository/     # Data access interfaces
│   ├── security/       # JWT security implementation
│   └── service/        # Business logic
```

## 🔌 API Usage Examples

### Authentication
```http
# Login
POST /api/auth/login
Content-Type: application/json

{
  "username": "user@example.com",
  "password": "password123"
}

# Response
{
  "token": "eyJhbGciOiJIUzI1...",
  "refreshToken": "8c4e2a78-eaf0-4...",
  "userId": 1,
  "username": "user@example.com"
}
```

### Creating a Transaction
```http
# Transfer between accounts
POST /api/transactions
Authorization: Bearer eyJhbGciOiJIUzI1...
Content-Type: application/json

{
  "amount": 150.00,
  "senderId": 1,
  "receiverId": 2,
  "description": "Service payment"
}
```

### Checking Balance
```http
# Get account balance
GET /api/accounts/balance
Authorization: Bearer eyJhbGciOiJIUzI1...

# Response
{
  "accountId": 1,
  "balance": 850.00,
  "owner": "John Smith",
  "updatedAt": "2023-05-30T14:32:15"
}
```

## 🧪 Testing

This project includes both unit and integration tests:

```bash
# Run all tests
./mvnw test

# Run only unit tests
./mvnw test -Dtest=*Test

# Run only integration tests
./mvnw test -Dtest=*IT
```

## 📊 Monitoring

The application provides monitoring endpoints via Spring Actuator:

• `/actuator/health` - Application health status  
• `/actuator/info` - Application info  

## 🚀 Next Steps
- Financial report implementation  
- 2FA authentication  
- Administrative dashboard  
- Real-time notifications  
- Data export in multiple formats  

## 👤 Author

Vitor Oliveira  
• [GitHub](https://github.com/vitordoliveira)

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.
