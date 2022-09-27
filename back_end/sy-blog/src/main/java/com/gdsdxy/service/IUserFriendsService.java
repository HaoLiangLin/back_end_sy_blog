package com.gdsdxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdsdxy.entity.UserFriends;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IUserFriendsService extends IService<UserFriends> {
}
