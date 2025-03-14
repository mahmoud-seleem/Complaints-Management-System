package com.example.Complaints.Management.System.core.domain.services;

import com.example.Complaints.Management.System.core.application.dto.UserDto;
import com.example.Complaints.Management.System.core.application.services.UserService;
import com.example.Complaints.Management.System.core.domain.entities.User;
import com.example.Complaints.Management.System.core.infrastructure.Repository.UserRepo;
import com.example.Complaints.Management.System.shared.Security.SecurityUtils;
import com.example.Complaints.Management.System.shared.Utils.Validation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class UserServiceImp implements UserService {

    public UserServiceImp() {
    }

    public UserServiceImp(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    @Autowired
    private UserRepo userRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private Validation validation;

    @Transactional
    public UserDto registerUser(UserDto userDto) throws NoSuchFieldException, IllegalAccessException {
        // validation logic on the DTO object to be implemented here
        validation.validateGeneralUserRegistrationData(userDto);
        User user = populateUser(userDto);
        user = userRepo.saveAndFlush(user);
        //entityManager.refresh(registeredUser);
        userDto.setUserId(user.getUserId());
        return userDto;
    }

    @Transactional
    public UserDto updateUser(UserDto userDto) throws Exception {
        // validation to all existed and required fields
        validation.validateGeneralUserUpdateData(userDto);
        User user = validation.isUserExist(userDto.getUserId());
        updateUserData(user,userDto);
        User updatedUser = userRepo.saveAndFlush(user);
        return populateUserDto(updatedUser,userDto);
    }

    @Transactional
    public UserDto getUserById(Long id) throws IllegalAccessException {
        User user = validation.isUserExist(id);
        validation.isAllowedUser(id);
        UserDto response = new UserDto();
        return populateUserDto(user,response);
    }
    public UserDto deleteUser(Long id) throws IllegalAccessException {
        User user = validation.isUserExist(id);
        validation.isAllowedUser(id);
        UserDto userDto = new UserDto();
        populateUserDto(user,userDto);
        userRepo.delete(user);
        return userDto;
    }

    // helper methods

    private User populateUser(UserDto userDto){
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setPassword(SecurityUtils.PASSWORD_ENCODER.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setAge(userDto.getAge());
        List<String> phones = user.getPhoneNumbers();
        if (userDto.getPhoneNumbers() != null){
            for (String phone : userDto.getPhoneNumbers()){
                phones.add(phone);
            }
        }
        return user;
    }

    private User updateUserData(User user,UserDto userDto) throws Exception {
        Class<?> dtoClass = userDto.getClass();
        Class<?> entityClass = user.getClass();

        for (Field dtoField : getAllFields(dtoClass)) {
            dtoField.setAccessible(true);
            Object value = dtoField.get(userDto); // Get DTO field value

            if (value != null) { // Only map non-null values
                Field entityField = getField(entityClass, dtoField.getName());
                if (     entityField != null &&
                        !entityField.getName().equals("userId")){
                        entityField.setAccessible(true);
                        entityField.set(user, value); // Set value in entity
                }
            }
        }
        user.setPassword(SecurityUtils.PASSWORD_ENCODER.encode(user.getPassword()));
        return user;
    }
    private UserDto populateUserDto(User user, UserDto userDto) throws IllegalAccessException {
        System.out.println(user);
        for (Field entityField : getAllFields(user.getClass())) {
            System.out.println(entityField.getName());
            entityField.setAccessible(true);
            Object value = entityField.get(user);
            if (value != null) { // Only map non-null values
                Field dtoField = getField(userDto.getClass(), entityField.getName());
                if (dtoField != null) {
                    dtoField.setAccessible(true);
                    dtoField.set(userDto, value);
                }
            }
        }
        return userDto;
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

    private static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while(clazz != null) {
            fields.addAll(Arrays.stream(clazz.getDeclaredFields()).toList());
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

//    public List<UserDto>
    }

