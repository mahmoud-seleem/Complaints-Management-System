package com.example.Complaints.Management.System.Controllers;

import com.example.Complaints.Management.System.DTO.AdminDto;
import com.example.Complaints.Management.System.DTO.UserDto;
import com.example.Complaints.Management.System.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/sign-up")
    public AdminDto registerAdmin(@Valid @RequestBody AdminDto adminDto){
        return adminService.registerAdmin(adminDto);
    }
    @PutMapping("/update")
    public AdminDto updateAdmin(@Valid @RequestBody AdminDto adminDto) throws Exception{
        return adminService.updateAdmin(adminDto);
    }
    @GetMapping("/{id}")
    public AdminDto getAdmin(@PathVariable("id") Long id){
        return adminService.getAdminById(id);
    }
    @DeleteMapping("/{id}")
    public AdminDto deleteAdmin(@Valid @PathVariable("id") Long id){
        return adminService.deleteAdmin(id);
    }
}
