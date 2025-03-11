package com.example.Complaints.Management.System.services;

import com.example.Complaints.Management.System.core.application.dto.AdminDto;
import com.example.Complaints.Management.System.core.domain.entities.Admin;
import com.example.Complaints.Management.System.core.domain.services.AdminServiceImp;
import com.example.Complaints.Management.System.core.infrastructure.Repository.AdminRepo;
import com.example.Complaints.Management.System.shared.Security.SecurityUtils;
import com.example.Complaints.Management.System.shared.Utils.CustomValidationException;
import com.example.Complaints.Management.System.shared.Utils.Validation;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AdminServiceTest {

    @InjectMocks
    private AdminServiceImp adminService;

    @Mock
    private AdminRepo adminRepo;

    @Mock
    private Validation validation;

    private AdminDto adminDto;
    private Admin admin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_AdminRegistration_HappyScenario() throws NoSuchFieldException, IllegalAccessException {
        AdminDto input = new AdminDto();
        input.setUserName("mahmoud");
        input.setEmail("mahmoud@mahmoud.com");
        input.setPassword("123456789");
        input.setAge(25);
        input.setPhoneNumbers(List.of("+201061424231"));

        Admin admin = new Admin();
        admin.setAge(25);
        admin.setUserId(1L);
        admin.setEmail("mahmoud@mahmoud.com");
        admin.setPassword("123456789");
        admin.setAge(25);
        admin.setPhoneNumbers(List.of("+201061424231"));

        when(adminRepo.saveAndFlush(any(Admin.class))).thenReturn(admin);
        doNothing().when(validation).validateGeneralUserRegistrationData(input);

        AdminDto result = adminService.registerAdmin(input);

        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        verify(adminRepo, times(1)).saveAndFlush(any(Admin.class));
    }

    @Test
    void test_AdminRegistration_UnHappyScenario() throws NoSuchFieldException, IllegalAccessException {
        AdminDto input = new AdminDto();
        input.setEmail("mahmoud@mahmoud.com");
        input.setPassword("123456789");
        input.setAge(25);
        input.setPhoneNumbers(List.of("+201061424231"));

        doThrow(new CustomValidationException())
                .when(validation).validateGeneralUserRegistrationData(input);

        assertThrows(CustomValidationException.class,() -> adminService.registerAdmin(input));
        verify(adminRepo, times(0)).saveAndFlush(any(Admin.class));
    }

    
}