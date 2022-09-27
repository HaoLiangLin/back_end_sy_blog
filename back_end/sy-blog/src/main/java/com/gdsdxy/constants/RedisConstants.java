package com.gdsdxy.constants;

public class RedisConstants {
    // 用户登录验证码
    public static final String LOGIN_CODE_KEY = "login:code:";
    // 用户登录验证码有效期 5分钟
    public static final Long LOGIN_CODE_TTL = 5L;

    // 用户登录token
    public static final String LOGIN_USER_KEY = "login:token:";
    // 用户登录token过期期限 30分钟
    public static final Long LOGIN_USER_TTL = 30L;

    // 用户忘记密码验证码
    public static final String FORGOT_PASSWORD_CODE_KEY = "password:code";
    // 用户忘记密码验证码有效期
    public static final Long FORGOT_PASSWORD_CODE_TTL = 5L;
}
