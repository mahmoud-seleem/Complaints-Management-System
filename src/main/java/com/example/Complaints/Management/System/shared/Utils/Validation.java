package com.example.Complaints.Management.System.shared.Utils;

import com.example.Complaints.Management.System.core.application.dto.AdminDto;
import com.example.Complaints.Management.System.core.domain.entities.Admin;
import com.example.Complaints.Management.System.core.infrastructure.Repository.AdminRepo;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;

public class Validation {

    @Autowired
    private AdminRepo adminRepo;

    public void validateAdminRegistrationData(AdminDto adminDto){

    }

    public void ensureFieldIsNull(Class clazz,Object object,String name){
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            if (field.get(object) != null){
                field.set(object,null);
            }
        }catch (Exception e){

        }

    }

    public Admin isAdminExist(Long id){
        try {
            return adminRepo.findById(id).get();
        } catch (Exception e) {
            throw new ValidationException("Admin with id = " + id +" Doesn't Exist !!");
        }
    }
}
