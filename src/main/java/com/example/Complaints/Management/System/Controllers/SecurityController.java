package com.example.Complaints.Management.System.Controllers;

import com.example.Complaints.Management.System.DTO.AdminDto;
import com.example.Complaints.Management.System.DTO.UserDto;
import com.example.Complaints.Management.System.services.AdminService;
import com.example.Complaints.Management.System.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
public class SecurityController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private HttpSession session; // Inject HttpSession for session management

    @PostMapping("/admin/sign-up")
    public AdminDto registerAdmin(@Valid @RequestBody AdminDto adminDto){
        return adminService.registerAdmin(adminDto);
    }
    @PostMapping("/user/sign-up")
    public UserDto registerUser(@Valid @RequestBody UserDto userDto){
        return userService.registerUser(userDto);
    }
//    @GetMapping("/login")
//    public ResponseEntity<String> login() {
//        return ResponseEntity.ok("Please log in using the login form.");
//    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate(); // Destroy session
        SecurityContextHolder.clearContext(); // Clear security context
        return ResponseEntity.ok("Logged out successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@PathParam("username") String username, @PathParam("password") String password) {
        try {
            System.out.println(username);
            System.out.println(password );
            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // Store authentication in SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Return a welcome message
            return ResponseEntity.ok("Welcome, " + username + "! You are now logged in.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
