package com.gdsdxy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_user")
public class User {
    @TableId("id")
    private Long id;
    @TableField("phone")
    private String phone;
    @TableField("account")
    private String account;
    @TableField("password")
    private String password;
    @TableField("identity")
    private Integer identity;
    @TableField("icon")
    private String icon;
    @TableField("nickname")
    private String nickname;
    @TableField("update_account")
    private Integer updateAccount;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    @TableLogic
    @TableField("isDel")
    private Integer isDel;
}
