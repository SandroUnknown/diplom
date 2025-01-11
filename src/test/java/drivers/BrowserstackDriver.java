package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.credentials.CredentialsConfig;
import config.mobile.RemoveAndroidConfig;
import helpers.Browserstack;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;

public class BrowserstackDriver implements WebDriverProvider {

    private final RemoveAndroidConfig deviceConfig;
    private final CredentialsConfig credentialsConfig;

    // TODO : переписать
    public BrowserstackDriver() {
        this.deviceConfig = ConfigFactory.create(RemoveAndroidConfig.class, System.getProperties());
        this.credentialsConfig = ConfigFactory.create(CredentialsConfig.class, System.getProperties());
    }

    Browserstack browserstack = new Browserstack();

    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {
        MutableCapabilities caps = new MutableCapabilities();

        //caps.setCapability("browserstack.user", deviceConfig.getBrowserstackUser());
        //caps.setCapability("browserstack.key", deviceConfig.getBrowserstackKey());
        caps.setCapability("browserstack.user", credentialsConfig.getBrowserstackUser());
        caps.setCapability("browserstack.key", credentialsConfig.getBrowserstackKey());
        caps.setCapability("app", browserstack.checkUploadedAppsList());
        caps.setCapability("device", deviceConfig.getDevice());
        caps.setCapability("os_version", deviceConfig.getOsVersion());

        try {
            return new RemoteWebDriver(
                    //new URL(deviceConfig.getRemoteUrl()), caps);
                    new URL(credentialsConfig.getBrowserstackHost()), caps);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}