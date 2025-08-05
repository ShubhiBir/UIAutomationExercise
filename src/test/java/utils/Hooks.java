package utils;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import java.util.logging.Level;


public class Hooks {
    private Manager manager;

    public Hooks(Manager manager) {
        this.manager = manager;
    }

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        options.addArguments("start-maximized");
        options.setCapability(ChromeOptions.LOGGING_PREFS, logPrefs);
        manager.setDriver(new ChromeDriver(options));
    }

    @After
    public void tearDown() {
        manager.getDriver().quit();
        System.out.println("Quit Driver!");
    }
}
