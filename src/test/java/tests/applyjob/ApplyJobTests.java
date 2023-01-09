package tests.applyjob;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import tests.demoqa.TestBase;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@Tag("Apply job")
public class ApplyJobTests {
    @BeforeAll
    static void beforeAll() {
        Configuration.holdBrowserOpen = true;

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;

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


    @DisplayName("Проверка корректной работы поиска через поисковую строку")
    @Test
    void searchBySearchStringTest() {

        step("Открываем сайт главную страницу DNS", () -> {
            open("https://www.dns-shop.ru/");
        });
        step("Вводим в поисковую строку слово Перчатки", () -> {
            $("[name=q]").setValue("Перчатки").pressEnter();
        });
        step("Проверяем, что вывелись результаты именно по запросу 'перчатки'", () -> {
            $(".products-list__group-title").shouldHave(text("Перчатки"));
        });

    }

    @DisplayName("Проверка корректной работы поиска по категориям")
    @Test
    void searchByCategoryTest() {

        step("Открываем сайт главную страницу DNS", () -> {
            open("https://www.dns-shop.ru/");
        });
        step("Выбираем пункт 'Смартфоны и Фототехника'", () -> {
            $(".menu-desktop").find(byText("Смартфоны и фототехника")).click();
        });
        step("Проверяем, что открылся нужный раздел ", () -> {
            $(".subcategory__page-title").shouldHave(text("Смартфоны и фототехника"));
        });

    }

    @DisplayName("Проверка корректной работы поиска магазинов")
    @Test
    void searchShopsTest() {
        step("Открываем сайт главную страницу DNS", () -> {
            open("https://www.dns-shop.ru/");
        });
        step("Выбираем пункт 'Магазины'", () -> {
            $(".header-top-menu").find(byText("Магазины")).click();
        });
        step("Выбиваем с строку поиска по магазинам 'белая дача'", () -> {
            $("[data-role=shop-search-field]").setValue("белая дача");
        });
        step("Проверяем, что в выдаче есть магазин DNS в Мега Белая Дача", () -> {
            $(".shop-list-item:not(.hidden)").shouldHave(text("белая дача"));
        });
    }

    @DisplayName("Проверка перехода на страницу акций с главной страницы")
    @Test
    void searchByPromotionsTest() {
        step("Открываем сайт главную страницу DNS", () -> {
            open("https://www.dns-shop.ru/");
        });
        step("Нажимаем на кнопку: Все акции", () -> {
            $(".homepage-actions__action-buttons").find(byText("Все акции")).click();
        });
        step("Проверяем, что открылся раздел Акции", () -> {
            $(".actions-filters").shouldHave(text("Вид акций"));
        });
    }

    @DisplayName("Проверка перехода на страницу сравнения товаров с главной страницы")
    @Test
    void compareProductsTest() {
        step("Открываем сайт главную страницу DNS", () -> {
            open("https://www.dns-shop.ru/");
        });
        step("Нажимаем на кнопку 'Сравнение'", () -> {
            $(".compare-link-counter").click();
        });
        step("Проверяем, что открылся раздел Сравнение", () -> {
            $(".top-compare__header").shouldHave(text("Часто сравниваемые"));
        });
    }

}
