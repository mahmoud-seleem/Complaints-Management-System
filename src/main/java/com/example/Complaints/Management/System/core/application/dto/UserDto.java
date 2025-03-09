package com.example.Complaints.Management.System.core.application.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)  // Excludes null values
public class UserDto {

    private Long userId = null;

    private String userName;

    private String password;

    private String email;

    private Integer age;

    private List<String> phoneNumbers = null;

    private String token;

    public UserDto() {
    }

    public UserDto(String userName, String password, String email, List<String> phoneNumbers) {
        this.userName = userName;
        this.password = password;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
