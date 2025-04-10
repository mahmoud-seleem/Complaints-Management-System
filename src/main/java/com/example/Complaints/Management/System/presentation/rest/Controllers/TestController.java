package com.example.Complaints.Management.System.presentation.rest.Controllers;

import com.example.Complaints.Management.System.core.domain.services.ExternalApiServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private ExternalApiServiceImp externalApiService;


    @GetMapping("/random-joke")
    public ResponseEntity<Object> getRandomJoke(){
        return ResponseEntity.ok(externalApiService.getRandomJoke());
    }
}
