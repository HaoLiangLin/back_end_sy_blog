package com.gdsdxy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("tb_blog_classification")
@Data
public class BlogClassification {
    @TableId("id")
    private Long id;

    @TableField("name")
    private String name;

    @TableField("icon")
    private String icon;

    @TableField("fid")
    private Long fid;

    @TableField("description")
    private String description;
}
