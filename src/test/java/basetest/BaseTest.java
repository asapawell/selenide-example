package basetest;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import service.LocalBrowser;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class BaseTest {

    @BeforeEach
    @Step("Открытие браузера")
    public void browserOpen() {
        try {
            Selenide.localStorage().clear();
            Selenide.sessionStorage().clear();
        } catch (Throwable ignored) {

        }
        open("/");
        WebDriver driver = WebDriverRunner.getWebDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    @Step("Закрытие браузера")
    public void closeBrowser() {
        closeWebDriver();
    }
}
