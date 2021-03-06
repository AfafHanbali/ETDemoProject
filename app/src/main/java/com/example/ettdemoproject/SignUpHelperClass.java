package com.example.ettdemoproject;

/**
 * @author : Afaf Hanbali
 * Created on 2020-Oct-5
 */
public class SignUpHelperClass {

    private String name;
    private String username;
    private String password;
    private String email;

    public SignUpHelperClass() {

    }

    public SignUpHelperClass(String name, String username, String password, String email) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
