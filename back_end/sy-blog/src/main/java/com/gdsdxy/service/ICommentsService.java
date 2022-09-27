package com.gdsdxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdsdxy.entity.Comments;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ICommentsService extends IService<Comments> {
}
