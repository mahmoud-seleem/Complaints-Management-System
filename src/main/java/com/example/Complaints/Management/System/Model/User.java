package com.example.Complaints.Management.System.Model;

import com.example.Complaints.Management.System.Utils.Role;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(value = "USER")
public class User extends GeneralUser {

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Complaint> complaints = new ArrayList<>();

    public User(Long userId, String userName, String password, String email, int age, Role role, List<Complaint> complaints) {
        super(userId, userName, password, email, age, role);
        this.complaints = complaints;
    }

    public User(){
        super();
    }

    public User(List<Complaint> complaints) {
        this.complaints = complaints;
    }

    public List<Complaint> getComplaints() {
        return complaints;
    }

    public void setComplaints(List<Complaint> complaints) {
        this.complaints = complaints;
    }
    public void addComplaint(Complaint complaint){
        this.complaints.add(complaint);
    }
}
