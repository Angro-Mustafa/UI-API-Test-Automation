SauceDemo & JSONPlaceholder Automation Suite

A comprehensive automation project for robust, repeatable UI and API testing—using Selenium and REST-assured with Java, TestNG, Maven, and GitHub Actions CI/CD. This project covers login, cart, and checkout flows (UI) and full CRUD endpoint coverage (API), with easy setup and maintainability.
Demo Systems

    UI: SauceDemo

    API: JSONPlaceholder

Test Credentials (SauceDemo UI)
Field	Value
Username	standard_user
Password	secret_sauce
Project Structure

text
/
├── src/
│   └── test/java/
│         ├── SauceDemoUITests.java
│         └── JsonPlaceholderApiTests.java
├── pom.xml
├── .github/workflows/ci.yml
└── README.md

Setup & Getting Started
1. Prerequisites

    Java 17+ (check version: java -version)

    Maven 3.6+ (check version: mvn -version)

    Firefox browser (for UI tests)

    Git (for cloning the repository)

    Internet connection

2. Clone the Repository

text
git clone <your-repo-url>
cd <project-root>

Replace <your-repo-url> with your GitHub repository link.
3. Install Dependencies

Maven will handle all dependency downloads automatically:

text
mvn install -DskipTests=true

Running the Tests
UI Tests (SauceDemo)

text
mvn test -Dtest=SauceDemoUITests

    Runs Selenium-driven tests for login, cart, and checkout using Firefox in headless mode.

    Test credentials are set as per the UI section above.

API Tests (JSONPlaceholder)

text
mvn test -Dtest=JsonPlaceholderApiTests

    Tests all major /posts endpoints with REST-assured, including positive/negative flows.

All Tests

text
mvn clean test

CI/CD: GitHub Actions Integration

    Pipeline file: .github/workflows/ci.yml

    Trigger: Every push or pull request to main

    What happens:

        Installs dependencies

        Runs UI and API tests

        Archives UI screenshots (if your test framework outputs them to target/screenshots/)

View runs and artifacts in your repository's “Actions” tab.
Test Coverage
Area	Scenario	Automation Tool
UI	Login (valid/invalid)	Selenium + TestNG
UI	Add item, remove item, checkout overview	Selenium + TestNG
API	GET, POST, PUT, DELETE on /posts	REST-assured
API	Negative test: GET /posts/999999	REST-assured
How to Change Browser or Add More Coverage

    To run tests on Chrome instead of Firefox, update WebDriver instantiation (see Selenium docs).

    To expand UI coverage, add new methods to SauceDemoUITests.java. For API, add more tests to JsonPlaceholderApiTests.java.

Troubleshooting

    Element not found: The demo UI may have changed—re-check selectors in the browser and update tests if needed.

    Browser driver issues: WebDriverManager will auto-download geckodriver for Firefox.

    Dependencies: If builds fail, check pom.xml and rerun mvn install.

    CI failures: View GitHub “Actions” logs for detailed error output and stack traces.

Limitations & Notes

    Public demos: Data resets regularly and test states may change between runs.

    JSONPlaceholder API does not persist data; POST/PUT/DELETE are mock operations.

    CI uses headless browser mode by default for speed and compatibility.
