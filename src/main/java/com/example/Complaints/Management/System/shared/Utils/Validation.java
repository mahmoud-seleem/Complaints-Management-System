package com.example.Complaints.Management.System.shared.Utils;

import com.example.Complaints.Management.System.core.application.dto.CompDto;
import com.example.Complaints.Management.System.core.application.dto.GeneralUserDto;
import com.example.Complaints.Management.System.core.domain.entities.Admin;
import com.example.Complaints.Management.System.core.domain.entities.GeneralUser;
import com.example.Complaints.Management.System.core.domain.entities.Status;
import com.example.Complaints.Management.System.core.domain.entities.User;
import com.example.Complaints.Management.System.core.infrastructure.Repository.AdminRepo;
import com.example.Complaints.Management.System.core.infrastructure.Repository.GeneralUserRepo;
import com.example.Complaints.Management.System.core.infrastructure.Repository.StatusRepo;
import com.example.Complaints.Management.System.core.infrastructure.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private UserRepo userRepo;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    public void validateGeneralUserRegistrationData(GeneralUserDto dto) throws NoSuchFieldException, IllegalAccessException {
        ensureGeneralUserDoesNotExist(dto.getUserName());
        ensureFieldIsNull(GeneralUserDto.class, dto, "userId");
        ensureFieldIsNull(GeneralUserDto.class, dto, "token");
        validateGeneralUserRequiredFieldsAreNotNull(dto);
        validateUserNameIsValid(dto.getUserName());
        validatePasswordIsValid(dto.getPassword());
        validateEmailIsValid(dto.getEmail());
        validateAge(dto.getAge());
        // validation for the phone numbers
        validatePhoneNumbers(dto.getPhoneNumbers());
    }


    public void validateGeneralUserUpdateData(GeneralUserDto dto) throws NoSuchFieldException, IllegalAccessException {
        GeneralUser generalUser =  isGeneralUserExist(dto.getUserId());
        if (generalUser.getRole().equals("ADMIN")){
            isAllowedAdmin(dto.getUserId());
        }else {
            isAllowedUser(dto.getUserId());
        }
        if (dto.getUserName() != null) {
            throw new CustomValidationException(
                    "UserName can't be updated once created,Please remove it !",
                    "userName",
                    dto.getUserName());
        }
        validateOptionalUpdatableFields(dto);
    }

    public void validateOptionalUpdatableFields(GeneralUserDto dto) {
        if (dto.getPassword() != null) {
            validatePasswordIsValid(dto.getPassword());
        }
        if (dto.getEmail() != null) {
            validateEmailIsValid(dto.getEmail());
        }
        if (dto.getAge() != null) {
            validateAge(dto.getAge());
        }
        if (dto.getPhoneNumbers() != null) {
            validatePhoneNumbers(dto.getPhoneNumbers());
        }
    }

    public void validateGeneralUserRequiredFieldsAreNotNull(GeneralUserDto dto) {
        isNotNull(dto.getUserName(), "userName");
        isNotNull(dto.getPassword(), "password");
        isNotNull(dto.getEmail(), "email");
        isNotNull(dto.getAge(), "age");
        isNotNull(dto.getPhoneNumbers(), "phoneNumbers");
    }

    public void validatePhoneNumbers(List<String> numbers) {
        for (String number : numbers) {
            validatePhoneNumber(number);
        }
    }

    public void validatePhoneNumber(String phoneNumber) {
        // assumption that the phone numbers are all from egypt for now
        if (phoneNumber.startsWith("+20") && phoneNumber.length() == 13) {
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
        Field field = getField(clazz, name);
        field.setAccessible(true);
        if (field.get(object) != null) {
            field.set(object, null);
        }
    }

    private static Field getField(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass(); // Search in parent class
            }
        }
        return null; // Field not found
    }

    public void validateUserNameIsValid(String userName) {
        if (!userName.isEmpty()) {
            return;
        }
        throw new CustomValidationException(
                "userName should not be empty !",
                "userName",
                userName);
    }

    public void validatePasswordIsValid(String password) {
        if (password.length() >= 5) {
            return;
        }
        throw new CustomValidationException(
                "Password should not be less than 5 chars !",
                "password",
                password);
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

    public GeneralUser isGeneralUserExist(Long id) {
        try {
            return generalUserRepo.findById(id).get();
        } catch (Exception e) {
            throw new CustomValidationException(
                    "GeneralUser with this id Doesn't Exist !!",
                    "id",
                    id);
        }
    }

    public Admin isAdminExist(Long id) {
        try {
            return adminRepo.findById(id).get();
        } catch (Exception e) {
            throw new CustomValidationException(
                    "Admin with this id Doesn't Exist !!",
                    "id",
                    id);
        }
    }

    public User isUserExist(Long id) {
        try {
            return userRepo.findById(id).get();
        } catch (Exception e) {
            throw new CustomValidationException(
                    "User with this id Doesn't Exist !!",
                    "id",
                    id);
        }
    }

    public boolean isStatusExist(String type) {
        Status status = statusRepo.findByStatusType(type);
        return (status != null);
    }

    public void validateStatusIsExist(String type) {
        if (isStatusExist(type)) {
            return;
        }
        throw new CustomValidationException(
                "Status with this type Doesn't Exist !",
                "statusType",
                type);
    }

    public void validateStatusIsNotExist(String type) {
        if (!isStatusExist(type)) {
            return;
        }
        throw new CustomValidationException(
                "Status with this type already Exist !",
                "statusType",
                type);
    }


    public void validateNewComplaintData(CompDto dto) {
        validateNewComplaintRequiredFields(dto);
        isUserExist(dto.getOwnerId());
        isAllowedUser(dto.getOwnerId());
        validComplaintDataLength(dto.getTitle(), "title");
        validComplaintDataLength(dto.getDescription(), "description");
        validComplaintDataLength(dto.getCategory(), "category");
    }

    public void validComplaintDataLength(String data, String name) {
        if (data.isEmpty()) {
            throw new CustomValidationException(
                    name + " Can't be empty",
                    name,
                    data
            );
        }
    }

    public void validateNewComplaintRequiredFields(CompDto dto) {
        isNotNull(dto.getOwnerId(), "ownerId");
        isNotNull(dto.getTitle(), "title");
        isNotNull(dto.getDescription(), "description");
        isNotNull(dto.getCategory(), "category");
    }

    public void isAllowedAdmin(Long givenId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = null;
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        }
        Admin currentUser = adminRepo.findByUserName(userName);
        if (currentUser.getUserId().longValue() != givenId.longValue()) {
            throw new CustomValidationException(
                    "Current Admin with id = " + currentUser.getUserId() + " is not = the given id value ",
                    "admin/user/assignee Id",
                    givenId
            );
        }
    }

    public void isAllowedUser(Long givenId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = null;
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        }
        User currentUser = userRepo.findByUserName(userName);
        if (currentUser.getUserId().longValue() != givenId.longValue()) {
            throw new CustomValidationException(
                    "Current user with id = " + currentUser.getUserId() + " is not = the given id value ",
                    "user/owner Id",
                    givenId
            );
        }
    }
}
