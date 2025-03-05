package com.example.Complaints.Management.System.Repository;

import com.example.Complaints.Management.System.Model.GeneralUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneralUserRepo extends JpaRepository<GeneralUser,Long> {
}
