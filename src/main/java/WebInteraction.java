import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.util.List;
import java.util.Objects;

public class WebInteraction {

    private static Logger logger;

    /**
     * To launch the url on the web browser
     *
     * @param driver - WebDriver object
     * @param URL    - URL to launch on the browser
     */
    public static void Launch(WebDriver driver, String URL) {
        try {
            if (!URL.isEmpty()) {
                driver.navigate().to(URL);
                driver.getCurrentUrl();
            }
        } catch (Exception e) {
            logger.error("Application failed to Launch : " + e.getMessage());
        }
    }

    /**
     * To get a webElement from the xpath
     *
     * @param driver     - WebDriver object
     * @param ObjectName - String with dynamic xpath value
     * @return WebElement
     */
    public static WebElement findWebElement(WebDriver driver, String ObjectName) {
        if (ObjectName.isEmpty()) {
            logger.error("String provided as input is empty.");
            return null;
        }
        try {
            return driver.findElement(By.xpath(ObjectName));
        } catch (Exception e) {
            logger.info("Exception occurred while fetching the WebElement." + e.getMessage());
            return null;
        }
    }

    /**
     * To get all elements using xpath
     *
     * @param driver     - WebDriver object
     * @param ObjectName - String with dynamic xpath value
     * @return WebElement
     */
    public List<WebElement> findWebElements(WebDriver driver, String ObjectName) {
        if (ObjectName.isEmpty()) {
            logger.error("String provided as input is empty.");
            return null;
        }
        try {
            return driver.findElements(By.xpath(ObjectName));
        } catch (Exception e) {
            logger.info("Exception occurred while fetching the WebElement." + e.getMessage());
            return null;
        }
    }

    /**
     * To verify if the element is displayed on the web page
     *
     * @param element - Element to be verified
     * @return ifDisplayed ? true : false
     */
    public static boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            logger.info("Exception occurred while verifying the element is displayed.");
            return false;

        } finally {
            logger.info("Verifying element is displayed.");
        }
    }

    /**
     * To verify if the element is displayed on the web page
     *
     * @param driver     - WebDriver object
     * @param ObjectName - String with dynamic xpath value
     * @return ifDisplayed ? true : false
     */
    public static boolean isElementDisplayed(WebDriver driver, String ObjectName) {
        if (ObjectName.isEmpty()) {
            logger.error("String provided as input is empty.");
            return false;
        }
        try {
            return Objects.requireNonNull(findWebElement(driver, ObjectName)).isDisplayed();
        } catch (NullPointerException | NoSuchElementException e) {
            logger.info("Exception occurred while verifying the element is displayed.");
            return false;
        }
    }

    /**
     * To verify if the element is enabled on the web page
     *
     * @param element - Element to be verified
     * @return ifDisplayed ? true : false
     */
    public boolean isElementEnabled(WebElement element) {
        try {
            return element.isEnabled();
        } catch (Exception e) {
            logger.info("Exception occurred while verifying the element is displayed.");
            return false;

        } finally {
            logger.info("Verifying element is displayed.");
        }
    }

    /**
     * Clicking the Web Element
     *
     * @param driver - WebDriver
     * @param Obj    - object for which the click to be performed
     */
    public static void Click(WebDriver driver, String Obj) {
        try {
            if (Obj != null) {
                if (isElementDisplayed(driver, Obj)) {
                    Objects.requireNonNull(findWebElement(driver, Obj)).click();
                }
            }
            logger.info("Element found is null");
        } catch (Exception e) {
            logger.error("Exception occurred while clicking " + e.getMessage());
        }
    }

    /**
     * send keys to the Web Element
     *
     * @param driver     - WebDriver
     * @param ObjectName - object for which the value to be set
     * @param key        - value to send to the element
     * @return boolean
     */
    public static boolean typeKeys(WebDriver driver, String ObjectName, String key) {
        if (ObjectName == null || key == null) return false;
        try {
            findWebElement(driver, ObjectName).sendKeys(key);
            return true;
        } catch (NoSuchElementException e) {
            logger.error("Error Occurred" + e.getMessage());
            return false;
        }
    }

    public static void explicitWait(WebDriver driver, String ObjectName, long timeInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ObjectName)));
    }

    public static void explicitWaitForSwitchFrame(WebDriver driver, String ObjectName, long timeInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(ObjectName)));
    }

    public static void explicitWaitForClickable(WebDriver driver, String ObjectName, long timeInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ObjectName)));
    }

}
