package com.example.Complaints.Management.System.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Long statusId;

    @Column(name = "status_type")
    private String statusType;

    @OneToMany(mappedBy = "status",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ComplaintStatus> complaintStatuses = new ArrayList<>();

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public List<ComplaintStatus> getComplaintStatuses() {
        return complaintStatuses;
    }

    public void setComplaintStatuses(List<ComplaintStatus> complaintStatuses) {
        this.complaintStatuses = complaintStatuses;
    }
}
