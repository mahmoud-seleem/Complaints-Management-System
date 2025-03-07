package com.example.Complaints.Management.System.Controllers;

import com.example.Complaints.Management.System.DTO.CompDto;
import com.example.Complaints.Management.System.DTO.CompStatusDto;
import com.example.Complaints.Management.System.services.CompService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaint")
public class CompController {

    @Autowired
    private CompService compService;

    @PostMapping("/new")
    public CompDto createNewComplaint(@RequestBody CompDto compDto) throws IllegalAccessException {
        return  compService.createNewComplaint(compDto);
    }
    @PutMapping("/edit")
    public CompDto editComplaint(@RequestBody CompDto compDto) throws IllegalAccessException {
        return  compService.editComplaint(compDto);
    }
    @PutMapping("/change-status")
    public CompDto changeComplaintStatus(
            @PathParam("adminId") Long adminId,
            @PathParam("compId") Long compId,
            @PathParam("newType") String newType) throws IllegalAccessException {
        return  compService.changeComplaintStatus(adminId,compId,newType);
    }

    @PutMapping("/change-assignee")
    public CompDto changeComplaintAssignee(
            @PathParam("adminId") Long adminId,
            @PathParam("compId") Long compId,
            @PathParam("assignee")Long assignee) throws IllegalAccessException {
        return  compService.changeComplaintAssignee(adminId,compId,assignee);
    }

    @GetMapping("/{id}")
    public CompDto getComplaint(@PathVariable("id") Long id ) throws IllegalAccessException {
        return  compService.getComplaintById(id);
    }
    @GetMapping("/history/{id}")
    public List<CompStatusDto> getComplaintHistory(@PathVariable("id") Long id ) throws IllegalAccessException {
        return  compService.getComplaintHistory(id);
    }

    @GetMapping("/user/{id}")
    public List<CompDto> getAllUserComplaints(@PathVariable("id") Long id ) throws IllegalAccessException {
        return  compService.getAllUserComplaints(id);
    }
    @GetMapping("/admin/{id}")
    public List<CompDto> getAllAdminAssignedComplaints(@PathVariable("id") Long id ) throws IllegalAccessException {
        return  compService.getAllAdminAssignedComplaints(id);
    }

    @DeleteMapping("/delete")
    public List<CompDto> deleteUserComplaint(@PathParam("userId") Long userId,@PathParam("compId") Long compId) throws IllegalAccessException {
        return  compService.deleteComplaint(userId,compId);
    }


}
