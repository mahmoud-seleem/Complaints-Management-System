package com.example.Complaints.Management.System.services;

import com.example.Complaints.Management.System.DTO.AdminDto;
import com.example.Complaints.Management.System.Entities.Admin;
import com.example.Complaints.Management.System.Repository.AdminRepo;
import com.example.Complaints.Management.System.Security.SecurityUtils;
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
public class AdminService {

    public AdminService() {
    }

    @Autowired
    private AdminRepo adminRepo;

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public AdminDto registerAdmin(AdminDto adminDto){
        // validation logic on the DTO object to be implemented here
        Admin registeredAdmin = adminRepo.save(populateAdmin(adminDto));
        entityManager.refresh(registeredAdmin);
        adminDto.setUserId(registeredAdmin.getUserId());
        return adminDto;
    }
    private Admin populateAdmin(AdminDto adminDto){
        Admin admin = new Admin();
        admin.setUserName(adminDto.getUserName());
        admin.setPassword(SecurityUtils.PASSWORD_ENCODER.encode(adminDto.getPassword()));
        admin.setEmail(adminDto.getEmail());
        admin.setAge(adminDto.getAge());
        List<String> phones = admin.getPhoneNumbers();
        if (adminDto.getPhoneNumbers() != null){
            for (String phone :adminDto.getPhoneNumbers()){
                phones.add(phone);
            }
        }
        return admin;
    }

    @Transactional
    public AdminDto updateAdmin(AdminDto adminDto) throws Exception {
        // validation to all existed and required fields
        Admin admin ;
        try{
            admin =  adminRepo.findById(adminDto.getUserId()).get();
        }catch (Exception e){
            throw new ValidationException("User not exist");
        }
        admin  = updateAdminData(admin ,adminDto);
        Admin updatedAdmin = adminRepo.saveAndFlush(admin );
        return populateAdminDto(updatedAdmin,adminDto);
    }
    private Admin updateAdminData(Admin admin , AdminDto adminDto) throws Exception {
        Class<?> dtoClass = adminDto.getClass();
        Class<?> entityClass = admin .getClass();

        for (Field dtoField : dtoClass.getDeclaredFields()) {
            dtoField.setAccessible(true);
            Object value = dtoField.get(adminDto); // Get DTO field value

            if (value != null) { // Only map non-null values
                Field entityField = getField(entityClass, dtoField.getName());
                if (entityField != null &&
                        !entityField.getName().equals("userId") &&
                        !entityField.getName().equals("phoneNumbers")){
                    entityField.setAccessible(true);
                    entityField.set(admin , value); // Set value in entity
                }
            }
        }
        admin.setPassword(SecurityUtils.PASSWORD_ENCODER.encode(admin.getPassword()));
        return admin ;
    }
    private AdminDto populateAdminDto(Admin admin , AdminDto adminDto) throws IllegalAccessException {
        System.out.println(admin );
        for (Field entityField : getAllFields(admin .getClass())) {
            System.out.println(entityField.getName());
            entityField.setAccessible(true);
            Object value = entityField.get(admin );
            if (value != null) { // Only map non-null values
                Field dtoField = getField(adminDto.getClass(), entityField.getName());
                if (dtoField != null) {
                    dtoField.setAccessible(true);
                    dtoField.set(adminDto, value);
                }
            }
        }
        return adminDto;
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

    public AdminDto getAdminById(Long id) {
        try {
            AdminDto response = new AdminDto();
            return populateAdminDto(adminRepo.findById(id).get(),response);
        }catch (NoSuchElementException e){
            throw new ValidationException("User Doesn't Exist !!");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public AdminDto deleteAdmin(Long id){
        AdminDto adminDto = new AdminDto();
        Admin admin  ;
        try {
            admin  = adminRepo.findById(id).get();
            adminDto = populateAdminDto(admin ,adminDto);
            adminRepo.delete(admin );
//            entityManager.flush();
        } catch (NoSuchElementException e){
            throw new ValidationException("User Doesn't Exist !!");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return adminDto;
    }
//    public List<UserDto>
    }

