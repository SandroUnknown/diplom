package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.CredentialsConfig;
import config.MobileConfig;
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

public class EmulationConfigDriver implements WebDriverProvider {

    private final MobileConfig mobileConfig;
    private final CredentialsConfig credentialsConfig;

    public EmulationConfigDriver() {

        this.mobileConfig = ConfigFactory.create(MobileConfig.class, System.getProperties());
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
            throw new RuntimeException("Неверный URL для Appium сервера: " + e.getMessage(), e);
        }
    }

    private String getAppPath() {

        String appName = mobileConfig.getAppName();
        String appPath = "src/test/resources/apps/" + appName;

        File app = new File(appPath);
        if (!app.exists()) {
            throw new RuntimeException("Приложение не найдено по пути: " + app.getAbsolutePath());
        }
        return app.getAbsolutePath();
    }
}