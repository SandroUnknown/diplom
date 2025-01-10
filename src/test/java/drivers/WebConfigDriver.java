package drivers;

import com.codeborne.selenide.Configuration;
import config.web.WebConfig;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static java.lang.String.format;

public class WebConfigDriver {

    private final WebConfig config;

    public WebConfigDriver() {
        this.config = ConfigFactory.create(WebConfig.class, System.getProperties());
        createDriver();
    }

    private void createDriver() {

        Configuration.browser = config.getBrowserName();
        Configuration.browserVersion = config.getBrowserVersion();
        Configuration.browserSize = config.getBrowserSize();

        Configuration.pageLoadStrategy = config.getPageLoadStrategy();
        Configuration.timeout = config.getTimeout();

        Configuration.baseUrl = config.getBaseUrl();

        String url = config.getRemoteUrl();
        if (!url.isEmpty()) {

            String login = config.getRemoteLogin();
            String password = config.getRemotePassword();
            if (login != null && password != null) {
                Configuration.remote = format("https://%s:%s@%s", login, password, url);
            }

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                    "enableVNC", true,
                    "enableVideo", true
            ));

            Configuration.browserCapabilities = capabilities;
        }
    }
}