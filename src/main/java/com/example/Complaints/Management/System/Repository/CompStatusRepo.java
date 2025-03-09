package com.example.Complaints.Management.System.Repository;

import com.example.Complaints.Management.System.Entities.ComplaintStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompStatusRepo extends JpaRepository<ComplaintStatus,Long> {
}
