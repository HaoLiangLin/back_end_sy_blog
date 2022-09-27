package com.gdsdxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdsdxy.entity.BlogClassification;
import com.gdsdxy.mapper.BlogClassificationMapper;
import com.gdsdxy.service.IBlogClassificationService;
import org.springframework.stereotype.Service;

@Service
public class BlogClassificationServiceImpl extends ServiceImpl<BlogClassificationMapper, BlogClassification> implements IBlogClassificationService {
}
