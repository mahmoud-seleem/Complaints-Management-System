package com.example.Complaints.Management.System.Repository;

import com.example.Complaints.Management.System.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    User findByUserNameIgnoreCase(String userName);
    User findByUserName(String userName);

}
