import io.github.bonigarcia.wdm.config.OperatingSystem;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.slf4j.Logger;

import java.util.HashMap;

public class DriverSetUp {

    /**
     * driver is initialized as protected so as to used in inherited classes
     */

    protected static WebDriver webdriver;
    public static Logger log;

    public static void initiateDriver() {
        terminateDriver();
        if (webdriver == null) {
            startAppiumService();
        }
    }

    public static void terminateDriver() {
        if (webdriver != null) {
            webdriver.close();
            webdriver.quit();
        }
    }

    private static void startAppiumService() {
        try {
            if (webdriver == null) {
                HashMap<String, Object> chromePrefs = new HashMap<>();

                //region Setting Chrome preferences and capabilities
                chromePrefs.put("profile.default_content_settings.popups", 0);
                ChromeOptions options = new ChromeOptions();
               // options.addArguments("headless");
                options.addArguments("--safebrowsing-disable-download-protection");
                options.addArguments("--whitelisted-ips");
                options.addArguments("--no-sandbox");
                options.addArguments("--ignore-certificate-errors");
                options.setExperimentalOption("prefs", chromePrefs);
                options.addArguments("disable-dev-shm-usage");
                options.addArguments("disable-popup-blocking");
                options.addArguments("--disable-extensions"); //to disable browser extension popup
                options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, "accept");
                options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "accept");
                options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                //endregion
                WebDriverManager.chromedriver().operatingSystem(OperatingSystem.valueOf("WIN")).setup();
                webdriver = new ChromeDriver(options);
                webdriver.manage().deleteAllCookies();
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}
