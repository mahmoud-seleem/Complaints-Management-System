package com.example.Complaints.Management.System.core.infrastructure.Repository;

import com.example.Complaints.Management.System.core.domain.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepo extends JpaRepository<Status,Long> {
    Status findByStatusType(String statusType);
}
