package com.example.Complaints.Management.System.shared.Utils;

import com.example.Complaints.Management.System.core.application.dto.AdminDto;
import com.example.Complaints.Management.System.core.application.dto.GeneralUserDto;
import com.example.Complaints.Management.System.core.domain.entities.Admin;
import com.example.Complaints.Management.System.core.domain.entities.GeneralUser;
import com.example.Complaints.Management.System.core.infrastructure.Repository.AdminRepo;
import com.example.Complaints.Management.System.core.infrastructure.Repository.GeneralUserRepo;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Validation {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private GeneralUserRepo generalUserRepo;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    public void validateGeneralUserRegistrationData(GeneralUserDto dto) throws NoSuchFieldException, IllegalAccessException {
        ensureGeneralUserDoesNotExist(dto.getUserName());
        ensureFieldIsNull(AdminDto.class, dto, "userId");
        ensureFieldIsNull(AdminDto.class, dto, "token");
        validateGeneralUserRequiredFieldsAreNotNull(dto);
        validateUserNameAndPasswordAreValid(dto);
        validateEmailIsValid(dto.getEmail());
        validateAge(dto.getAge());
        // validation for the phone numbers
        validatePhoneNumbers(dto.getPhoneNumbers());
    }

    public void validateGeneralUserRequiredFieldsAreNotNull(GeneralUserDto dto) {
        isNotNull(dto.getUserName(), "userName");
        isNotNull(dto.getPassword(), "password");
        isNotNull(dto.getEmail(), "email");
        isNotNull(dto.getAge(), "age");
        isNotNull(dto.getPhoneNumbers(),"phoneNumbers");
    }

    public void validatePhoneNumbers(List<String> numbers){
        for (String number : numbers){
            validatePhoneNumber(number);
        }
    }
    public void validatePhoneNumber(String phoneNumber){
        // assumption that the phone numbers are all from egypt for now
        if (phoneNumber.startsWith("+20") && phoneNumber.length() == 13){
            return;
        }
        throw new CustomValidationException(
                "Phone Number should be in a proper format",
                "phoneNumbers",
                phoneNumber
        );
    }
    public void validateAge(int age) {
        if (age <= 16) {
            throw new CustomValidationException(
                    "age must be greater than 16",
                    "age",
                    age
            );
        }
    }

    public void validateEmailIsValid(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new CustomValidationException(
                    "Email should be in a proper format like : example@example.example",
                    "email",
                    email);
        }
    }

    public void isNotNull(Object value, String name) {
        if (value == null) {
            throw new CustomValidationException("field can't be null !!", name, null);
        }
    }

    public void ensureFieldIsNull(Class clazz, Object object, String name) throws NoSuchFieldException, IllegalAccessException {
        Field field = getField(clazz,name);
        field.setAccessible(true);
        if (field.get(object) != null) {
            field.set(object, null);
        }
    }

    private static Field getField(Class<?> clazz, String fieldName) {
        while(clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass(); // Search in parent class
            }
        }
        return null; // Field not found
    }

    public void validateUserNameAndPasswordAreValid(GeneralUserDto dto) {
        if (!dto.getUserName().isEmpty()) {
            if (dto.getPassword().length() >= 5) {
            } else {
                throw new CustomValidationException(
                        "password should be more than 5 chars !!",
                        "password",
                        dto.getPassword());
            }
        } else {
            throw new CustomValidationException(
                    "userName should not be empty !",
                    "userName",
                    dto.getUserName());
        }
    }


    public void ensureGeneralUserDoesNotExist(String userName) {
        GeneralUser user = generalUserRepo.findByUserName(userName);
        if (user != null) {
            throw new CustomValidationException(
                    "User with this userName Already Exists !",
                    "userName",
                    userName);
        }
    }

    public Admin isAdminExist(Long id) {
        try {
            return adminRepo.findById(id).get();
        } catch (Exception e) {
            throw new ValidationException("Admin with id = " + id + " Doesn't Exist !!");
        }
    }
}
