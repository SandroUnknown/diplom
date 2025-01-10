package config.mobile;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:properties/local.properties"
})
public interface LocalAndroidConfig extends Config {

    @Key("android.os_version")
    String getPlatformVersion();

    @Key("android.device")
    String getDeviceName();

    @Key("android.app_package")
    String getAppPackage();

    @Key("android.activity")
    String getAppActivity();

    @Key("appiumServerUrl")
    String getAppiumServerUrl();
}
