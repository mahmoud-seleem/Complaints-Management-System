package com.example.Complaints.Management.System.services;

import com.example.Complaints.Management.System.DTO.CompDto;
import com.example.Complaints.Management.System.Model.Admin;
import com.example.Complaints.Management.System.Model.Complaint;
import com.example.Complaints.Management.System.Model.ComplaintStatus;
import com.example.Complaints.Management.System.Model.User;
import com.example.Complaints.Management.System.Repository.AdminRepo;
import com.example.Complaints.Management.System.Repository.CompStatusRepo;
import com.example.Complaints.Management.System.Repository.ComplaintRepo;
import com.example.Complaints.Management.System.Repository.UserRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CompService {
    @Autowired
    private ComplaintRepo complaintRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AdminRepo adminRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CompStatusRepo compStatusRepo;

    @Transactional
    public CompDto createNewComplaint(CompDto compDto) throws IllegalAccessException {
        Complaint complaint = new Complaint();
        populateComplaint(complaint, compDto);
        complaint.setCreationDate(new Date());
        complaint.setCurrentStatus("SUBMITTED");
        complaintRepo.saveAndFlush(complaint);

        return populateComplaintDto(complaint,compDto);
    }


    @Transactional
    public CompDto editComplaint(CompDto compDto) throws IllegalAccessException {
        // validation to check for the id of complaint
        // validation to check the userId from the database == ownerId of this compDto
        // to enforce that the same user of the complaint must be the only one that can edit it.
        Complaint complaint;
        try {
            complaint = complaintRepo.findById(compDto.getCompId()).get();
        }catch (Exception e){
            throw new ValidationException("Complaint Doesn't Exist !! ");
        }
        updateComplaintData(complaint,compDto);
        complaintRepo.saveAndFlush(complaint);
        return populateComplaintDto(complaint,compDto);
    }

    private ComplaintStatus createCompStatus(Complaint complaint, Admin admin){
        ComplaintStatus complaintStatus = new ComplaintStatus();
        complaintStatus.setComplaint(complaint);
        complaintStatus.setAdmin(admin);
        complaintStatus.setStatusDate(new Date());
        return complaintStatus;
    }
    private void updateComplaintData(Complaint complaint,CompDto compDto){
        String title = compDto.getTitle();
        String description = compDto.getDescription();
        String category = compDto.getCategory();
        if (title != null){
            complaint.setTitle(title);
        }
        if (description != null){
            complaint.setDescription(description);
        }
        if (category != null){
            complaint.setCategory(category);
        }
    }
    private Complaint populateComplaint(Complaint complaint, CompDto compDto) throws IllegalAccessException {
        User user;
        try {
            user = userRepo.findById(compDto.getOwnerId()).get();
        } catch (Exception e) {
            throw new ValidationException("User Doesn't Exist !!");
        }
        complaint.setUser(user);
        try{
            complaint.setAdmin(adminRepo.findById(1l).get());
        }catch (Exception e){
            throw new ValidationException("Admin Doesn't Exist !!");
        }
        for (Field dtoField : getAllFields(compDto.getClass())) {
            dtoField.setAccessible(true);
            Object value = dtoField.get(compDto); // Get DTO field value

            if (value != null) { // Only map non-null values
                Field entityField = getField(complaint.getClass(), dtoField.getName());
                if (entityField != null && !dtoField.getName().equals("creationDate")) {
                    entityField.setAccessible(true);
                    entityField.set(complaint, value); // Set value in entity
                }
            }
        }
        return complaint;
    }
    private CompDto populateComplaintDto(Complaint complaint, CompDto compDto) throws IllegalAccessException {
        for (Field entityField : getAllFields(complaint.getClass())) {
            entityField.setAccessible(true);
            Object value = entityField.get(complaint); // Get DTO field value

            if (value != null) { // Only map non-null values
                Field dtoField = getField(compDto.getClass(), entityField.getName());
                if (dtoField != null && !dtoField.getName().equals("creationDate")) {
                    dtoField.setAccessible(true);
                    dtoField.set(compDto, value); // Set value in entity
                }
            }
        }
        compDto.setOwnerId(complaint.getUser().getUserId());
        compDto.setAssigneeId(complaint.getAdmin().getUserId());
        compDto.setCreationDate(complaint.getCreationDate().toString());
        return compDto;
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
