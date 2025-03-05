package com.example.Complaints.Management.System.Controllers;

import com.example.Complaints.Management.System.DTO.UserDto;
import com.example.Complaints.Management.System.Model.Admin;
import com.example.Complaints.Management.System.Model.Status;
import com.example.Complaints.Management.System.Model.User;
import com.example.Complaints.Management.System.Repository.AdminRepo;
import com.example.Complaints.Management.System.Repository.ComplaintRepo;
import com.example.Complaints.Management.System.Repository.StatusRepo;
import com.example.Complaints.Management.System.services.UserService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private UserService userService;

    @Autowired
    private ComplaintRepo complaintRepo;

    @Autowired
    private StatusRepo statusRepo;

    @PostMapping("/sign-up")
    public User registerUser(@Valid @RequestBody UserDto userDto){
        return userService.registerUser(userDto);

    }
    @GetMapping("/{id}")
    public User getUser(@PathVariable("id")Long id){
        return userService.getUser(id);
    }
    @PostMapping("/admin/sign-up")
    public void registerAdmin(){
        adminRepo.save(new Admin("mahmoud"));
    }

}
