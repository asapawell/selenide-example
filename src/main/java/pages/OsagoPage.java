package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import elements.Button;
import elements.CheckBox;
import elements.DropDownList;
import elements.Input;
import elements.Preloader;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import pages.components.SideMenu;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class OsagoPage {

    private final SelenideElement osagoTitle = $x("//section[@id='main']//h1[contains(.,'ОСАГО')]")
            .as("Заголовок ОСАГО");

    private final Input licenseInput = new Input($x("//*[@formcontrolname='licensePlate']")
            .as("Номер"));

    private final Preloader preloader = new Preloader();

    private final Preloader iframePreloader = new Preloader($x("//*[@class='preloader-image']"));

    private final Button calculateButton = new Button($x("//button[@test-id='calculateBtnLicenseComponent']")
            .as("Рассчитать"));

    private final SelenideElement licenseNumber = $x("//div[contains(@class,'carDataGroup')]//div[@class='subTitle']")
            .as("Номер авто");

    private final DropDownList markDdl = new DropDownList($x("//*[@formcontrolname='brandId']")
            .as("Марка"));

    private final DropDownList modelDdl = new DropDownList($x("//*[@formcontrolname='modelId']")
            .as("Модель"));

    private final DropDownList productionYear = new DropDownList($x("//*[@formcontrolname='productionYear']")
            .as("Модель"));

    private final DropDownList horsePower = new DropDownList($x("//*[@formcontrolname='horsePower']")
            .as("Модель"));

    private final Input stsNumberControlInput = new Input($x("//*[@formcontrolname='stsNumber']")
            .as("Серия и номер СТС"));

    private final SelenideElement popOver = $x("//popover-container[@role='tooltip']").as("СТС поповер");

    private final CheckBox otherFormat = new CheckBox($x("//*[@test-id='licensePlateFormatControlName']")
            .as("Другой формат"));

    private final DropDownList purposeOfUseDdl = new DropDownList($x("//*[@formcontrolname='purposeOfUse']")
            .as("Цель использования авто"));

    private final Button continueBtn = new Button($x("//*[@test-id='continueBtnCarDataComponent']").
            as("Продолжить"));

    private final DropDownList stsDateDdl = new DropDownList($x("//*[@formcontrolname='stsDate']")
            .as("Дата выдачи СТС"));

    private final DropDownList vinNumberDdl = new DropDownList($x("//*[@formcontrolname='vinNumber']")
            .as("Номер вин"));

    private final Button menu = new Button($x("//div[@class='header-buttons left']/button/span")
            .as("Меню"));


    @Step("Проверить заголовок страницы")
    public void checkTitle() {
        osagoTitle.shouldBe(visible);
    }

    @Step("Проскролить до видимости поля номера автомобиля")
    public OsagoPage scrollToLicenseNumber() {
        checkTitle();
        iframePreloader.checkPreloader();
        Selenide.switchTo().frame($x("//iframe[@id='angularAppIframe']").as("Блок рассчета"));
        preloader.checkPreloader();
        licenseInput.scrollIntoView();
        return this;
    }

    @Step("Проверить, что поле ввода содержит 'Введите номер'")
    public OsagoPage checkPlaceHolderEnterLicenseNumber() {
        licenseInput.checkPlaceholder("Введите номер авто");
        return this;
    }

    @Step("Ввести номер авто")
    public OsagoPage setLicenseNumber(String value) {
        licenseInput.checkPlaceholder("A 000 AA 000");
        licenseInput.setData(value);
        return this;
    }

    @Step("Кликнуть кнопку 'Рассчитать'")
    public OsagoPage initCalculate() {
        calculateButton.click();
        calculateButton.getBtnPreloader().checkPreloader();
        return this;
    }

    @Step("Проверить, что номер авто отображается")
    public OsagoPage checkLicenseNumberIsVisible(String value) {
        String actual = licenseNumber.getText().replaceAll("\\s+", "");
        assertThat(actual).isEqualTo(value);
        return this;
    }

    @Step("Выбрать из дропдауна 'Марка' значение MAZDA по полному совпадению")
    public OsagoPage chooseMark(String markName) {
        markDdl.openDdlAndChooseExactOption(markName);
        return this;
    }

    @Step("Выбрать из дропдауна 'Модель' значение CX-3 по поиску '3'")
    public OsagoPage findModelViaPartlySearch(String partName, String fullName) {
        modelDdl.setPartialValueInDdlInput(partName, fullName);
        return this;
    }

    @Step("Выбрать год '2026' путем разворачивания дропдауна и скрола до нужного значения")
    public OsagoPage chooseProductionYear(String year) {
        productionYear.scrollToValue(year);
        return this;
    }

    @Step("Проскролить страницу до элемента 'Цель использования авто'. Выбрать 'Такси'")
    public OsagoPage choosePurposeUseOf() {
        purposeOfUseDdl.scrollIntoView();
        purposeOfUseDdl.openDdlAndChooseExactOption("Такси");
        return this;
    }

    @Step("Ввести серию и номер СТС, воспользовавшись подсказкой")
    public OsagoPage inputStsNumberControl(String value) {
        stsNumberControlInput.clickTooltip();
        popOver.shouldBe(visible);
        makeScreenshot();
        stsNumberControlInput.setData(value);
        return this;
    }

    @Step("Проверить, что поле 'Мощность' недоступна для редактирования")
    public OsagoPage checkHorsePowerShouldBeDisabled() {
        horsePower.checkDdlIsDisabled();
        return this;
    }

    @Step("Активировать тогл")
    public OsagoPage activateToggle() {
        otherFormat.setChecked();
        return this;
    }

    @Step("Деактивировать тогл")
    public OsagoPage deactivateToggle() {
        otherFormat.setNotChecked();
        return this;
    }

    @Step("Кликнуть кнопку 'Продолжить'")
    public OsagoPage initContinue() {
        continueBtn.scrollIntoView();
        continueBtn.click();
        horsePower.checkViolationDdl("Укажите мощность Вашего автомобиля в л.с.");
        stsDateDdl.checkViolationDdl("Укажите корректную дату выдачи СТС документа");
        vinNumberDdl.checkViolationDdl("Заполните VIN номер");

        return this;
    }

    @Step("Вызвать боковое меню")
    public SideMenu openSideMenu() {
        switchTo().defaultContent();
        menu.scrollIntoView();
        menu.click();

        return new SideMenu();
    }

    public void makeScreenshot() {
        SelenideElement img = $("popover-container img")
                .shouldBe(visible);
        File actualImg = img.screenshot();
        try {
            if (actualImg != null) {
                Allure.addAttachment("Снимок", new FileInputStream(actualImg));
            }
        } catch (IOException ex) {
            log.warn("attachScreenshotToAllure(): FAILED\n{}", ex.getMessage());
        }

    }
}
