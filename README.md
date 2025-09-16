# 📚 Bookstore API

## Description
Bookstore API is a system developed with **Spring Boot** that enables the management of an online bookstore. It includes features for users, books, purchases, loans, and authentication using **JWT**.

## 🚀 Technologies Used
- Java 17
- Spring Boot 3.4.3
- Spring Web
- Spring Security (JWT)
- Hibernate (JPA)
- MySQL
- Lombok
---

## 📦 Installation and Configuration
### 1️⃣ Clone the Repository
```sh
git clone https://github.com/Ne052003/Library-Online.git
cd library-online
```

### 2️⃣ Configure the Database
Create an `application.properties` or `application.yml` file and configure the database:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 3️⃣ Run the Application
```sh
./gradlew bootRun
```

---

## 🔑 Authentication (JWT)
### Register
`POST /auth/register`
```json
{
  "fullName": "John Doe",
  "email": "john@example.com",
  "password": "secure123"
}
```

### Login
`POST /auth/login`
```json
{
  "email": "john@example.com",
  "password": "secure123"
}
```
Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsIn...",
  "user": {
    "id": 2,
    "fullName": "",
    "email": "",
    "role": "",
    "createdAt": ""
  }
}
```

### Get User Profile
`GET /library/users/profile` (Requires `Authorization: Bearer {token}`)

---

## 📚 Main Endpoints
### 📖 Books
- `GET /library/books` → Retrieve all books
- `POST /library/books` → Add a book (Admin)
- `GET /library/books/{id}` → Retrieve a book by ID

### 💵 Bills
- `POST /library/bills` → Create a bill
- `GET /library/bills/{id}` → View bill details

---

## 🖥️ Frontend (React)
For the frontend in **React + Vite**, clone the repository and follow these steps:
```sh
git clone https://github.com/Ne052003/Library-Frontend.git
cd library-frontend
npm install
npm run dev
```
---

## 🔧 Future Improvements
✅ Support for book cover images 📷  
✅ Purchase and loan history 📜  
✅ User notifications 📩

---

## 🏗 Contributions
If you want to contribute, create a **Pull Request** or open an **Issue** for improvements. 🚀

---

💡 _Developed by [Neoly Alexis Moreno](https://github.com/Ne052003)_
