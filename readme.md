# Auth Service

## Overview
The **Auth Service** is responsible for handling authentication and authorization for the application. It integrates with Google OAuth 2.0 to authenticate users, generates JWT tokens for secure communication, and stores OAuth credentials for further usage by other services.

## Key Responsibilities
- **User Authentication** using Google OAuth 2.0
- **JWT Token Generation & Validation**
- **Secure API Endpoints** using Spring Security
- **Inter-Service Authentication** for communication with other microservices
- **Kafka Integration** (for event-based communication)

## Technologies Used
- **Spring Boot** (Security, OAuth2 Client, Web, Kafka)
- **Spring Security**
- **JWT (JSON Web Tokens)**
- **MySQL** (for user storage, if needed in the future)
- **Kafka** (for event-driven architecture)
- **Lombok** (for reducing boilerplate code)

---

## Architecture
```plaintext
                      +----------------------------+
                      |        Frontend (UI)       |
                      +-------------+--------------+
                                    |
                                    | (OAuth Login Request)
                                    v
                      +-------------+--------------+
                      |     Auth Service (Spring)  |
                      | - Handles OAuth Flow      |
                      | - Generates JWT Tokens   |
                      | - Stores OAuth Tokens    |
                      +-------------+--------------+
                                    |
                                    | (Valid JWT Token)
                                    v
      +----------------------+   +--------------------------+
      |   Expense Service    |   |   Email Fetch Service   |
      | - Secure Endpoints   |   | - Uses OAuth Token      |
      +----------------------+   +--------------------------+
                                    |
                                    | (Fetch Emails via Gmail API)
                                    v
                      +----------------------------+
                      |       Google Services      |
                      +----------------------------+
```

---

## API Endpoints

### Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/oauth2/authorize/google` | Redirects to Google OAuth 2.0 for authentication |
| GET    | `/login/oauth2/code/google` | Google callback endpoint, exchanges authorization code for access token |
| POST   | `/auth/token/validate` | Validates JWT token and returns user details |

### Internal APIs (For Other Services)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/auth/internal/userinfo` | Retrieves user info based on JWT token (used internally) |
| GET    | `/auth/internal/oauth-token/{userId}` | Retrieves stored Google OAuth token for Email Fetch Service |

---

## Security & Token Handling
- **Spring Security OAuth2 Client** manages the authentication flow.
- **JWT tokens** are issued upon successful authentication and used for securing endpoints.
- **OAuth Tokens** are securely stored and retrieved when needed for Gmail API.

---

## Kafka Events
- **`user.logged_in`** → Triggered when a user logs in successfully.
- **`user.token_refreshed`** → Triggered when OAuth token is refreshed.

---

## Environment Variables
The application uses environment variables for sensitive configurations.

```properties
# Server Configuration
server.port=8080
spring.application.name=auth-service

# Google OAuth2
spring.security.oauth2.client.registration.google.client-id=${GOOGLE_CLIENT_ID}
spring.security.oauth2.client.registration.google.client-secret=${GOOGLE_CLIENT_SECRET}
spring.security.oauth2.client.registration.google.scope=openid,profile,email
spring.security.oauth2.client.registration.google.redirect-uri={baseUrl}/login/oauth2/code/google
spring.security.oauth2.client.provider.google.issuer-uri=https://accounts.google.com

# JWT Configuration
jwt.secret=${JWT_SECRET}
jwt.expiration=86400000  # 1 day in milliseconds

# Kafka Configuration
spring.kafka.bootstrap-servers=${KAFKA_BROKER}
spring.kafka.consumer.group-id=auth-group
```

## Environment Variables (`.env`)
Create a `.env` file in the root directory and configure the following variables:

```
# Database Configuration
DB_URL=jdbc:mysql://localhost:3306/fin_pareek
DB_USER=root
DB_PASSWORD=yourpassword

# Google OAuth2 Credentials
GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret
JWT_SECRET=your_jwt_secret_key

# Kafka Configuration
KAFKA_BROKER=localhost:9092

# Application Configuration
SERVER_PORT=8080
```

---

## Deployment & Scaling
- Can be deployed as a **Docker container**
- Load-balanced using **Kubernetes** or **ECS**
- Uses **Spring Profiles** for different environments (dev, prod)

---

## Future Enhancements
- Implement **Refresh Tokens** for JWT expiration handling
- Support **Multiple Authentication Providers** (e.g., GitHub, Apple Sign-In)
- Introduce **RBAC (Role-Based Access Control)**

---

This document serves as the high-level overview of the **Auth Service** in the application. 🚀