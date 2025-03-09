package com.example.Complaints.Management.System.Controllers;

import com.example.Complaints.Management.System.Entities.Status;
import com.example.Complaints.Management.System.services.StatusService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @PostMapping("/new")
    public Status createNewStatus(@PathParam("statusType") String statusType){
        //System.out.println(statusType);
        return statusService.createNewStatus(statusType);
    }
    @GetMapping("/all")
    public List<Status> getPreDefinedStatuses(){
        return statusService.getAllStatuses();
    }

    @PutMapping("/update")
    public Status updateStatusType(@PathParam("id") Long id , @PathParam("newType") String newType){
        return statusService.updateStatus(id,newType);
    }

    @DeleteMapping("/{id}")
    public void deleteStatus(@PathVariable("id") Long id){
        statusService.deleteStatus(id);
    }

}
