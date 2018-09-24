package com.example.omgimbot.hidroponik.features.auth.model;

/**
 * Created by hynra [github.com/hynra] on 20/03/2018.
 */

public class RegistrationModel {

    String name;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSignupType() {
        return signupType;
    }

    public void setSignupType(int signupType) {
        this.signupType = signupType;
    }

    public String getRole(){
        return role;
    }

    public void setRole(String role){
        this.role = role ;
    }

    String username;
    String email;
    String phone;
    String cityid;
    String password;
    String role;
    int signupType;
}


