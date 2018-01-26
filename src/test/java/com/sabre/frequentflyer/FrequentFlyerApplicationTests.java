package com.sabre.frequentflyer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FrequentFlyerApplication.class)
@WebAppConfiguration
public class FrequentFlyerApplicationTests {
    private WebDriver driver;
    private WebDriverWait wait;
    private final static String TEST_USER = "example@example.com";
    private final static String TEST_PASSWORD = "admin";
    private final static ByChained TABS_LOGOUT = new ByChained(By.className("tabs"), By.className("logoutButton"));

    @Before
    public void before() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 15);
    }

    private void doLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(By.className("auth0-lock-submit")));
        driver.findElement(By.name("email")).sendKeys(TEST_USER);
        driver.findElement(By.name("password")).sendKeys(TEST_PASSWORD);
        driver.findElement(By.className("auth0-lock-submit")).click();
        wait.until(ExpectedConditions.elementToBeClickable(TABS_LOGOUT));
    }

    @Test
    public void auth0Login() {
        driver.get("http://localhost:8080");
        doLogin();
        driver.findElement(TABS_LOGOUT).click();
    }

    @Test
    public void changingPersonalData() {
        driver.get("http://localhost:8080");
        doLogin();
        driver.findElement(By.id("profileTab")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));

        WebElement nameInput = driver.findElement(By.id("name"));
        nameInput.clear();
        nameInput.sendKeys("Flights Administrator");

        WebElement addressInput = driver.findElement(By.id("address"));
        addressInput.clear();
        addressInput.sendKeys("Br St. 11, 87-100 Thorn");

        driver.findElement(By.tagName("button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        assertEquals(driver.findElement(By.id("name")).getText(), ("Flights Administrator"));
    }

    @After
    public void after() {
        driver.close();
    }
}