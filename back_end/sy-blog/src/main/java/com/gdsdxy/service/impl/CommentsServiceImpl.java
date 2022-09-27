package com.gdsdxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdsdxy.entity.Comments;
import com.gdsdxy.mapper.CommentsMapper;
import com.gdsdxy.service.ICommentsService;
import org.springframework.stereotype.Service;

@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements ICommentsService {
}
