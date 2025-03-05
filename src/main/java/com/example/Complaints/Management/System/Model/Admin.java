package com.example.Complaints.Management.System.Model;

import com.example.Complaints.Management.System.Utils.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends GeneralUser{

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Complaint> complaints = new ArrayList<>();

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<ComplaintStatus> complaintStatuses = new ArrayList<>();

    public Admin(Long userId, String userName, String password, String email, int age, Role role, List<Complaint> complaints) {
        super(userId, userName, password, email, age, role);
        this.complaints = complaints;
    }

    public Admin(List<Complaint> complaints) {
        this.complaints = complaints;
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
