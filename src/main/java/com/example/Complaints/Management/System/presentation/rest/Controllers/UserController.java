package com.example.Complaints.Management.System.presentation.rest.Controllers;

import com.example.Complaints.Management.System.core.application.dto.CompDto;
import com.example.Complaints.Management.System.core.application.dto.CompStatusDto;
import com.example.Complaints.Management.System.core.application.dto.UserDto;
import com.example.Complaints.Management.System.core.domain.entities.Status;
import com.example.Complaints.Management.System.core.domain.services.CompService;
import com.example.Complaints.Management.System.core.domain.services.StatusService;
import com.example.Complaints.Management.System.core.domain.services.UserService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CompService compService;

    @Autowired
    private StatusService statusService;
//    @PostMapping("/sign-up")
//    public UserDto registerUser(@Valid @RequestBody UserDto userDto){
//        return userService.registerUser(userDto);
//    }
    @PutMapping("/update-info")
    public UserDto updateUser(@Valid @RequestBody UserDto userDto) throws Exception{
        return userService.updateUser(userDto);
    }
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }
    @DeleteMapping("/{id}")
    public UserDto deleteUser(@Valid @PathVariable("id") Long id){
        return userService.deleteUser(id);
    }
    @PostMapping("/new-complaint")
    public CompDto createNewComplaint(@RequestBody CompDto compDto) throws IllegalAccessException {
        return  compService.createNewComplaint(compDto);
    }
    @PutMapping("/complaint/edit")
    public CompDto editComplaint(@RequestBody CompDto compDto) throws IllegalAccessException {
        return  compService.editComplaint(compDto);
    }
    @GetMapping("/complaint/{id}")
    public CompDto getComplaint(@PathVariable("id") Long id ) throws IllegalAccessException {
        return  compService.getComplaintById(id);
    }
    @GetMapping("/complaint/history/{id}")
    public List<CompStatusDto> getComplaintHistory(@PathVariable("id") Long id ) throws IllegalAccessException {
        return  compService.getComplaintHistory(id);
    }
    @GetMapping("/complaints/{id}")
    public List<CompDto> getAllUserComplaints(@PathVariable("id") Long id ) throws IllegalAccessException {
        return  compService.getAllUserComplaints(id);
    }
    @DeleteMapping("/complaint/delete")
    public List<CompDto> deleteUserComplaint(@PathParam("userId") Long userId, @PathParam("compId") Long compId) throws IllegalAccessException {
        return  compService.deleteComplaint(userId,compId);
    }
    @GetMapping("/complaints/{userId}/next")
    public List<CompDto> getUserNextComplaints(
            @PathVariable Long userId,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(required = false) String status) throws IllegalAccessException {
        return compService.getUserNextComplaints(userId,cursor,size,status,false);
    }

    @GetMapping("/complaints/{userId}/prev")
    public List<CompDto> getUserPrevComplaints(
            @PathVariable Long userId,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(required = false) String status) throws IllegalAccessException {
        return compService.getUserPrevComplaints(userId,cursor,size,status,false);
    }
    @GetMapping("/all-statuses")
    public List<Status> getPreDefinedStatuses(){
        return statusService.getAllStatuses();
    }

}
