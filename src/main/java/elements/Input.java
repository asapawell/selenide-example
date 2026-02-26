package elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.disabled;

public class Input extends BaseElement {

    protected SelenideElement input;

    protected Button toolTipBtn;

    public Input(SelenideElement container) {
        super(container);
        this.input = container.$("[luinput],input").as("Поле ввода");
        this.toolTipBtn = new Button(container.$x(".//*[@class='info-icon-btn']").as("Тултип"));
    }

    @Step("Установить значение '{value}' в поле '{this.alias}'")
    public void setData(String value) {
        input.click();
        input.setValue(value);
    }

    @Step("Установить значение '{value}' в поле '{this.alias}'")
    public void enterData(String value) {
        input.sendKeys(value);
    }

    @Step("Проверить текст плейсхолдера [{placeholderText}]")
    public void checkPlaceholder(String placeholderText) {
        input.shouldHave(attribute("placeholder", placeholderText));
    }

    @Step("Проверить введенное значение [{text}] в поле ввода '{this.alias}'")
    public void checkValue(String text) {
        input.shouldHave(Condition.value(text));
    }

    @Step("Выбрать тултип с подсказкой в поле '{this.alias}'")
    public void clickTooltip() {
        toolTipBtn.click();
    }

    @Step("Проверить недоступность поля ввода '{this.alias}'")
    public void shouldBeDisabled() {
        input.shouldBe(disabled);
    }

    public void click() {
        input.click();
    }
}
