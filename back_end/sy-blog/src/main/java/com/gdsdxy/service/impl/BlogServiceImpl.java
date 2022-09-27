package com.gdsdxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdsdxy.entity.Blog;
import com.gdsdxy.mapper.BlogMapper;
import com.gdsdxy.service.IBlogService;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {
}
