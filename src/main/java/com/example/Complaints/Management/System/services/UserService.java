package com.example.Complaints.Management.System.services;

import com.example.Complaints.Management.System.DTO.UserDto;
import com.example.Complaints.Management.System.Model.User;
import com.example.Complaints.Management.System.Repository.UserRepo;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User registerUser(UserDto userDto){
        User user;
        //userRepo.findByUserNameIgnoreCase(userDto.getUserName());
//        if(user != null){
//            throw new ValidationException("User already exists");
//        }
        user = new User();
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        List<String> phones = user.getPhoneNumbers();
        for (String phone : userDto.getPhoneNumbers()){
            phones.add(phone);
        }
         userRepo.save(user);
        return userRepo.findByUserNameIgnoreCase(user.getUserName());
    }
    public UserService() {
    }

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    public User getUser(Long id) {
        return userRepo.findById(id).get();
    }
}
