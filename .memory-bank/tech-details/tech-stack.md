# Technology Stack

## Backend

### Core
- **Language**: Kotlin 2.1.21
- **Framework**: Spring Boot 3.5.0
- **Build Tool**: Gradle 8.x with Kotlin DSL
- **Java Version**: JDK 17

### Database
- **RDBMS**: PostgreSQL 42.7.6
- **Migration**: Flyway 11.8.2
- **Connection Pool**: HikariCP 6.3.0
- **ORM**: Spring Data JPA

### Security
- **Framework**: Spring Security
- **Authentication**: JWT (JSON Web Token) 0.11.5

## Clients

### Shared
- **Platform**: Kotlin Multiplatform
- **Target**: Shared business logic and data models

### Mobile & Tablet Apps
- **UI Framework**: Jetpack Compose
- **Platform**: Android

### iOS App
- **Language**: Swift
- **Platform**: iOS Native

## Development Tools

### Version Control
- Git with Git hooks (pre-commit)
- Gitleaks for secret detection

### Containerization
- Docker
- Docker Compose

### Build System
- Gradle with Convention Plugins (build-logic module)

## Infrastructure

### Deployment
- Development environment: `deploy/dev/`
- Production environment: `deploy/prod/`
- Docker-based deployment

### CI/CD
- Configuration in `.github/` directory
