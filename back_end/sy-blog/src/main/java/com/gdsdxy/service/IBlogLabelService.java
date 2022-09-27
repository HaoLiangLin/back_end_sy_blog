package com.gdsdxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdsdxy.entity.BlogLabel;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IBlogLabelService extends IService<BlogLabel> {
}
