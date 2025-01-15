package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.CredentialsConfig;
import config.MobileConfig;
import helpers.browserstack.Browserstack;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;

public class BrowserstackConfigDriver implements WebDriverProvider {

    private final MobileConfig mobileConfig;
    private final CredentialsConfig credentialsConfig;

    public BrowserstackConfigDriver() {
        this.mobileConfig = ConfigFactory.create(MobileConfig.class, System.getProperties());
        this.credentialsConfig = ConfigFactory.create(CredentialsConfig.class, System.getProperties());
    }

    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {

        Browserstack browserstack = new Browserstack();
        MutableCapabilities caps = new MutableCapabilities();

        caps.setCapability("browserstack.user", credentialsConfig.getBrowserstackUser());
        caps.setCapability("browserstack.key", credentialsConfig.getBrowserstackKey());
        caps.setCapability("app", browserstack.getAppUrl(mobileConfig.getAppName()));
        caps.setCapability("device", mobileConfig.getDeviceName());
        caps.setCapability("os_version", mobileConfig.getOsVersion());

        try {
            return new RemoteWebDriver(
                    new URL(credentialsConfig.getBrowserstackHost()), caps);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Неверные данные для Browserstak: " + e.getMessage(), e);
        }
    }
}