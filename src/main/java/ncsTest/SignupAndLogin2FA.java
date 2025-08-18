package ncsTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

public class SignupAndLogin2FA {

    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        // Disable password manager and popups
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
        driver.manage().window().maximize();

        try {
            // ✅ Step 1: Signup
            driver.get("https://nenosystems.in/SSO/#/login");
            driver.findElement(By.xpath("//a[normalize-space()='Sign up here']")).click();

            // Generate dynamic values
            Random rand = new Random();
            String firstName = "Ncs" + rand.nextInt(1000);
            String lastName = "Test" + rand.nextInt(1000);
            String email = "ncs" + System.currentTimeMillis() + "@gmail.com";
            String password = "Test@123";

            // Fill signup form
            driver.findElement(By.xpath("(//input[@id='firstName'])[1]")).sendKeys(firstName);
            driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys(lastName);
            driver.findElement(By.xpath("//input[@id='login']")).sendKeys(email);
            driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password);

            // Click Sign Up
            driver.findElement(By.xpath("//button[normalize-space()='Sign Up']")).click();
            System.out.println("Signup attempted with: " + email);

            Thread.sleep(3000);

            // ✅ Step 2: Open new window and perform login + 2FA
            WebDriver loginDriver = driver.switchTo().newWindow(WindowType.WINDOW);
            loginDriver.get("https://nenosystems.in/SSO/#/login");

            WebDriverWait wait = new WebDriverWait(loginDriver, Duration.ofSeconds(15));

// Enter login details (use provided email + password)
            loginDriver.findElement(By.id("inputEmailAddress")).sendKeys("raman@gmail.com");
            loginDriver.findElement(By.id("inputChoosePassword")).sendKeys("Test@123");

// Click the login button
            loginDriver.findElement(By.cssSelector("div.d-grid")).click();


            // Wait for OTP field
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("code")));

            // Generate OTP from your correct secret key
            String otp = NOTPGenerator.getOTP("CVQHNZAYUKTKLPSC"); // replace with real secret
            System.out.println("Generated OTP: " + otp);

            // Enter OTP
            loginDriver.findElement(By.id("code")).sendKeys(otp);

            // Click Submit
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[normalize-space()='Submit']"))).click();

            // Handle popups
            handleAllPopups(loginDriver, wait);

            Thread.sleep(3000);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // driver.quit(); // Uncomment to close all browsers at end
        }
    }

    // Handle multiple in-app popups
    private static void handleAllPopups(WebDriver driver, WebDriverWait wait) {
        boolean popupFound = true;
        while (popupFound) {
            try {
                WebElement okBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//button[normalize-space()='OK']")));
                System.out.println("Popup appeared. Clicking OK...");
                okBtn.click();
                Thread.sleep(1000);
            } catch (TimeoutException e) {
                popupFound = false;
                System.out.println("No more popups found.");
            } catch (Exception ex) {
                popupFound = false;
                System.out.println("Unexpected issue while handling popup: " + ex.getMessage());
            }
        }
    }
}
