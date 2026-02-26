package elements;

import com.codeborne.selenide.ScrollIntoViewOptions;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebElementCondition;
import lombok.Getter;

import static com.codeborne.selenide.ScrollIntoViewOptions.Block.center;

public abstract class BaseElement {

    @Getter
    SelenideElement container;

    @Getter
    String alias;

    public BaseElement(SelenideElement container) {
        this.container = container;
        this.alias = (container.getAlias() != null && !container.getAlias().isEmpty()) ? container.getAlias()
                : container.getSearchCriteria();
    }

    public BaseElement() {}

    public void shouldBe(WebElementCondition condition) {
        container.shouldBe(condition);
    }

    public void scrollIntoView(){
        container.scrollIntoView(ScrollIntoViewOptions.instant().block(center));
    }

    public void click() {
        container.click();
    }

}
