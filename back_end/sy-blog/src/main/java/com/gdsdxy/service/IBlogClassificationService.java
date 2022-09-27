package com.gdsdxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdsdxy.entity.BlogClassification;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IBlogClassificationService extends IService<BlogClassification> {
}
