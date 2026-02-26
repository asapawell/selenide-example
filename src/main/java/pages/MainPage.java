package pages;

import elements.Button;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class MainPage {

    private final Button autoButton = new Button($x("//a[@href='/auto']").as("Авто"));

    private final Button cookie = new Button($x("//div[contains(@class,'block--cookie')]/button")
            .as("Согласие куки"));

    @Step("Подтвердить согласие куки")
    public MainPage acceptCookiePopup() {
        cookie.click();
        return this;
    }

    @Step("Перейти в раздел авто страхования")
    public AutoPage chooseAutoCategory() {
        autoButton.click();
        return new AutoPage();
    }
}
