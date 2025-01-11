package drivers;

import config.credentials.CredentialsConfig;
import lombok.Getter;
import org.aeonbits.owner.ConfigFactory;

@Getter
public class CredentialsConfigDriver {

    private final String email;
    private final String password;
    private final String token;

    public CredentialsConfigDriver() {
        CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class, System.getProperties());

        this.email = config.getTodoistEmail();
        this.password = config.getTodoistPassword();
        this.token = config.getTodoistToken();
    }
}