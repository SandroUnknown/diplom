package config.mobile;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:properties/${env}.properties",
        "classpath:properties/local.properties"
        //"classpath:properties/browserstack.properties"
})
public interface RemoveAndroidConfig extends Config {

    @Key("android_device")
    String getDevice();

    @Key("android_os_version")
    String getOsVersion();

    //============

    @Key("android_app_name")
    String getAppName();


}