# Calculator REST API — CI/CD Pipeline

An end-to-end CI/CD project that automates the build, testing, containerization, and deployment of a Calculator REST API using Java, Spring Boot, Jenkins, Docker, and Docker Compose.

## Tech Stack

- **Backend:** Java 21, Spring Boot, Maven
- **Testing:** JUnit 5, Spring Boot Test, MockMvc
- **CI/CD:** Jenkins
- **Version Control:** Git, GitHub
- **Containerization:** Docker, Multi-stage Dockerfile
- **Deployment:** Docker Compose, Nginx
- **Container Registry:** Docker Hub

## Features

- REST API supporting addition, subtraction, multiplication, and division.
- Input validation and structured error responses.
- Division-by-zero handling with HTTP 400.
- Spring Boot Actuator health endpoint.
- Automated build and testing through Jenkins.
- Docker image creation and publishing to Docker Hub.
- Automated deployment using Docker Compose.
- Nginx reverse proxy for API access.
- Jenkins email notifications for successful and failed builds.

## Project Structure

```text
calculator/
├── src/
│   ├── main/
│   │   └── java/
│   └── test/
│       └── java/
├── nginx/
│   └── default.conf
├── Dockerfile
├── docker-compose.yml
├── Jenkinsfile
├── pom.xml
├── .dockerignore
└── .gitignore
```

## Running Locally

### Prerequisites

- Java 21 or later
- Maven
- Docker
- Docker Compose

### 1. Clone the repository

```bash
git clone https://github.com/Arshbir1/calculator.git
cd calculator
```

### 2. Build the application

```bash
mvn clean package
```

### 3. Run tests

```bash
mvn test
```

The test suite includes unit tests for all calculator operations and integration tests using MockMvc to verify API responses and error handling.

### 4. Run the application

```bash
mvn spring-boot:run
```

The application runs on port `8080`.

## Docker Compose Deployment

Build and start the complete application stack:

```bash
docker-compose up -d --build
```

Alternatively, use `docker compose` if you have the Docker Compose plugin installed.

The deployment consists of:

- **Calculator:** Spring Boot application running internally on port 8080.
- **Nginx:** Reverse proxy exposing the API on port 8083.

Access the API at:

`http://localhost:8083`

Check running containers:

```bash
docker-compose ps
```

Stop the deployment:

```bash
docker-compose down
```

## API Endpoints

All endpoints use HTTP GET requests with query parameters `a` and `b`.

| Operation | Endpoint |
|---|---|
| Addition | `/api/v1/add?a=10&b=5` |
| Subtraction | `/api/v1/subtract?a=10&b=5` |
| Multiplication | `/api/v1/multiply?a=10&b=5` |
| Division | `/api/v1/divide?a=10&b=5` |
| Health Check | `/actuator/health` |

### Example Requests

```bash
curl "http://localhost:8083/api/v1/add?a=10&b=5"

curl "http://localhost:8083/api/v1/subtract?a=10&b=5"

curl "http://localhost:8083/api/v1/multiply?a=10&b=5"

curl "http://localhost:8083/api/v1/divide?a=10&b=5"
```

Division by zero returns HTTP 400 with a structured error response.

```json
{
  "error": "Division by zero is not allowed"
}
```

Check application health:

```bash
curl http://localhost:8083/actuator/health
```

## CI/CD Pipeline

The Jenkins pipeline automates the following stages:

1. **Checkout:** Retrieves the latest source code from GitHub.
2. **Build:** Compiles and packages the application using Maven.
3. **Test:** Executes JUnit tests and publishes test reports.
4. **Docker Build:** Builds a multi-stage Docker image with build-specific and latest tags.
5. **Docker Push:** Publishes the image to Docker Hub using Jenkins-managed credentials.
6. **Deploy:** Deploys the application using Docker Compose and verifies application health.

The pipeline uses Poll SCM to detect repository changes and trigger builds automatically. Jenkins also sends email notifications after successful or failed builds.

## Docker Hub

Docker images are published to:

https://hub.docker.com/r/snowden69/calculator

Available tags include `latest` and build-specific image tags.

## Author

**Arshbir Singh Dang**

GitHub: https://github.com/Arshbir1/calculator
