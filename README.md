# Course Outline Management

## Introduction

This repository is a course outline management system designed to streamline the administrative and educational processes for schools. The application includes features for both administrators and clients, ensuring efficient and effective management of course outlines.

1. [Introduction](#introduction)
2. [Features](#features)
3. [Technologies Used](#technologies-used)
4. [Setup](#setup)
5. [About Client Repository](#about-client-repository)

## Features

### Admin Features

- User Management: Manage users including students, teachers, and admins.
- Course Management: Oversee courses, majors, and faculties.
- Education Program Management: Manage educational programs and assign courses.
- Syllabus Assignment: Assign course outline creation tasks.
- Batch Cloning: Clone course outlines from previous years in bulk.
- Syllabus Reuse: Reuse course outlines across multiple years.

### Client Features
- Teachers: View and edit assigned course outlines.
- Students: Search, view educational programs and detailed course outlines.

## Technologies Used

### Languages

- Java
- JavaScript
- SQL

### Frameworks

- Spring MVC
- ReactJs (Client side - [Another repository](#about-client-repository))

## Setup

1. Clone the repository:
   ```
   git clone https://github.com/ThDat7/Course-Outline-Management.git
   ```
2. Navigate to the project directory:
   ```
   cd quanlydecuong
   ```
3. Set up the database using the provided SQL scripts:
   Execute `DropSchemaScript.sql` to drop and create the schema.
4. Build the project using Maven:
   ```
   mvn clean install
   ```
5. Run the application:
   ```
   mvn spring-boot:run
   ```
6. Go to the generate data endpoint to fake data:
   `localhost:8080/QuanLyDeCuong/data`

## About Client Repository

[About that repository](https://github.com/ThDat7/quanlydecuong_frontend)
