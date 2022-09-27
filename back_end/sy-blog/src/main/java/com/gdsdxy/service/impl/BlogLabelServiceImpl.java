package com.gdsdxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdsdxy.entity.BlogLabel;
import com.gdsdxy.mapper.BlogLabelMapper;
import com.gdsdxy.service.IBlogLabelService;
import org.springframework.stereotype.Service;

@Service
public class BlogLabelServiceImpl extends ServiceImpl<BlogLabelMapper, BlogLabel> implements IBlogLabelService {
}
