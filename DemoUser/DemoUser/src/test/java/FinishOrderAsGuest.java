import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class FinishOrderAsGuest {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.get("http://shop.pragmatic.bg/index.php?route=product/product&product_id=40");
        driver.manage().window().maximize();
    }

    @Test
    public void placeOrderAsGuestTest() {
        driver.findElement(By.id("button-cart")).click();
        WebElement cartButton = driver.findElement(By.xpath("//span[@id='cart-total']/ .."));
        cartButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='dropdown-menu pull-right']//i[@class='fa fa-share']/ ..")));
        WebElement checkoutLink = driver.findElement(By.xpath("//ul[@class='dropdown-menu pull-right']//i[@class='fa fa-share']/ .."));
        checkoutLink.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[value='guest']")));
        driver.findElement(By.cssSelector("[value='guest']")).click();
        driver.findElement(By.id("button-account")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-payment-firstname")));
        driver.findElement(By.id("input-payment-firstname")).sendKeys("Milen");
        driver.findElement(By.id("input-payment-lastname")).sendKeys("Bozhinov");
        String prefix = RandomStringUtils.randomAlphabetic(7);
        String sufix = RandomStringUtils.randomAlphabetic(5);
        String domain = RandomStringUtils.randomAlphabetic(3);
        String emailAddress = prefix + "@" + sufix + "." + domain;
        driver.findElement(By.id("input-payment-email")).sendKeys(emailAddress);
        String phoneNumber = RandomStringUtils.randomNumeric(10);
        driver.findElement(By.id("input-payment-telephone")).sendKeys(phoneNumber);
        driver.findElement(By.id("input-payment-address-1")).sendKeys(RandomStringUtils.randomAlphabetic(5));
        driver.findElement(By.id("input-payment-city")).sendKeys(RandomStringUtils.randomAlphabetic(5));
        driver.findElement(By.id("input-payment-postcode")).sendKeys(RandomStringUtils.randomNumeric(4));
        WebElement countrySelect = driver.findElement(By.id("input-payment-country"));
        Select countryDropdown = new Select(countrySelect);
        countryDropdown.selectByValue("33");
        WebElement regionSelect = driver.findElement(By.id("input-payment-zone"));
        Select regionDropdown = new Select(regionSelect);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#input-payment-zone option[value='505']")));
        regionDropdown.selectByValue("479");
        driver.findElement(By.id("button-guest")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("button-shipping-method")));
        driver.findElement(By.id("button-shipping-method")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='agree']")));
        driver.findElement(By.cssSelector("input[name='agree']")).click();
        driver.findElement(By.id("button-payment-method")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("button-confirm")));
        driver.findElement(By.id("button-confirm")).click();


    }
    @AfterMethod
    public void quitDriver() {
        driver.quit(); }
}

