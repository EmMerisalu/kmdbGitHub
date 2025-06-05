# 🎬 KMDB - Kotlin Movie Database (Spring Boot REST API)

A full-featured RESTful API for managing a movie database, built using **Spring Boot**, **Spring Data JPA**, and **ModelMapper**. It supports full CRUD operations for Movies, Genres, and Actors, along with advanced filtering, searching, and relationship handling.

---

## 📚 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Getting Started](#-getting-started)
- [Usage](#-usage)
- [API Endpoints](#-api-endpoints)
- [Project Structure](#-project-structure)

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

#### 💻 Installing Maven

✅ **Windows**  
- Download from: https://maven.apache.org/download.cgi  
- Unzip to e.g., `C:\apache-maven-3.9.6`  
- Add environment variables:  
  - `MAVEN_HOME = C:\apache-maven-3.9.6`  
  - Add `C:\apache-maven-3.9.6\bin` to the `PATH` variable  
- Restart terminal or computer  
- Test: `mvn -v`

✅ **macOS**  
- Install with Homebrew:  
  `brew install maven`  
- Test: `mvn -v`

✅ **Linux (Debian/Ubuntu)**  
```bash
sudo apt update
sudo apt install maven
mvn -v
```

- SQLite (required as the database engine)

✅ **Windows**  
- Download SQLite tools ZIP (Precompiled Binaries for Windows)  
- Extract and add to `PATH`  
- Test: `sqlite3`

✅ **macOS**  
- SQLite is preinstalled  
- To upgrade: `brew install sqlite`

✅ **Linux (Debian/Ubuntu)**  
```bash
sudo apt update
sudo apt install sqlite3
```

- ✅ Verify Installation (All OS):  
  `sqlite3 --version`

---

### Installation

📦 Clone the project:
```bash
git clone https://gitea.kood.tech/erikmartinmerisalu/kmdb.git
cd kmdb
```

🛠 Build the project:
```bash
mvn clean install
```

🚀 Run the application:
```bash
mvn spring-boot:run
```

🌐 Access the API:  
Visit: `http://localhost:8080/api`

---

## 🛠️ Usage

### Creating a Movie 🎬
**POST** `/api/movies`  
**Body (JSON):**
```json
{
  "title": "Inception",
  "releaseYear": 2010,
  "genreIds": [1, 2],
  "actorIds": [5, 7]
}
```

### Creating a Genre 🏷️
**POST** `/api/genres`  
**Body (JSON):**
```json
{
  "name": "Sci-Fi"
}
```

### Creating an Actor 🎭
**POST** `/api/actors`  
**Body (JSON):**
```json
{
  "name": "Leonardo DiCaprio"
}
```

---

## 📡 API Endpoints

### Postman Collection

A Postman collection is included at:  
`kmdb/src/main/java/merisalu/kmdb/movie-database-api.postman_collection.json`

To use:
1. Open Postman
2. Click **Import**
3. Select the file above
4. Test API endpoints

---

## 🎬 Movies

- `GET /api/movies?page=0&size=100`  
- `GET /api/movies?page=0&size=100&sort=releaseYear,asc`  
- `GET /api/movies/{id}`  
- `GET /api/movies?genre={genreId}`  
- `GET /api/movies?year={year}`  
- `GET /api/movies?actor={actorId}`  
- `GET /api/movies/{movieId}/actors`  
- `GET /api/movies/search?title={title}`  
- `POST /api/movies`  
- `PATCH /api/movies/{id}`  
- `DELETE /api/movies/{id}?force=true`

---

## 🎭 Actors

- `GET /api/actors`  
- `GET /api/actors/{id}`  
- `GET /api/actors/search?name={name}`  
- `POST /api/actors`  
- `PATCH /api/actors/{id}`  
- `PATCH /api/actors/batch`  
- `DELETE /api/actors/{id}?force=true`
  **Example Body (JSON):**
```json
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
```

---

## 🏷️ Genres

- `GET /api/genres`  
- `GET /api/genres/{id}`  
- `GET /api/genres/{id}/movies`  
- `POST /api/genres`  
- `PATCH /api/genres/{id}`  
- `DELETE /api/genres/{id}?force=true`

---

## 📂 Project Structure

```
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
```