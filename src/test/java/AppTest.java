import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import sun.rmi.runtime.Log;

import java.io.IOException;

public class AppTest extends DriverSetUp {

    public static Logger log = Logger.getLogger(Log.class.getName());
    static ExcelUtils excelUtils = new ExcelUtils();

    @BeforeClass
    public static void beforeMethodTearUp() {
        log.info("Test Automation Started !!!!");
        DriverSetUp.initiateDriver();
        ReadData.getInstance();
        WebInteraction.Launch(webdriver, ReadData.getInstance().getData("URL"));
    }

    @Test(priority = 1)
    public void verifyTheSignUpPageIsDisplayed() {
        WebInteraction.explicitWait(webdriver, ReadData.getInstance().getData("SignUp"), 60);
        Assert.assertTrue(WebInteraction.isElementDisplayed(webdriver, ReadData.getInstance().getData("SignUp")));
    }

    @Test(priority = 2)
    public void verifySignUpPage() {
        boolean result = false;
        if (WebInteraction.isElementDisplayed(webdriver, ReadData.getInstance().getData("FirstName")))
            if (WebInteraction.isElementDisplayed(webdriver, ReadData.getInstance().getData("LastName")))
                if (WebInteraction.isElementDisplayed(webdriver, ReadData.getInstance().getData("Email")))
                    if (WebInteraction.isElementDisplayed(webdriver, ReadData.getInstance().getData("Password")))
                        if (WebInteraction.isElementDisplayed(webdriver, ReadData.getInstance().getData("Country")))
                            if (WebInteraction.isElementDisplayed(webdriver, ReadData.getInstance().getData("MobileNumber")))
                                if (WebInteraction.isElementDisplayed(webdriver, ReadData.getInstance().getData("ReferralCheckbox")))
                                    if (WebInteraction.isElementDisplayed(webdriver, ReadData.getInstance().getData("CoinDCXAgree")))
                                        if (WebInteraction.isElementDisplayed(webdriver, ReadData.getInstance().getData("Register")))
                                            result = true;

        Assert.assertTrue(result);
    }

    @Test(priority = 3)
    @Parameters({"fileName"})
    public void validateFormSubmission(String fileName) throws IOException {
        String excelFilePath = ReadData.getInstance().getData("Path_TestData") + fileName;
        excelUtils.setExcelFile(excelFilePath, "formData");
        //iterate over all the row to print the data present in each cell.
        for (int i = 1; i <= excelUtils.getRowCountInSheet(); i++) {
            try {
                WebInteraction.typeKeys(webdriver, ReadData.getInstance().getData("FirstName"), excelUtils.getCellData(i, 0));
                WebInteraction.typeKeys(webdriver, ReadData.getInstance().getData("LastName"), excelUtils.getCellData(i, 1));
                WebInteraction.typeKeys(webdriver, ReadData.getInstance().getData("Email"), excelUtils.getCellData(i, 2));
                WebInteraction.typeKeys(webdriver, ReadData.getInstance().getData("Password"), excelUtils.getCellData(i, 3));
                WebInteraction.Click(webdriver, ReadData.getInstance().getData("Country"));
                WebInteraction.Click(webdriver, ReadData.getInstance().getData("dropCountry") + excelUtils.getCellData(i, 4) + "']");
                WebInteraction.typeKeys(webdriver, ReadData.getInstance().getData("MobileNumber"), String.valueOf(excelUtils.getCellData(i, 5)));
                if (excelUtils.getCellData(i, 6).equals("Yes")) {
                    WebInteraction.Click(webdriver, ReadData.getInstance().getData("ReferralCheckbox"));
                    WebInteraction.typeKeys(webdriver, ReadData.getInstance().getData("ReferralCode"), String.valueOf(excelUtils.getCellData(i, 7)));
                }
                WebInteraction.explicitWaitForSwitchFrame(webdriver, ReadData.getInstance().getData("captcha"), 20);
                new WebDriverWait(webdriver, 20).until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.recaptcha-checkbox-border"))).click();
                Thread.sleep(60);//time wait for manual captcha validation
                webdriver.switchTo().parentFrame();
                WebInteraction.explicitWaitForClickable(webdriver, ReadData.getInstance().getData("Register"), 10);
                WebInteraction.Click(webdriver, ReadData.getInstance().getData("Register"));
            } catch (Exception ignored) {
            } finally {
                if (WebInteraction.isElementDisplayed(webdriver, ReadData.getInstance().getData("confirmationForm"))) {
                    excelUtils.setCellValue(i, 9, "PASS", ReadData.getInstance().getData("Path_TestData"));
                } else {
                    excelUtils.setCellValue(i, 9, "FAIL", ReadData.getInstance().getData("Path_TestData"));
                }
            }
        }
    }

    @AfterClass
    public static void afterMethodTearDown() {
        log.info("Test Automation Completed !!!!");
        webdriver.quit();
    }

}