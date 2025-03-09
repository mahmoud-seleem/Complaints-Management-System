package com.example.Complaints.Management.System.core.application.services;

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
public interface StatusService {

    Status createNewStatus(String statusType);

    List<Status> getAllStatuses();

    Status updateStatus(Long id, String newType) throws ValidationException;

    void deleteStatus(Long id) throws ValidationException;
}
