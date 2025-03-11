package com.example.Complaints.Management.System.presentation.rest.Controllers;

import com.example.Complaints.Management.System.core.application.dto.AdminDto;
import com.example.Complaints.Management.System.core.application.dto.CompDto;
import com.example.Complaints.Management.System.core.application.dto.CompStatusDto;
import com.example.Complaints.Management.System.core.application.services.AdminService;
import com.example.Complaints.Management.System.core.application.services.CompService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CompService compService;

    @PutMapping("/update-info")
    public AdminDto updateAdmin(@Valid @RequestBody AdminDto adminDto) throws Exception{
        return adminService.updateAdmin(adminDto);
    }
    @GetMapping("/{id}")
    public AdminDto getAdmin(@PathVariable("id") Long id) throws IllegalAccessException {
        return adminService.getAdminById(id);
    }
    @DeleteMapping("/{id}")
    public AdminDto deleteAdmin(@Valid @PathVariable("id") Long id){
        return adminService.deleteAdmin(id);
    }

    @PutMapping("/complaint/change-status")
    public CompDto changeComplaintStatus(
            @PathParam("adminId") Long adminId,
            @PathParam("compId") Long compId,
            @PathParam("newType") String newType) throws IllegalAccessException {
        return  compService.changeComplaintStatus(adminId,compId,newType);
    }

    @PutMapping("/complaint/change-assignee")
    public CompDto changeComplaintAssignee(
            @PathParam("adminId") Long adminId,
            @PathParam("compId") Long compId,
            @PathParam("assignee")Long assignee) throws IllegalAccessException {
        return  compService.changeComplaintAssignee(adminId,compId,assignee);
    }
    @GetMapping("/complaint/{id}")
    public CompDto getComplaint(@PathVariable("id") Long id ) throws IllegalAccessException {
        return  compService.getComplaintById(id);
    }
    @GetMapping("/complaint/history/{id}")
    public List<CompStatusDto> getComplaintHistory(@PathVariable("id") Long id ) throws IllegalAccessException {
        return  compService.getComplaintHistory(id);
    }
    @GetMapping("/complaints/user/{id}")
    public List<CompDto> getAllUserComplaints(@PathVariable("id") Long id ) throws IllegalAccessException {
        return  compService.getAllUserComplaints(id);
    }
    @GetMapping("/complaints/admin/{id}")
    public List<CompDto> getAllAdminAssignedComplaints(@PathVariable("id") Long id ) throws IllegalAccessException {
        return  compService.getAllAdminAssignedComplaints(id);
    }
    @GetMapping("/complaints/user/{userId}/next")
    public List<CompDto> getUserNextComplaints(
            @PathVariable Long userId,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(required = false) String status) throws IllegalAccessException {
        return compService.getUserNextComplaints(userId,cursor,size,status,false);
    }

    @GetMapping("/complaints/user/{userId}/prev")
    public List<CompDto> getUserPrevComplaints(
            @PathVariable Long userId,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(required = false) String status) throws IllegalAccessException {
        return compService.getUserPrevComplaints(userId,cursor,size,status,false);
    }
    @GetMapping("/complaints/{userId}/next")
    public List<CompDto> getAdminNextComplaints(
            @PathVariable Long userId,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(required = false) String status) throws IllegalAccessException {
        return compService.getUserNextComplaints(userId,cursor,size,status,true);
    }

    @GetMapping("/complaints/{userId}/prev")
    public List<CompDto> getAdminPrevComplaints(
            @PathVariable Long userId,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(required = false) String status) throws IllegalAccessException {
        return compService.getUserPrevComplaints(userId,cursor,size,status,true);
    }
}
