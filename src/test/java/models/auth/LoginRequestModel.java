package models.auth;

import lombok.Data;

@Data
public class LoginRequestModel {
    String userName, password;
}