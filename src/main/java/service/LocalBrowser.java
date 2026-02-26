package service;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.logevents.SelenideLogger;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import java.util.logging.Level;

public class LocalBrowser {

    public static void browserSetup() {
        SelenideLogger.addListener("listener", new AllureSelenideListener());
        Configuration.browserSize = "1920x1080";
        Configuration.screenshots = false;
        Configuration.savePageSource = false;
        Configuration.timeout = 8000;
        Configuration.baseUrl = "https://www.rgs.ru/mainb";

        LoggingPreferences loggingPreferences = new LoggingPreferences();
        loggingPreferences.enable(LogType.BROWSER, Level.ALL);
        loggingPreferences.enable(LogType.PERFORMANCE, Level.ALL);

        Configuration.fileDownload = FileDownloadMode.FOLDER;
        Configuration.pageLoadStrategy = "eager";
        Configuration.downloadsFolder = "target/downloads";
    }
}
