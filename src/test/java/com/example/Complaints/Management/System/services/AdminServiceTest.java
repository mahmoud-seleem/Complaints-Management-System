package com.example.Complaints.Management.System.services;

import com.example.Complaints.Management.System.core.application.dto.AdminDto;
import com.example.Complaints.Management.System.core.domain.entities.Admin;
import com.example.Complaints.Management.System.core.domain.services.AdminService;
import com.example.Complaints.Management.System.core.infrastructure.Repository.AdminRepo;
import com.example.Complaints.Management.System.shared.Security.SecurityUtils;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private AdminRepo adminRepo;

    private AdminDto adminDto;
    private Admin admin;

    @BeforeEach
    public void setUp() {
        // Setup AdminDto for testing
        adminDto = new AdminDto();
        adminDto.setUserName("admin_user");
        adminDto.setPassword("password123");
        adminDto.setEmail("admin@example.com");
        adminDto.setAge(35);
        adminDto.setPhoneNumbers(List.of("9876543210"));

        // Setup Admin entity for testing
        admin = new Admin();
        admin.setUserName("admin_user");
        admin.setPassword(SecurityUtils.PASSWORD_ENCODER.encode("password123"));
        admin.setEmail("admin@example.com");
        admin.setAge(35);
        admin.setPhoneNumbers(List.of("9876543210"));
        admin.setUserId(1L); // Mock userId for the saved Admin
    }
    @Test
    void testRegisterAdmin_HappyPath() {
        // Given: Mock the saveAndFlush method to return a mocked Admin entity
        when(adminRepo.saveAndFlush(any(Admin.class))).thenReturn(admin);

        // When: Call the registerAdmin method
        AdminDto result = adminService.registerAdmin(adminDto);

        // Then: Assert that the returned AdminDto is not null and contains the expected values
        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        assertEquals("admin_user", result.getUserName());
        assertEquals("admin@example.com", result.getEmail());

        // Verify that saveAndFlush was called once
        verify(adminRepo, times(1)).saveAndFlush(any(Admin.class));
    }
    @Test
    void testUpdateAdmin_AdminNotFound() {
        // Given: Mock the behavior of findById to throw a NoSuchElementException
        AdminDto updatedAdminDto = new AdminDto();
        updatedAdminDto.setUserId(1L);
        updatedAdminDto.setUserName("updated_admin");
        updatedAdminDto.setEmail("updated_admin@example.com");

        when(adminRepo.findById(anyLong())).thenThrow(new NoSuchElementException("Admin not found"));

        // When & Then: Assert that a ValidationException is thrown when trying to update a non-existing admin
        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            adminService.updateAdmin(updatedAdminDto);
        });
        assertEquals("User not exist", thrown.getMessage());

        // Verify that findById was called once
        verify(adminRepo, times(1)).findById(anyLong());
    }
    @Test
    void testDeleteAdmin_AdminNotFound() {
        // Given: Mock the behavior of findById to throw a NoSuchElementException
        Long adminId = 1L;

        when(adminRepo.findById(adminId)).thenThrow(new NoSuchElementException("Admin not found"));

        // When & Then: Assert that a ValidationException is thrown when trying to delete a non-existing admin
        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            adminService.deleteAdmin(adminId);
        });
        assertEquals("User Doesn't Exist !!", thrown.getMessage());

        // Verify that findById was called once
        verify(adminRepo, times(1)).findById(adminId);
    }
    @Test
    void testGetAdminById_HappyPath() {
        // Given: Mock the findById method to return an Optional containing the admin entity
        when(adminRepo.findById(1L)).thenReturn(Optional.of(admin));

        // When: Call the getAdminById method
        AdminDto result = adminService.getAdminById(1L);

        // Then: Assert that the returned AdminDto is not null and contains the expected values
        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        assertEquals("admin_user", result.getUserName());
        assertEquals("admin@example.com", result.getEmail());
        assertEquals(35, result.getAge());

        // Verify that findById was called once
        verify(adminRepo, times(1)).findById(1L);
    }
    @Test
    void testGetAdminById_AdminNotFound() {
        // Given: Mock the findById method to return an empty Optional (admin not found)
        when(adminRepo.findById(1L)).thenReturn(Optional.empty());

        // When & Then: Assert that a ValidationException is thrown when the admin is not found
        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            adminService.getAdminById(1L);
        });
        assertEquals("User Doesn't Exist !!", thrown.getMessage());

        // Verify that findById was called once
        verify(adminRepo, times(1)).findById(1L);
    }
    @Test
    void testDeleteAdmin_HappyPath() {
        // Given: Mock the findById method to return an Optional containing the admin entity
        when(adminRepo.findById(1L)).thenReturn(Optional.of(admin));

        // When: Call the deleteAdmin method
        AdminDto result = adminService.deleteAdmin(1L);

        // Then: Assert that the returned AdminDto is not null and contains the expected values
        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        assertEquals("admin_user", result.getUserName());
        assertEquals("admin@example.com", result.getEmail());

        // Verify that delete was called once
        verify(adminRepo, times(1)).findById(1L);
        verify(adminRepo, times(1)).delete(any(Admin.class));
    }
    @Test
    void testUpdateAdmin_HappyPath() throws Exception {
        // Given: Mock the findById method to return an Optional containing the admin entity
        when(adminRepo.findById(1L)).thenReturn(Optional.of(admin));

        // Given: Mock the saveAndFlush method to return the updated admin entity
        when(adminRepo.saveAndFlush(any(Admin.class))).thenReturn(admin);

        // Create a new AdminDto with updated values
        AdminDto updatedAdminDto = new AdminDto();
        updatedAdminDto.setUserId(1L);
        updatedAdminDto.setUserName("updated_admin");
        updatedAdminDto.setEmail("updated_admin@example.com");
        updatedAdminDto.setAge(40);
        updatedAdminDto.setPhoneNumbers(List.of("9876543210", "1234567890"));

        // When: Call the updateAdmin method
        AdminDto result = adminService.updateAdmin(updatedAdminDto);

        // Then: Assert that the returned AdminDto is not null and contains the updated values
        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        assertEquals("updated_admin", result.getUserName());
        assertEquals("updated_admin@example.com", result.getEmail());
        assertEquals(40, result.getAge());

        // Verify that findById was called once
        verify(adminRepo, times(1)).findById(1L);
        verify(adminRepo, times(1)).saveAndFlush(any(Admin.class));  // Ensure the saveAndFlush method was called
    }
}
