# Campus Event Manager

## Overview
Campus event manager is a JavaFX application built using Maven as the build management tool. It allows teachers to publish events for students to attend to.

To create events, you must have a teacher account. You can create a teacher account by checking the "is teacher" box in the registration screen.

## Class diagram
![class diagram](https://github.com/Juho-source/refactoredeventmanager/blob/main/docs/classdiagram.png?raw=true)

## Prerequisites
Before running the application, ensure you have the following installed:
- Java 21
- Apache Maven
- MariaDB

## Setup & Installation
1. Clone the repository:
   ```sh
   git clone https://github.com/Juho-source/refactoredeventmanager.git
   cd refactoredeventmanager
   ```

2. Switch to the correct branch:
   ```sh
   git checkout demo2
   ```
3. Set up the database:
   - Ensure MySQL is running
   - Create a new database
   - Import the SQL dump file:
     ```sh
     mysql -u root -p eventmanagement < eventmanagementsql.sql
     ```
4. Build the project using Maven:
   ```sh
   mvn clean install
   ```

5. Run the application:
   ```sh
   mvn javafx:run
   ```

## Packaging
To create an executable JAR:
```sh
mvn clean package
```
The JAR will be available in the `target/` directory.
