package elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Getter;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

@Getter
public class DropDownList extends BaseElement {

    protected Input input;

    protected SelenideElement optionsWrapper;

    protected ElementsCollection options;

    protected SelenideElement violation;

    public DropDownList(SelenideElement container) {
        super(container);
        this.input = new Input(container);
        this.optionsWrapper = $x("//*[contains(@class,'dropdown-menu')]").as("Меню выпадающих элементов");
        this.options = $$x("//*[contains(@class,'dropdown-item')]").as("Элементы выпадающего списка");
        this.violation = container.$x(".//app-form-field-error").as("Предупреждение");
    }

    @Step("Раскрыть выпадающий список '{this.alias}'")
    public void openDdl() {
        input.click();
        optionsWrapper.shouldBe(visible);
    }

    @Step("Выбрать первое значение выпадающего списка '{this.alias}'")
    public void selectFirst() {
        SelenideElement firstOption = options.first().as("Первый элемент выпадающего списка");
        firstOption.as(firstOption.getText().trim()).click();
    }

    @Step("Выбрать значение [{optionText}] в выпадающем списке '{this.alias}'")
    public void selectExactChoose(String optionText) {
        options.filter(Condition.exactText(optionText)).first().as(optionText).click();
    }

    @Step("Выбрать значение [{optionText}] в выпадающем списке '{this.alias}'")
    public void selectChoose(String optionText) {
        options.filter(Condition.text(optionText)).first().as(optionText).click();
        input.checkValue(optionText);
    }

    @Step("Открыть выпадающий список '{this.alias}' и выбрать значение [{optionText}]")
    public void openDdlAndChooseExactOption(String optionText) {
        openDdl();
        selectExactChoose(optionText);
        input.checkValue(optionText);
    }


    @Step("Ввести частичное значение '{partValue}' в выпадающий список '{this.alias}' и выбрать полное '{fullValue}'")
    public void setPartialValueInDdlInput(String partValue, String fullValue) {
        input.enterData(partValue);
        options.shouldHave(sizeGreaterThan(0));
        selectChoose(fullValue);
    }

    @Step("Проскроллить до значения [{optionText}] в выпадающем списке '{this.alias}'")
    public void scrollToValue(String optionText) {
        openDdl();
        SelenideElement element = options.filter(Condition.text(optionText)).first().as(optionText);
        element.scrollIntoCenter();
        element.click();
    }

    @Step("Проверить текст предупреждения [{optionText}] выпадающего списка '{this.alias}'")
    public void checkViolationDdl(String optionText) {
        violation.shouldHave(text(optionText));
    }

    @Step("Проверить недоступность поля '{this.alias}'")
    public void checkDdlIsDisabled() {
        input.shouldBeDisabled();
    }
}
