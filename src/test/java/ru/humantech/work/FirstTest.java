package ru.humantech.work;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirstTest {
  private WebDriver driver;
  //private boolean acceptNextAlert = true;
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
  public void testGeneralLink()  throws Exception {

    String parentHandle = driver.getWindowHandle();
//    gotoServiceOfBP6Page();
    gotoBP6PageGeneralLink();

    WebDriverWait wait = new WebDriverWait(driver, 20);
    wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className("ButtonNext")))).click();
    //personal consent page
    wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name("PersonalConsent")))).click();
    driver.findElement(By.className("ButtonNext")).click();
    //first page with selects
    Select field = new Select(driver.findElement(By.xpath("//select[@tabindex='10']")));
    field.selectByValue("35");
    Select country = new Select(driver.findElement(By.xpath("//select[@tabindex='11']")));
    country.selectByValue("30");
    Select language = new Select(driver.findElement(By.xpath("//select[@tabindex='12']")));
    language.selectByValue("25");
    Select gender = new Select(driver.findElement(By.xpath("//select[@tabindex='13']")));
    gender.selectByValue("161");
    Select education = new Select(driver.findElement(By.xpath("//select[@tabindex='14']")));
    education.selectByValue("20");
    Select educationDesc = new Select(driver.findElement(By.xpath("//select[@tabindex='15']")));
    educationDesc.selectByValue("11");
    Select position = new Select(driver.findElement(By.xpath("//select[@tabindex='16']")));
    position.selectByValue("6");
    Select yearOfBirth = new Select(driver.findElement(By.xpath("//select[@tabindex='17']")));
    yearOfBirth.selectByValue("103");
    driver.findElement(By.className("ButtonNext")).click();
    //info
    driver.findElement((By.className("LastName"))).sendKeys("Куприянова");
    driver.findElement((By.className("FirstName"))).sendKeys("Марина");
    driver.findElement((By.className("MiddleName"))).sendKeys("Владимировна");
    Select dayOfBirth = new Select(driver.findElement(By.xpath("//select[@class='BirthDay' and @tabindex='15']")));
    dayOfBirth.selectByValue("13");
    Select monthOfBirth = new Select(driver.findElement(By.xpath("//select[@class='BirthMonth' and @tabindex='16']")));
    monthOfBirth.selectByValue("03");
    driver.findElement((By.className("Email"))).sendKeys("ololo.ololo.0987@gmail.com");
    driver.findElement(By.className("ButtonNext")).click();

    //instruction
    Thread.sleep(1000);
    wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className("ButtonNext")))).click();

    Robot robot = new Robot();
    int i;
    int questionCount;

   //test block  1

    questionCount= Integer.parseInt(driver.findElement(By.xpath("//*[@class='BlockQuestionsCount']")).getText());
    for (i = 1; i<=questionCount; i++) {
      Thread.sleep(1000);
      wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//*[@class='BlockQuestionNumber']")), String.valueOf(i)));
      robot.keyPress(KeyEvent.VK_1);
      driver.findElement(By.className("ButtonNext")).click();
    }

   //test block  2
    Thread.sleep(1000);
    wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className("ButtonNext")))).click();
    questionCount = Integer.parseInt(driver.findElement(By.xpath("//*[@class='BlockQuestionsCount']")).getText());
    for (i = 1; i<=questionCount; i++){
      Thread.sleep(1000);
      wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//*[@class='BlockQuestionNumber']")), String.valueOf(i)));
      robot.keyPress(KeyEvent.VK_2);
      driver.findElement(By.className("ButtonNext")).click();
    }

   //test block 3
    Thread.sleep(1000);
    wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className("ButtonNext")))).click();
    questionCount = Integer.parseInt(driver.findElement(By.xpath("//*[@class='BlockQuestionsCount']")).getText());
    for (i = 1; i<=questionCount; i++){
      Thread.sleep(1000);
      wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//*[@class='BlockQuestionNumber']")), String.valueOf(i)));
      robot.keyPress(KeyEvent.VK_3);
      driver.findElement(By.className("ButtonNext")).click();
    }

    //test block 4
    Thread.sleep(1000);
    wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className("ButtonNext")))).click();
    questionCount = Integer.parseInt(driver.findElement(By.xpath("//*[@class='BlockQuestionsCount']")).getText());
    for (i = 1; i<=questionCount; i++){
      Thread.sleep(1000);
      wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//*[@class='BlockQuestionNumber']")), String.valueOf(i)));
      robot.keyPress(KeyEvent.VK_2);
      driver.findElement(By.className("ButtonNext")).click();
    }

    //test block 5
    Thread.sleep(1000);
    wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className("ButtonNext")))).click();
    questionCount = Integer.parseInt(driver.findElement(By.xpath("//*[@class='BlockQuestionsCount']")).getText());
    for (i = 1; i<=questionCount; i++){
      Thread.sleep(1000);
      wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//*[@class='BlockQuestionNumber']")), String.valueOf(i)));
      robot.keyPress(KeyEvent.VK_3);
      driver.findElement(By.className("ButtonNext")).click();
    }

    //test block 6
    Thread.sleep(1000);
    wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className("ButtonNext")))).click();
    questionCount = Integer.parseInt(driver.findElement(By.xpath("//*[@class='BlockQuestionsCount']")).getText());
    for (i = 1; i<=questionCount; i++){
      Thread.sleep(1000);
      wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//*[@class='BlockQuestionNumber']")), String.valueOf(i)));
      robot.keyPress(KeyEvent.VK_3);
      driver.findElement(By.className("ButtonNext")).click();
    }

    //test block 7
    Thread.sleep(1000);
    wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className("ButtonNext")))).click();
    questionCount = Integer.parseInt(driver.findElement(By.xpath("//*[@class='BlockQuestionsCount']")).getText());
    for (i = 1; i<=questionCount; i++){
      Thread.sleep(1000);
      wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//*[@class='BlockQuestionNumber']")), String.valueOf(i)));
      robot.keyPress(KeyEvent.VK_3);
      driver.findElement(By.className("ButtonNext")).click();
    }

    //test block 8
    Thread.sleep(1000);
    wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className("ButtonNext")))).click();
    questionCount = Integer.parseInt(driver.findElement(By.xpath("//*[@class='BlockQuestionsCount']")).getText());
    for (i = 1; i<=questionCount; i++){
      Thread.sleep(1000);
      wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//*[@class='BlockQuestionNumber']")), String.valueOf(i)));
      robot.keyPress(KeyEvent.VK_3);
      driver.findElement(By.className("ButtonNext")).click();
    }

    //test block 9
    Thread.sleep(1000);
    wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className("ButtonNext")))).click();
    questionCount = Integer.parseInt(driver.findElement(By.xpath("//*[@class='BlockQuestionsCount']")).getText());
    for (i = 1; i<=questionCount; i++){
      Thread.sleep(1000);
      wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//*[@class='BlockQuestionNumber']")), String.valueOf(i)));
      robot.keyPress(KeyEvent.VK_1);
      driver.findElement(By.className("ButtonNext")).click();
    }

    //result
    Thread.sleep(15000);
    wait.until(ExpectedConditions.presenceOfElementLocated(By.className("ButtonNext"))).click();
    wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("MTSReportBody"))));
    //driver.findElement(By.className("ButtonNext")).click();
    //wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("test_title"))));
    //driver.findElement(By.className("ButtonNext")).click();
    driver.close();
    driver.switchTo().window(parentHandle);
  }

  private void gotoBP6PageGeneralLink() {
    driver.findElement(By.xpath("//*[@class='MTSLink' and @href='/maintest-5i/?testing=81c6ed42f577a7f3']")).click();

    for (String winHandle : driver.getWindowHandles()) {
      driver.switchTo().window(winHandle);
    }
  }

//  private void gotoServiceOfBP6Page() {
//    driver.findElement(By.xpath("//*[@class='MTSLink' and @href='/maintest-5i/?action=service&test=171107-155030-f69b']")).click();
//    String parentHandle = driver.getWindowHandle();
//  }

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
