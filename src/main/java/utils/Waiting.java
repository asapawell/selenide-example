package utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.UIAssertionError;
import io.qameta.allure.Step;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;

public class Waiting {

    @Step("Ожидание изменения состояния объекта {se}")
    public void softWait(SelenideElement se) {
        int loopLimit = 3; /*количество повторных поисков элемента*/
        for (int i = 0; i < loopLimit; i++) {
            try {
                Selenide.Wait()
                        .withTimeout(Duration.ofSeconds(3))
                        .until(webDriver -> {
                            try {
                                return se.toWebElement().isDisplayed();
                            } catch (Throwable e) {
                                return false;
                            }
                        });
            } catch (TimeoutException e) {
                // если не найден, сразу выходим
                return;
            }
            se.should(Condition.disappear, Duration.ofSeconds(60));
        }
    }

    @Step("Проверка отсутствия активного элемента {se}")
    public void softWaitWithReturn(SelenideElement se) {
        try {
            se.shouldBe(Condition.visible, Duration.ofSeconds(4));
        } catch (UIAssertionError ignored) {
        }
        se.shouldNotBe(Condition.visible, Duration.ofSeconds(60));
    }
}
