package common;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class test {

    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setUp() {
        // ✅ Setup Extent Report
        ExtentSparkReporter spark = new ExtentSparkReporter("extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);

        test = extent.createTest("Google Launch Test");

        // ✅ Setup WebDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        Reporter.log("✅ Browser launched and maximized", true);
        test.info("Browser launched and maximized");
    }

    @Test
    public void launchGoogle() {
        driver.get("https://www.google.com");
        Reporter.log("✅ Navigated to Google", true);
        test.pass("Navigated to Google successfully");

        String title = driver.getTitle();
        Reporter.log("📄 Page Title: " + title, true);
        test.info("Page title captured: " + title);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            Reporter.log("✅ Browser closed", true);
            test.info("Browser closed successfully");
        }

        // ✅ Flush Extent Report
        extent.flush();
    }
}
