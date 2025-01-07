package com.mehdi.Laboratory.controller;

import com.mehdi.Laboratory.dto.register.AddUserReqDTO;
import com.mehdi.Laboratory.dto.register.AddUserResDTO;
import com.mehdi.Laboratory.service.UserServiceImpl;
import com.mehdi.Laboratory.shared.constants.Routes;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = Routes.USERS)
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<AddUserResDTO> registerUser(@Valid @RequestBody AddUserReqDTO addUserReqDTO) {
        return ResponseEntity.ok(userService.save(addUserReqDTO));
    }

}
