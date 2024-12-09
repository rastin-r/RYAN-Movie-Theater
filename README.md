# **RYAN-Movie-Theater**

RYAN-Movie-Theater is a movie theater management system built in Java. It allows **Admins** to manage movies and bookings, while **Users** can view movies, book tickets with discounts, and view their reservations.

![Flowchart](https://github.com/user-attachments/assets/1288b896-dfa0-4d06-9367-d0462d35fb21)

---

## **Features**

### Admin
1. **Add New Movies**:
    - Admins can add new movies with details such as title, description, genre, cast, music, screenplay, duration, and showtimes.
2. **Remove Movies**:
    - Admins can remove movies by selecting their ID.
3. **View Bookings**:
    - Admins can view all existing reservations with details like user, movie, showtime, seat, and discount.
4. **Remove Bookings**:
    - Admins can delete bookings by selecting their reservation ID.
5. **View All Movies**:
    - Admins can view the list of movies currently in the system.

### User
1. **View Now Showing and Leaving Soon**:
    - Movies are categorized into "Now Showing" and "Leaving Soon".
2. **View Movie Details**:
    - Users can view full details of any selected movie, including showtimes.
3. **Book Tickets**:
    - Users can:
        - Select a **date** (at least 3 weeks in advance).
        - Choose an available **showtime**.
        - Select a **seat** (rows 1-9, columns A-Z).
        - Apply discounts (20% for Students with 12-digit ID, 30% for Retirees with 8-digit ID).
        - Generate a unique **Reservation Number** upon successful booking.
4. **View Reservations**:
    - Users can view their existing reservations with details like movie name, showtime, discount type, and reservation number.


![Use case diagram example-2](https://github.com/user-attachments/assets/fe5c87d6-fd40-424e-b1fd-27c574d81680)


---

## **Technologies Used**
- **Java** (Core Programming)
- **MySQL** (Database Management)
- **JUnit** (Testing Framework)
- **Mockito** (Unit Testing for Controllers)
- **Maven** (Build and Dependency Management)

---

## **Setup Instructions**

### Prerequisites
Ensure you have the following installed:
1. **Java Development Kit (JDK)** (version 17+)
2. **MySQL** (database server)
3. **Maven** (for build and dependency management)

---

### Steps to Set Up the Project

1. **Clone the Project**:
   ```bash
   git clone https://github.com/your-repo/RYAN-Movie-Theater.git
   cd RYAN-Movie-Theater
   ```

2. **Database Setup**:
    - Open MySQL and create the database:
      ```sql
      CREATE DATABASE sdm_db;
      USE sdm_db;
      ```
    - Execute the provided **SQL DDL and DML scripts** located in:
      ```
      src/main/resources/setup.sql
      ```
    - Ensure the MySQL connection settings in `DatabaseConnection.java` are correct:
      ```java
      private static final String URL = "jdbc:mysql://localhost:3306/sdm_db";
      private static final String USER = "your-username";
      private static final String PASSWORD = "your-password";
      ```

3. **Build the Project**:
   Use Maven to build the project:
   ```bash
   mvn clean install
   ```

4. **Run the Application**:
   Run the `Main` class:
   ```bash
   java -jar target/RYAN-Movie-Theater.jar
   ```

---

## **How to Use**

### 1. Admin Workflow
- Select **Admin** role and log in using:
  ```
  Username: RYAN-admin
  Password: RYAN-sdm24!
  ```
- Use the Admin menu to:
    - Add or remove movies.
    - View or remove bookings.
    - View all available movies.

### 2. User Workflow
- Register a new user account or log in.
- Use the User menu to:
    - View movies and details.
    - Book tickets by selecting date, showtime, and seat.
    - Apply discounts with valid IDs.
    - View existing reservations.
      
![Activity diagram](https://github.com/user-attachments/assets/f84d24ed-8f38-45dc-a07a-5860b9f43acc)

---

## **Database Tables**

![ERD (crow's foot)-2](https://github.com/user-attachments/assets/440dfada-e9dd-465e-96a2-e56cde60081f)


---

## **Testing**
To run unit tests:
```bash
mvn test
```

Test classes include:
- `AuthControllerTest`
- `AdminControllerTest` (needs to be set up)
- `ReservationControllerTest`
- `UserControllerTest` (needs to be set up)

---

## **Sample Data**
The system includes preloaded data:
- Admin account.
- Sample movies and showtimes.
- Example users and bookings.

---

## **Contributing**
Contributions are welcome! To contribute:
1. Fork the repository.
2. Create a new branch for your feature:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add feature XYZ"
   ```
4. Push your branch and open a Pull Request.

---

## **License**
This project is licensed under the MIT License. See the LICENSE file for details.

---

## **Contact**
For any questions or support:
- **Name**: RYAN-Movies Support Team
- **GitHub**: [github.com/your-repo](https://github.com/RYAN-Movie-Theater)

By following this guide, you will have the RYAN-Movie-Theater project up and running efficiently! 🎬
