package com.example.Complaints.Management.System.Security;

import com.example.Complaints.Management.System.Entities.GeneralUser;
import com.example.Complaints.Management.System.Repository.GeneralUserRepo;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private GeneralUserRepo generalUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GeneralUser generalUser;
        try{
            generalUser = generalUserRepo.findByUserName(username);
            if (generalUser == null){
                throw new ValidationException("User Not Found");
            }
        }catch (Exception e ){
            throw new ValidationException("User Not Found");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(generalUser.getUserName())
                .password(generalUser.getPassword())
                .authorities((GrantedAuthority) () -> "ROLE_"+generalUser.getRole())
                .build();
    }
}
