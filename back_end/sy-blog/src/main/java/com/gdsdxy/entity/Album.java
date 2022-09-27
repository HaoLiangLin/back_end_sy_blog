package com.gdsdxy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("tb_blog_album")
@Data
public class Album {
    @TableId("id")
    private Long id;

    @TableField("uid")
    private Long uid;

    @TableField("name")
    private String name;

    @TableField("content")
    private String content;

    @TableField("upload_date")
    private Date uploadDate;

    @TableField("province")
    private String province;

    @TableField("city")
    private String city;

    @TableField("district")
    private String district;

    @TableField("address")
    private String address;

    @TableField("isDel")
    @TableLogic
    private Integer isDel;
}
