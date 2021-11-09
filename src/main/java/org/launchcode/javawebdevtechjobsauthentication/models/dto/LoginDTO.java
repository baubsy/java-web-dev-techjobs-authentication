package org.launchcode.javawebdevtechjobsauthentication.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginDTO {
    @NotNull
    @Size(min=3, max=15)
    private String username;
    @NotNull
    @Size(min=4, max=20)
    private String password;

    public LoginDTO(){};
    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
