package tests.demoqa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


public class TestBase {

    @BeforeAll
    static void beforeAll() {
        /** базовый урл */
        // Configuration.baseUrl = "https://demoqa.com";
        Configuration.baseUrl = System.getProperty("base_url", "https://www.google.com/");
        /** адрес удаленного браузера (selenoid) */
        // Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
        Configuration.remote = System.getProperty("remote_url", "https://www.google.com/");

        Configuration.holdBrowserOpen = true;

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;

        /** выбор браузера */
        capabilities.setCapability(CapabilityType.BROWSER_NAME,
                (System.getProperty("browser_name", "chrome")));
        /** выбор версии браузера */
        capabilities.setCapability(CapabilityType.BROWSER_VERSION,
                (System.getProperty("browser_version", "100.0")));
        /** выбора разрешения из сборки дженкинс */
        Configuration.browserSize = System.getProperty("browser_size", "1920x1080");
        /** еще передаем видео урл (в классе Attach в папке helpers)
         * https://selenoid.autotests.cloud/video/ */



        // capabilities.setCapability(CapabilityType.BROWSER_NAME, "chrome");
        // Configuration.browserSize = "1920x1080";



    }

    @BeforeEach
    void addListener(){

        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void addAttachments(){

        Attach.screenshotAs("Last Screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }
}
