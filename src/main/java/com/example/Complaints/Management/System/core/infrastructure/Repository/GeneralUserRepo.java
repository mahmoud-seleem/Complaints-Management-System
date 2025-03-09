package com.example.Complaints.Management.System.core.infrastructure.Repository;

import com.example.Complaints.Management.System.core.domain.entities.GeneralUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralUserRepo extends JpaRepository<GeneralUser,Long> {
    GeneralUser findByUserName(String userName);
}
