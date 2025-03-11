package com.example.Complaints.Management.System.core.application.services;

import com.example.Complaints.Management.System.core.application.dto.AdminDto;
import com.example.Complaints.Management.System.core.domain.entities.Admin;
import com.example.Complaints.Management.System.core.infrastructure.Repository.AdminRepo;
import com.example.Complaints.Management.System.shared.Security.SecurityUtils;
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
public interface AdminService {
    AdminDto registerAdmin(AdminDto adminDto) throws NoSuchFieldException, IllegalAccessException;
    AdminDto updateAdmin(AdminDto adminDto) throws Exception;
    AdminDto getAdminById(Long id);
    AdminDto deleteAdmin(Long id);
    }

