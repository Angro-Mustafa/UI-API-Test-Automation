# Juice Shop Automation Suite

A full-stack test automation project for [OWASP Juice Shop](https://juice-shop.herokuapp.com) using Selenium (UI), REST-assured (API), Java, TestNG, and GitHub Actions CI/CD. This setup covers all required functional flows and is quick to start, maintain, and extend.

## Demo Site

- **Web App:** https://juice-shop.herokuapp.com  
- **Sample Test Credentials:**  
  - Email: `test994@test.com`  
  - Password: `222888Mmm!`  
  *(You can register your own test user if needed; data may reset periodically.)*

## Prerequisites

- Java 17+ (or edit Java version in `pom.xml`)
- Maven 3.6+
- Chrome browser (latest)
- Git (for code checkout)
- No need to manage browser drivers manually (WebDriverManager will handle it)

## Project Structure

/
├── src/
│ └── test/java/
│ ├── JuiceShopUITests.java # Selenium UI tests
│ └── JuiceShopApiTests.java # REST-assured API tests
├── pom.xml # Maven configuration
├── .github/workflows/ci.yml # GitHub Actions workflow (CI/CD)
├── README.md # This file
└── target/ # Build, test, and artifacts output

## How To Run The Tests

### UI Tests (Selenium)

mvn test -Dtest=JuiceShopUITests

### API Tests (REST-assured)

mvn test -Dtest=JuiceShopApiTests

### To Run All Tests

mvn clean test

## Continuous Integration (CI) with GitHub Actions

- CI pipeline is set up in `.github/workflows/ci.yml`
- On every push or pull request:
  - Installs all dependencies
  - Runs both UI and API test suites
  - Collects UI screenshots (on failure)
  - Generates JaCoCo code coverage for API
  - Uploads all test artifacts for review

## Test Coverage & Scenarios

**UI Automation (Selenium)**  
- Login with valid/invalid credentials
- Register a user
- Add product to basket
- Change basket item quantity
- Remove item from basket
- Assert data after every action

**API Automation (REST-assured)**  
- `POST /rest/user/login` (valid/invalid)
- `GET /api/Products`
- `POST /api/BasketItems` (valid/invalid)
- `PUT /api/BasketItems/:id` (valid/invalid)
- `DELETE /api/BasketItems/:id` (valid/invalid)

## Customization & Usage Notes

- **Test Data:**  
  The Juice Shop demo resets frequently, and many users may alter data. Always use throwaway test accounts.

- **Drivers:**  
  Chrome WebDriver is managed by WebDriverManager—no manual setup.

- **Java Version:**  
  Change `pom.xml` `<maven.compiler.source/target/release>` if using a different JDK version.

- **Credentials:**  
  Update test credentials in code if current default expires.

- **Troubleshooting:**  
  Use browser DevTools to inspect UI selectors and API endpoints if tests break after a Juice Shop UI update.

- **Extending Tests:**  
  Add more tests for areas like user profile, ordering, or feedback as needed.

## Quick Start Table

| Step      | Command                                          |
|-----------|--------------------------------------------------|
| Clone     | `git clone https://github.com/your-username/juice-shop-automation.git` |
| Build     | `mvn install`                                    |
| UI Tests  | `mvn test -Dtest=JuiceShopUITests`               |
| API Tests | `mvn test -Dtest=JuiceShopApiTests`              |
| All Tests | `mvn clean test`                                 |

## Additional Resources

- **OWASP Juice Shop:** https://juice-shop.herokuapp.com
- **Selenium WebDriver:** https://www.selenium.dev/
- **REST-assured:** https://rest-assured.io/
- **TestNG:** https://testng.org/doc/
- **GitHub Actions:** https://docs.github.com/en/actions


