package com.gdsdxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdsdxy.entity.UserFriends;
import com.gdsdxy.mapper.UserFriendsMapper;
import com.gdsdxy.service.IUserFriendsService;
import org.springframework.stereotype.Service;

@Service
public class UserFriendsServiceImpl extends ServiceImpl<UserFriendsMapper, UserFriends> implements IUserFriendsService {
}
