package elements;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import utils.Waiting;


import static com.codeborne.selenide.Selenide.$x;

public class Preloader extends BaseElement{

    public Preloader(SelenideElement container) {
        super(container);
    }

    @Step("Проверка отсуствия активного прелоадера")
    public void checkPreloader() {
        new Waiting().waitUntilDisappear(container);
    }
}
