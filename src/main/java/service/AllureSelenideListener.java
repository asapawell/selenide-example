package service;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.LogEvent;
import com.codeborne.selenide.logevents.LogEventListener;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StatusDetails;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.util.ResultsUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.UUID;

@Slf4j
public class AllureSelenideListener implements LogEventListener {

    private final AllureLifecycle lifecycle = Allure.getLifecycle();

    @Override
    public void afterEvent(LogEvent logEvent) {
        String uuid = UUID.randomUUID().toString();
        lifecycle.startStep(uuid, new StepResult().setName(logEvent.toString()).setStatus(Status.PASSED));

        if (logEvent.getStatus().equals(LogEvent.EventStatus.FAIL)) {
            if (logEvent.getError().getCause() != null && logEvent.getError().getCause().getMessage().contains("unexpected alert open")) {
                Alert alert = Selenide.switchTo().alert();
                alert.accept();
            } else {
                lifecycle.addAttachment("screenshot", "image/png", "png", getScreenshotBytes());
            }
            lifecycle.updateStep(uuid, stepResult -> {
                StatusDetails statusDetails = ResultsUtils.getStatusDetails(logEvent.getError()).orElse(new StatusDetails());
                stepResult.setStatus(ResultsUtils.getStatus(logEvent.getError()).orElse(Status.BROKEN));
                stepResult.setStatusDetails(statusDetails);
            });
        }
        lifecycle.stopStep(uuid);
    }

    private byte[] getScreenshotBytes() {
        try {
            WebDriver driver = WebDriverRunner.getWebDriver();
            if (driver instanceof TakesScreenshot) {
                return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            } else {
                log.warn("Driver does not support screenshots: {}", driver.getClass().getName());
            }
        } catch (WebDriverException e) {
            log.error("Failed to take screenshot", e);
        }
        return new byte[0];
    }

    @Override
    public void beforeEvent(LogEvent logEvent) {

    }
}
