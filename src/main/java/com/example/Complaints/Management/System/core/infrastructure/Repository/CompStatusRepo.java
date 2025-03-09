package com.example.Complaints.Management.System.core.infrastructure.Repository;

import com.example.Complaints.Management.System.core.domain.entities.ComplaintStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompStatusRepo extends JpaRepository<ComplaintStatus,Long> {
}
