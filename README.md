# React.js + Spring Dockerized App

This is a simple template for a full-stack web application using React.js for the frontend, Spring Boot for the backend, and Docker for containerization.

## Prerequisites

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

## Getting Started

### Build and Run the Docker Containers for all application

```bash
docker-compose up --build
```

This command will build the Docker images for the React.js frontend and Spring Boot backend, and then start the containers.

Open your browser and go to http://localhost:3000 to access the React.js frontend, and http://localhost:8080 for the Spring Boot backend.

```bash
docker-compose down
```
This command will stop and remove the running Docker containers.

Project Structure

``
client/: React.js frontend code
``

``
server/: Spring Boot backend code
``

``
docker-compose.yml: Docker Compose configuration for running both frontend and backend containers
``

```
The app is 100% launchable)
```
