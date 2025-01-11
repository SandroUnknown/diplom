package drivers;

import config.credentials.CredentialsConfig;
import lombok.Getter;
import org.aeonbits.owner.ConfigFactory;

@Getter
public class CredentialsConfigDriver {

    private final String todoistEmail;
    private final String todoistPassword;
    private final String todoistToken;

    private final String remoteWebHost;
    private final String remoteHostLogin;
    private final String remoteHostPassword;

    private final String browserstackHost;
    private final String browserstackUser;
    private final String browserstackKey;

    public CredentialsConfigDriver() {
        CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class, System.getProperties());

        this.todoistEmail = config.getTodoistEmail();
        this.todoistPassword = config.getTodoistPassword();
        this.todoistToken = config.getTodoistToken();

        this.remoteWebHost = config.getRemoteWebHost();
        this.remoteHostLogin = config.getRemoteHostLogin();
        this.remoteHostPassword = config.getRemoteHostPassword();

        this.browserstackHost = config.getBrowserstackHost();
        this.browserstackUser = config.getBrowserstackUser();
        this.browserstackKey = config.getBrowserstackKey();
    }
}