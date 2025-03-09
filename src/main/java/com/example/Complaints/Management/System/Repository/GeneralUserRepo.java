package com.example.Complaints.Management.System.Repository;

import com.example.Complaints.Management.System.Entities.GeneralUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralUserRepo extends JpaRepository<GeneralUser,Long> {
    GeneralUser findByUserName(String userName);
}
