package com.example.Complaints.Management.System.core.application.services;

import com.example.Complaints.Management.System.core.application.dto.UserDto;
import com.example.Complaints.Management.System.core.domain.entities.User;
import com.example.Complaints.Management.System.core.infrastructure.Repository.UserRepo;
import com.example.Complaints.Management.System.shared.Security.SecurityUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {

    public UserService() {
    }

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    @Autowired
    private UserRepo userRepo;

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public UserDto registerUser(UserDto userDto){
        // validation logic on the DTO object to be implemented here
        User user = populateUser(userDto);
        user = userRepo.saveAndFlush(user);
        //entityManager.refresh(registeredUser);
        userDto.setUserId(user.getUserId());
        return userDto;
    }
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

    @Transactional
    public UserDto updateUser(UserDto userDto) throws Exception {
        // validation to all existed and required fields
        User user;
        try{
            user =  userRepo.findById(userDto.getUserId()).get();
        }catch (Exception e){
            throw new ValidationException("User not exist");
        }
        user = updateUserData(user,userDto);
//        System.out.println(user.getUserName());
        User updatedUser = userRepo.saveAndFlush(user);
        return populateUserDto(updatedUser,userDto);
    }
    private User updateUserData(User user,UserDto userDto) throws Exception {
        Class<?> dtoClass = userDto.getClass();
        Class<?> entityClass = user.getClass();

        for (Field dtoField : dtoClass.getDeclaredFields()) {
            dtoField.setAccessible(true);
            Object value = dtoField.get(userDto); // Get DTO field value

            if (value != null) { // Only map non-null values
                Field entityField = getField(entityClass, dtoField.getName());
                if (     entityField != null &&
                        !entityField.getName().equals("userId") &&
                        !entityField.getName().equals("phoneNumbers")){
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

    public UserDto getUserById(Long id) {
        try {
            UserDto response = new UserDto();
            return populateUserDto(userRepo.findById(id).get(),response);
        }catch (NoSuchElementException e){
            throw new ValidationException("User Doesn't Exist !!");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public UserDto deleteUser(Long id){
        UserDto userDto = new UserDto();
        User user ;
        try {
            user = userRepo.findById(id).get();
            userDto = populateUserDto(user,userDto);
            userRepo.delete(user);
//            entityManager.flush();
        } catch (NoSuchElementException e){
            throw new ValidationException("User Doesn't Exist !!");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return userDto;
    }
//    public List<UserDto>
    }

