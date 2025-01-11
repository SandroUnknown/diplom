package drivers;

import config.credentials.CredentialsConfig;
import lombok.Getter;
import org.aeonbits.owner.ConfigFactory;

@Getter
public class CredentialsConfigDriver {

    private final String todoistEmail;
    private final String todoistPassword;
    private final String todoistToken;
    private final String remoteHost;
    private final String remoteHostLogin;
    private final String remoteHostPassword;

    public CredentialsConfigDriver() {
        CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class, System.getProperties());

        this.todoistEmail = config.getTodoistEmail();
        this.todoistPassword = config.getTodoistPassword();
        this.todoistToken = config.getTodoistToken();
        this.remoteHost = config.getRemoteHost();
        this.remoteHostLogin = config.getRemoteHostLogin();
        this.remoteHostPassword = config.getRemoteHostPassword();
    }
}