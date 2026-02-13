# Development Conventions

## Git Workflow

### Branching Strategy
- Documented in `docs/GIT_FLOW.md`
- Main branch: `main`
- Feature branches: created as needed
- Git hooks: Pre-commit checks via `scripts/git-hooks/pre-commit.sh`

### Commit Standards
- Conventional commits encouraged
- Use Gitleaks to prevent secret commits
- Run `./scripts/install.sh` to set up hooks

## Code Organization

### Backend
- **Module per feature**: Each feature gets its own module under `backend/feature/`
- **Clean architecture**: Separate domain, data, and repository layers in `core/`
- **README per module**: Each module should have a README.md explaining its purpose

### Client Apps
- **Feature modules**: UI and logic organized by feature
- **Core modules**: Shared components in `core/{data,domain,ui}`
- **Shared code**: Common logic in `clients/shared/`

## Build Configuration

### Gradle
- Use Kotlin DSL (`.gradle.kts`)
- Convention plugins in `build-logic/` for shared configuration
- Version catalog in `gradle/libs.versions.toml`

### Dependencies
- Centralize versions in `libs.versions.toml`
- Apply conventions via custom plugins
- Keep dependency versions up-to-date

## Code Style

### Kotlin
- Target: Kotlin 2.1.21 language features
- JDK compatibility: JDK 17
- Prefer immutability and functional patterns
- Use data classes for DTOs and models

### Spring Boot
- Use Spring annotations for DI
- Controller → Service → Repository layering
- DTO classes for API contracts

## Documentation

### README Files
- Root README: Project overview and quick start
- Module READMEs: Feature-specific documentation
- Technical docs: In `docs/` directory

### Code Comments
- Document public APIs
- Explain "why" not "what"
- Keep comments up-to-date

## Environment Setup

### Prerequisites
- Git
- Docker and Docker Compose
- JDK 17 or higher
- Gitleaks (for development)

### Local Development
1. Clone repository
2. Run `./scripts/install.sh` to set up Git hooks
3. Configure environment variables
4. Use Docker Compose for backend services
