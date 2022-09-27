package com.gdsdxy.dto;

import lombok.Data;

@Data
public class LoginFormDTO {
    private String account;
    private String password;
    private String phone;
    private String code;
}
