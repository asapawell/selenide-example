package pages.components;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import elements.Button;
import io.qameta.allure.Step;
import pages.DmsPage;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class SideMenu {

    private final SelenideElement container = $x("//div[@class='modal-container']").as("Контейнер");

    private final SelenideElement header = container.$x(".//div[@class='modal-header']").as("Заголовок");

    private final SelenideElement body = container.$x(".//div[@class='modal-body']").as("Заголовок");

    private final ElementsCollection items = header.$$x(".//*[contains(@class,'item')]").as("Элементы");


    @Step("Проверить заголовок бокового меню")
    public SideMenu checkHeader() {
        container.should(appear);
        items.forEach(el -> el.shouldBe(visible));
        items.shouldHave(CollectionCondition.exactTexts("Частным лицам", "Бизнесу"));

        return this;
    }

    @Step("Перейти в 'Личный кабинет ДМС'")
    public DmsPage goToDms() {
        ElementsCollection buttons = body.$$x(".//div[contains(@class,'list-item')]");
        new Button(buttons.findBy(text("Личный кабинет ДМС")).as("Личный кабинет ДМС")).click();

        return new DmsPage();
    }
}
