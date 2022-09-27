package com.gdsdxy.dto;

import lombok.Data;

@Data
public class PwdFormDTO {
    private String phone;
    private String oldPassword;
    private String newPassword;
}
