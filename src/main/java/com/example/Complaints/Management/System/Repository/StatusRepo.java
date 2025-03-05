package com.example.Complaints.Management.System.Repository;

import com.example.Complaints.Management.System.Model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepo extends JpaRepository<Status,Long> {
}
