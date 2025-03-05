package com.example.Complaints.Management.System.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simple")
public class SimpleController {

    @GetMapping("/user")
    public String getUser(){
        return "User";
    }
}
