package config.credentials;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:properties/credentials.properties"
})
public interface CredentialsConfig extends Config {

    @Key("todoist_email")
    String getTodoistEmail();

    @Key("todoist_password")
    String getTodoistPassword();

    @Key("todoist_token")
    String getTodoistToken();

    @Key("web_remote_url")
    String getRemoteWebHost();

    @Key("web_remote_login")
    String getRemoteHostLogin();

    @Key("web_remote_password")
    String getRemoteHostPassword();

    @Key("browserstack_url")
    String getBrowserstackHost();

    @Key("browserstack_user")
    String getBrowserstackUser();

    @Key("browserstack_key")
    String getBrowserstackKey();
}