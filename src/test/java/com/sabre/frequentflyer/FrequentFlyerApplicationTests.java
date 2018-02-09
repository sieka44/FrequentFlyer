package com.sabre.frequentflyer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
//@SpringBootTest(classes = FrequentFlyerApplication.class)
@WebAppConfiguration
public class FrequentFlyerApplicationTests {
    public static final String PROFILE_UPDATE_SUCCESS_STRING = "Successfully updated your profile!";
    public static final String TICKET_ADD_SUCCESS_STRING = "Successfully added a ticket!";
    private WebDriver driver;
    private WebDriverWait wait;
    private final static String TEST_USER = "example@example.com";
    private final static String TEST_PASSWORD = "admin";
    private final static ByChained TABS_LOGOUT = new ByChained(By.className("tabs"), By.className("logoutButton"));
    private final static String TEST_NAME = "Flights Administrator";

    @Before
    public void before() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 20);
    }

    private void doLogin() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("auth0-lock-form")));
        WebElement auth0Form = driver.findElement(By.className("auth0-lock-form"));
        wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(auth0Form, By.name("email")));
        auth0Form.findElement(By.name("email")).sendKeys(TEST_USER);
        auth0Form.findElement(By.name("password")).sendKeys(TEST_PASSWORD);
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
        nameInput.sendKeys("incorrect_input");

        nameInput.submit();
        assertEquals("validate invalid", nameInput.getAttribute("class"));

        nameInput.clear();
        nameInput.sendKeys(TEST_NAME);

        WebElement addressInput = driver.findElement(By.id("address"));
        addressInput.clear();
        addressInput.sendKeys("Br St. 11, 87-100 Thorn");

        driver.findElement(By.tagName("button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast")));
        assertEquals(PROFILE_UPDATE_SUCCESS_STRING, driver.findElement(By.className("toast")).getText());
    }

    private void clickFix(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    private void pickDateToday(String inputId) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id(inputId)));
        driver.findElement(By.id(inputId)).click();
        By datepicker = By.id(inputId+"_root");
        wait.until(ExpectedConditions.attributeContains(datepicker, "class", "picker--focused"));
        ByChained today = new ByChained(datepicker, By.className("picker__today"));
        wait.until(ExpectedConditions.elementToBeClickable(today));
        driver.findElement(today).click();
        wait.until(ExpectedConditions.attributeToBe(datepicker, "class", "picker"));
        try {
            Thread.sleep(100); //wait for popup to close
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addingTicket() {
        driver.get("http://localhost:8080");
        doLogin();
        driver.findElement(By.id("addTicketTab")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("from")));

        WebElement submitButton = driver.findElement(By.className("checkboxes")).findElement(By.tagName("button"));
        submitButton.click();

        wait.until(ExpectedConditions.numberOfElementsToBe(By.className("invalid"), 4));
        clickFix(driver.findElement(By.id("rt")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("returnDate")));
        submitButton.click();
        wait.until(ExpectedConditions.numberOfElementsToBe(By.className("invalid"), 5));

        driver.findElement(By.id("from")).sendKeys("Cracow");
        wait.until(ExpectedConditions.numberOfElementsToBe(By.className("invalid"), 4));
        WebElement to = driver.findElement(By.id("to"));
        to.sendKeys("Cracow");
        submitButton.click();
        wait.until(ExpectedConditions.numberOfElementsToBe(By.className("invalid"), 5));
        to.clear();
        to.sendKeys("Warsaw");
        submitButton.click();
        wait.until(ExpectedConditions.numberOfElementsToBe(By.className("invalid"), 3));

        pickDateToday("departureDate");
        submitButton.click();
        wait.until(ExpectedConditions.numberOfElementsToBe(By.className("invalid"), 2));
        pickDateToday("returnDate");
        submitButton.click();
        wait.until(ExpectedConditions.numberOfElementsToBe(By.className("invalid"), 1));
        clickFix(driver.findElement(By.id("e")));
        wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        submitButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast")));
        assertEquals(TICKET_ADD_SUCCESS_STRING, driver.findElement(By.className("toast")).getText());
    }

    @After
    public void after() {
        driver.close();
    }
}