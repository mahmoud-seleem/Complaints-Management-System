package com.example.Complaints.Management.System.Security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityUtils {

    public static final BCryptPasswordEncoder PASSWORD_ENCODER =
            new BCryptPasswordEncoder(10);


}
