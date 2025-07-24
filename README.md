text
# SauceDemo & JSONPlaceholder Automation Suite

Automated testing for [SauceDemo](https://www.saucedemo.com/) (UI) and [JSONPlaceholder](https://jsonplaceholder.typicode.com/) (API) using Java, Selenium, REST-assured, TestNG, and GitHub Actions.

## Prerequisites

- Java 17+
- Maven 3.6+
- Firefox browser (for UI tests)
- Git

## Structure

.
├── src/test/java/
│ ├── SauceDemoUITests.java
│ └── JsonPlaceholderApiTests.java
├── pom.xml
├── .github/workflows/ci.yml
└── README.md

text

## Running the Tests

### UI Tests (SauceDemo)

mvn test -Dtest=SauceDemoUITests

text

### API Tests (JSONPlaceholder)

mvn test -Dtest=JsonPlaceholderApiTests

text

### Run All Tests

mvn clean test

text

## CI/CD with GitHub Actions

- Workflow: `.github/workflows/ci.yml`
- Triggers on push/PR to `main`.
- Runs UI and API tests; archives UI screenshots.

## Test Coverage

- **UI:** Login (valid/invalid), add/remove item, checkout overview (SauceDemo)
- **API:** CRUD on `/posts` (JSONPlaceholder), positive/negative cases

---

Test credentials for SauceDemo:

- **Username:** standard_user
- **Password:** secret_sauce

---

**All code is ready for cloud and local execution.**