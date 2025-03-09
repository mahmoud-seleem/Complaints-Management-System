package com.example.Complaints.Management.System.services;

import com.example.Complaints.Management.System.core.application.dto.CompDto;
import com.example.Complaints.Management.System.core.domain.entities.Admin;
import com.example.Complaints.Management.System.core.domain.entities.Complaint;
import com.example.Complaints.Management.System.core.domain.entities.Status;
import com.example.Complaints.Management.System.core.domain.entities.User;
import com.example.Complaints.Management.System.core.domain.services.CompService;
import com.example.Complaints.Management.System.core.infrastructure.Repository.*;
import jakarta.persistence.EntityManager;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompServiceTest {

    @InjectMocks
    private CompService compService;

    @Mock
    private ComplaintRepo complaintRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private AdminRepo adminRepo;

    @Mock
    private StatusRepo statusRepo;

    @Mock
    private CompStatusRepo compStatusRepo;

    @Mock
    private EntityManager entityManager;

    private User mockUser;
    private Admin mockAdmin;
    private Admin mockAdmin2;
    private Complaint mockComplaint;
    private CompDto mockCompDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock data setup
        mockUser = new User();
        mockUser.setUserId(1L);

        mockAdmin = new Admin();
        mockAdmin.setUserId(2L);

        mockAdmin2 = new Admin();
        mockAdmin2.setUserId(3L);

        mockComplaint = new Complaint();
        mockComplaint.setCompId(1L);
        mockComplaint.setCreationDate(new Date());
        mockComplaint.setUser(mockUser);
        mockComplaint.setAdmin(mockAdmin);

        mockCompDto = new CompDto();
        mockCompDto.setCompId(1L);
        mockCompDto.setTitle("Demo");
        mockCompDto.setDescription("Complaint Description");
        mockCompDto.setCategory("General");
        mockCompDto.setOwnerId(1L);
        mockCompDto.setAssigneeId(2L);
    }

    // Happy path for creating a complaint
    @Test
    void testCreateNewComplaint() throws IllegalAccessException {
        when(userRepo.findById(1L)).thenReturn(Optional.of(mockUser));
        when(adminRepo.findById(1L)).thenReturn(Optional.of(mockAdmin));
        when(complaintRepo.saveAndFlush(any(Complaint.class))).thenReturn(mockComplaint);
        when(statusRepo.findByStatusType("Submitted")).thenReturn(new Status(6,"Submitted"));

        CompDto result = compService.createNewComplaint(mockCompDto);

        assertNotNull(result);
        assertEquals("Demo", result.getTitle());
        assertEquals("Complaint Description", result.getDescription());
        assertEquals("General", result.getCategory());
    }

    // Unhappy path for creating a complaint (user not found)
    @Test
    void testCreateNewComplaint_UserNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            compService.createNewComplaint(mockCompDto);
        });

        assertEquals("User Doesn't Exist !!", exception.getMessage());
    }

    // Happy path for editing a complaint
    @Test
    void testEditComplaint() throws IllegalAccessException {
        when(complaintRepo.findById(1L)).thenReturn(Optional.of(mockComplaint));
        when(complaintRepo.saveAndFlush(any(Complaint.class))).thenReturn(mockComplaint);

        CompDto result = compService.editComplaint(mockCompDto);

        assertNotNull(result);
        assertEquals("Demo", result.getTitle());
        assertEquals("Complaint Description", result.getDescription());
    }

    // Unhappy path for editing a complaint (complaint not found)
    @Test
    void testEditComplaint_ComplaintNotFound() {
        when(complaintRepo.findById(1L)).thenReturn(Optional.empty());

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            compService.editComplaint(mockCompDto);
        });

        assertEquals("Complaint Doesn't Exist !! ", exception.getMessage());
    }

    // Happy path for getting a complaint by ID
    @Test
    void testGetComplaintById() throws IllegalAccessException {
        when(complaintRepo.findById(1L)).thenReturn(Optional.of(mockComplaint));

        CompDto result = compService.getComplaintById(1L);

        assertNotNull(result);
        assertEquals("Demo", result.getTitle());
        assertEquals("Demo-description", result.getDescription());
    }

    // Unhappy path for getting a complaint by ID (complaint not found)
    @Test
    void testGetComplaintById_ComplaintNotFound() {
        when(complaintRepo.findById(1L)).thenReturn(Optional.empty());

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            compService.getComplaintById(1L);
        });

        assertEquals("Complaint Doesn't Exist !! ", exception.getMessage());
    }

    // Happy path for deleting a complaint
    @Test
    void testDeleteComplaint() throws IllegalAccessException {
        when(complaintRepo.findById(1L)).thenReturn(Optional.of(mockComplaint));
        when(userRepo.findById(1L)).thenReturn(Optional.of(mockUser));
        doNothing().when(complaintRepo).delete(mockComplaint);

        List<CompDto> result = compService.deleteComplaint(1L, 1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(complaintRepo, times(1)).delete(mockComplaint);
    }

    // Unhappy path for deleting a complaint (complaint not found)
    @Test
    void testDeleteComplaint_ComplaintNotFound() {
        when(complaintRepo.findById(1L)).thenReturn(Optional.empty());

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            compService.deleteComplaint(1L, 1L);
        });

        assertEquals("Complaint Doesn't Exist !! ", exception.getMessage());
    }

    // Unhappy path for deleting a complaint (user not authorized)
    @Test
    void testDeleteComplaint_UserNotAuthorized() throws IllegalAccessException {
        when(complaintRepo.findById(1L)).thenReturn(Optional.of(mockComplaint));
        mockComplaint.getUser().setUserId(2L);  // Set a different user

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            compService.deleteComplaint(1L, 1L);
        });

        assertEquals("User Can Only Delete From His Own Complaints !", exception.getMessage());
    }

    // Happy path for changing complaint status
    @Test
    void testChangeComplaintStatus() throws IllegalAccessException {
        when(complaintRepo.findById(1L)).thenReturn(Optional.of(mockComplaint));
        when(adminRepo.findById(2L)).thenReturn(Optional.of(mockAdmin));
        when(statusRepo.findByStatusType("Resolved")).thenReturn(new Status(8,"Resolved"));
        when(complaintRepo.saveAndFlush(mockComplaint)).thenReturn(mockComplaint);

        CompDto result = compService.changeComplaintStatus(2L, 1L, "Resolved");

        assertNotNull(result);
        assertEquals("Resolved", result.getCurrentStatus());
    }

    // Unhappy path for changing complaint status (admin not authorized)
    @Test
    void testChangeComplaintStatus_AdminNotAuthorized() {
        when(complaintRepo.findById(1L)).thenReturn(Optional.of(mockComplaint));

        assertThrows(ValidationException.class, () -> {
            compService.changeComplaintStatus(2L, 1L, "Resolved");
        });

    }

    // Happy path for changing complaint assignee
    @Test
    void testChangeComplaintAssignee() throws IllegalAccessException {
        when(complaintRepo.findById(1L)).thenReturn(Optional.of(mockComplaint));
        when(adminRepo.findById(2L)).thenReturn(Optional.of(mockAdmin));
        when(adminRepo.findById(3L)).thenReturn(Optional.of(mockAdmin2));
        when(complaintRepo.saveAndFlush(mockComplaint)).thenReturn(mockComplaint);

        CompDto result = compService.changeComplaintAssignee(2L, 1L, 3L);

        assertNotNull(result);
        assertEquals(3L, result.getAssigneeId());
    }
    @Test
    void testChangeComplaintAssignee_AdminNotAuthorized() {
        when(complaintRepo.findById(1L)).thenReturn(Optional.of(mockComplaint));

        assertThrows(ValidationException.class, () -> {
            compService.changeComplaintAssignee(2L, 1L, 2L);
        });
    }
}