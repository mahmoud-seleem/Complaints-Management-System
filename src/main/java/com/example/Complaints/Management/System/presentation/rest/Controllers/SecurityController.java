package com.example.Complaints.Management.System.presentation.rest.Controllers;

import com.example.Complaints.Management.System.core.application.dto.AdminDto;
import com.example.Complaints.Management.System.core.application.dto.UserDto;
import com.example.Complaints.Management.System.core.application.services.UserService;
import com.example.Complaints.Management.System.core.domain.entities.GeneralUser;
import com.example.Complaints.Management.System.core.domain.services.EmailServiceImp;
import com.example.Complaints.Management.System.core.domain.services.OtpServiceImp;
import com.example.Complaints.Management.System.core.infrastructure.Repository.GeneralUserRepo;
import com.example.Complaints.Management.System.shared.Security.JWTUtils;
import com.example.Complaints.Management.System.core.domain.services.AdminServiceImp;
import com.example.Complaints.Management.System.shared.Utils.CustomValidationException;
import com.example.Complaints.Management.System.shared.Utils.Validation;
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
    private EmailServiceImp emailService;
    @Autowired
    private AdminServiceImp adminService;

    @Autowired
    private OtpServiceImp otpService;
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private GeneralUserRepo generalUserRepo;

    @Autowired
    private Validation validation;
    @Autowired
    private HttpSession session; // Inject HttpSession for session management

    @PostMapping("/admin/sign-up")
    public AdminDto registerAdmin(@Valid @RequestBody AdminDto adminDto) throws NoSuchFieldException, IllegalAccessException {
        String password = adminDto.getPassword();
        adminDto = adminService.registerAdmin(adminDto);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(adminDto.getUserName(), password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String otp = otpService.generateOtp(adminDto.getUserName());
        emailService.sendOtpEmail(adminDto.getEmail(), otp);
//            String token = jwtUtils.generateToken(username);
//            return ResponseEntity.ok("JWT-TOKEN = " + token);
//        return ResponseEntity.ok("otp sent to the email");
//        String token = jwtUtils.generateToken(adminDto.getUserName());
//        adminDto.setToken(token);
        return adminDto;
    }
    @PostMapping("/user/sign-up")
    public UserDto registerUser(@Valid @RequestBody UserDto userDto) throws NoSuchFieldException, IllegalAccessException {
        String password = userDto.getPassword();
        userDto = userService.registerUser(userDto);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getUserName(), password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String token = jwtUtils.generateToken(userDto.getUserName());
//        userDto.setToken(token);
        String otp = otpService.generateOtp(userDto.getUserName());
        emailService.sendOtpEmail(userDto.getEmail(), otp);
        return userDto;
        }

    @PostMapping("/login")
    public ResponseEntity<String> login(@PathParam("username") String username, @PathParam("password") String password) {
        try {
            System.out.println(username);
            System.out.println(password);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String otp = otpService.generateOtp(username);
            String email = generalUserRepo.findByUserName(username).getEmail();
            emailService.sendOtpEmail(email, otp);
//            String token = jwtUtils.generateToken(username);
//            return ResponseEntity.ok("JWT-TOKEN = " + token);
            return ResponseEntity.ok("otp sent to the email");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(
            @PathParam("username") String username,
            @PathParam("otp") String otp){
        // validation for the username
            GeneralUser user = generalUserRepo.findByUserName(username);
            if (user == null ){
                throw new CustomValidationException(
                        "no user with the provided username ",
                        "username",
                        username);
            }
        boolean isValid = otpService.validateOtp(username,otp);
        if (isValid) {
            otpService.clearOtp(username);
            String token = jwtUtils.generateToken(username);
            return ResponseEntity.ok("JWT-TOKEN = " + token);
        }else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
        }
    }

    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(
            @PathParam("toEmail") String toEmail,
            @PathParam("subject") String subject,
            @PathParam("body") String body){
        emailService.sendEmail(toEmail,subject,body);
    return ResponseEntity.ok("Success");
    }
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
//        request.getSession().invalidate(); // Destroy session
//        SecurityContextHolder.clearContext(); // Clear security context
//        return ResponseEntity.ok("Logged out successfully.");
//    }
}
