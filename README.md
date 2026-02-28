# Travel Agency Management System

Travel Agency Management System is a web-based application designed to support and simplify the management of travel agency operations. The system allows administrators and travel agents to manage vacation packages, reservations and user accounts, while clients can explore available vacation offers and create reservations through a web interface.

The application is built using **Spring Boot** and follows a **layered MVC architecture**, separating the presentation layer, business logic and data access components. This approach improves maintainability, scalability and overall system organization.

The platform also integrates an **email notification service**, which automatically sends confirmation emails when users register or create reservations.

---

# System Roles

The system supports multiple user roles, each with specific permissions:

**Administrator**

* manages system users
* manages roles and permissions
* oversees system activity

**Travel Agent**

* manages vacation offers
* views and manages reservations
* updates available travel packages

**Client**

* browses available vacation packages
* creates reservations
* manages personal account information

---

# Main Features

The application provides several features designed to support travel agency operations:

* user registration and authentication
* role-based access control
* vacation package management (create, update, delete)
* reservation management
* client account management
* email confirmation for registration and reservations
* export of vacation data in multiple formats (CSV, TXT, PDF)

---

# System Architecture

The application follows a **layered architecture**, separating responsibilities into several logical layers.

**Controller Layer**
Handles HTTP requests and exposes endpoints used by the web interface.

**Service Layer**
Contains the core business logic of the application and coordinates operations between controllers and repositories.

**Repository Layer**
Handles communication with the database using Spring Data repositories.

**Entity Layer**
Defines the domain models used in the application, such as users, reservations and vacation packages.

This architecture improves modularity, maintainability and testability of the application.

---

# Technologies Used

Backend:

* Java
* Spring Boot
* Spring MVC
* Spring Data JPA
* Maven

Database:

* PostgreSQL

Other Technologies:

* REST APIs
* Email Service Integration

---

# Running the Application

To run the application locally:

1. Clone the repository

git clone https://github.com/AlexandraBirlea/Travel-Agency-Management-System.git

2. Navigate to the project folder

cd Travel-Agency-Management-System

3. Run the application using Maven

./mvnw spring-boot:run

The application will start locally and can be accessed through a web browser.

---

## Author

Alexandra-Maria Bîrlea  
MSc Student – Applied Computer Science  
Faculty of Automation and Computer Science  
Technical University of Cluj-Napoca
