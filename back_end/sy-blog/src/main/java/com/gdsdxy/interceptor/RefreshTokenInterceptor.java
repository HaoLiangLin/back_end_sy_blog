package com.gdsdxy.interceptor;

import cn.hutool.core.bean.BeanUtil;
import com.gdsdxy.dto.UserDTO;
import com.gdsdxy.utils.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.gdsdxy.constants.RedisConstants.LOGIN_USER_KEY;
import static com.gdsdxy.constants.RedisConstants.LOGIN_USER_TTL;

/**
 * 拦截所有请求
 */
@Component
public class RefreshTokenInterceptor implements HandlerInterceptor {
    private StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     *
     * 作用：刷新登录用户token实现，避免访问非登录接口，而token失效
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求头中的token
        String token = request.getHeader("authorization");

        String key = LOGIN_USER_KEY + token;
        // 从Redis查询数据
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(key);

        // 数据存在
        if (!userMap.isEmpty()) {
            // 将map转换成Bean
            UserDTO userDTO = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), false);

            // 将用户信息保存
            UserHolder.saveUser(userDTO);

            // 刷新token有效期
            stringRedisTemplate.expire(key, LOGIN_USER_TTL, TimeUnit.MINUTES);
        }

        // 放行
        return true;
    }
}
