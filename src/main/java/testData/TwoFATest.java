package testData;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TwoFATest {

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://qa.throughouttechnologies.com/employees/sign_in");
            driver.manage().window().maximize();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));


            driver.findElement(By.id("email")).sendKeys("rajabhaiya1137@gmail.com");
            driver.findElement(By.id("login-password")).sendKeys("Test@123");
            driver.findElement(By.xpath("//input[@type='submit']")).click();

            // Wait for OTP input field to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("otp_attempt")));

            // Generate OTP from your correct secret key
            String otp = OTPGenerator.getOTP("BX5XO6J4YTANLLWGYF7HGLSU3WFXV7GE");
            System.out.println("Generated OTP: " + otp);

            driver.findElement(By.id("otp_attempt")).sendKeys(otp);

            // Wait for and click "Confirm and Enable Two Factor" button
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@name='commit'])[1]"))).click();

            Thread.sleep(3000);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
         //   driver.quit();
        }
    }
}
