# 💳 Payment API

A Simplified Payment System Using Spring Boot

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-6DB33F?style=for-the-badge&logo=spring-boot)
![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-Auth-000000?style=for-the-badge&logo=json-web-tokens)
![Swagger](https://img.shields.io/badge/Swagger-API_Docs-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

## About the Project

Payment API is a REST-based system for managing financial transactions between users, built with Spring Boot and Java. This project supports essential banking operations such as deposits, withdrawals, and transfers, secured with JWT authentication and refresh tokens.

Key points include:
- **Layered architecture**: Controllers, Services, Repositories, and Models  
- **Security**: Complete Spring Security integration with JWT  
- **REST API**: Well-defined RESTful endpoints with appropriate responses  
- **Documentation**: Fully documented using Swagger/OpenAPI  

## Implemented Features

### Authentication & Users
- ✅ User registration  
- ✅ Login with JWT token generation  
- ✅ Refresh token mechanism for session renewal  
- ✅ Full CRUD for user entities  

### Accounts & Transactions
- ✅ Account creation and linking to users  
- ✅ Real-time balance inquiries  
- ✅ Deposit and withdrawal operations  
- ✅ Transfers between accounts  
- ✅ Transaction history and details  

### Security
- ✅ Bearer token (JWT) authentication  
- ✅ Endpoint protection via Spring Security  
- ✅ Request data validation  
- ✅ Token expiration control  

## Technologies Used

| Technology       | Version | Purpose                             |
|------------------|---------|-------------------------------------|
| Spring Boot      | 3.2.3   | Main framework                      |
| Spring Security  | 3.2.3   | Authentication and authorization    |
| Spring Data JPA  | 3.2.3   | Data persistence                    |
| MySQL            | 8.0     | Database                            |
| Swagger/OpenAPI  | 2.0.2   | API documentation                   |
| JWT              | 0.11.5  | Token-based authentication          |
| Maven            | 3.6+    | Dependency management               |
| Java             | 17      | Programming language                |
| Spring Actuator  | 3.2.3   | Application monitoring              |

## Requirements

- JDK 17 or later  
- Maven 3.6+  
- MySQL 8.0+  
- Java IDE (recommended: VS Code, IntelliJ IDEA, or Eclipse)  

## Installation and Execution

1. Clone the Repository:
```bash
git clone https://github.com/vitordoliveira/payment-api.git
cd payment-api
```

2. Configure the Database:
```sql
CREATE DATABASE payment_api_db;
```
In src/main/resources/application.properties:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/payment_api_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. Build and Run:
```bash
./mvnw clean install
./mvnw spring-boot:run
```
The API will be available at http://localhost:8080

## Swagger API Documentation

A comprehensive and interactive Swagger UI is available at:
http://localhost:8080/swagger-ui/index.html

- View all available endpoints  
- Test requests directly  
- Examine data models and responses  
- Authenticate to test protected endpoints  

## Project Structure

```
PAYMENT-API/
├── src/main/java/com/vitoroliveira/paymentapi/
│   ├── config/          # Configuration (Security, OpenAPI)
│   │   ├── OpenApiConfig.java
│   │   └── SecurityConfig.java
│   ├── controller/      # REST API Endpoints
│   │   ├── AccountController.java
│   │   ├── AuthController.java
│   │   ├── TransactionController.java
│   │   └── UserController.java
│   ├── dto/             # Data Transfer Objects
│   ├── exception/       # Exception handling
│   ├── model/           # JPA Entities
│   │   ├── Account.java
│   │   ├── RefreshToken.java
│   │   ├── Transaction.java
│   │   └── User.java
│   ├── repository/      # Data access interfaces
│   ├── security/        # JWT security implementation
│   └── service/         # Business logic
```

## Main API Endpoints

### Authentication
```http
# User registration
POST /api/auth/register
Content-Type: application/json

{
  "username": "user123",
  "password": "password123",
  "email": "user@example.com",
  "fullName": "John Doe"
}

# Login
POST /api/auth/login
Content-Type: application/json

{
  "username": "user123",
  "password": "password123"
}

# Refresh token
POST /api/auth/refreshtoken
Content-Type: application/json

{
  "refreshToken": "8c4e2a78-eaf0-4..."
}
```

### Account Management
```http
# Check balance
GET /api/accounts/balance
Authorization: Bearer eyJhbGciOiJIUzI1...
```

```http
# Deposit
POST /api/accounts/deposit
Authorization: Bearer eyJhbGciOiJIUzI1...
Content-Type: application/json

{
  "amount": 100.00
}
```

```http
# Withdraw
POST /api/accounts/withdraw
Authorization: Bearer eyJhbGciOiJIUzI1...
Content-Type: application/json

{
  "amount": 50.00
}
```

### Transactions
```http
# Create a transfer
POST /api/transactions
Authorization: Bearer eyJhbGciOiJIUzI1...
Content-Type: application/json

{
  "amount": 150.00,
  "senderId": 1,
  "receiverId": 2,
  "description": "Payment"
}

# List transactions
GET /api/transactions
Authorization: Bearer eyJhbGciOiJIUzI1...

# Get transaction details
GET /api/transactions/{id}
Authorization: Bearer eyJhbGciOiJIUzI1...
```

## Monitoring

Spring Actuator provides essential monitoring features:
```text
/actuator/health — Application health status
/actuator/info — Application information
```

## Author

Vitor Oliveira  
• [GitHub](https://github.com/vitordoliveira)

## License

This project is licensed under the MIT License.
