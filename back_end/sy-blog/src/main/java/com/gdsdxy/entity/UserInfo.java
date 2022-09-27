package com.gdsdxy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_user_info")
public class UserInfo {
    @TableId("id")
    private Long id;

    @TableField("username")
    private String username;

    @TableField("age")
    private Integer age;

    @TableField("gender")
    private String gender;

    @TableField("email")
    private String email;

    @TableField("birthday")
    private Date birthday;

    @TableField("qq")
    private String qq;

    @TableField("fans")
    private Integer fans;

    @TableField("followee")
    private Integer followee;

    @TableField("level")
    private Integer level;

    @TableField("introduce")
    private String introduce;
}
