# Course Recovery System

A Java-based Course Recovery System developed using NetBeans.  
This system helps manage students, course results, eligibility checking, course recovery planning, academic reports, and email notifications.

## Project Overview

The Course Recovery System is designed to support academic recovery management for students who need to retake or recover failed modules. The system provides role-based features such as user management, eligibility checking, recovery plan generation, report viewing, and email notification logging.

This project was developed as part of a Java Object-Oriented Programming assignment to demonstrate the use of Java, Swing GUI, file handling, modular programming, and object-oriented design principles.

## Features

- User login system
- Role-based access
- User management
- Student eligibility checking
- Course recovery plan generation
- Academic performance report
- Email notification feature
- OTP/password recovery support
- File-based data storage
- Java Swing graphical user interface

## Technologies Used

- Java
- Java Swing
- NetBeans IDE
- Maven
- File Handling
- Object-Oriented Programming
- CSV/TXT Data Files

## Project Structure

```text
Course-Recovery-System/
│
├── src/
│   └── main/
│       └── java/
│           └── crs/
│               ├── gui/
│               ├── models/
│               └── services/
│
├── student_information.csv
├── course_assessment_information.csv
├── results.csv
├── email_log.txt
├── pom.xml
└── README.md
```

## Main Modules

### 1. User Management
Handles login, user accounts, and role-based access.

### 2. Eligibility Check
Checks whether a student is eligible for course recovery based on academic results.

### 3. Course Recovery Plan
Generates recovery tasks and plans for students who need academic recovery.

### 4. Academic Report
Displays academic performance information and generates reports.

### 5. Email Notification
Sends or logs email notifications related to recovery plans, reports, and user actions.

## How to Run the Project

### Option 1: Run Using NetBeans

1. Open NetBeans IDE.
2. Click **File > Open Project**.
3. Select the project folder.
4. Wait for Maven dependencies to load.
5. Right-click the project.
6. Click **Run**.

### Option 2: Run Using Terminal

Open terminal inside the project folder and run:

```bash
mvn clean compile
```

Then run the main class:

```bash
mvn exec:java -Dexec.mainClass="crs.gui.LoginFrame"
```

## Learning Outcomes

Through this project, I learned how to:

- Build a Java GUI application using Swing
- Apply object-oriented programming concepts
- Use modular programming with separate models, services, and GUI classes
- Read and write data using CSV/TXT files
- Implement login and role-based access
- Manage project dependencies using Maven
- Work with a team-based Java project structure

## Future Improvements

- Connect the system with a database
- Improve UI design
- Add admin dashboard analytics
- Add stronger authentication
- Generate PDF reports
- Add real email sending with secure configuration

## Author

**Muhammad Usman**  
Computer Science Student  
Asia Pacific University of Technology & Innovation

GitHub: [usmanmunim](https://github.com/usmanmunim)  
LinkedIn: [Muhammad Usman](https://www.linkedin.com/in/muhammad-usman-9712b7336/)
