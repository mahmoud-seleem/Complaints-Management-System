package com.example.Complaints.Management.System.core.domain.services;

import com.example.Complaints.Management.System.core.application.dto.AdminDto;
import com.example.Complaints.Management.System.core.application.services.AdminService;
import com.example.Complaints.Management.System.core.domain.entities.Admin;
import com.example.Complaints.Management.System.core.infrastructure.Repository.AdminRepo;
import com.example.Complaints.Management.System.shared.Security.SecurityUtils;
import com.example.Complaints.Management.System.shared.Utils.Validation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Temporal;
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
public class AdminServiceImp implements AdminService {

    public AdminServiceImp() {
    }

    @Autowired
    private AdminRepo adminRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private Validation validation;

    @Transactional
    public AdminDto registerAdmin(AdminDto adminDto) throws NoSuchFieldException, IllegalAccessException {
        // validation logic on the DTO object to be implemented here
        validation.validateGeneralUserRegistrationData(adminDto);
        Admin registeredAdmin = adminRepo.saveAndFlush(populateAdmin(adminDto));
        adminDto.setUserId(registeredAdmin.getUserId());
        return adminDto;
    }

    @Transactional
    public AdminDto updateAdmin(AdminDto adminDto) throws Exception {
        // validation to all existed and required fields
        validation.validateGeneralUserUpdateData(adminDto);
        Admin admin = validation.isAdminExist(adminDto.getUserId());
        admin = updateAdminData(admin, adminDto);
        Admin updatedAdmin = adminRepo.saveAndFlush(admin);
        return populateAdminDto(updatedAdmin, adminDto);
    }



    @Transactional
    public AdminDto getAdminById(Long id) throws IllegalAccessException {
        Admin admin = validation.isAdminExist(id);
        validation.isAllowedAdmin(id);
        AdminDto adminDto = new AdminDto();
        return populateAdminDto(admin, adminDto);
    }

    @Transactional
    public AdminDto deleteAdmin(Long id) throws IllegalAccessException {
        Admin admin = validation.isAdminExist(id);
        validation.isAllowedAdmin(id);
        AdminDto adminDto = new AdminDto();
        populateAdminDto(admin, adminDto);
        adminRepo.delete(admin);
        return adminDto;
    }

    private Admin populateAdmin(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setUserName(adminDto.getUserName());
        admin.setPassword(SecurityUtils.PASSWORD_ENCODER.encode(adminDto.getPassword()));
        admin.setEmail(adminDto.getEmail());
        admin.setAge(adminDto.getAge());
        List<String> phones = admin.getPhoneNumbers();
        if (adminDto.getPhoneNumbers() != null) {
            for (String phone : adminDto.getPhoneNumbers()) {
                phones.add(phone);
            }
        }
        return admin;
    }

    private Admin updateAdminData(Admin admin, AdminDto adminDto) throws Exception {
        Class<?> dtoClass = adminDto.getClass();
        Class<?> entityClass = admin.getClass();

        for (Field dtoField : getAllFields(dtoClass)) {
            dtoField.setAccessible(true);
            Object value = dtoField.get(adminDto); // Get DTO field value

            if (value != null) { // Only map non-null values
                Field entityField = getField(entityClass, dtoField.getName());
                if (entityField != null &&
                        !entityField.getName().equals("userId")) {
                    entityField.setAccessible(true);
                    entityField.set(admin, value); // Set value in entity
                }
            }
        }
        admin.setPassword(SecurityUtils.PASSWORD_ENCODER.encode(admin.getPassword()));
        return admin;
    }

    private AdminDto populateAdminDto(Admin admin, AdminDto adminDto) throws IllegalAccessException {
        System.out.println(admin);
        for (Field entityField : getAllFields(admin.getClass())) {
            System.out.println(entityField.getName());
            entityField.setAccessible(true);
            Object value = entityField.get(admin);
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
        while (clazz != null) {
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
        while (clazz != null) {
            fields.addAll(Arrays.stream(clazz.getDeclaredFields()).toList());
            clazz = clazz.getSuperclass();
        }
        return fields;
    }


}

