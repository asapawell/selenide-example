package tests;

import basetest.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestTemplate;
import pages.MainPage;
import service.Env;

public class OsagoTest extends BaseTest {

    private final MainPage mainPage = new MainPage();

    private final static String LICENSE_NUMBER = "Р444ЕВ44";
    private final static String MARK = "MAZDA";
    private final static String PART_OF_MODEL_NAME = "3";
    private final static String MODEL = "CX-3";
    private final static String STS = "1234567879";
    private final static String PROD_YEAR = "2026";

    @Env({Env.Browser.chrome, Env.Browser.firefox})
    @TestTemplate
    @DisplayName("Рассчитать стоимость ОСАГО")
    public void calculateOsagoTest() {
        mainPage
                .chooseAutoCategory()
                .checkOsagoCard()
                .scrollToLicenseNumber()
                .setLicenseNumber(LICENSE_NUMBER)
                .activateToggle()
                .checkPlaceHolderEnterLicenseNumber()
                .deactivateToggle()
                .setLicenseNumber(LICENSE_NUMBER)
                .initCalculate()
                .checkLicenseNumberIsVisible(LICENSE_NUMBER)
                .chooseMark(MARK)
                .findModelViaPartlySearch(PART_OF_MODEL_NAME, MODEL)
                .checkHorsePowerShouldBeDisabled()
                .chooseProductionYear(PROD_YEAR)
                .choosePurposeUseOf()
                .inputStsNumberControl(STS)
                .initContinue()
                .openSideMenu()
                .checkHeader()
                .goToDms()
                .checkTitle();
    }
}
