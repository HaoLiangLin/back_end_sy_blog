package com.gdsdxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdsdxy.entity.Blog;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IBlogService extends IService<Blog> {
}
