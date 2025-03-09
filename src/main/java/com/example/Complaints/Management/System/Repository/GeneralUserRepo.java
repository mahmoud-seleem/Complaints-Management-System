package com.example.Complaints.Management.System.Repository;

import com.example.Complaints.Management.System.Model.Admin;
import com.example.Complaints.Management.System.Model.GeneralUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralUserRepo extends JpaRepository<GeneralUser,Long> {
    GeneralUser findByUserName(String userName);
}
