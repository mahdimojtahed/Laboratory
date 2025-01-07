package com.mehdi.Laboratory.service;

import com.mehdi.Laboratory.dto.register.AddUserReqDTO;
import com.mehdi.Laboratory.dto.register.AddUserResDTO;

public interface UserService {

    AddUserResDTO save(AddUserReqDTO addUserReqDTO);

}
