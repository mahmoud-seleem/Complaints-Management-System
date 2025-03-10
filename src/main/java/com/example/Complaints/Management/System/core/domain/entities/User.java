package com.example.Complaints.Management.System.core.domain.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(value = "USER")
public class User extends GeneralUser {

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Complaint> complaints = new ArrayList<>();

    public User(Long userId, String userName, String password, String email, int age, List<Complaint> complaints) {
        super(userId, userName, password, email, age);
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
