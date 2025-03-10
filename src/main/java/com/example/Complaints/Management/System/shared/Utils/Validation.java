package com.example.Complaints.Management.System.shared.Utils;

import com.example.Complaints.Management.System.core.application.dto.AdminDto;
import com.example.Complaints.Management.System.core.application.dto.GeneralUserDto;
import com.example.Complaints.Management.System.core.domain.entities.Admin;
import com.example.Complaints.Management.System.core.infrastructure.Repository.AdminRepo;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;

public class Validation {

    @Autowired
    private AdminRepo adminRepo;

    public void validateAdminRegistrationData(AdminDto adminDto) throws NoSuchFieldException, IllegalAccessException {
        ensureFieldIsNull(AdminDto.class,adminDto,"userId");
        validateGeneralUserRequiredFieldsAreNotNull(adminDto);
        }


        public void validateGeneralUserRequiredFieldsAreNotNull(GeneralUserDto dto){
            isNotNull(dto.getUserName(),"userName");
            isNotNull(dto.getPassword(),"password");
            isNotNull(dto.getEmail(),"email");
            isNotNull(dto.getAge(),"age");
        }
    public void isNotNull(Object value,String name){
        if (value == null){
            throw new CustomValidationException("field can't be null !!",name,null);
        }
    }
    public void ensureFieldIsNull(Class clazz,Object object,String name) throws NoSuchFieldException, IllegalAccessException {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            if (field.get(object) != null){
                field.set(object,null);
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
