package com.example.Complaints.Management.System.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)  // Excludes null values
public class CompStatusDto {
    private String status;
    private String Date;
    private Long changerId;

    public CompStatusDto(String status, String date, Long changerId) {
        this.status = status;
        Date = date;
        this.changerId = changerId;
    }

    public CompStatusDto() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Long getChangerId() {
        return changerId;
    }

    public void setChangerId(Long changerId) {
        this.changerId = changerId;
    }
}

