# 🎬 KMDB - Kotlin Movie Database (Spring Boot REST API)

A full-featured RESTful API for managing a movie database, built using **Spring Boot**, **Spring Data JPA**, and **ModelMapper**. It supports full CRUD operations for Movies, Genres, and Actors, along with advanced filtering, searching, and relationship handling.

---

## 📚 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Project Structure](#project-structure)

---

## ✨ Features
- Manage Movies, Genres, and Actors via REST endpoints.
- Supports complex relationships:
- Many-to-Many: Movies ↔ Genres, Movies ↔ Actors
- Custom search endpoints (e.g., by title, genre, year).
- Pagination and filtering support.
- Exception handling with meaningful error messages.
- JSON-based request/response model.

---

## 💻 Tech Stack

- **Java 17+**
- **Spring Boot**
- **Spring Data JPA**
- **H2 / PostgreSQL / MySQL (Configurable)**
- **ModelMapper**
- **Lombok**
- **Maven**

---

## 🚀 Getting Started

### Prerequisites
- Java 17 or newer
- Maven
    💻 Installing Maven
    ✅ Windows
    Download from: https://maven.apache.org/download.cgi
    Unzip the archive to a folder (e.g., C:\apache-maven-3.9.6)
    Add Maven to your environment variables:
        MAVEN_HOME = C:\apache-maven-3.9.6
        Add C:\apache-maven-3.9.6\bin to the PATH variable.

    Restart terminal or computer.
    Test:
    mvn -v

    ✅ macOS
    Install with Homebrew:
    brew install maven
    Test:
    mvn -v

    ✅Linux (Debian/Ubuntu)
    sudo apt update
    sudo apt install maven
    Test:
    mvn -v

- SQLite (required as the database engine)
    ✅Windows:
        Download the SQLite tools ZIP (under Precompiled Binaries for Windows).
        Extract the files and add the folder to your system’s PATH variable.
        Open Command Prompt and run sqlite3 to verify the installation.

    ✅macOS:
    SQLite comes pre-installed on most macOS versions. To upgrade or reinstall:
    brew install sqlite

    ✅Linux (Debian/Ubuntu):
        sudo apt update
        sudo apt install sqlite3

    ✅Verify Installation (All OS):
        sqlite3 --version


### Installation

📦 Clone the project:
    git clone https://gitea.kood.tech/erikmartinmerisalu/kmdb.git
    cd kmdb
🛠 Build the project:
    mvn clean install

🚀 Run the application:
    mvn spring-boot:run

🌐Access the API
    Visit: http://localhost:8080/api

### 🛠️ Usage

Creating a Movie 🎬:
    POST /api/movies
    Request Body raw(JSON) example:

{
"title": "Inception",
"releaseYear": 2010,
"genreIds": [1, 2],
"actorIds": [5, 7]
}

Creating a Genre 🏷️ :
    POST /api/genres
    Request Body raw(JSON) example:

{
"name": "Sci-Fi"
}

Creating an Actor 🎭:
    POST /api/actors
    Request Body raw(JSON) example:

{
"name": "Leonardo DiCaprio"
}

### 📡 API Endpoints

## Postman Collection
A Postman collection for testing the Movie Database API is included in the `kmdb\src\main\java\merisalu\kmdb` folder.

To use it:
1. Open Postman.
2. Click on **Import**.
3. Select the `movie-database-api.postman_collection.json` file from the `kmdb\src\main\java\merisalu\kmdb` folder.
4. Start sending requests to test the API endpoints.

## 🎬 Movies

GET /api/movies?page=0&size=100
    Get all movies (Increase size=100 if more then 100 movie elements are present)

GET /api/movies?page=0&size=100&sort=releaseYear,asc
    Returns all movies in ascending order sorted by defined (Variable)
    (Increase size=100 if more then 100 movie elements are present up to 149)
    (Replace sort=(Variable),asc with sort=(Duration/Title/id/releaseYear),asc)
    Example: http://localhost:8080/api/movies?page=0&size=100&sort=releaseYear,asc

GET /api/movies/{id}
    Get a single movie by its ID

GET /api/movies?genre={genreId}
    Get all movies by a specific genre ID

GET /api/movies?year={year}
    Get all movies released in a specific year

GET /api/movies?actor={actorId}
    Get all movies featuring a specific actor by ID

GET /api/movies/{movieId}/actors
    Get all actors for a specific movie by movie ID

GET /api/movies/search?title={title}
    Search movies by title

POST /api/movies
    Create a new movie

PATCH /api/movies/{id}
    Update an existing movie

DELETE /api/movies/{id}?force=true
    Delete a movie (force optional)

## 🎭 Actors

GET /api/actors
    Get a list of all actors

GET /api/actors/{id}
    Get an actor by ID

GET /api/actors/search?name={name}
    Search actors by name (case-insensitive)

POST /api/actors
    Create a new actor

PATCH /api/actors/{id}
    Update an existing actor

PATCH /api/actors/batch
    Update or Delete multiple actors
    Example JSON file for body:
{
    "removeActorIds": [14, 15],
    "updateActors": [
        {
        "id": 13,
        "name": "Florence Pugh",
        "birthDate": "1996-01-04"
        },
        {
        "id": 7,
        "name": "Scarlett Johansson",
        "birthDate": "1984-11-22"
        }
    ]
}

DELETE /api/actors/{id}?force=true
    Delete an actor (force optional)

## 🏷️ Genres

GET /api/genres
    Get a list of all genres

GET /api/genres/{id}
    Get a genre by ID

GET /api/genres/{id}/movies
    Get all movies in a specific genre

POST /api/genres
    Create a new genre

PATCH /api/genres/{id}
    Update an existing genre

DELETE /api/genres/{id}?force=true
    Delete a genre (force optional)


📂 Project Structure
src/main/java/merisalu/kmdb/
│
├── config/            # Configuration classes (e.g., StartupCleanupRunner)
├── controller/        # REST controllers
├── dto/               # Data transfer objects
├── exception/         # Custom exceptions
├── mapper/            # Entity-DTO mappers
├── model/             # JPA entities
├── repository/        # Spring Data JPA repositories
├── service/           # Service interfaces
├── service/impl/      # Service implementations
└── KmdbApplication.java  # Main Spring Boot application