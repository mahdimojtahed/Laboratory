package com.mehdi.Laboratory.dto.register;

import com.mehdi.Laboratory.dto.BaseResponse;
import com.mehdi.Laboratory.shared.constants.ResponseCodePool;

public class AddUserResDTO extends BaseResponse {

    private String username;

    public AddUserResDTO(String username, ResponseCodePool responseCodePool) {
        super(responseCodePool);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
