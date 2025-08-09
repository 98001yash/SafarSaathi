# 🌏 SafarSaathi — Backend

SafarSaathi is a **microservices-based travel companion matching platform** that helps travelers find compatible companions for their journeys.  
The backend is built with **Spring Boot**, following a modular architecture for scalability, maintainability, and future feature expansion.

---

## 🚀 Tech Stack

- **Java 21** + **Spring Boot**
- **Spring Cloud** (Eureka, API Gateway, Feign Clients)
- **Spring Security + JWT** (Centralized Authentication)
- **MySQL** (Primary database)
-  **PostgreSQl** (Primary database)
- **Neo4j** (Graph relationships for companion connections)
- **Kafka** (Planned: Notification & event streaming)
- **Python ML Service** (Companion matching engine)
- **Docker** (Containerization)
- **Feign Client** for inter-service communication

---

## 🏗️ Microservices Overview

| Service Name        | Description |
|---------------------|-------------|
| **auth-service**    | Handles signup, login, and JWT token generation. |
| **user-service**    | Manages user profiles including bio, location, lifestyle, and travel preferences. |
| **trip-service**    | Manages trip creation, updates, search, and status changes. |
| **companion-service** | Handles companion requests, preferences, and connections (with Neo4j relationships). |
| **matching-service** | Finds top travel companion matches using ML-based ranking. |
| **bot-service**     | Provides basic chat/recommendations (AI-assisted). |
| **notification-service** *(planned)* | Sends notifications via Email, WhatsApp, etc., using Kafka events. |
| **chat-service** *(planned)* | Real-time chat between matched companions using WebSocket/STOMP. |

---

---

## 🔑 Features

- **Centralized Authentication** at API Gateway
- **Secure JWT-based authorization** for all services
- **Profile-driven matching** with ML integration
- **Graph relationships** to track companion requests (Neo4j)
- **Trip management** with search filters and geocoding
- **Future-ready** for notifications & chat
- **Inter-service communication** via Feign Clients

---

## ⚙️ How to Run

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/your-username/SafarSaathi.git
cd SafarSaathi

2️⃣ Start Eureka Server
cd eureka-server
mvn spring-boot:run

3️⃣ Start API Gateway
cd api-gateway
mvn spring-boot:run

4️⃣ Start Microservices
Run each microservice in separate terminals:

cd auth-service && mvn spring-boot:run
cd user-service && mvn spring-boot:run
cd trip-service && mvn spring-boot:run
cd companion-service && mvn spring-boot:run
cd matching-service && mvn spring-boot:run
cd bot-service && mvn spring-boot:run

*📜 License
This project is licensed under the MIT License — feel free to use and adapt it.

yaml
Copy
Edit

---

If you want, I can also make this **README** with:
- A **diagram** showing service interactions
- **Badges** (Java, Spring Boot, Docker, MySQL, etc.)
- Example **API request/response JSON** for each service

That would make it even more polished and professional.**

