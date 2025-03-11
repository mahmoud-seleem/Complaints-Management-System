package com.example.Complaints.Management.System.core.application.services;

import com.example.Complaints.Management.System.core.application.dto.UserDto;
import com.example.Complaints.Management.System.core.domain.entities.User;
import com.example.Complaints.Management.System.core.infrastructure.Repository.UserRepo;
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
public interface UserService {
    UserDto registerUser(UserDto userDto) throws ValidationException, NoSuchFieldException, IllegalAccessException;

    UserDto updateUser(UserDto userDto) throws Exception;

    UserDto getUserById(Long id) throws ValidationException, IllegalAccessException;

    UserDto deleteUser(Long id) throws ValidationException, IllegalAccessException;
    }

