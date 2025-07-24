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



## Setup Instructions

### 1. Clone the Repository

- git clone https://github.com/Angro-Mustafa/UI-API-Test-Automation.git
- cd UI-API-Test-Automation


### 2. Install Dependencies

mvn install -DskipTests=true



### 3. Verify Requirements

- Ensure Java, Maven, and Firefox are correctly installed and in your system path.

### 4. Configuration

- Test credentials for SauceDemo are set in the test class:
    - **Username:** `standard_user`
    - **Password:** `secret_sauce`
- JSONPlaceholder API URLs are set in the API test class.

## Running the Tests

### UI Tests (SauceDemo)

mvn test -Dtest=SauceDemoUITests



### API Tests (JSONPlaceholder)

mvn test -Dtest=JsonPlaceholderApiTests



### Run All Tests

mvn clean test



## CI/CD with GitHub Actions

- Workflow file: `.github/workflows/ci.yml`
- Triggers on push or pull request to `main`
- Pipeline does:
    - Installs dependencies
    - Runs UI and API tests
    - Archives UI screenshots (if produced to `target/screenshots/`)

## Test Coverage

|       Area      |                Scenario                  |   Tool/Framework    |
|:---------------:|:----------------------------------------:|:-------------------:|
| UI (SauceDemo)  | Login (valid/invalid), add/remove item, checkout overview | Selenium + TestNG   |
| API (JSONPlaceholder) | GET/POST/PUT/DELETE posts, negative cases   | REST-assured + TestNG |

## Test Credentials for SauceDemo

- **Username:** `standard_user`
- **Password:** `secret_sauce`

## Notes

- All tests are designed for local and CI use.
- Update selectors or endpoints in test classes if test failures result from demo site changes.
- Screenshots and logs are stored under `target/` or available as CI artifacts.
- Browser WebDriver binaries are managed automatically via WebDriverManager.

**Quick start, robust automation, and CI ready — out of the box!**
