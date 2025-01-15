package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.credentials.CredentialsConfig;
import config.mobile.RemoveAndroidConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import javax.annotation.Nonnull;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static io.appium.java_client.remote.AutomationName.ANDROID_UIAUTOMATOR2;
import static io.appium.java_client.remote.MobilePlatform.ANDROID;

public class EmulationDriver implements WebDriverProvider {

    private final RemoveAndroidConfig mobileConfig;
    private final CredentialsConfig credentialsConfig;

    public EmulationDriver() {

        this.mobileConfig = ConfigFactory.create(RemoveAndroidConfig.class, System.getProperties());
        this.credentialsConfig = ConfigFactory.create(CredentialsConfig.class, System.getProperties());
    }

    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {

        UiAutomator2Options options = new UiAutomator2Options();

        options.setAutomationName(ANDROID_UIAUTOMATOR2)
                .setPlatformName(ANDROID)
                .setPlatformVersion(mobileConfig.getOsVersion())
                .setDeviceName(mobileConfig.getDeviceName())
                .setApp(getAppPath())
                .setAppPackage(mobileConfig.getAppPackage())
                .setAppActivity(mobileConfig.getAppActivity());

        return new AndroidDriver(getAppiumServerUrl(), options);
    }

    public URL getAppiumServerUrl() {
        try {
            return new URL(credentialsConfig.getEmulatorUrl());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getAppPath() {

        String appName = mobileConfig.getAppName();
        String appPath = "src/test/resources/apps/" + appName;

        File app = new File(appPath);
        if (!app.exists()) {
            // TODO : вызвать исключение, что приложение не обнаружено в папке ресурс/апп
        }
        return app.getAbsolutePath();
    }
}