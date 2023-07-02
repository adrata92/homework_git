import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class CreatingNewUserTest {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.get("http://shop.pragmatic.bg/admin");
        driver.manage().window().maximize();
    }

    @Test
    public void creatingUserTest() {
        driver.findElement(By.id("input-username")).sendKeys("admin");
        WebElement passwordInputField = driver.findElement(By.id("input-password"));
        passwordInputField.sendKeys("parola123!");
        passwordInputField.submit();
        WebElement customersDropdown = driver.findElement(By.cssSelector("#menu-customer>a"));
        customersDropdown.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement customersLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#collapse5 > li:nth-child(1) > a")));
        customersLabel.click();
        WebElement plusButton = driver.findElement(By.xpath("//*[@class='fa fa-plus']/ .."));
        plusButton.click();
        driver.findElement(By.id("input-firstname")).sendKeys("Test");
        driver.findElement(By.id("input-lastname")).sendKeys("Testov");
        String prefix = RandomStringUtils.randomAlphabetic(7);
        String sufix = RandomStringUtils.randomAlphabetic(5);
        String domain = RandomStringUtils.randomAlphabetic(3);
        String emailAddress = prefix + "@" + sufix + "." + domain;
        driver.findElement(By.id("input-email")).sendKeys(emailAddress);
        driver.findElement(By.id("input-telephone")).sendKeys("0888666777");
        driver.findElement(By.id("input-password")).sendKeys("123!parola");
        driver.findElement(By.id("input-confirm")).sendKeys("123!parola");
        driver.findElement(By.cssSelector(".fa.fa-save")).click();
        driver.findElement(By.id("input-email")).sendKeys(emailAddress);
        driver.findElement(By.id("button-filter")).click();

        List<WebElement> emailElements = driver.findElements(By.xpath("//td[@class='text-left']/a[text()='E-Mail']"));
        Assert.assertTrue(!emailElements.isEmpty(), "The email address is not registered.");
    }

    @AfterMethod
        public void quitDriver() {
        driver.quit(); }

}
