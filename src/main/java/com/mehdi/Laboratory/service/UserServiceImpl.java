package com.mehdi.Laboratory.service;

import com.mehdi.Laboratory.controller.ArchTestController;
import com.mehdi.Laboratory.domain.JpaUser;
import com.mehdi.Laboratory.dto.register.AddUserReqDTO;
import com.mehdi.Laboratory.dto.register.AddUserResDTO;
import com.mehdi.Laboratory.repository.UserRepository;
import com.mehdi.Laboratory.shared.constants.ResponseCodePool;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ArchTestController archTestController;

    public UserServiceImpl(UserRepository userRepository, ArchTestController archTestController) {
        this.userRepository = userRepository;
        this.archTestController = archTestController;
    }

    @Override
    public AddUserResDTO save(AddUserReqDTO addUserReqDTO) {
        validateSaveUser(addUserReqDTO);
        userRepository.findByUsername(addUserReqDTO.getUsername())
                .ifPresent((u) -> {throw new RuntimeException("User already exists with username" + u.getUsername());});
        JpaUser user = userRepository.save(new JpaUser(addUserReqDTO.getFirstName(), addUserReqDTO.getLastName(), addUserReqDTO.getUsername(), new Date(addUserReqDTO.getBirthDate()), addUserReqDTO.getPassword()));
        return new AddUserResDTO(user.getUsername(), ResponseCodePool.SUCCESS);
    }

    public static void validateSaveUser(AddUserReqDTO addUserReqDTO) {
        if (addUserReqDTO.getBirthDate() == null || addUserReqDTO.getBirthDate().equals(0L)) {
            throw new RuntimeException("REQUIRED_BIRTHDATE");
        }

        if (addUserReqDTO.getBirthDate() > new Date().getTime()) {
            throw new RuntimeException("Birth date must be in the past");
        }

        if (addUserReqDTO.getBirthDate() < -1549119418) {
            throw new RuntimeException("Birth date must be bigger than 1920");
        }

        if (addUserReqDTO.getPassword().length() < 8) {
            throw new RuntimeException("Password must be at least 8 characters");
        }

        if (addUserReqDTO.getPassword().contains("password")) {
            throw new RuntimeException("Password can not contain the word password");
        }

        if (addUserReqDTO.getPassword().contains(addUserReqDTO.getFirstName())) {
            throw new RuntimeException("Password can not contain the First name");
        }

        if (addUserReqDTO.getPassword().contains(addUserReqDTO.getLastName())) {
            throw new RuntimeException("Password can not contain the Last name");
        }

        if (addUserReqDTO.getPassword().contains(addUserReqDTO.getUsername())) {
            throw new RuntimeException("Password can not contain username");
        }
    }


}
