package com.example.Complaints.Management.System.core.domain.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class OtpServiceImp {
    private final HashMap<String, String> otpStorage = new HashMap<>();
    private final HashMap<String, Long> otpExpiry = new HashMap<>();
    private final long OTP_EXPIRATION_TIME = 5 * 60 * 1000; // OTP expires after 5 minutes

    // Generate OTP and store it
    public String generateOtp(String username) {
        String otp = String.valueOf((int) (Math.random() * 900000) + 100000); // 6-digit OTP
        otpStorage.put(username, otp);
        otpExpiry.put(username, System.currentTimeMillis() + OTP_EXPIRATION_TIME); // OTP expiration time
        return otp;
    }

    // Validate OTP
    public boolean validateOtp(String username, String otp) {
        Long expiryTime = otpExpiry.get(username);
        if (expiryTime == null || System.currentTimeMillis() > expiryTime) {
            otpStorage.remove(username); // Expired OTP, remove it
            otpExpiry.remove(username);
            return false;
        }
        return otp.equals(otpStorage.get(username));
    }

    // Clear OTP after successful validation
    public void clearOtp(String username) {
        otpStorage.remove(username);
        otpExpiry.remove(username);
    }
}
