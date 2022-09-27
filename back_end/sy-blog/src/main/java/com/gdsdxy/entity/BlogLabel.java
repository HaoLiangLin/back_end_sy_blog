package com.gdsdxy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("tb_blog_label")
@Data
public class BlogLabel {
    @TableId("id")
    private Long id;

    @TableField("name")
    private String name;

    @TableField("description")
    private String description;
}
