package com.example.Complaints.Management.System.services;

import com.example.Complaints.Management.System.core.application.services.StatusService;
import com.example.Complaints.Management.System.core.domain.entities.Status;
import com.example.Complaints.Management.System.core.infrastructure.Repository.StatusRepo;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class StatusServiceImpTest {

    @InjectMocks
    private StatusService statusService;

    @Mock
    private StatusRepo statusRepo;

    @Mock
    private EntityManager entityManager;

    private Status status;
    @BeforeEach
    void setUp() {
        status = new Status();
        status.setStatusType("Open");
        status.setStatusId(1L);
    }

    @Test
    void createNewStatus() {

        // Given
        String statusType = "Open";
        when(statusRepo.saveAndFlush(any(Status.class))).thenReturn(status);

        // When
        Status result = statusService.createNewStatus(statusType);

        // Then
        assertNotNull(result);
        assertEquals("Open", result.getStatusType());
    }

    @Test
    void getAllStatuses() {
        when(statusRepo.findAll()).thenReturn(List.of(status));

        // When
        List<Status> statuses = statusService.getAllStatuses();

        // Then
        assertNotNull(statuses);
        assertFalse(statuses.isEmpty());
    }

    @Test
    void updateStatus() {
        String newType = "Closed";
        when(statusRepo.findById(1L)).thenReturn(Optional.of(status));
        when(statusRepo.saveAndFlush(any(Status.class))).thenReturn(status);

        // When
        Status updatedStatus = statusService.updateStatus(1L, newType);

        // Then
        assertNotNull(updatedStatus);
        assertEquals("Closed", updatedStatus.getStatusType());
    }

    @Test
    void deleteStatus() {

        when(statusRepo.findById(1L)).thenReturn(Optional.of(status));

        // When
        statusService.deleteStatus(1L);

        // Then
        verify(statusRepo, times(1)).findById(1L);
        verify(statusRepo, times(1)).delete(status);
    }
}