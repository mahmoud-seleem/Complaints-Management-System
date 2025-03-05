package com.example.Complaints.Management.System.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "complaint")
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complaint_id")
    private Long compId;
    private String title;
    private String description;
    @Column(name = "attachment_url")
    private String attachmentUrl;

    @Column(name = "creation_date")
    private Date creationDate;

    private String category;

    @Column(name = "current_status")
    private String currentStatus;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @OneToMany(mappedBy = "complaint", cascade = CascadeType.ALL)
    private List<ComplaintStatus> complaintStatuses = new ArrayList<>();

    public Complaint(Long compId, String title, String description, String attachmentUrl, Date creationDate, String category, String currentStatus) {
        this.compId = compId;
        this.title = title;
        this.description = description;
        this.attachmentUrl = attachmentUrl;
        this.creationDate = creationDate;
        this.category = category;
        this.currentStatus = currentStatus;
    }

    public Complaint() {
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
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

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public List<ComplaintStatus> getComplaintStatuses() {
        return complaintStatuses;
    }

    public void setComplaintStatuses(List<ComplaintStatus> complaintStatuses) {
        this.complaintStatuses = complaintStatuses;
    }
}
