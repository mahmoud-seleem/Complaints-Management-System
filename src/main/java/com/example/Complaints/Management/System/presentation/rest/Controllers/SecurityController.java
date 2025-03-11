package com.example.Complaints.Management.System.presentation.rest.Controllers;

import com.example.Complaints.Management.System.core.application.dto.AdminDto;
import com.example.Complaints.Management.System.core.application.dto.UserDto;
import com.example.Complaints.Management.System.core.application.services.UserService;
import com.example.Complaints.Management.System.shared.Security.JWTUtils;
import com.example.Complaints.Management.System.core.domain.services.AdminServiceImp;
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
    private AdminServiceImp adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;


    @Autowired
    private HttpSession session; // Inject HttpSession for session management

    @PostMapping("/admin/sign-up")
    public AdminDto registerAdmin(@Valid @RequestBody AdminDto adminDto) throws NoSuchFieldException, IllegalAccessException {
        String password = adminDto.getPassword();
        adminDto = adminService.registerAdmin(adminDto);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(adminDto.getUserName(), password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(adminDto.getUserName());
        adminDto.setToken(token);
        return adminDto;
    }
    @PostMapping("/user/sign-up")
    public UserDto registerUser(@Valid @RequestBody UserDto userDto) throws NoSuchFieldException, IllegalAccessException {
        String password = userDto.getPassword();
        userDto = userService.registerUser(userDto);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getUserName(), password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(userDto.getUserName());
        userDto.setToken(token);
        return userDto;
        }

    @PostMapping("/login")
    public ResponseEntity<String> login(@PathParam("username") String username, @PathParam("password") String password) {
        try {
            System.out.println(username);
            System.out.println(password );
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtils.generateToken(username);
            return ResponseEntity.ok("JWT-TOKEN = " +token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
//        request.getSession().invalidate(); // Destroy session
//        SecurityContextHolder.clearContext(); // Clear security context
//        return ResponseEntity.ok("Logged out successfully.");
//    }
}
