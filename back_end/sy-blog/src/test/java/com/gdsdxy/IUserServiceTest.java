package com.gdsdxy;

import com.gdsdxy.entity.User;
import com.gdsdxy.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class IUserServiceTest {
    @Resource
    private IUserService  userService;

    @Test
    void insertTest() {
        User user = new User();
        user.setPhone("13011112222");
        user.setAccount("tom");
        user.setPassword("123456");
        boolean rs = userService.save(user);
        System.out.println(rs);
    }

    @Test
    void selectTest() {
        User user = userService.getById(1570696396229685249l);
        System.out.println("user = " + user);
    }
}
