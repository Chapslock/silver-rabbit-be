# Silver Rabbit backend

## First time project setup

### Project requirements

* Java 20
* Docker (if you need a personal database)

### Database

#### If you need to set up a database

* Make sure docker is installed
* Run docker
  command ``docker run --name postgresql -e POSTGRES_USER=postgresql -e POSTGRES_PASSWORD=postgresql -p 5432:5432 -v /data:/var/lib/postgresql/data -d postgres``
* Database configuration for the application can be found
  at [application.properties](src/main/resources/application.properties)

#### For existing databases

* Refer to the configuration file at [application.properties](src/main/resources/application.properties)

### Running the app

### `./gradlew bootRun`

Runs the app

### `./gradlew test`

Runs the tests

## README

### Hexagonal Architecture

The Hexagonal Architecture, also known as Ports and Adapters or Clean Architecture, is an architectural pattern that
aims to decouple the application's core business logic from external dependencies like databases, frameworks, and
external services. This separation enables us to write clean, maintainable, and testable code, fostering flexibility and
adaptability.

### Key Dependencies

- **Java 20**: The latest Java version, incorporating the newest language features to enhance development.
- **Spring Framework**: Provides powerful features for dependency injection, data access, transaction management, and
  more.
- **Hibernate**: Used as the Object-Relational Mapping (ORM) tool to interact with the underlying database.
- **PostgreSQL**: The primary production database, offering robustness and scalability.
- **H2 Database**: Utilized for testing purposes, ensuring isolated and fast tests without impacting the production
  database.
- **Liquibase**: Enables database schema version control and seamless migration management.
