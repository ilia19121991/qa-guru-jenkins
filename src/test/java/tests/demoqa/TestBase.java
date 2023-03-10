package tests.demoqa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;


public class TestBase {

    @BeforeAll
    static void beforeAll() {
        Configuration.holdBrowserOpen = true;

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;

        /** базовый урл */
        Configuration.baseUrl = System.getProperty("base_url", "https://www.google.com/");
        /** адрес удаленного браузера (selenoid) */
        Configuration.remote = System.getProperty("remote_url", "https://www.google.com/");
        /** выбор браузера */
        Configuration.browser = System.getProperty("browser_name", "abc");
        /** выбор версии браузера */
        Configuration.browserVersion = System.getProperty("browser_version", "0.0");
        /** выбора разрешения из сборки дженкинс */
        Configuration.browserSize = System.getProperty("browser_size", "0x0");
        /** еще передаем видео урл (в классе Attach в папке helpers)
         * https://selenoid.autotests.cloud/video/ */


        // Configuration.baseUrl = "https://demoqa.com";
        // Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";

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
