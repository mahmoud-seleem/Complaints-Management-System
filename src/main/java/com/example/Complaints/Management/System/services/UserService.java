package com.example.Complaints.Management.System.services;

import com.example.Complaints.Management.System.DTO.UserDto;
import com.example.Complaints.Management.System.Model.User;
import com.example.Complaints.Management.System.Repository.UserRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public UserDto registerUser(UserDto userDto){
        // validation logic on the DTO object to be implemented here
        User registeredUser = userRepo.save(populateUser(userDto));
        entityManager.refresh(registeredUser);
        userDto.setUserId(registeredUser.getUserId());
        return userDto;
    }
    private User populateUser(UserDto userDto){
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
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
                if (entityField != null &
                        !entityField.getName().equals("userId") &
                        !entityField.getName().equals("phoneNumbers")){
                    entityField.setAccessible(true);
                    entityField.set(user, value); // Set value in entity
                }
            }
        }
        return user;
    }
    private UserDto populateUserDto(User user, UserDto userDto) throws IllegalAccessException {
        Class<?> dtoClass = userDto.getClass();
        Class<?> entityClass = user.getClass();

        for (Field entityField : entityClass.getDeclaredFields()) {
            entityField.setAccessible(true);
            Object value = entityField.get(user);

            if (value != null) { // Only map non-null values
                Field dtoField = getField(dtoClass, entityField.getName());
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


    public UserService() {
    }

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    public User getUserById(Long id) {

        try {
            return userRepo.findById(id).get();
        }catch (NoSuchElementException e){
            throw new ValidationException("User Doesn't Exist !!");
        }
    }
    public User deleteUser(Long id){
        User user ;
        try {
            user = userRepo.findById(id).get();
            userRepo.delete(user);
            entityManager.flush();
        } catch (NoSuchElementException e){
            throw new ValidationException("User Doesn't Exist !!");
        }
        return user;
    }

    }

