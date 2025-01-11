package drivers;

import com.codeborne.selenide.Configuration;
import config.credentials.CredentialsConfig;
import config.web.WebConfig;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static java.lang.String.format;

public class WebConfigDriver {

    private final WebConfig webConfig;
    private final CredentialsConfig credentialConfig;

    public WebConfigDriver() {
        this.webConfig = ConfigFactory.create(WebConfig.class, System.getProperties());
        this.credentialConfig = ConfigFactory.create(CredentialsConfig.class, System.getProperties());
        createDriver();
    }

    private void createDriver() {

        Configuration.browser = webConfig.getBrowserName();
        Configuration.browserVersion = webConfig.getBrowserVersion();
        Configuration.browserSize = webConfig.getBrowserSize();

        Configuration.pageLoadStrategy = webConfig.getPageLoadStrategy();
        Configuration.timeout = webConfig.getTimeout();

        Configuration.baseUrl = webConfig.getBaseUrl();

        String url = credentialConfig.getRemoteHost();
        if (!url.isEmpty()) {

            String login = credentialConfig.getRemoteHostLogin();
            String password = credentialConfig.getRemoteHostPassword();
            if (login != null && password != null) {
                Configuration.remote = format("https://%s:%s@%s/wd/hub", login, password, url);

                // TODO : удалить
                System.out.println(Configuration.remote);
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