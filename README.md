# Disaster Response System (DRS)

In today’s rapidly changing environment, the need for responsive, secure, and scalable disaster response systems is more critical than ever. The DRS-Enhanced application developed for this project addresses these needs by enabling real-time coordination, secure data handling, and effective multi-agency communication during emergencies. Built using Java, JavaFX, and MySQL, and structured with the MVC architecture, the system provides a user-friendly interface and a robust backend to support disaster scenarios such as floods, fires, or earthquakes. This report presents the design, implementation, and testing of the system, with a focus on usability, security, and architectural soundness.


This project is a **JavaFX-based Disaster Response System (DRS)** built as part of the COIT20258 assignment. It includes user login functionality, disaster reporting, manager-level department assignments, responder marking, and proper JUnit-based testing of the system's core logic.

---

## 🔧 Technologies Used

- Java 11
- JavaFX
- MySQL
- Maven
- JUnit 5 (JUnit Jupiter)
- MVC Pattern (Model-View-Controller)
- NetBeans IDE

---

## 🚀 Features

### 🔐 User Login System
- Role-based login:
  - Manager
  - Responder
  - Civilian
- Passwords hashed using SHA-256 before storing in the database

### 🧑 Civilian Panel
- Civilian users can submit disaster reports with:
  - Disaster type
  - Location
  - Severity level
  - Reporter name
  - Description
- Report is sent to the server and saved in MySQL

### 🧑‍💼 Manager Panel
- View all disaster reports
- Assign department to each disaster
- View assigned department column
- Prioritized response logic (manager can sort by severity if needed)

### 👨‍🚒 Responder Panel
- View disaster reports
- Mark selected report as "Handled"
- Refresh view after marking

### ✅ JUnit Test Coverage
- Unit tests written for:
  - Insert disaster
  - Fetch all disasters
  - Update assigned department
  - Mark as handled
- Tests use assertions (`assertEquals`, `assertFalse`, `assertTrue`, etc.)
- Maven Surefire plugin configured for test runs

---




## 🛠️ Setup Instructions

### 📁 Database Setup

1. Open MySQL Workbench
2. Run the provided `drs_setup.sql` file to:
   - Create the `drs_db` database
   - Create `disasters` and `users` tables
   - Insert sample users
   - Add `handled` and `assigned_department` fields

```sql
-- Example user credentials:
-- Username: manager1 | Password: managerpass | Role: Manager
-- Username: responder1 | Password: responderpass | Role: Responder
```

---

## 💻 Running the Application

### From NetBeans

1. Open the project in NetBeans.
2. Click on Clean & Build
3. Click Run Project




---

## 🧪 Running JUnit Tests

### From NetBeans

1. Navigate to DisasterReportDAOTest.java
2. Right-click on the file → Click Run File

### Expected Output:
```
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running dao.DisasterReportDAOTest
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
```


## 📌 Notes
- Civilian panel is accessible without login for demonstration purposes.
- Application strictly follows the MVC (Model-View-Controller) architecture.
- Data is exchanged between JavaFX client and server using Java Sockets over TCP.
- All JUnit tests are isolated from GUI logic for modularity.


## 📣 Author
- Student ID: [Your ID Here]
- Course: COIT20258
- Assignment 3 – Secured Disaster Response System


## 📋 License
This project is developed solely for academic evaluation purposes as part of the COIT20258 unit at CQUniversity.

© 2025. All rights reserved.
