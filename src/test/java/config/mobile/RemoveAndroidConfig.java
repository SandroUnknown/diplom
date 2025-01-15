package config.mobile;

import org.aeonbits.owner.Config;

@Config.Sources({
        //"classpath:properties/remove.properties"
        "classpath:properties/browserstack.properties"
})
public interface RemoveAndroidConfig extends Config {

    @Key("app")
    String getApp();

    @Key("device")
    String getDevice();

    @Key("os_version")
    String getOsVersion();

    //============

   /* @Key("android_app")
    String getApp();

    @Key("android_device")
    String getDevice();

    @Key("android_os_version")
    String getOsVersion();*/


    //============

    /*@Key("browserstack.user")
    String getBrowserstackUser();

    @Key("browserstack.key")
    String getBrowserstackKey();

    @Key("android.app")
    String getApp();

    @Key("remoteUrl")
    String getRemoteUrl();

    @Key("android.device")
    String getDevice();

    @Key("android.os_version")
    String getOsVersion();*/
}