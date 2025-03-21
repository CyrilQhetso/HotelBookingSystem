# Java Hotel Booking System  

## 📌 Project Description  
The Java Hotel Booking System is a GUI-based application that allows customers to book and cancel hotel rooms. It supports different room types with unique pricing, maintains a log of bookings and cancellations, and persists data using local file storage.  

## 🚀 Features  
- Book and cancel hotel rooms  
- View available rooms before booking  
- Different pricing for different room types  
- Booking log showing booking and cancellation times  
- Persistent storage using local files  

## 🛠 Technologies Used  
- **Java** (OOP, File I/O, Swing for GUI)  
- **Swing** (Graphical User Interface)  

## 📂 Project Structure  

HotelBookingSystem/
│── src/
│   ├── model/
│   │   ├── Hotel.java
│   │   ├── Room.java
│   │   ├── StandardRoom.java
│   │   ├── DeluxeRoom.java
│   │   ├── Booking.java
│   │   ├── Customer.java
│   │   ├── BookingStorage.java
│   ├── gui/
│   │   ├── HotelBookingGUI.java
|   ├── main/
|   |   ├── Main.java
│── README.md
│── bookings.txt
│── .gitignore

## ⚙️ How to Run the Project  

### Prerequisites  
- Install **Java 17+**  
- Install **JDK (Java Development Kit)**  

### Steps to Run  
1. **Clone the repository**  
   ```sh
   git clone https://github.com/CyrilQhetso/HotelBookingSystem
   cd HotelBookingSystem

Compile project :
javac -d bin src/model/*.java src/ui/*.java

run project :
java -cp bin main.Main
