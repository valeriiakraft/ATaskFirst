import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import java.util.concurrent.TimeUnit;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class PayTest {

    WebDriver driver;
    DataHelper.PayInfo payInfo = DataHelper.getPayInfo();

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    public void positiveTest() {
        driver.get("https://www.mts.by/");
        driver.findElement(By.className("cookie__close")).click();
        driver.findElement(By.id("connection-phone")).sendKeys(payInfo.getPhoneNumber());
        driver.findElement(By.id("connection-sum")).sendKeys(payInfo.getSum());
        driver.findElement(By.id("connection-email")).sendKeys(payInfo.getMail());
        driver.findElements(By.className("button__default")).get(0).click();
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS); // указала такое время, потому что у меня долго прогружается окно оплаты
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class='bepaid-iframe']")));
        String actualSum = driver.findElement(By.xpath("//p[contains(text(),' BYN ')]")).getText().trim().substring(0, 6);
        String expectedSum = payInfo.getSum();
        assertEquals(expectedSum, actualSum);
        String actualNumber = driver.findElement(By.xpath("//p[contains(text(),'Оплата')]")).getText().trim().substring(30);
        String expectedNumber = payInfo.getPhoneNumber();
        assertEquals(expectedNumber, actualNumber);
        String actualSumOnButton = driver.findElement(By.xpath("//button[contains(text(),'Оплатить')]")).getText().trim().substring(9, 15);
        String expectedSumOnButton = payInfo.getSum();
        assertEquals(expectedSumOnButton, actualSumOnButton);
    }
}
