SauceDemo & JSONPlaceholder Automation Suite

Automated testing for SauceDemo (UI) and JSONPlaceholder (API) using Java, Selenium, REST-assured, TestNG, and GitHub Actions.

Prerequisites
•	Java 17+
Check with java -version
•	Maven 3.6+
Check with mvn --version
•	Firefox browser (for UI tests)
•	Git


Structure
.
├── src/test/java/
│     ├── SauceDemoUITests.java
│     └── JsonPlaceholderApiTests.java
├── pom.xml
├── .github/workflows/ci.yml
└── README.md

Setup Instructions
1. Clone the Repository
git clone https://github.com/Angro-Mustafa/UI-API-Test-Automation.git
cd UI-API-Test-Automation

2. Install Dependencies
Run the following in the project root to download all Maven dependencies:
mvn install -DskipTests=true

3. Verify Requirements
•	Ensure Java is available in your system path.
•	Ensure Maven is available in your system path.
•	Ensure Firefox is installed (the UI tests launch it in headless mode).
No additional WebDriver setup is required—WebDriverManager will automatically download and configure the required binaries at runtime.
4. Configuration
•	Test credentials for SauceDemo are already set in the UI test class:
o	Username: standard_user
o	Password: secret_sauce
•	URL endpoints for JSONPlaceholder are set in the API test class.
•	You can adjust browser options or base URLs inside the test classes if needed.
Running the Tests
UI Tests (SauceDemo)
mvn test -Dtest=SauceDemoUITests

API Tests (JSONPlaceholder)
mvn test -Dtest=JsonPlaceholderApiTests

Run All Tests
mvn clean test

CI/CD with GitHub Actions
•	Workflow file: .github/workflows/ci.yml
•	Triggers on all pushes and pull requests targeting main.
•	The CI pipeline will:
o	Install dependencies
o	Run all UI and API tests
o	Archive UI screenshots (from target/screenshots/, if produced)
You can monitor pipeline runs and test results in the repository's GitHub "Actions" tab.
Test Coverage
•	UI:
o	Login (valid & invalid)
o	Add/remove item to/from cart
o	Checkout overview (SauceDemo)
•	API:
o	CRUD on /posts endpoint
o	Positive & negative scenarios (e.g., non-existent posts)
Test Credentials for SauceDemo
•	Username: standard_user
•	Password: secret_sauce
Notes
•	All code is designed to work both locally and in cloud CI environments.
•	For troubleshooting, consult the test output and see screenshots or logs in target/ or from GitHub Actions artifacts.
•	If site selectors or endpoints change, update them in the test classes accordingly.
•	To switch UI tests to another browser, modify the WebDriver initialization in SauceDemoUITests.java and ensure the required browser is installed.
