package com.example.Complaints.Management.System.core.domain.services;

import com.example.Complaints.Management.System.core.application.dto.CompDto;
import com.example.Complaints.Management.System.core.application.dto.CompStatusDto;
import com.example.Complaints.Management.System.core.application.services.CompService;
import com.example.Complaints.Management.System.core.domain.entities.*;
import com.example.Complaints.Management.System.core.infrastructure.Repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CompServiceImp implements CompService {
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

    @Autowired
    private StatusRepo statusRepo;

    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Transactional
    public CompDto createNewComplaint(CompDto compDto) throws IllegalAccessException {
        Complaint complaint = new Complaint();
        populateComplaint(complaint, compDto);
        complaint.setCreationDate(new Date());
        complaint.setCurrentStatus("Submitted");
        complaintRepo.saveAndFlush(complaint);
        createCompStatus(
                complaint,
                statusRepo.findByStatusType("Submitted"),
                complaint.getAdmin());
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

    @Transactional
    public CompDto getComplaintById(Long id) throws IllegalAccessException {
        CompDto compDto = new CompDto();
        Complaint complaint;
        try {
            complaint = complaintRepo.findById(id).get();
        }catch (Exception e){
            throw new ValidationException("Complaint Doesn't Exist !! ");
        }
        return populateComplaintDto(complaint,compDto);
    }


    @Transactional
    public List<CompDto> getAllUserComplaints(Long id) throws IllegalAccessException {
        User user;
        try {
            user = userRepo.findById(id).get();
        } catch (Exception e) {
            throw new ValidationException("User Doesn't Exist !!");
        }

        List<CompDto> compDtos = new ArrayList<>();
        for (Complaint complaint : user.getComplaints()){
            compDtos.add(populateComplaintDto(complaint,new CompDto()));
        }
        return compDtos;
    }
    @Transactional
    public List<CompDto> getAllAdminAssignedComplaints(Long id) throws IllegalAccessException {
        Admin admin;
        try {
            admin = adminRepo.findById(id).get();
        } catch (Exception e) {
            throw new ValidationException("Admin Doesn't Exist !!");
        }

        List<CompDto> compDtos = new ArrayList<>();
        for (Complaint complaint : admin.getComplaints()){
            compDtos.add(populateComplaintDto(complaint,new CompDto()));
        }
        return compDtos;
    }

    private ComplaintStatus createCompStatus(Complaint complaint, Status status, Admin admin){
        ComplaintStatus complaintStatus = new ComplaintStatus(complaint,status,admin,new Date());
        return compStatusRepo.saveAndFlush(complaintStatus);
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

    @Transactional
    public List<CompDto> deleteComplaint(Long userId,Long compId) throws IllegalAccessException {
        Complaint complaint;
        try {
            complaint = complaintRepo.findById(compId).get();
        }catch (Exception e){
            throw new ValidationException("Complaint Doesn't Exist !! ");
        }
        if(complaint.getUser().getUserId() == userId){
            complaintRepo.delete(complaint);
            entityManager.flush();
            System.out.println("deleted");
        }else {
            throw new ValidationException("User Can Only Delete From His Own Complaints !");
        }
        return getAllUserComplaints(userId);
    }

    @Transactional
    public CompDto changeComplaintStatus(Long adminId,Long compId,String newType) throws IllegalAccessException {
        Complaint complaint = isComplaintExist(compId);
        if (adminId != complaint.getAdmin().getUserId()){
            throw new ValidationException(
                    "Only Assigned Admin Can Change This Complaint Status");
        }
        Admin admin = isAdminExist(adminId);
        Status newstatus = isValidStatusType(newType);
        if (newType.equals(complaint.getCurrentStatus())){
            return populateComplaintDto(
                    complaint,
                    new CompDto());
        }
        createCompStatus(complaint,newstatus,admin);
        complaint.setCurrentStatus(newType);
        return populateComplaintDto(
                complaintRepo.saveAndFlush(complaint),
                new CompDto());
    }
    @Transactional
    public CompDto changeComplaintAssignee(Long adminId,Long compId,Long newAssignee) throws IllegalAccessException {
        Complaint complaint = isComplaintExist(compId);
        Admin admin = isAdminExist(adminId);
        if (adminId != complaint.getAdmin().getUserId()){
            throw new ValidationException(
                    "Only Assigned Admin Can set new Assignee for this Complaint");
        }
        Admin assignee = isAdminExist(newAssignee);
        complaint.setAdmin(assignee);
        return populateComplaintDto(
                complaintRepo.saveAndFlush(complaint),
                new CompDto());
    }

    @Transactional
    public List<CompStatusDto> getComplaintHistory(Long compId){
        Complaint complaint = isComplaintExist(compId);
        List<CompStatusDto> result = new ArrayList<>();
        for (ComplaintStatus complaintStatus : complaint.getComplaintStatuses()){
            result.add(populateCompStatusDto(complaintStatus,new CompStatusDto()));
        }
        return result;
    }

    @Transactional
    public List<CompDto> getUserNextComplaints(Long userId,
                                               String cursor,
                                               int size,
                                               String status,
                                               boolean isAdmin) throws IllegalAccessException {
        if (status != null){
            isValidStatusType(status);
        }
        if(isAdmin == true){
            isAdminExist(userId);
        }else {
            isUserExist(userId);
        }
        System.out.println("cursor: "+cursor);
        Date lastCursor = null;
        if (cursor == null) {
          lastCursor = new Date(0);
        }else {
            try {
                lastCursor = Date.from(
                        LocalDateTime.parse(cursor, FORMATTER)
                                .atZone(ZoneId.systemDefault()).toInstant());
            }catch (Exception e){
                throw new ValidationException(e.getMessage());
            }
        }
        System.out.println(lastCursor);
        List<CompDto> result = new ArrayList<>();
        List<Complaint> complaints;
        if (isAdmin == true ){
            complaints = complaintRepo.findAdminNextComplaintsNative(userId, status,lastCursor,size);
        }else {
            complaints = complaintRepo.findUserNextComplaintsNative(userId, status,lastCursor,size);
        }
        for (Complaint complaint : complaints){
            System.out.println(complaint.getCreationDate());
            result.add(populateComplaintDto(complaint,new CompDto()));
        }
        return result;
    }

        @Transactional
        public List<CompDto> getUserPrevComplaints(Long userId,
                                                   String cursor,
                                                   int size,
                                                   String status,
                                                   boolean isAdmin) throws IllegalAccessException {
            if (status != null){
                isValidStatusType(status);
            }
            if(isAdmin == true){
                isAdminExist(userId);
            }else {
                isUserExist(userId);
            }
            Date lastCursor;
            if (cursor == null) {
                lastCursor = new Date(0);
            }else {
                try {
                    lastCursor = Date.from(
                            LocalDateTime.parse(cursor, FORMATTER)
                                    .atZone(ZoneId.systemDefault()).toInstant());
                }catch (Exception e){
                    throw new ValidationException(e.getMessage());
                }
            }
            System.out.println(lastCursor);
            List<CompDto> result = new ArrayList<>();
            List<Complaint> complaints;
            if (isAdmin == true ){
                complaints = complaintRepo.findAdminPrevComplaintsNative(userId, status,lastCursor,size);
            }else {
                complaints = complaintRepo.findUserPrevComplaintsNative(userId, status,lastCursor,size);
            }
            for (Complaint complaint : complaints){
                System.out.println(complaint.getCreationDate());
                result.add(populateComplaintDto(complaint,new CompDto()));
            }
            return result;
    }
    private CompStatusDto populateCompStatusDto(ComplaintStatus complaintStatus, CompStatusDto compStatusDto){
        compStatusDto.setStatus(complaintStatus.getStatus().getStatusType());
        compStatusDto.setDate(complaintStatus.getStatusDate().toString());
        compStatusDto.setChangerId(complaintStatus.getAdmin().getUserId());
        return compStatusDto;
    }


    private Admin isAdminExist(Long id){
        try {
            return adminRepo.findById(id).get();
        } catch (Exception e) {
            throw new ValidationException("Admin with id = " + id +" Doesn't Exist !!");
        }
    }
    private User isUserExist(Long id){
        try {
            return userRepo.findById(id).get();
        } catch (Exception e) {
            throw new ValidationException("User with id = " + id +" Doesn't Exist !!");
        }
    }

    private Status isValidStatusType(String type){
        Status status = statusRepo.findByStatusType(type);
        if(status == null){
            throw new ValidationException("Invalid Status Type!!");
        }
        return status;
    }
    private Complaint isComplaintExist(Long id){
        try {
            return complaintRepo.findById(id).get();
        }catch (Exception e){
            throw new ValidationException("Complaint with id = "+id+" Doesn't Exist !! ");
        }
    }
//    private CompDto populateComplaintDtoWithAllDetails(Complaint complaint, CompDto compDto) throws IllegalAccessException {
//
//    }
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
