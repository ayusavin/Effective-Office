# Testing Conventions

## Testing Strategy

### Backend Testing
- **Unit Tests**: Test business logic in isolation
- **Integration Tests**: Test database interactions and API endpoints
- **Repository Tests**: Verify data access layer functionality

### Client Testing
- **Unit Tests**: Test ViewModels and business logic
- **UI Tests**: Compose UI testing for critical user flows
- **Shared Module Tests**: Cross-platform logic validation

## Test Organization

### Backend Structure
```
src/
├── main/
│   └── kotlin/
└── test/
    └── kotlin/
```

### Naming Conventions
- Test classes: `[ClassName]Test`
- Integration tests: `[Feature]IntegrationTest`
- Test methods: Descriptive method names explaining the scenario

## Testing Tools

### Backend
- **JUnit 5**: Test framework
- **MockK**: Mocking library for Kotlin
- **Spring Test**: Integration testing support
- **Testcontainers**: Database testing with PostgreSQL

### Clients
- **JUnit**: Unit testing
- **Compose Testing**: UI component testing
- **Turbine**: Flow testing
- **MockK**: Mocking for Kotlin

## Best Practices

### Unit Tests
- Test one thing at a time
- Use descriptive test names
- Follow AAA pattern: Arrange, Act, Assert
- Mock external dependencies

### Integration Tests
- Use Testcontainers for database tests
- Clean up test data after each test
- Test realistic scenarios end-to-end
- Verify both success and failure cases

### Test Coverage
- Aim for high coverage of business logic
- Prioritize testing critical paths
- Don't test framework code
- Focus on behavior, not implementation

## Running Tests

### Backend
```bash
./gradlew test                    # Run all tests
./gradlew :backend:app:test       # Run app module tests
./gradlew :backend:feature:booking:test  # Run specific module
```

### Clients
```bash
./gradlew :clients:tablet:composeApp:test
./gradlew :clients:tv:composeApp:test
```

## Continuous Integration
- Tests run automatically on PR
- All tests must pass before merge
- Coverage reports generated per module
