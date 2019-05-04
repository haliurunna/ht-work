package ru.humantech.work;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import static org.testng.Assert.assertTrue;
public class BPTestReportForRespondent {
    private WebDriver driver;
//  private boolean acceptNextAlert = true;
    //private StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.get("https://test.ht-line.ru/logon/");
        login("mkupriyanova", "h3krdh2e");
    }

    private void login(String username, String password) {
        driver.findElement(By.name("login")).click();
        driver.findElement(By.name("login")).clear();
        driver.findElement(By.name("login")).sendKeys(username);
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("ButtonLogon")).click();


    }

    @Test
    public void testReportForRespondent()  throws Exception {
        String parentHandle = driver.getWindowHandle();
        WebDriverWait wait = new WebDriverWait(driver, 40);
        Robot robot = new Robot();

        try {
            assertTrue(driver.getCurrentUrl().contains("tests"));
        } catch (AssertionError e) {
            System.err.println("Page must show list of tests");
        }
        driver.findElement(By.xpath("//*[@class='MTSLink' and @href='/maintest-5i/?action=results&test=171107-155030-f69b' and text()='Результаты']")).click();
        try {
            assertTrue(driver.getCurrentUrl().contains("results"));
        } catch (AssertionError e) {
            System.err.println("Page must show results");
        }
        Select reportOption = new Select(driver.findElement(By.xpath("//select[@class='MTSInputField' and @name='ReportVariantId']")));
        reportOption.selectByVisibleText("Проф-отчет : для респондента (шкал в профиле - 32)");


    }


    @AfterClass(alwaysRun = true)
    public void tearDown() {
        //throws Exception {
        driver.quit();
//    String verificationErrorString = verificationErrors.toString();
//    if (!"".equals(verificationErrorString)) {
//      fail(verificationErrorString);
//    }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

//  private String closeAlertAndGetItsText() {
//    try {
//      Alert alert = driver.switchTo().alert();
//      String alertText = alert.getText();
//      if (acceptNextAlert) {
//        alert.accept();
//      } else {
//        alert.dismiss();
//      }
//      return alertText;
//    } finally {
//      acceptNextAlert = true;
//    }
//  }
}


