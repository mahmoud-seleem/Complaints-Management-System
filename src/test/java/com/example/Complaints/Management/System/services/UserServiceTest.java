package com.example.Complaints.Management.System.services;

import com.example.Complaints.Management.System.core.application.dto.UserDto;
import com.example.Complaints.Management.System.core.domain.entities.User;
import com.example.Complaints.Management.System.core.domain.services.UserServiceImp;
import com.example.Complaints.Management.System.core.infrastructure.Repository.UserRepo;
import jakarta.persistence.EntityManager;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserServiceImp userService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private EntityManager entityManager;

    private UserDto userDto;

    private User user;

    @BeforeEach
    public void setUp() {
        userDto = new UserDto();
        userDto.setUserName("john_doe");
        userDto.setPassword("password123");
        userDto.setEmail("john.doe@example.com");
        userDto.setAge(30);
        userDto.setPhoneNumbers(List.of("123-456-7890"));

        user = new User();
        user.setUserName("john_doe");
        user.setPassword("password123");
        user.setEmail("john.doe@example.com");
        user.setAge(30);
        user.setPhoneNumbers(List.of("123-456-7890"));
        user.setUserId(100L);
    }

    @Test
    void testRegisterUser_HappyPath() {
        when(userRepo.saveAndFlush(any(User.class))).thenReturn(user);
        UserDto result = userService.registerUser(userDto);
        assertNotNull(result);
        assertEquals("john_doe", result.getUserName());
        assertEquals("john.doe@example.com", result.getEmail());
        verify(userRepo, times(1)).saveAndFlush(any(User.class));
    }

    @Test
    void testUpdateUser_HappyPath() throws Exception {
        // Given
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(userRepo.saveAndFlush(any(User.class))).thenReturn(user);

        userDto.setUserId(1L);
        userDto.setUserName("updated_username");

        // When
        UserDto updatedUserDto = userService.updateUser(userDto);

        // Then
        assertNotNull(updatedUserDto);
        assertEquals("updated_username", updatedUserDto.getUserName());
        verify(userRepo, times(1)).findById(1L);
        verify(userRepo, times(1)).saveAndFlush(any(User.class));
    }

    @Test
    void testUpdateUser_UnhappyPath_UserNotFound() throws Exception {
        // Given
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userService.updateUser(userDto);
        });
        assertEquals("User not exist", exception.getMessage());
    }

    @Test
    void testGetUserById_HappyPath() {
        // Given
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        // When
        UserDto result = userService.getUserById(1L);

        // Then
        assertNotNull(result);
        assertEquals("john_doe", result.getUserName());
        verify(userRepo, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_UnhappyPath_UserNotFound() {
        // Given
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userService.getUserById(1L);
        });
        assertEquals("User Doesn't Exist !!", exception.getMessage());
    }

    @Test
    void testDeleteUser_HappyPath() {
        // Given
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        // When
        UserDto deletedUserDto = userService.deleteUser(1L);

        // Then
        assertNotNull(deletedUserDto);
        assertEquals("john_doe", deletedUserDto.getUserName());
        verify(userRepo, times(1)).findById(1L);
        verify(userRepo, times(1)).delete(any(User.class));
    }

    @Test
    void testDeleteUser_UnhappyPath_UserNotFound() {
        // Given
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userService.deleteUser(1L);
        });
        assertEquals("User Doesn't Exist !!", exception.getMessage());
    }
}
