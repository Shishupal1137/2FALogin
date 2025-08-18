package common;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class test2 {

    WebDriver driver;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        Reporter.log("✅ Browser launched and maximized", true);
    }

    @Test
    public void launchFlipkart() {
        driver.get("https://www.flipkart.com/");
        Reporter.log("✅ Navigated to Flipkart", true);

        String title = driver.getTitle();
        Reporter.log("📄 Page Title: " + title, true);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            Reporter.log("✅ Browser closed successfully", true);
        }
    }
}
