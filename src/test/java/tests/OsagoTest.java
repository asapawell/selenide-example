package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestTemplate;
import pages.MainPage;
import pages.OsagoPage;
import service.Env;

import static com.codeborne.selenide.Selenide.open;

public class OsagoTest {

    private final MainPage mainPage = new MainPage();
    private final OsagoPage osagoPage = new OsagoPage();

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
        open("/");

        initiateOsagoInsurance();

        fillCarData();

        fillOwnerData();

        verifySideMenu();
    }

    private void initiateOsagoInsurance() {
        mainPage
                .chooseAutoCategory()
                .checkOsagoCard()
                .scrollToLicenseNumber();
    }

    private void fillCarData() {
        osagoPage
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
                .chooseLastProductionYearByScrolling(PROD_YEAR);
    }

    private void fillOwnerData() {
        osagoPage
                .choosePurposeUseOf()
                .inputStsNumberControl(STS)
                .clickContinue();
    }

    private void verifySideMenu() {
        osagoPage
                .openSideMenu()
                .checkHeader()
                .goToDms()
                .checkTitle();
    }
}
