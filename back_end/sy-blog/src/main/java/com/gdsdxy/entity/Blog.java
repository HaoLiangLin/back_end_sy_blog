package com.gdsdxy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("tb_blog")
@Data
public class Blog {
    @TableId("id")
    private Long id;

    @TableField("title")
    private String title;

    @TableField("synopsis")
    private String synopsis;

    @TableField("cover")
    private String cover;

    @TableField("uid")
    private Long uid;

    @TableField("cid")
    private Long cid;

    @TableField("originality")
    private Integer originality;

    @TableField("content")
    private String content;

    @TableField("likes")
    private Integer likes;

    @TableField("read")
    private Integer read;

    @TableField("isComments")
    private Integer isComments;

    @TableField("labels")
    private String labels;

    @TableField("create_date")
    private Date createDate;

    @TableField("release_date")
    private Date releaseDate;

    @TableField("update_date")
    private Date updateDate;

    @TableField("status")
    private Integer status;

    @TableField("isDel")
    @TableLogic
    private Integer isDel;
}
