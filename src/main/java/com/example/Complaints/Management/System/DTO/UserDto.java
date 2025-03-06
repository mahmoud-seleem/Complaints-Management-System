package com.example.Complaints.Management.System.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)  // Excludes null values
public class UserDto {

    private Long userId = null;

    private String userName;

    private String Password;

    private String email;

    private int age;

    private List<String> phoneNumbers = null;

    public UserDto() {
    }

    public UserDto(String userName, String password, String email, List<String> phoneNumbers) {
        this.userName = userName;
        Password = password;
        this.email = email;
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
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
