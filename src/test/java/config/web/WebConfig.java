package config.web;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:properties/${env}.properties",
        "classpath:properties/local.properties"
})
public interface WebConfig extends Config {

    @Key("web_browser_name")
    @DefaultValue("chrome")
    String getBrowserName();

    @Key("web_browser_version")
    @DefaultValue("125.0")
    String getBrowserVersion();

    @Key("web_browser_size")
    @DefaultValue("1920x1080")
    String getBrowserSize();

    /*@Key("web_remote_url")
    String getRemoteUrl();

    @Key("web_remote_login")
    String getRemoteLogin();

    @Key("web_remote_password")
    String getRemotePassword();*/

    @Key("web_page_load_strategy")
    @DefaultValue("eager")
    String getPageLoadStrategy();

    @Key("web_timeout")
    @DefaultValue("5000")
    long getTimeout();

    @Key("web_base_url")
    String getBaseUrl();
}