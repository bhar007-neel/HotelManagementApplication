# 🏨 Hotel Management Web Application

A **Java-based full-stack hotel management system** that simplifies operations like managing hotels, rooms, and employees — all through a secure admin portal.

> ✅ Built with **Java Servlets**, **JSP**, **PostgreSQL**, and **JDBC**  
> 📂 Follows **MVC architecture** and uses **DAO design pattern**  
> 🎥 [Watch Demo Video on YouTube](https://youtu.be/rybHyttDmk4)

---

## 🎥 Live Demo

[![Hotel Management System Demo](https://img.youtube.com/vi/rybHyttDmk4/0.jpg)](https://youtu.be/rybHyttDmk4)

> 📺 Click the image to watch a full walkthrough of the app in action.

---

## 🚀 Features

🔐 **Admin Authentication**  
Only verified admins can access management panels.

🏨 **Hotel Management**  
Add, update, and delete hotel records, including manager assignments.

🛏️ **Room Management**  
Manage rooms with respect to their hotels. Ensures referential integrity using foreign keys.

👷 **Employee Management (by SSN)**  
Search for employees by SSN and perform all CRUD operations.

🧠 **Data Access Object (DAO) Layer**  
Clean, reusable backend logic to handle database interaction.

🗃️ **PostgreSQL Integration**  
Database-driven architecture with proper normalization and relationships.

---

## 🧰 Technologies Used

| Layer           | Tools/Technologies                        |
|----------------|--------------------------------------------|
| Frontend        | JSP, HTML, CSS                            |
| Backend         | Java, Servlets                            |
| Database        | PostgreSQL                                |
| Server          | Apache Tomcat                             |
| Design Pattern  | DAO (Data Access Object)                  |
| Architecture    | MVC-inspired structure                    |

---

## 📁 Project Structure


e-Hotels/
├── src/
│ ├── dao/
│ ├── model/
│ ├── servlet/
├── web/
│ ├── jsp/
│ ├── css/
│ └── login.jsp
├── sql/
│ └── schema.sql
├── package.json
└── README.md


---

## 🧠 What I Learned

✅ Real-world web architecture using Java  
✅ How to use JDBC to interact with PostgreSQL  
✅ Structuring web apps using MVC and DAO patterns  
✅ Ensuring data consistency through foreign key constraints  
✅ Building full CRUD features with form validation

---


## 🔧 Setup Instructions

1. Clone the project:
   ```bash
   git clone https://github.com/your-username/hotel-management-app.git
   cd hotel-management-app
Import the project into your IDE (e.g., IntelliJ, Eclipse).

Set up PostgreSQL:

Create DB and tables using schema.sql.

Configure database credentials in your DAO files.

Deploy to Apache Tomcat.



