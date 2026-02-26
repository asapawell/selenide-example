package elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.Objects;

public class CheckBox extends BaseElement {

    public CheckBox(SelenideElement container) {
        super(container);
    }

    @Step("Установить чек-бокс '{this.alias}' в состояние 'Установлен'")
    public void setChecked() {
        if (!isChecked()) {
            container.click();
            shouldBeSelected();
        }
    }

    @Step("Установить чек-бокс '{this.alias}' в состояние 'Не установлен'")
    public void setNotChecked() {
        if (isChecked()) {
            container.click();
            shouldNotBeSelected();
        }
    }

    @Step("Проверить состояние 'Установлен' у чекбокса '{this.alias}'")
    public void shouldBeSelected() {
        container.shouldHave(Condition.domProperty("checked", "true"));
    }

    @Step("Проверить состояние 'Не установлен' у чекбокса '{this.alias}'")
    public void shouldNotBeSelected() {
        container.shouldNotHave(Condition.domProperty("checked", "true"));
    }

    private boolean isChecked() {
        return Boolean.parseBoolean(container.getDomProperty("checked"));
    }

}
