# 🏨 Hotel Management System - README

## 📖 Overview
The **Hotel Management System** is a **Java-based desktop application** built to streamline hotel operations, such as customer check-ins, room booking, employee management, and more. It features a user-friendly interface designed for both receptionists and administrators to manage daily tasks efficiently.

---

## ✨ Features

### 🔐 1. User Authentication
- Secure login system for hotel staff
- Role-based access for Admins and Receptionists

### 👤 2. Customer Management
- Add, view, and manage customer details
- Check-in and check-out operations
- Room allocation and deposit tracking

### 🏨 3. Room Management
- Add, update, and view room information
- Track room availability and cleaning status
- Room search by type, bed, and price
- Manage pricing and room categories

### 👨‍💼 4. Employee Management
- Add and manage employees
- View employee details department-wise
- Manager-specific access control

### 🚗 5. Driver & Pickup Services
- Maintain driver details and availability
- Car details management
- Schedule and manage pickup services

### 🛠️ 6. Administrative Functions
- Manage departments and roles
- Track deposits and pending amounts
- Generate system-wide reports

---

## 🖥️ Technical Specifications

### 💻 System Requirements
- Java Runtime Environment (JRE) 8 or later
- MySQL Server
- Minimum 2GB RAM
- 500MB available disk space

### 🗃️ Database Configuration
- **Database name**: `hotelManagementSystem`
- **Default credentials**:
  - Username: `root`
  - Password: `Ronal@2626` *(change in production)*

### 📚 Libraries Used
- **JDBC** for database connectivity
- **RS2XML (DbUtils)** for ResultSet to TableModel conversion
- **Java Swing** for the graphical user interface (GUI)

---

## 🔧 Installation Guide

### ✅ Prerequisites
- Install **Java Development Kit (JDK)** 8 or later
- Install **MySQL Server**
- Set up **MySQL Connector/J**

### 🛠️ Database Setup
1. Create a MySQL database named `hotelManagementSystem`
2. Execute the provided `.sql` scripts to generate necessary tables and sample data

### 🚀 Application Setup
1. Clone or download the project
2. Import it into your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse, NetBeans)
3. Update credentials in `Conn.java` if required
4. Build and run `HotelManagementSystem.java`

---

## 🧭 Usage Instructions

### ▶️ Starting the Application
- Run `HotelManagementSystem.java`
- Login using the provided admin or receptionist credentials

### 🧰 Main Functionalities
- **Reception Desk**: Customer check-in/check-out, room assignment
- **Admin Panel**: Employee and department management
- **Reports**: Generate views for customer, employee, and room info

### 🔄 Navigation
- Use the menu bar for switching modules
- Buttons are labeled for intuitive usage
- Use “Back” to return to the previous screen

---
