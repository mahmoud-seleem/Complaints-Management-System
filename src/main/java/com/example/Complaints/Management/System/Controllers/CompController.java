package com.example.Complaints.Management.System.Controllers;

import com.example.Complaints.Management.System.DTO.CompDto;
import com.example.Complaints.Management.System.services.CompService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


}
