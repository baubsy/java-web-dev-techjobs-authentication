package org.launchcode.javawebdevtechjobsauthentication.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;

@Entity
public class User extends AbstractEntity{
    private String username;
    private String password;
    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User(){}

    public User(String aUsername, String aPassword){
        this.password = encoder.encode(aPassword);
        this.username = aUsername;
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

    public Boolean checkPassword(String aPassword){
        return encoder.matches(aPassword, this.password);
    }
}
