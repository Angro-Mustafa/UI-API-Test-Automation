import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration; // ✅ Import for Duration

public class JuiceShopUITests {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // ✅ Fixed for Selenium 4+
        driver.manage().window().maximize();
        driver.get("https://juice-shop.herokuapp.com/#/login");
        dismissPopups();
    }

    private void dismissPopups() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.close-dialog"))).click();
        } catch (Exception ignored) {}
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".mat-simple-snack-bar-content")));
        } catch (Exception ignored) {}
    }

    @Test(priority = 1)
    public void loginWithValidCredentials() {
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("test994@test.com");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("222888Mmm!");
        driver.findElement(By.id("loginButton")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navbarLogoutButton")));
        Assert.assertTrue(driver.findElement(By.id("navbarLogoutButton")).isDisplayed());
    }

    @Test(priority = 2)
    public void loginWithInvalidCredentials() {
        driver.get("https://juice-shop.herokuapp.com/#/login");
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("invalid@user.com");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("WrongPass123!");
        driver.findElement(By.id("loginButton")).click();
        WebElement snackBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mat-simple-snack-bar-content")));
        Assert.assertTrue(snackBar.getText().toLowerCase().contains("invalid"));
    }

    @Test(priority = 3)
    public void registerNewUser() {
        driver.get("https://juice-shop.herokuapp.com/#/register");
        String unique = String.valueOf(System.currentTimeMillis());
        driver.findElement(By.id("emailControl")).sendKeys("auto" + unique + "@test.com");
        driver.findElement(By.id("passwordControl")).sendKeys("SomePass123!");
        driver.findElement(By.id("repeatPasswordControl")).sendKeys("SomePass123!");
        driver.findElement(By.name("securityAnswer")).sendKeys("Automation");
        driver.findElement(By.id("registerButton")).click();
        WebElement snackBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mat-simple-snack-bar-content")));
        Assert.assertTrue(snackBar.getText().toLowerCase().contains("registration completed"));
    }

    @Test(priority = 4, dependsOnMethods = "loginWithValidCredentials")
    public void addItemToBasket() {
        driver.get("https://juice-shop.herokuapp.com/#/search");
        WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[aria-label='Add to Basket']")));
        addBtn.click();
        WebElement basketCounter = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.fa-layers-counter")));
        Assert.assertTrue(Integer.parseInt(basketCounter.getText()) > 0);
    }

    @Test(priority = 5, dependsOnMethods = "addItemToBasket")
    public void editBasketItemQuantity() {
        driver.findElement(By.id("navbarBasketButton")).click();
        WebElement qtyInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='quantity']")));
        int origQty = Integer.parseInt(qtyInput.getAttribute("value"));
        qtyInput.clear();
        qtyInput.sendKeys(String.valueOf(origQty + 1));
        driver.findElement(By.cssSelector("button[aria-label='update quantity']")).click();
        wait.until(ExpectedConditions.attributeToBe(qtyInput, "value", String.valueOf(origQty + 1)));
        Assert.assertEquals(qtyInput.getAttribute("value"), String.valueOf(origQty + 1));
    }

    @Test(priority = 6, dependsOnMethods = "editBasketItemQuantity")
    public void deleteItemFromBasket() {
        WebElement removeBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[aria-label^='Remove']")));
        removeBtn.click();
        wait.until(ExpectedConditions.invisibilityOf(removeBtn));
        Assert.assertTrue(driver.getPageSource().contains("Your basket is empty") || driver.findElements(By.cssSelector("mat-row")).size() == 0);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}