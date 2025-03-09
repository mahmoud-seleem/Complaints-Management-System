package com.example.Complaints.Management.System.core.domain.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "complaint",indexes = {
@Index(name = "idx_creation_date", columnList = "creation_date")}
    )
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complaint_id")
    private Long compId;
    private String title = "Demo";
    private String description = "Demo-description";
//    @Column(name = "attachment_url")
//    private String attachmentUrl = null;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP) // Ensures proper Date storage
    private Date creationDate = null;

    private String category = "normal";

    @Column(name = "current_status")
    private String currentStatus = null;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @OneToMany(mappedBy = "complaint", cascade = CascadeType.ALL)
    @OrderBy("statusDate ASC")
    private List<ComplaintStatus> complaintStatuses = new ArrayList<>();

    public Complaint(Long compId, String title, String description, Date creationDate, String category, String currentStatus, User user, Admin admin, List<ComplaintStatus> complaintStatuses) {
        this.compId = compId;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.category = category;
        this.currentStatus = currentStatus;
        this.user = user;
        this.admin = admin;
        this.complaintStatuses = complaintStatuses;
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
