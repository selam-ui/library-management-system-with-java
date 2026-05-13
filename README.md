# Library Management System (JavaFX)

A complete **Maven-based Library Management System** built with JavaFX and MySQL. This application manages books, users, and borrowing records with a professional UI.

## 🏗️ Project Structure

```
library-management-system-with-java/
├── src/main/java/com/librarymanagementsystem/
│   ├── app/              (Application Entry Point)
│   ├── config/           (Constants & Configuration)
│   ├── controller/       (JavaFX Controllers)
│   ├── dao/              (Data Access Layer)
│   ├── dto/              (Data Transfer Objects)
│   ├── exception/        (Custom Exceptions)
│   ├── model/            (Entity Models)
│   ├── service/          (Business Logic)
│   └── util/             (Utilities)
├── src/main/resources/
│   ├── ui/               (FXML Files)
│   ├── db.properties     (Database Config)
│   └── style.css         (UI Styling)
├── pom.xml               (Maven Configuration)
└── README.md
```

## 📦 Dependencies

- **JavaFX 17.0.1** - Modern UI framework
- **MySQL Connector 8.0.33** - Database driver
- **SLF4J & Logback** - Logging framework
- **JUnit 4** - Testing framework

## 🚀 Quick Start

### 1. Prerequisites
- Java 17 or higher
- MySQL Server running
- Maven 3.6+

### 2. Database Setup

```sql
CREATE DATABASE library_db;
USE library_db;

-- Create Users Table
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(100),
    phone_number VARCHAR(15),
    role ENUM('ADMIN', 'LIBRARIAN', 'MEMBER') NOT NULL,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

-- Create Books Table
CREATE TABLE books (
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    author VARCHAR(100) NOT NULL,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    quantity INT NOT NULL,
    available_quantity INT NOT NULL,
    category VARCHAR(50),
    date_added TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    price DOUBLE NOT NULL,
    description TEXT
);

-- Create Borrow Records Table
CREATE TABLE borrow_records (
    borrow_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    borrow_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    due_date TIMESTAMP NOT NULL,
    return_date TIMESTAMP,
    status ENUM('ACTIVE', 'RETURNED', 'OVERDUE') DEFAULT 'ACTIVE',
    fine DOUBLE DEFAULT 0.0,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id)
);
```

### 3. Configure Database

Edit `src/main/resources/db.properties`:
```properties
db.url=jdbc:mysql://localhost:3306/library_db
db.username=root
db.password=your_password
```

### 4. Build & Run

```bash
# Clean and compile
mvn clean compile

# Run the application
mvn javafx:run

# Package as JAR
mvn clean package
```

## 🎯 Features

✅ **User Management**
- Register, login, and manage users
- Role-based access (Admin, Librarian, Member)
- User activation/deactivation

✅ **Book Management**
- Add, update, and delete books
- Search books by title, author, ISBN
- Filter by category
- Track available quantities

✅ **Borrow System**
- Borrow books with automatic due dates
- Return books and calculate fines
- Track overdue books
- Fine calculation (5 currency units per day)

✅ **Security**
- SHA-256 password hashing
- Password strength validation
- Authentication & authorization

✅ **Validation**
- Email validation
- Phone number validation
- ISBN validation
- Input length validation

✅ **Logging**
- SLF4J with Logback integration
- Comprehensive error logging
- Transaction logging

## 📋 Implemented Classes

### Models
- `Book.java` - Book entity
- `User.java` - User entity
- `BorrowRecord.java` - Borrow transaction

### DTOs
- `BookDTO.java` - Book data transfer
- `UserDTO.java` - User data transfer

### DAOs
- `BookDAO.java` & `BookDAOImpl.java`
- `UserDAO.java` & `UserDAOImpl.java`
- `BorrowDAO.java` & `BorrowDAOImpl.java` (Stub)
- `DBConnection.java` - Connection pooling

### Services
- `AuthService.java` - Authentication
- `BookService.java` - Book operations
- `UserService.java` (Stub)
- `BorrowService.java` (Stub)

### Controllers
- `LoginController.java` - Login screen
- `DashboardController.java` (Stub)
- `BookController.java` (Stub)
- `UserController.java` (Stub)
- `BorrowController.java` (Stub)

### Utilities
- `Logger.java` - Logging wrapper
- `PasswordUtil.java` - Password hashing
- `DateUtil.java` - Date utilities
- `Validator.java` - Input validation
- `DBConfig.java` - Config loader

### Configuration
- `Constants.java` - App constants

## 🔧 Next Steps

1. **Implement remaining DAOs** - BorrowDAOImpl complete CRUD
2. **Implement remaining Services** - UserService, BorrowService
3. **Create FXML UI screens** - Dashboard, Books, Users, Borrow
4. **Implement remaining Controllers** - Link services to UI
5. **Add unit tests** - JUnit test cases
6. **Add data validation** - Form validation on UI

## 📞 Support

For issues or questions, create an issue in the repository.

## 📄 License

MIT License - Feel free to use for educational purposes.
