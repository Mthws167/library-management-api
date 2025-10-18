Library Management API (Spring Boot)
This is the backend application for the Library Management system, built with Spring Boot, providing RESTful APIs to manage users, books, and loans.
How to Run
Prerequisites

Java 21: Ensure you have Java Development Kit (JDK) 21 installed. You can check your version with java -version.
Maven: Install Maven 3.8+ or use the included mvnw wrapper.
MySQL: Install MySQL Server and create a database named library_db.

Database Configuration

Install MySQL: Download and install MySQL from mysql.com.
Create the Database:
Log in to MySQL:mysql -u root -p


Create the database:CREATE DATABASE library_db;


Verify Connection: Ensure MySQL is running on localhost:3306 with the credentials root/admin.

Steps to Run the Application

Clone the Repository:
git clone <link repository>
cd library-management/library-management-api


Install Dependencies:

   mvn install

Run the Application:

   mvn spring-boot:run
   or
   mvn spring-boot:debug



Access the API:

The application will be available at http://localhost:8080.
API endpoints are under /api/ (e.g., /api/users, /api/books, /api/loans).



Notes

Database Auto-Configuration: The application uses spring.jpa.hibernate.ddl-auto=update, which will automatically create/update tables based on the entity definitions.

Logging: SQL queries are logged in DEBUG mode (logging.level.org.hibernate.SQL=DEBUG) for troubleshooting.

Port: The server runs on server.port=8080. Change this in src/main/resources/application.properties if needed.

Testing: Run unit tests with:
   mvnw test


Environment: The spring.profiles.active=prod is set. For development, you can create an application-dev.properties file and set spring.profiles.active=dev.

Google Books API: The API key and URL are commented out. To enable book recommendations, uncomment and provide a valid API key from Google Cloud.

