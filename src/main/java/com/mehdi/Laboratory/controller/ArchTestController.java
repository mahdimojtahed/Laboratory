package com.mehdi.Laboratory.controller;

import com.mehdi.Laboratory.service.UserServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ArchTestController {

    // todo add another serviceImpl in here to prevent circular dependency
//    private final UserServiceImpl userService;

//    public ArchTestController(UserServiceImpl userService) {
//        this.userService = userService;
//    }




}
