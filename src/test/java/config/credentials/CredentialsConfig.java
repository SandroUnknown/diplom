package config.credentials;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:properties/credentials.properties"
})
public interface CredentialsConfig extends Config {

    @Key("todoist.email")
    String getTodoistEmail();

    @Key("todoist.password")
    String getTodoistPassword();

    @Key("todoist.token")
    String getTodoistToken();
}