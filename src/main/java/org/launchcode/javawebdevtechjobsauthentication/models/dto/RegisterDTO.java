package org.launchcode.javawebdevtechjobsauthentication.models.dto;

public class RegisterDTO extends LoginDTO{
    private String verifyPassword;

    public RegisterDTO(){};
    public RegisterDTO(String username, String password, String verifyPassword) {
        super(username, password);
        this.verifyPassword = verifyPassword;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }
}
