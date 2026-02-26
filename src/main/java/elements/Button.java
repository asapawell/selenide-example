package elements;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Getter;

import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.visible;

@Getter
public class Button extends BaseElement {

    protected Preloader btnPreloader;

    public Button(SelenideElement container) {
        super(container);
        this.btnPreloader = new Preloader(container.as("Контейнер кнопки '" + container.getAlias() + "'"));
    }

    @Step("Нажать на кнопку '{this.alias}'")
    public void click() {
        container.should(visible);
        container.shouldNotBe(disabled);
        container.click();
    }
}
