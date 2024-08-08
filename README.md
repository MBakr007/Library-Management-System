
# Library Management System

This project is a Library Management System API built using Spring Boot. The system allows librarians to manage books, patrons, and borrowing records.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- **Java Development Kit (JDK)**: Version 17 or later installed.
- **Apache Maven**: Version 3.6.3 or later installed.
- **MySQL Server**: Installed and running.
- **MySQL Workbench**: Installed and configured for database management.
- **Postman** or any other API testing tool (optional, for testing API endpoints).

## Setup

### 1. Clone the Repository

```bash
git clone https://github.com/MBakr007/Library-Management-System.git
cd library-management-system
```

### 2. Database Setup

1. Open **MySQL Workbench** and create a new database schema:

   ```sql
   CREATE DATABASE library_db;
   ```

2. Update the `application.properties` file located in `src/main/resources` with your MySQL credentials:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/library_db
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   spring.jpa.hibernate.ddl-auto=update
   ```

### 3. Build the Application

Use Maven to build the project:

```bash
mvn clean install
```

### 4. Run the Application

Start the Spring Boot application:

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

## API Endpoints

### Book Management

- **GET /api/books**: Retrieve a list of all books.
- **GET /api/books/{id}**: Retrieve details of a specific book by ID.
- **POST /api/books**: Add a new book to the library.
- **PUT /api/books/{id}**: Update an existing book's information.
- **DELETE /api/books/{id}**: Remove a book from the library.

### Patron Management

- **GET /api/patrons**: Retrieve a list of all patrons.
- **GET /api/patrons/{id}**: Retrieve details of a specific patron by ID.
- **POST /api/patrons**: Add a new patron to the system.
- **PUT /api/patrons/{id}**: Update an existing patron's information.
- **DELETE /api/patrons/{id}**: Remove a patron from the system.

### Borrowing Records

- **POST /api/borrow/{bookId}/patron/{patronId}**: Allow a patron to borrow a book.
- **PUT /api/return/{bookId}/patron/{patronId}**: Record the return of a borrowed book by a patron.

## Testing

You can test the API endpoints using Postman or any other API testing tool. Here’s an example to add a new book using Postman:

1. **POST /api/books**
    - URL: `http://localhost:8080/api/books`
    - Method: POST
    - Body: JSON (example)

   ```json
   {
     "title": "The Great Gatsby",
     "author": "F. Scott Fitzgerald",
     "publicationYear": 1925,
     "isbn": "9780743273565"
   }
   ```

2. **GET /api/books**
    - URL: `http://localhost:8080/api/books`
    - Method: GET
    - Response: A list of books in the system.
