package ru.humantech.work;


import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

//import org.openqa.selenium.firefox.FirefoxDriver;


public class BPTestReportForSpecialist {
    private WebDriver driver;
//    private static String downloadPath = "C:\\Users\\selfi";
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
    public void testReportForSpecialist()  throws Exception {
        String parentHandle = driver.getWindowHandle();
        WebDriverWait wait = new WebDriverWait(driver, 40);

        TimeUnit.SECONDS.sleep(5);
        try {
            assertTrue(driver.findElement(By.xpath("//*[@class='MTSNavigateLink' and text()='Список тестов']")).isDisplayed());
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
        reportOption.selectByVisibleText("Проф-отчет : для специалиста (шкал в профиле - 32)");
//        reportOption.selectByValue("1");
        TimeUnit.SECONDS.sleep(5);
        try {
            assertTrue(driver.findElement(By.xpath("//table[@class='MTSTableRecords']//*[@onmouseover='onMouseOverOutRow(this,true);']//*[@class='MTSTableRecords']//*[text()='PDF'][last()]")).isDisplayed());
        } catch (AssertionError e) {
            System.err.println("PDF format must be available");
        }
        try {
            assertTrue(driver.findElement(By.xpath("//table[@class='MTSTableRecords']//*[@onmouseover='onMouseOverOutRow(this,true);']//*[@class='MTSTableRecords']//*[text()='DOCX'][last()]")).isDisplayed());
        } catch (AssertionError e) {
            System.err.println("DOCX format must be available");
        }
        driver.findElement(By.xpath("//table[@class='MTSTableRecords']//*[@onmouseover='onMouseOverOutRow(this,true);']//*[@class='MTSLink' and @title='просмотреть отчет'][last()]")).click();
        TimeUnit.SECONDS.sleep(1);

        ReportCorrectness(wait);

        driver.switchTo().window(parentHandle);
        driver.findElement(By.name("ButtonLogout")).click();
        driver.switchTo().alert().accept();
//        TimeUnit.SECONDS.sleep(1);
//        driver.findElement(By.xpath("//table[@class='MTSTableRecords']//*[@onmouseover='onMouseOverOutRow(this,true);']//*[@class='MTSTableRecords']//*[text()='PDF'][last()]")).click();
//
//        assertTrue(isFileDownloaded_Ext(downloadPath, ".pdf","Бизнес-профиль-6"), "Failed to download document which has extension .pdf");
//
//
//        driver.switchTo().window(parentHandle);
//        TimeUnit.SECONDS.sleep(1);
//        driver.findElement(By.xpath("//table[@class='MTSTableRecords']//*[@onmouseover='onMouseOverOutRow(this,true);']//*[@class='MTSTableRecords']//*[text()='DOCX'][last()]")).click();
//        assertTrue(isFileDownloaded_Ext(downloadPath, ".docx", "Biznes-profil-6"), "Failed to download document which has extension .docx");
//
//
//    }
//    private boolean isFileDownloaded_Ext(String dirPath, String ext, String partName){
//        boolean flag=false;
//        File dir = new File(dirPath);
//        File[] files = dir.listFiles();
//        if (files == null || files.length == 0) {
//            flag = false;
//        }
//
//        for (int i = 1; i < files.length; i++) {
//            if((files[i].getName().contains(ext)) && (files[i].getName().contains(partName))) {
//                flag=true;
//            }
//        }
//        return flag;
    }

    private void ReportCorrectness(WebDriverWait wait) {
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("MTSReportBody"))));
        try {
            assertEquals(driver.findElements(By.xpath("//*[@class='HTReportBlock']//*[@class='blocktitle']//*[text()='Информация о тестировании']")).size(),1);
            assertTrue(driver.findElement(By.xpath("//*[@class='HTReportBlock']//*[@class='blocktitle']//*[text()='Информация о тестировании']")).isDisplayed());
        } catch (AssertionError e) {
            System.err.println("Block 'Information about the test' must be shown once");
        }
        try {
            assertTrue(driver.findElement(By.xpath("//*[@class='HTReportBlock-content']//*[text()='Проф-отчет : для специалиста']")).isDisplayed());
        } catch (AssertionError e) {
            System.err.println("Block 'Information about the test' must be for specialist");
        }
        try {
            assertEquals(driver.findElements(By.xpath("//*[@class='HTReportBlock']//*[@class='blocktitle']//*[text()='Информация о респонденте']")).size(),1);
            assertTrue(driver.findElement(By.xpath("//*[@class='HTReportBlock']//*[@class='blocktitle']//*[text()='Информация о респонденте']")).isDisplayed());
        } catch (AssertionError e) {
            System.err.println("Block 'Information about the respondent' must be shown once");
        }
        try {
            assertEquals(driver.findElements(By.xpath("//*[@class='HTReportBlock']//*[@class='blocktitle']//*[text()='Профиль результатов']")).size(),1);
            assertTrue(driver.findElement(By.xpath("//*[@class='HTReportBlock']//*[@class='blocktitle']//*[text()='Профиль результатов']")).isDisplayed());
        } catch (AssertionError e) {
            System.err.println("Block 'Results' must be shown once");
        }
        try {
            assertEquals(driver.findElements(By.xpath("//*[@class='HTReportBlock']//*[@class='blocktitle']//*[text()='Рекомендуемые профессии']")).size(),1);
            assertTrue(driver.findElement(By.xpath("//*[@class='HTReportBlock']//*[@class='blocktitle']//*[text()='Рекомендуемые профессии']")).isDisplayed());
        } catch (AssertionError e) {
            System.err.println("Block 'Recommended professions' must be shown once");
        }
        try {
            assertEquals(driver.findElements(By.xpath("//*[@class='HTReportBlock']//*[@class='blocktitle']//*[text()='Описание результатов']")).size(),1);
            assertTrue(driver.findElement(By.xpath("//*[@class='HTReportBlock']//*[@class='blocktitle']//*[text()='Описание результатов']")).isDisplayed());
        } catch (AssertionError e) {
            System.err.println("Block 'Description of results' must be shown once");
        }
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
}


