package com.example.Complaints.Management.System.core.application.services;

import com.example.Complaints.Management.System.core.application.dto.CompDto;
import com.example.Complaints.Management.System.core.application.dto.CompStatusDto;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public interface CompService {
    CompDto createNewComplaint(CompDto compDto) throws IllegalAccessException, ValidationException;

    CompDto editComplaint(CompDto compDto) throws IllegalAccessException, ValidationException;

    CompDto getComplaintById(Long id) throws IllegalAccessException, ValidationException;

    List<CompDto> getAllUserComplaints(Long id) throws IllegalAccessException, ValidationException;

    List<CompDto> getAllAdminAssignedComplaints(Long id) throws IllegalAccessException, ValidationException;

    List<CompDto> deleteComplaint(Long userId, Long compId) throws IllegalAccessException, ValidationException;

    CompDto changeComplaintStatus(Long adminId, Long compId, String newType) throws IllegalAccessException, ValidationException;

    CompDto changeComplaintAssignee(Long adminId, Long compId, Long newAssignee) throws IllegalAccessException, ValidationException;

    List<CompStatusDto> getComplaintHistory(Long compId);

    List<CompDto> getUserNextComplaints(Long userId, String cursor, int size, String status, boolean isAdmin) throws IllegalAccessException, ValidationException;

    List<CompDto> getUserPrevComplaints(Long userId, String cursor, int size, String status, boolean isAdmin) throws IllegalAccessException, ValidationException;

}
