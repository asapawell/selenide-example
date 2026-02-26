package pages;

import com.codeborne.selenide.SelenideElement;
import elements.Button;
import elements.Preloader;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class AutoPage {

    private final SelenideElement osagoCard = $x("//div[@class='card'][.//*[contains(@href,'/auto/osago')]]")
            .as("Карточка ОСАГО");

    private final Button detailedButton = new Button(osagoCard.$x(".//div[normalize-space()='Подробнее']")
            .as("Подробнее"));

    private final Preloader preloader = new Preloader();

    @Step("Открыть страницу расчета 'Осаго' через клик по плитке 'Осаго'")
    public OsagoPage checkOsagoCard() {
        osagoCard.shouldBe(visible);
        detailedButton.click();
        preloader.checkPreloader();
        return new OsagoPage();
    }
}
