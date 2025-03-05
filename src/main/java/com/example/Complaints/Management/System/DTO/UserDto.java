package com.example.Complaints.Management.System.DTO;


import jakarta.validation.constraints.NotNull;

import java.util.List;

public class UserDto {
    @NotNull
    private String userName;
    @NotNull
    private String Password;
    @NotNull
    private String Email;


    private int age;

    private List<String> phoneNumbers = null;

    public UserDto() {
    }

    public UserDto(String userName, String password, String email, List<String> phoneNumbers) {
        this.userName = userName;
        Password = password;
        Email = email;
        this.phoneNumbers = phoneNumbers;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
