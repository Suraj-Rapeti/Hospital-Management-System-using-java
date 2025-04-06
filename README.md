# 🏥 Hospital Management System - Java Case Study

This is a **Java-based console application** designed as a case study project to simulate a simple Hospital Management System. It enables **doctors** to manage patient appointments and records while allowing **patients** to book appointments, manage bills, and view their medical history.

## 📌 Features

### 👨‍⚕️ For Doctors:
- Secure login with username and password
- View patient appointments
- Change patient status (e.g., Safe, Emergency)
- Postpone or cancel appointments
- Manage multiple patients with unique IDs

### 👩‍⚕️ For Patients:
- View and manage appointments with available doctors
- Book, postpone, or cancel appointments
- View and pay bills
- View personal health/medical records

## 💡 Concepts Demonstrated
- **Object-Oriented Programming**  
  - Inheritance (`Person` → `Doctor`, `Patient`)  
  - Polymorphism (`viewRecord()` overridden in `Doctor` and `Patient`)  
  - Encapsulation and abstraction
- **Interfaces**  
  - `Appointmentable` interface for appointment management
- **Exception Handling**  
  - Handling invalid actions like duplicate bookings, invalid patient IDs, etc.
- **Collections**  
  - Usage of `ArrayList` and `HashMap` to manage appointments and medical records

## ⚙️ Technologies Used
- Java (Standard Edition)
- Java Collections Framework
- Console-based I/O

## Structure
- `Person.java` – Base class for shared attributes
- `Doctor.java` – Manages appointments and patient status
- `Patient.java` – Manages health records and billing
- `HospitalManagementSystem.java` – Main driver class with menu-based interaction

