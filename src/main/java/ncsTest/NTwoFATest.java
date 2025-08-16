package ncsTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class NTwoFATest {

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://nenosystems.in/SSO/#/login");
            driver.manage().window().maximize();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            driver.findElement(By.xpath("//input[@id='inputEmailAddress']")).sendKeys("Raman@gmail.com");
            driver.findElement(By.xpath("//input[@id='inputChoosePassword']")).sendKeys("Test@123");
            driver.findElement(By.xpath("//div[@class='d-grid']")).click();

            // Wait for OTP input field to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='code']")));

            // Generate OTP from your correct secret key
            String otp = NOTPGenerator.getOTP("CVQHNZAYUKTKLPSC");
            System.out.println("Generated OTP: " + otp);

            // Enter OTP
            driver.findElement(By.xpath("//input[@id='code']")).sendKeys(otp);

            // Click Submit button after OTP entry
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Submit']"))).click();

            // Wait for possible alert and accept it
            try {
                wait.withTimeout(Duration.ofSeconds(5))
                        .until(ExpectedConditions.alertIsPresent());
                Alert alert = driver.switchTo().alert();
                System.out.println("Alert text: " + alert.getText());
                alert.accept();
                System.out.println("Alert accepted.");
            } catch (TimeoutException e) {
                System.out.println("No alert appeared after submit.");
            }

            Thread.sleep(3000);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // driver.quit();
        }
    }
}
