package com.example.Complaints.Management.System.core.domain.services;

import com.example.Complaints.Management.System.core.application.services.StatusService;
import com.example.Complaints.Management.System.core.domain.entities.Status;
import com.example.Complaints.Management.System.core.infrastructure.Repository.StatusRepo;
import com.example.Complaints.Management.System.shared.Utils.CustomValidationException;
import com.example.Complaints.Management.System.shared.Utils.Validation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class StatusServiceImp implements StatusService {

    @Autowired
    private StatusRepo statusRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private Validation validation;
    @Transactional
    public Status createNewStatus(String statusType){
        // check for the type if exists (no duplication)
        validation.validateStatusIsNotExist(statusType);
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
        validation.validateStatusIsNotExist(newType);
        Status status;
        try{
            status = statusRepo.findById(id).get();
        }catch (Exception e ){
            throw new CustomValidationException(
                    "Status with this id Doesn't Exist !!",
                    "id",
                    id);
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
            throw new CustomValidationException(
                    "Status with this id Doesn't Exist !!",
                    "id",
                    id);
        }
        statusRepo.delete(status);
    }

}
