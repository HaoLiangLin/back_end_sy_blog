package com.gdsdxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdsdxy.entity.UserInfo;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IUserInfoService extends IService<UserInfo> {
}
