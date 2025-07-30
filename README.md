# Task Manager Backend
This is the backend for a lightweight Task Manager application.

## Tech Stack
- Java 17
- Spring Boot
- PostgreSQL
- Spring Data JPA

## Features
- Add, view, update, and delete tasks
- Assign categories like Work, Personal, etc.
- Set deadlines and statuses
- Filter tasks by:
  - Category
  - Deadline
  - Status
- Search tasks by title

## Project Structure (Spring Boot MVC)
- `Controller`: Handles HTTP requests (`TaskController.java`)
- `Service`: Contains business logic (`TaskService.java`)
- `Repository`: Interacts with PostgreSQL using Spring Data JPA (`TaskRepository.java`)
- `Model`: Represents the Task entity (`Task.java`)

## CORS
CORS is enabled via a configuration file to allow interaction with the frontend React app.

