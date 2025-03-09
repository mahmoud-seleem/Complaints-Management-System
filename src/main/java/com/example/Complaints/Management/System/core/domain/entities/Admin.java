package com.example.Complaints.Management.System.core.domain.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends GeneralUser{

    @OneToMany(mappedBy = "admin",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Complaint> complaints = new ArrayList<>();

    @OneToMany(mappedBy = "admin",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ComplaintStatus> complaintStatuses = new ArrayList<>();

    public Admin(Long userId, String userName, String password, String email, int age, String role, List<Complaint> complaints) {
        super(userId, userName, password, email, age);
        this.complaints = complaints;
    }

    public Admin(List<Complaint> complaints) {
        this.complaints = complaints;
    }

    public Admin(String userName){
        super(userName);
    }

    public Admin(){
        super();
    }

    public List<Complaint> getComplaints() {
        return complaints;
    }

    public void setComplaints(List<Complaint> complaints) {
        this.complaints = complaints;
    }

    public List<ComplaintStatus> getComplaintStatuses() {
        return complaintStatuses;
    }

    public void setComplaintStatuses(List<ComplaintStatus> complaintStatuses) {
        this.complaintStatuses = complaintStatuses;
    }
}
