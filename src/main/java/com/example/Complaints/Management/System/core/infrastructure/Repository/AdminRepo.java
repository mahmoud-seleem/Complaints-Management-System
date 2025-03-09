package com.example.Complaints.Management.System.core.infrastructure.Repository;

import com.example.Complaints.Management.System.core.domain.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends JpaRepository<Admin,Long> {
    Admin findByUserName(String userName);

}
