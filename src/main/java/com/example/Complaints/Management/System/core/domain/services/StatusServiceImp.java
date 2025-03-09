package com.example.Complaints.Management.System.core.domain.services;

import com.example.Complaints.Management.System.core.domain.entities.Status;
import com.example.Complaints.Management.System.core.infrastructure.Repository.StatusRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class StatusServiceImp {

    @Autowired
    private StatusRepo statusRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Status createNewStatus(String statusType){
        // check for the type if exists (no duplication)
        Status status = new Status();
        status.setStatusType(statusType);
        return statusRepo.saveAndFlush(status);
    }

    @Transactional
    public List<Status> getAllStatuses(){
        return statusRepo.findAll();
    }
    @Transactional
    public Status updateStatus(Long id,String newType){
        Status status;
        try{
            status = statusRepo.findById(id).get();
        }catch (Exception e ){
            throw new ValidationException("Status Doesn't Exist !!");
        }
        status.setStatusType(newType);
        return statusRepo.saveAndFlush(status);
    }

    @Transactional
    public void deleteStatus(Long id){
        Status status;
        try{
            status = statusRepo.findById(id).get();
        }catch (Exception e ){
            throw new ValidationException("Status Doesn't Exist !!");
        }
        statusRepo.delete(status);
    }

}
