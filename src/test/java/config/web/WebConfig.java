package config.web;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:properties/${env}.properties",
        "classpath:properties/local.properties"
})
public interface WebConfig extends Config {

    @Key("web.browser.name")
    @DefaultValue("chrome")
    String getBrowserName();

    @Key("web.browser.version")
    @DefaultValue("125.0")
    String getBrowserVersion();

    @Key("web.browser.size")
    @DefaultValue("1920x1080")
    String getBrowserSize();

    @Key("web.remote_url")
    String getRemoteUrl();

    @Key("web.remote_login")
    String getRemoteLogin();

    @Key("web.remote_password")
    String getRemotePassword();

    @Key("web.page_load_strategy")
    @DefaultValue("eager")
    String getPageLoadStrategy();

    @Key("web.timeout")
    @DefaultValue("5000")
    long getTimeout();

    @Key("web.base_url")
    String getBaseUrl();
}