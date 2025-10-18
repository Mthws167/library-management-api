# Library Management API (Spring Boot)

This is the backend application for the **Library Management System**, built with **Spring Boot**, providing **RESTful APIs** to manage users, books, and loans.

---

## How to Run

### Prerequisites

* **Java 21** – Ensure you have Java Development Kit (JDK) 21 installed.  
  Check your version with:
  ```bash
  java -version
  ```

* **Maven 3.8+** – Install Maven or use the included `mvnw` wrapper.

* **MySQL** – Install MySQL Server and create a database named `library_db`.

---

## Database Configuration

### Install MySQL
Download and install MySQL from [mysql.com](https://www.mysql.com/).

### Create the Database
Log in to MySQL:
```bash
mysql -u root -p
```

Create the database:
```sql
CREATE DATABASE library_db;
```

Verify connection:  
Ensure MySQL is running on `localhost:3306` with credentials **root/admin**.

---

## Steps to Run the Application

1. **Clone the Repository:**
   ```bash
   git clone <repository-link>
   cd library-management/library-management-api
   ```

2. **Install Dependencies:**
   ```bash
   mvn install
   ```

3. **Run the Application:**
   ```bash
   mvn spring-boot:run
   ```
   or
   ```bash
   mvn spring-boot:debug
   ```

---

## Access the API

Once running, the application will be available at:

[http://localhost:8080](http://localhost:8080)

**Endpoints** are available under `/api/`, for example:
* `/api/users`
* `/api/books`
* `/api/loans`

---

## Notes

> **Database Auto-Configuration:**  
> Uses `spring.jpa.hibernate.ddl-auto=update`, which will automatically create/update tables based on the entity definitions.

> **Logging:**  
> SQL queries are logged in DEBUG mode (`logging.level.org.hibernate.SQL=DEBUG`) for troubleshooting.

> **Port:**  
> The server runs on `server.port=8080`.  
> Change this in `src/main/resources/application.properties` if needed.

> **Testing:**  
> Run unit tests with:
> ```bash
> mvnw test
> ```

> **Environment:**  
> The active profile is `spring.profiles.active=prod`.  
> For development, create an `application-dev.properties` file and set:
> ```properties
> spring.profiles.active=dev
> ```

> **Google Books API:**  
> The API key and URL are commented out.  
> To enable book recommendations, uncomment and provide a valid API key from **Google Cloud**.
