package ncsTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NTwoFATest {

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();

        // ✅ Disable Chrome password manager & popups
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-password-manager-reauth");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.setExperimentalOption("prefs", new java.util.HashMap<String, Object>() {{
            put("credentials_enable_service", false);
            put("profile.password_manager_enabled", false);
        }});

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://nenosystems.in/SSO/#/login");
            driver.manage().window().maximize();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            // Enter login details
            driver.findElement(By.id("inputEmailAddress")).sendKeys("Raman@gmail.com");
            driver.findElement(By.id("inputChoosePassword")).sendKeys("Test@123");
            driver.findElement(By.cssSelector("div.d-grid")).click();

            // Wait for OTP input field
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("code")));

            // Generate OTP from your correct secret key
            String otp = NOTPGenerator.getOTP("CVQHNZAYUKTKLPSC");
            System.out.println("Generated OTP: " + otp);

            // Enter OTP
            driver.findElement(By.id("code")).sendKeys(otp);

            // Click Submit
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[normalize-space()='Submit']"))).click();

            // 🔹 Handle all in-app popups (1st, 2nd, etc.)
            handleAllPopups(driver, wait);

            Thread.sleep(3000);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // driver.quit();
        }
    }

    // Handle multiple in-app popups with OK buttons
    private static void handleAllPopups(WebDriver driver, WebDriverWait wait) {
        boolean popupFound = true;
        while (popupFound) {
            try {
                WebElement okBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//button[normalize-space()='OK']")));
                System.out.println("Popup appeared. Clicking OK...");
                okBtn.click();
                Thread.sleep(1000); // small wait before checking next popup
            } catch (TimeoutException e) {
                popupFound = false; // no more popups
                System.out.println("No more popups found.");
            } catch (Exception ex) {
                popupFound = false;
                System.out.println("Unexpected issue while handling popup: " + ex.getMessage());
            }
        }
    }
}
