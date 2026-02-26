package service;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class EnvAnnotationProcessor implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        // поддерживаем, если аннотация @Env есть на методе или на классе
        return findEnv(context).isPresent();
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        var env = findEnv(context).orElseThrow();
        return Stream.of(env.value()).map(BrowserInvocationContext::new);
    }

    private java.util.Optional<Env> findEnv(ExtensionContext ctx) {
        var method = ctx.getRequiredTestMethod().getAnnotation(Env.class);
        if (method != null) return java.util.Optional.of(method);
        var cls = ctx.getRequiredTestClass().getAnnotation(Env.class);
        return java.util.Optional.ofNullable(cls);
    }

    private record BrowserInvocationContext(Env.Browser browser) implements TestTemplateInvocationContext {

        @Override
        public String getDisplayName(int invocationIndex) {
            return "browser: " + browser.name();
        }

        @Override
        public List<Extension> getAdditionalExtensions() {
            return Collections.singletonList(new BrowserLifecycleExtension(browser));
        }
    }

    private record BrowserLifecycleExtension(Env.Browser browser) implements BeforeEachCallback {

        @Override
        public void beforeEach(ExtensionContext context) {
            LocalBrowser.browserSetup();
            switch (browser) {
                case chrome -> WebDriverRunner.setWebDriver(new ChromeDriver(new ChromeOptions()));
                case firefox -> WebDriverRunner.setWebDriver(new FirefoxDriver(new FirefoxOptions()));
                default -> throw new IllegalStateException("Unknown browser: " + browser);
            }
        }
    }
}