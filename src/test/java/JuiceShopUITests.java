import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class JuiceShopUITests {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        // You may add other arguments like '--no-sandbox' if needed for CI
        driver = new FirefoxDriver(options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().window().maximize();
    }

    private void dismissPopups() {
    try {
        // Use your provided XPath for welcome dialog
        WebElement dismissBtn = new WebDriverWait(driver, Duration.ofSeconds(4))
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"mat-mdc-dialog-0\"]/div/div/app-welcome-banner/div[2]/button[2]/span[2]/span")));
        // Click the parent button
        dismissBtn.findElement(By.xpath("./ancestor::button")).click();
    } catch (Exception ignored) {}
    try {
        WebElement cookieBtn = new WebDriverWait(driver, Duration.ofSeconds(3))
            .until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.cc-btn.cc-dismiss")));
        cookieBtn.click();
    } catch (Exception ignored) {}
    try {
        new WebDriverWait(driver, Duration.ofSeconds(3))
            .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".mat-simple-snack-bar-content")));
    } catch (Exception ignored) {}
    }


    private void waitForSnackBarText(String expected) {
        try {
            WebElement snackBar = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".mat-simple-snack-bar-content")));
            Assert.assertTrue(snackBar.getText().toLowerCase().contains(expected.toLowerCase()));
        } catch (TimeoutException e) {
            Assert.fail("Snack bar with text '" + expected + "' not found in time.");
        }
    }

    private void clickWhenClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        try {
            driver.findElement(locator).click();
        } catch (ElementClickInterceptedException e) {
            dismissPopups();
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            driver.findElement(locator).click();
        }
    }

    @Test(priority = 1)
    public void loginWithValidCredentials() {
        driver.get("https://juice-shop.herokuapp.com/#/login");
        dismissPopups();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email']")));
        driver.findElement(By.xpath("//input[@type='email']")).clear();
        driver.findElement(By.xpath("//input[@type='email']")).sendKeys("test994@test.com");
        driver.findElement(By.xpath("//input[@type='password']")).clear();
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("222888Mmm!");
        clickWhenClickable(By.xpath("//button[@id='loginButton']"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navbarLogoutButton")));
        Assert.assertTrue(driver.findElement(By.id("navbarLogoutButton")).isDisplayed(), "Logout button not found after login.");
    }

    @Test(priority = 2)
    public void loginWithInvalidCredentials() {
        driver.get("https://juice-shop.herokuapp.com/#/login");
        dismissPopups();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email']")));
        driver.findElement(By.xpath("//input[@type='email']")).clear();
        driver.findElement(By.xpath("//input[@type='email']")).sendKeys("invalid@user.com");
        driver.findElement(By.xpath("//input[@type='password']")).clear();
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("WrongPass123!");
        clickWhenClickable(By.xpath("//button[@id='loginButton']"));
        waitForSnackBarText("invalid");
    }

    @Test(priority = 3)
    public void registerNewUser() {
        driver.get("https://juice-shop.herokuapp.com/#/register");
        dismissPopups();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("emailControl")));
        String unique = String.valueOf(System.currentTimeMillis());
        driver.findElement(By.id("emailControl")).sendKeys("auto" + unique + "@test.com");
        driver.findElement(By.id("passwordControl")).sendKeys("SomePass123!");
        driver.findElement(By.id("repeatPasswordControl")).sendKeys("SomePass123!");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("securityQuestion")));
        WebElement securityInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("securityAnswerControl")));
        securityInput.sendKeys("Automation");
        clickWhenClickable(By.id("registerButton"));
        waitForSnackBarText("registration completed");
    }

    @Test(priority = 4, dependsOnMethods = "loginWithValidCredentials")
    public void addItemToBasket() {
        driver.get("https://juice-shop.herokuapp.com/#/search");
        dismissPopups();
        By addBtnLocator = By.xpath("//button[@aria-label='Add to Basket']");
        wait.until(ExpectedConditions.elementToBeClickable(addBtnLocator));
        clickWhenClickable(addBtnLocator);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.fa-layers-counter")));
        int count = Integer.parseInt(driver.findElement(By.cssSelector("span.fa-layers-counter")).getText());
        Assert.assertTrue(count > 0, "Basket counter was not incremented.");
    }

    @Test(priority = 5, dependsOnMethods = "addItemToBasket")
    public void editBasketItemQuantity() {
        clickWhenClickable(By.id("navbarBasketButton"));
        By qtyLocator = By.xpath("//input[@name='quantity']");
        WebElement qtyInput = wait.until(ExpectedConditions.visibilityOfElementLocated(qtyLocator));
        int origQty = Integer.parseInt(qtyInput.getAttribute("value"));
        qtyInput.clear();
        qtyInput.sendKeys(String.valueOf(origQty + 1));
        clickWhenClickable(By.xpath("//button[@aria-label='update quantity']"));
        wait.until(ExpectedConditions.attributeToBe(qtyInput, "value", String.valueOf(origQty + 1)));
        Assert.assertEquals(qtyInput.getAttribute("value"), String.valueOf(origQty + 1));
    }

    @Test(priority = 6, dependsOnMethods = "editBasketItemQuantity")
    public void deleteItemFromBasket() {
        By removeBtnLocator = By.xpath("//button[starts-with(@aria-label, 'Remove')]");
        wait.until(ExpectedConditions.elementToBeClickable(removeBtnLocator));
        clickWhenClickable(removeBtnLocator);
        wait.until(ExpectedConditions.or(
            ExpectedConditions.invisibilityOfElementLocated(removeBtnLocator),
            ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("mat-card"), "Your basket is empty")
        ));
        boolean empty = driver.getPageSource().contains("Your basket is empty") ||
                        driver.findElements(By.cssSelector("mat-row")).size() == 0;
        Assert.assertTrue(empty, "Basket not empty after removal.");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
