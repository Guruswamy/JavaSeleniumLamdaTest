package com.lambdatest;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Amazon_iPhone13_PriceTest {

    private RemoteWebDriver driver;
    private String Status = "failed";

    @BeforeTest
    @Parameters({"platformName", "browserName"})
    public void setup(String pName, String bName, Method m, ITestContext ctx) throws MalformedURLException {
        String username = System.getenv("LT_USERNAME") == null ? "guruswamy.1984" : System.getenv("LT_USERNAME");
        String authkey = System.getenv("LT_ACCESS_KEY") == null ? "<<ACCESSKEY>>" : System.getenv("LT_ACCESS_KEY");;
        
        /*
        Steps to run Smart UI project (https://beta-smartui.lambdatest.com/)
        Step - 1 : Change the hub URL to @beta-smartui-hub.lambdatest.com/wd/hub
        Step - 2 : Add "smartUI.project": "<Project Name>" as a capability above
        Step - 3 : Add "((JavascriptExecutor) driver).executeScript("smartui.takeScreenshot");" code wherever you need to take a screenshot
        Note: for additional capabilities navigate to https://www.lambdatest.com/support/docs/test-settings-options/
        */

        String hub = "@hub.lambdatest.com/wd/hub";

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", pName);
        caps.setCapability("browserName", bName);
        caps.setCapability("version", "latest");
        caps.setCapability("build", "Amazon iPhone 13 Price Test");
        caps.setCapability("name", ctx.getName() + this.getClass().getName());
        caps.setCapability("plugin", "git-testng");

        /*
        Enable Smart UI Project
        caps.setCapability("smartUI.project", "<Project Name>");
        */

        String[] Tags = new String[] { "Feature", "Magicleap", "Severe" };
        caps.setCapability("tags", Tags);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

    }

    @Test
    public void basicTest() throws InterruptedException {

        System.out.println("Loading Url");
        driver.get("https://www.amazon.com/");

        System.out.println("Search iPhone 13 from the Search Text bar");
        driver.findElement(By.xpath("//*[@id=\"twotabsearchtextbox\"]")).sendKeys("iphone13");
        driver.findElement(By.xpath("//*[@id=\"nav-search-submit-button\"]")).click();

        System.out.println("Retrieve iPhone 13 price from first occurance of the search - Not looking specific model");
        System.out.println("*********" + "iPhone 13 Price in Amazon is = " + driver.findElementByXPath("(//span[@class = \"a-price-whole\"][1])[1]").getText() + "*******");
        Status = "passed";

        Thread.sleep(150);

        System.out.println("TestFinished");
    }

    @AfterTest
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }

}