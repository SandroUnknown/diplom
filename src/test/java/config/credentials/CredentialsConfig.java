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
}