package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.$x;

public class DmsPage {

    private final SelenideElement title = $x("//*[@class='title word-breaking title--h2']").as("Заголовок");

    @Step("Проверить открытие страницы 'Личный кабинет ДМС'")
    public void checkTitle() {
        title.shouldBe(appear);
    }
}
