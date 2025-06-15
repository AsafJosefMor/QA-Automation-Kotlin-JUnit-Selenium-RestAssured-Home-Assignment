# Aura Automation Suite

## Overview
This project contains UI and API automation tests using Kotlin, Selenium, and RestAssured, following best practices.

## Tech Stack
- **Language:** Kotlin 1.8+
- **Build Tool:** Maven 3.8+
- **Testing Framework:** JUnit 5
- **API Testing:** RestAssured 4.x
- **UI Testing:** Selenium WebDriver 4.x (ChromeDriver)
- **Configuration Management:** `test.properties`

## Prerequisites
1. **Java JDK 11+** installed and `JAVA_HOME` configured.
2. **Maven** 3.8 or higher on the `PATH`.
3. **Chrome** browser installed (Chromedriver managed automatically via WebDriverManager).

## Configuration
All external settings live in `src/test/resources/test.properties`. Common keys include:
```properties
base.url=http://host:3000
login.username=admin@example.com
login.password=password
timeout.seconds=10
polling.millis=500
```  
Adjust values before running tests.

## Project Structure
```
├── pom.xml                   # Maven settings and dependency versions
├── README.md                 # Project documentation (this file)
├── src
│   ├── main
│   │   └── kotlin/aura/automation
│   │       ├── api          # API client classes and routes
│   │       ├── models       # Data models (Post, PostStatus)
│   │       ├── pages        # Page Object classes for UI tests
│   │       └── utils        # Helpers (config loader, JSON paths, wait utils)
│   └── test
│       ├── kotlin/aura/automation/tests
│       │   ├── api          # API test scenarios
│       │   └── ui           # UI test scenarios
│       └── resources        # `test.properties` and test data
```

## How to Run
- **In IntelliJ**: Use gutter icons or Maven tool window to run tests.
- **Command line**: `mvn clean verify`

## Reporting
Test results are available under `target/surefire-reports` by default. To integrate advanced reports (e.g., Allure), add the corresponding Maven plugin and configuration.

## Out of Scope
- Parallel execution setup
- CI pipeline definitions
- External report integration
