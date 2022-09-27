package com.gdsdxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdsdxy.entity.UserInfo;
import com.gdsdxy.mapper.UserInfoMapper;
import com.gdsdxy.service.IUserInfoService;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {
}
