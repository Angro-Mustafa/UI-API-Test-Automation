import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class SauceDemoUITests {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless", "--width=1920", "--height=1080");
        driver = new FirefoxDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test(priority = 1)
    public void loginWithValidCredentials() {
        driver.get("https://www.saucedemo.com/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name"))).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    @Test(priority = 2)
    public void loginWithInvalidCredentials() {
        driver.get("https://www.saucedemo.com/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name"))).sendKeys("locked_out_user");
        driver.findElement(By.id("password")).sendKeys("wrongpass");
        driver.findElement(By.id("login-button")).click();
        Assert.assertTrue(driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed());
    }

    @Test(priority = 3, dependsOnMethods = "loginWithValidCredentials")
    public void addItemToCart() {
        // Should already be logged in after valid login test
        driver.findElement(By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']")).click();
        Assert.assertEquals(driver.findElement(By.className("shopping_cart_badge")).getText(), "1");
    }

    @Test(priority = 4, dependsOnMethods = "addItemToCart")
    public void removeItemFromCart() {
        driver.findElement(By.className("shopping_cart_link")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-test='remove-sauce-labs-backpack']"))).click();
        Assert.assertTrue(driver.findElements(By.className("cart_item")).isEmpty());
    }

    @Test(priority = 5, dependsOnMethods = "addItemToCart")
    public void checkoutOverview() {
        driver.findElement(By.className("shopping_cart_link")).click();
        driver.findElement(By.id("checkout")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name"))).sendKeys("Test");
        driver.findElement(By.id("last-name")).sendKeys("User");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two"));
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
