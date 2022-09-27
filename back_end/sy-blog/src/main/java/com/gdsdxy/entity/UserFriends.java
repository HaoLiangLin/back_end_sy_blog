package com.gdsdxy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_user_friends")
public class UserFriends {
    @TableId("id")
    private Long id;

    @TableField("uid")
    private Long uid;

    @TableField("fid")
    private Long fid;

    @TableField("fnickname")
    private String fnickname;

    @TableField("remark")
    private String remark;

    @TableField("isCancel")
    private Integer isCancel;
}
