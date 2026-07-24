# 🛠️ Worker Booking System

A modern full-stack Worker Booking System built using **Java Spring Boot**, **Thymeleaf**, **HTML**, **CSS**, **JavaScript**, and **MySQL**. The application enables users to browse skilled professionals, book services, and manage appointments through an intuitive interface while providing administrators with complete control over the platform.

---

## 🚀 Features

### 👤 User Module
- User Registration & Login
- Secure Authentication
- Browse Available Workers
- Worker Search & Filtering
- Book Services
- Payment Integration
- Booking History
- User Profile Management
- Logout

### 👨‍💼 Admin Module
- Admin Login
- Dashboard Overview
- Manage Workers
- Manage Users
- View Bookings
- Booking Status Management
- System Monitoring

---

# ✨ Modern UI

- Responsive Design
- Glassmorphism Effects
- Smooth Animations
- Fixed Authentication Panel
- Mobile Friendly Layout
- Beautiful Gradient Theme
- Interactive Cards
- Clean Dashboard

---

# 🏗️ Technology Stack

## Backend
- Java 17
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA

## Frontend
- HTML5
- CSS3
- JavaScript
- Thymeleaf

## Database
- MySQL

## Build Tool
- Maven

---

# 📂 Project Structure

```
Worker-Booking-System-using-Java
│
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── controller
│   │   │   ├── service
│   │   │   ├── repository
│   │   │   ├── model
│   │   │   ├── config
│   │   │   └── WorkerBookingApplication.java
│   │   │
│   │   ├── resources
│   │   │   ├── static
│   │   │   ├── templates
│   │   │   └── application.properties
│   │
│   └── test
│
├── pom.xml
└── README.md
```

---

# ⚙️ Installation

## Clone Repository

```bash
git clone https://github.com/kishorev2006/Worker-Booking-System-using-Java.git
```

Move into the project

```bash
cd Worker-Booking-System-using-Java
```

---

## Configure Database

Create a MySQL database.

Example

```sql
CREATE DATABASE worker_booking;
```

Update

```
src/main/resources/application.properties
```

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/worker_booking
spring.datasource.username=root
spring.datasource.password=your_password
```

---

## Run the Project

Using Maven

```bash
./mvnw spring-boot:run
```

or

```bash
mvn spring-boot:run
```

Application runs at

```
http://localhost:8081
```

---

# 📸 Screenshots

## Landing Page

![Landing](screenshots/img1.png)

## User Login

![User Login](screenshots/img2.png)

## Admin Login

![Admin Login](screenshots/img3.png)

## User Dashboard

![Dashboard](screenshots/img4.png)

## Worker List

![Workers](screenshots/img5.png)

## Booking Page

![Booking](screenshots/img6.png)

## Admin Dashboard

![Admin Dashboard](screenshots/img7.png)

*(Add more screenshots as needed.)*

---

# 🔐 Security

- Spring Security Authentication
- Role-Based Access Control
- Session Management
- Secure Login
- Protected Routes

---

# 📈 Future Improvements

- Email Notifications
- OTP Verification
- Online Payment Gateway
- Worker Ratings & Reviews
- Live Booking Tracking
- Google Maps Integration
- Service Categories
- Mobile Application
- AI-Based Worker Recommendation

---

# 🎯 Learning Objectives

This project demonstrates

- Full Stack Java Development
- Spring Boot MVC Architecture
- Authentication & Authorization
- CRUD Operations
- Database Connectivity
- Session Management
- Responsive Web Design
- REST Principles
- MVC Design Pattern

---

# 👨‍💻 Developer

**Kishore V**

GitHub

https://github.com/kishorev2006

---

# ⭐ Support

If you found this project useful, consider giving it a ⭐ on GitHub.

---

# 📜 License

This project is developed for educational and portfolio purposes.
