package com.example.Complaints.Management.System.core.application.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)  // Excludes null values
public class AdminDto extends GeneralUserDto {


    public AdminDto() {
    }

    public AdminDto(String userName, String password, String email, List<String> phoneNumbers) {
        super(userName, password, email, phoneNumbers);
    }
}
