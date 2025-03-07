package com.example.Complaints.Management.System.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)  // Excludes null values
public class CompDto {
    private Long compId;
    private Long ownerId;
    private Long AssigneeId;
    private String title;
    private String description;
    private String creationDate;
    private String category;
    private String currentStatus;

    public CompDto(Long compId, Long ownerId, Long assigneeId, String title, String description, String creationDate, String category, String currentStatus) {
        this.compId = compId;
        this.ownerId = ownerId;
        AssigneeId = assigneeId;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.category = category;
        this.currentStatus = currentStatus;
    }

    public CompDto() {
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getAssigneeId() {
        return AssigneeId;
    }

    public void setAssigneeId(Long assigneeId) {
        AssigneeId = assigneeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }
}
