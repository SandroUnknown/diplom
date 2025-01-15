package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:properties/${env}.properties",
        "classpath:properties/local.properties"
})
public interface MobileConfig extends Config {

    @Key("mobile_device")
    String getDeviceName();

    @Key("mobile_os_version")
    String getOsVersion();

    @Key("android_app_name")
    String getAppName();

    @Key("android_app_package")
    String getAppPackage();

    @Key("android_app_activity")
    String getAppActivity();
}