package com.example.Complaints.Management.System.Controllers;

import com.example.Complaints.Management.System.DTO.UserDto;
import com.example.Complaints.Management.System.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public UserDto registerUser(@Valid @RequestBody UserDto userDto){
        return userService.registerUser(userDto);
    }
    @PutMapping("/update")
    public UserDto updateUser(@Valid @RequestBody UserDto userDto) throws Exception{
        return userService.updateUser(userDto);
    }
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") Long id){
        return userService.getAdminById(id);
    }
    @DeleteMapping("/{id}")
    public UserDto deleteUser(@Valid @PathVariable("id") Long id){
        return userService.deleteUser(id);
    }
}
