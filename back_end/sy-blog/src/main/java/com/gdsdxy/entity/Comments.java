package com.gdsdxy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("tb_blog_comments")
@Data
public class Comments {
    @TableId("id")
    private Long id;

    @TableField("bid")
    private Long  bid;

    @TableField("uid")
    private Long uid;

    @TableField("content")
    private String content;

    @TableField("time")
    private Date time;

    @TableField("likes")
    private Integer likes;

    @TableField("isAuthor")
    private Integer isAuthor;

    @TableField("fcid")
    private Long fcid;

    @TableField("status")
    private Integer status;

}
