package ncsTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Random;

public class Signup {

    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Open URL
        driver.get("https://nenosystems.in/SSO/#/login");

        // Click "Sign up here"
        driver.findElement(By.xpath("//a[normalize-space()='Sign up here']")).click();

        // Generate dynamic values
        Random rand = new Random();
        String firstName = "Ncs" + rand.nextInt(1000);
        String lastName = "Test" + rand.nextInt(1000);
        String email = "ncs" + System.currentTimeMillis() + "@gmail.com";
        String password = "Test@123"; // can also randomize if needed

        // Fill signup form
        driver.findElement(By.xpath("(//input[@id='firstName'])[1]")).sendKeys(firstName);
        driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys(lastName);
        driver.findElement(By.xpath("//input[@id='login']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password);

        // Click Sign Up button
        driver.findElement(By.xpath("//button[normalize-space()='Sign Up']")).click();

        // Console log
        System.out.println("Signup attempted with: " + firstName + " " + lastName + " | Email: " + email);

        // Wait to observe
        Thread.sleep(3000);

        // Close browser
       // driver.quit();
    }
}
