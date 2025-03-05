package com.example.Complaints.Management.System.Repository;

import com.example.Complaints.Management.System.Model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepo extends JpaRepository<Complaint,Long> {
}
