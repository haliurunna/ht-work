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
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.testng.Assert.assertTrue;

public class BPTestPersonalLink {
    private WebDriver driver;
//  private boolean acceptNextAlert = true;
    //private StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        driver = new ChromeDriver();
//        driver = new FirefoxDriver();
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
    public void testPersonalLink()  throws Exception {

        String parentHandle = driver.getWindowHandle();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        Robot robot = new Robot();

        sendingInvitation(wait);

        gotoBP6PagePersonalLink();
        String personalLink = driver.getCurrentUrl();
        activationTestSession(wait);
        testInstruction(wait);
        personalConsent(wait);
        preliminaryInformation(wait);
        respondentInformation(wait);

        testBlock(wait, robot, 1, "page must contains first test block instruction", KeyEvent.VK_1);
        testBlock(wait, robot, 2, "page must contains second test block instruction", KeyEvent.VK_2);
        testBlock(wait, robot, 3, "page must contains third test block instruction", KeyEvent.VK_3);
        testBlock(wait, robot, 4, "page must contains fourth test block instruction", KeyEvent.VK_2);
        testBlock(wait, robot, 5, "page must contains fifth test block instruction", KeyEvent.VK_3);
        testBlock(wait, robot, 6, "page must contains sixth test block instruction", KeyEvent.VK_2);
        testBlock(wait, robot, 7, "page must contains seventh test block instruction", KeyEvent.VK_3);
        testBlock(wait, robot, 8, "page must contains eighth test block instruction", KeyEvent.VK_3);
        testBlock(wait, robot, 9, "page must contains ninth test block instruction", KeyEvent.VK_1);

        showingResult(wait);

        driver.get(personalLink);
        TimeUnit.SECONDS.sleep(2);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("ModalPanel"))));
        try {
            Assert.assertTrue(driver.findElement(By.xpath("//*[@class='ModalHeader' and text()='Ошибка загрузки теста']")).isDisplayed());
            Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(),'Загруженная индивидуальная ссылка была использована ранее')")).isDisplayed());
        } catch (AssertionError e) {
            System.err.println("after reloading the test using personal link, an error must be appeared");
        }
        driver.switchTo().window(parentHandle);
        driver.findElement(By.name("ButtonLogout")).click();
        driver.switchTo().alert().accept();
    }

    private void activationTestSession(WebDriverWait wait) throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className("ActiveButton")))).click();
    }

    private void gotoBP6PagePersonalLink() throws InterruptedException {
        Select filter = new Select(driver.findElement(By.xpath("//select[@class='MTSInputField' and @name='TicketState']")));
        filter.selectByValue("sent");
        driver.findElement(By.xpath("//*[@class='MTSButton90' and @id='idButtonFiltersApply']")).click();
        TimeUnit.SECONDS.sleep(5);
        driver.findElement(By.xpath("//*[@class='MTSLinkPopup' and text()='ссылка'][last()]")).click();
        driver.switchTo().alert().accept();
        TimeUnit.SECONDS.sleep(1);
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
    }

    private void sendingInvitation(WebDriverWait wait) {
        driver.findElement(By.xpath("//*[@class='MTSLink' and @href='/maintest-5i/?action=tickets&test=171107-155030-f69b&operation=create']")).click();
        driver.findElement(By.name("ResponderEmail")).click();
        driver.findElement(By.name("ResponderEmail")).clear();
        driver.findElement(By.name("ResponderEmail")).sendKeys("ololo.ololo.0987@gmail.com");
        driver.findElement(By.xpath("//*[@class='MTSButton90' and @value='Добавить' and @title='Добавить запись в список']")).click();

        driver.findElement(By.xpath("//*[@class='MTSButton100' and @id='ButtonSend' and @value='Отправить']")).click();
        driver.switchTo().alert().accept();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@class='MTSPanelContent']//*[contains(text(),'Отправка завершена')]"))));
        driver.findElement(By.xpath("//*[@class='MTSMenuLink' and @href='/maintest-5i/?action=tickets&test=171107-155030-f69b']")).click();
    }

    private void showingResult(WebDriverWait wait) throws InterruptedException {
        TimeUnit.SECONDS.sleep(30);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className("ButtonNext")))).click();
        TimeUnit.SECONDS.sleep(20);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("MTSReportBody"))));
    }

    private void testBlock(WebDriverWait wait, Robot robot, int i2, String s, int vk) throws InterruptedException {
        int questionCount;
        int i;
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("ButtonNext"))));
        try {
            Assert.assertTrue(driver.findElement(By.xpath("//*[@class='BlockInstructionPanel']//*[@class='PanelValue']")).isDisplayed());
            Assert.assertEquals(Integer.parseInt((driver.findElement(By.xpath("//*[@class='BlockNumber']")).getText())), i2);
        } catch (AssertionError e) {
            System.err.println(s);
        }
        driver.findElement(By.className("ButtonNext")).click();
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className("ButtonNext"))));
        questionCount = Integer.parseInt(driver.findElement(By.xpath("//*[@class='BlockQuestionsCount']")).getText());
        for (i = 1; i <= questionCount; i++) {
//      Thread.sleep(1000);
            TimeUnit.SECONDS.sleep(1);
            wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//*[@class='BlockQuestionNumber']")), String.valueOf(i)));
            try {
                Assert.assertTrue(driver.findElement(By.className("ButtonInstruction")).isDisplayed());
            } catch (AssertionError e) {
                System.err.println("page with question must contains instruction button");
            }
//            driver.findElement(By.className("TestQuestion PlayerContent IsActive IsComplete")).sendKeys(numpad);
            robot.keyPress(vk);
            driver.findElement(By.className("ButtonNext")).click();
        }
    }

    private void testInstruction(WebDriverWait wait) {
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className("ButtonNext"))));
        try{
            Assert.assertTrue(driver.findElement(By.xpath("//*[@class='TestInstructionPanel']//*[@class='PanelValue']")).isDisplayed());
        }catch (AssertionError e){
            System.err.println("page must contains instruction for the test");
        }
        driver.findElement(By.className("ButtonNext")).click();
    }

    private void respondentInformation(WebDriverWait wait) throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className("ButtonNext"))));
        try{
            Assert.assertTrue(driver.findElement(By.xpath("//*[@class='ParticipantPanel']//*[@class='PanelValue']")).isDisplayed());
        }catch (AssertionError e){
            System.err.println("page must contains questionnaire");
        }
        TimeUnit.SECONDS.sleep(1);
        driver.findElement((By.className("LastName"))).sendKeys("Куприянова");
        TimeUnit.SECONDS.sleep(1);
        driver.findElement((By.className("FirstName"))).sendKeys("Марина");
        TimeUnit.SECONDS.sleep(1);
        driver.findElement((By.className("MiddleName"))).sendKeys("Владимировна");
        Select dayOfBirth = new Select(driver.findElement(By.xpath("//select[@class='BirthDay' and @tabindex='15']")));
        dayOfBirth.selectByValue("13");
        Select monthOfBirth = new Select(driver.findElement(By.xpath("//select[@class='BirthMonth' and @tabindex='16']")));
        monthOfBirth.selectByValue("03");
        driver.findElement(By.className("ButtonNext")).click();
    }

    private void preliminaryInformation(WebDriverWait wait)  {
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className("ButtonNext"))));
        try{
            Assert.assertTrue(driver.findElement(By.xpath("//*[@class='PreliminaryPanel']//*[@class='PanelValue']")).isDisplayed());
        }catch (AssertionError e){
            System.err.println("page must contains preliminary questionnaire");
        }

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
    }

    private void personalConsent(WebDriverWait wait) {
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name("PersonalConsent"))));
        try{
            Assert.assertTrue(driver.findElement(By.xpath("//*[@class='ConsentPanel']//*[@class='PanelValue']")).isDisplayed());
        }catch (AssertionError e){
            System.err.println("page must contains personal consent block");
        }
        driver.findElement((By.name("PersonalConsent"))).click();
        driver.findElement(By.className("ButtonNext")).click();
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
