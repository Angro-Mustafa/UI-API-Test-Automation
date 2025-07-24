<<<<<<< HEAD
# juice-shop-automation
=======
# Juice Shop Automation Suite

**Demo app:** https://juice-shop.herokuapp.com  
**Credentials:** test994@test.com / 222888Mmm!

## Quick Start

### Run UI Tests (Selenium)
1. Clone repo, ensure Java/Maven, ChromeDriver.
2. Edit test credentials if needed.
3. Run: `mvn test -Dtest=JuiceShopUITests`

### Run API Tests (REST-assured)
1. Run: `mvn test -Dtest=JuiceShopApiTests`

### CI Integration
- Tests run on push via GitHub Actions.
- Screenshots and reports uploaded as artifacts.

### Notes
- Use only throwaway/test credentials.
- Inspect API and UI elements if selectors break.
- Data volatility: public instance, may require fresh user/account each run.
>>>>>>> b3e14ac (Initial automation solution for Juice Shop)
