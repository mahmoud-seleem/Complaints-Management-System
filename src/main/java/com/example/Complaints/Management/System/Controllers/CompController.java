package com.example.Complaints.Management.System.Controllers;

import com.example.Complaints.Management.System.DTO.CompDto;
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

    @GetMapping("/{id}")
    public CompDto getComplaint(@PathVariable("id") Long id ) throws IllegalAccessException {
        return  compService.getComplaintById(id);
    }

    @GetMapping("/user/{id}")
    public List<CompDto> getAllUserComplaints(@PathVariable("id") Long id ) throws IllegalAccessException {
        return  compService.getAllUserComplaints(id);
    }

    @DeleteMapping("/delete")
    public List<CompDto> deleteUserComplaint(@PathParam("userId") Long userId,@PathParam("compId") Long compId) throws IllegalAccessException {
        return  compService.deleteComplaint(userId,compId);
    }


}
