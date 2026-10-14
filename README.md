# NASA Image Manager

A Java Swing desktop application developed as a university project. It imports NASA Image of the Day RSS entries and lets users browse and manage images, categories, and tags.

## Features

- User login and registration
- Administrator and user roles
- Importing images from NASA's RSS feed
- Image browsing and management
- Category and tag management
- XML export
- FlatLaf interface
- SQL Server persistence

## Technologies

- Java 25
- Swing and NetBeans GUI Builder
- Maven
- SQL Server and JDBC
- JAXB
- FlatLaf

## Project structure

- NASAImageProject/NASAImageManager — desktop application and GUI
- NASAImageProject/Dao — database access and models
- NASAImageProject/Utilities — shared utilities
- SQL — database initialization and cleanup scripts

## Setup

### 1. Database

Run SQL/init-database.sql in SQL Server Management Studio.
The script creates the NasaImage database, tables, stored procedures,
and a local demo administrator.

SQL Server must accept SQL authentication and be accessible through TCP/IP.

### 2. Local configuration

In NASAImageProject/Dao/src/main/resources/config/:

1. Copy db.example.properties to db.properties.
2. Enter your SQL Server name, database name, username, and password.

db.properties is excluded from Git because it contains local credentials.

### 3. Build and run

1. Install JDK 25 and configure NetBeans to use it.
2. Open NASAImageProject as a Maven project in NetBeans.
3. Clean and Build the parent project.
4. Run the NASAImageManager module.

An internet connection is required to import NASA RSS entries and images.

## Demo login

- Username: admin
- Password: admin

These credentials are for the local demonstration database only.

## Notes

- NetBeans .form files are included so the interface can be edited
  using the GUI Builder.
- Passwords currently use unsalted SHA-256 hashing. This is an
  educational implementation and should be upgraded before production use.
- SQL/clear-data.sql deletes application data. Use it only when
  you intend to reset your local demo database.

## Author

Vito Zupanovic