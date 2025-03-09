package com.example.Complaints.Management.System.Repository;

import com.example.Complaints.Management.System.Model.Admin;
import com.example.Complaints.Management.System.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends JpaRepository<Admin,Long> {
    Admin findByUserName(String userName);

}
