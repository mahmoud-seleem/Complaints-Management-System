package com.example.Complaints.Management.System.Controllers;

import com.example.Complaints.Management.System.Entities.Status;
import com.example.Complaints.Management.System.services.StatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@SpringBootTest
@AutoConfigureMockMvc
class StatusControllerTest {


    @InjectMocks
    private StatusService statusService;

//    @Autowired
//    private StatusController statusController;

    @InjectMocks
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        //mockMvc = MockMvcBuilders.standaloneSetup(statusController).build();
    }

    @Test
    void createNewStatus() throws Exception {
        String statusType = "InProgress";
        Status status = new Status(1L, statusType);
        when(statusService.createNewStatus(statusType)).thenReturn(status);
                mockMvc.perform(post("/api/status/new")
                        .param("statusType", statusType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusType").value(statusType));
        verify(statusService, times(1)).createNewStatus(statusType);

    }
    @Test
    void getPreDefinedStatuses() {
    }

    @Test
    void updateStatusType() {

    }

    @Test
    void deleteStatus() {
    }
}