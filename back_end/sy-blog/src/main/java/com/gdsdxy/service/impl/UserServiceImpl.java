package com.gdsdxy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdsdxy.dto.LoginFormDTO;
import com.gdsdxy.dto.PwdFormDTO;
import com.gdsdxy.dto.ResultVo;
import com.gdsdxy.dto.UserDTO;
import com.gdsdxy.entity.User;
import com.gdsdxy.entity.UserInfo;
import com.gdsdxy.mapper.UserMapper;
import com.gdsdxy.service.IUserInfoService;
import com.gdsdxy.service.IUserService;
import com.gdsdxy.utils.RegexUtils;
import com.gdsdxy.utils.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.gdsdxy.constants.RedisConstants.*;
import static com.gdsdxy.constants.SystemConstants.USER_NICK_NAME_PREFIX;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private IUserInfoService userInfoService;

    @Override
    public ResultVo register(User user) {
        // 1. 判断手机号是否被注册
        Integer phone = query().eq("phone", user.getPhone()).count();
        if (phone > 0) {
            return ResultVo.fail("该手机号已注册！");
        }
        // 2. 判断账号是否存在
        Integer account = query().eq("account", user.getAccount()).count();
        if (account > 0) {
            return ResultVo.fail("该账号已存在!");
        }
        // 3. 注册账号
        boolean result = save(user);
        // 4. 添加用户信息
        boolean result2 = saveUserInfo(user);

        return result && result2 ? ResultVo.ok() : ResultVo.fail("注册失败！");
    }

    @Override
    public ResultVo sendCode(String phone) {
        // 校验手机号
        if (RegexUtils.isPhoneInvalid(phone)) {
            // 不符合，返回错误信息
            return ResultVo.fail("无效手机号");
        }
        String key = LOGIN_CODE_KEY + phone;
        // 符合，生成验证码
        String code = RandomUtil.randomNumbers(6);

        // 保存验证码，并设置有效期
        stringRedisTemplate.opsForValue().set(key, code, LOGIN_CODE_TTL, TimeUnit.MINUTES);

        // 发送验证码
        System.out.println("发送短信验证码成功！验证码：" + code);

        return ResultVo.ok();
    }


    @Override
    public ResultVo login(LoginFormDTO loginFormDTO) {
        // 1. 判断用户是否账号登录
        if (StrUtil.isNotBlank(loginFormDTO.getAccount())) {
            // 账号登录
            return accountLogin(loginFormDTO.getAccount(), loginFormDTO.getPassword());
        }
        // 2. 判断用户是否手机登录
        if (StrUtil.isNotBlank(loginFormDTO.getPhone())) {
            // 手机登录
            return phoneLogin(loginFormDTO.getPhone(), loginFormDTO.getCode());
        }
        // 返回错误信息
        return ResultVo.fail("请求错误！");
    }

    @Override
    public ResultVo isPassword() {
        // 1. 获取登录用户
        UserDTO userDTO = UserHolder.getUser();
        // 2. 获取用户id
        Long userId = userDTO.getId();
        // 3. 查询用户
        User user = getById(userId);
        // 4. 判断用户是否设置密码
        if (StrUtil.isNotBlank(user.getPassword())) {
            return ResultVo.ok();
        }
        return ResultVo.fail("当前用户未设置密码");
    }

    @Override
    public ResultVo setPassword(PwdFormDTO pwdFormDTO) {
        // 1. 获取登录用户
        UserDTO userDTO = UserHolder.getUser();
        // 2. 获取用户id
        Long userId = userDTO.getId();
        // 3. 查询用户
        User user = getById(userId);
        // 4. 判断用户是否存在
        if (user == null) {
            // 用户不存在
            UserHolder.removeUser();
            return ResultVo.fail("用户不存在！");
        }
        // 5. 判断用户是否设置密码
        if (StrUtil.isBlank(user.getPassword())) {
            // 未设置密码，直接将新密码设置为密码
            boolean result = update().set("password", pwdFormDTO.getNewPassword()).eq("id", userId).update();
            return result ? ResultVo.ok() : ResultVo.fail("设置密码失败！");
        }
        // 6. 判断旧密码是否一致
        if (!user.getPassword().equals(pwdFormDTO.getOldPassword())) {
            // 不一致
            return ResultVo.fail("旧密码错误！");
        }
        // 7. 修改密码
        boolean result = update().set("password", pwdFormDTO.getNewPassword()).eq("id", userId).update();
        return result ? ResultVo.ok() : ResultVo.fail("修改密码失败！");
    }

    @Override
    public ResultVo updateIcon(String icon) {
        if (StrUtil.isBlank(icon)) {
            return ResultVo.fail("头像不存在！");
        }
        // 获取用户id
        Long userId = UserHolder.getUser().getId();
        boolean result = update().set("icon", icon).eq("id", userId).update();
        return result ? ResultVo.ok() : ResultVo.fail("头像修改失败！");
    }

    @Override
    public ResultVo updateNickName(String nickname) {
        if (StrUtil.isBlank(nickname)) {
            return ResultVo.fail("昵称不能为空！");
        }
        // 获取用户id
        Long userId = UserHolder.getUser().getId();
        boolean result = update().set("nickname", nickname).eq("id", userId).update();
        return result ? ResultVo.ok() : ResultVo.fail("昵称修改失败！");
    }

    @Override
    public ResultVo isNotUpdateAccount() {
        // 获取用户id
        Long userId = UserHolder.getUser().getId();
        User user = getById(userId);
        return user.getUpdateAccount() < 1 ? ResultVo.ok() : ResultVo.fail();
    }

    @Override
    public ResultVo updateAccount(String account) {
        if (StrUtil.isBlank(account)) {
            return ResultVo.fail("账号不能为空！");
        }
        // 获取用户id
        Long userId = UserHolder.getUser().getId();
        User user = getById(userId);
        if (user.getUpdateAccount() > 0) {
            return ResultVo.fail("您的账号已超过修改次数，无法进行修改！");
        }
        boolean result = update().set("account", account).set("update_account", 1).eq("id", userId).update();
        return result ? ResultVo.ok() : ResultVo.fail("账号修改失败！");
    }

    @Override
    public ResultVo logOut(String token) {
        // 获得key
        String key = LOGIN_USER_KEY + token;
        // 删除Redis中的用户信息
        stringRedisTemplate.delete(key);
        // 删除线程变量
        UserHolder.removeUser();
        return ResultVo.ok();
    }

    @Override
    public ResultVo codePassword(String phone) {
        Integer count = query().eq("phone", phone).count();
        if (count <= 0) {
            return ResultVo.fail("手机号不存在");
        }
        return createCode(phone, FORGOT_PASSWORD_CODE_KEY, FORGOT_PASSWORD_CODE_TTL, TimeUnit.MINUTES);
    }

    @Override
    public ResultVo checkCodePassword(String phone, String code) {
        return checkCode(FORGOT_PASSWORD_CODE_KEY, phone, code);
    }

    @Override
    public ResultVo updatePassword(PwdFormDTO pwdFormDTO) {
        // 获取手机号
        String phone = pwdFormDTO.getPhone();
        // 根据手机号查询用户
        User user = query().eq("phone", phone).one();
        // 获取用户id
        Long userId = user.getId();
        // 修改密码
        boolean result = update().set("password", pwdFormDTO.getNewPassword()).eq("id", userId).update();
        return result ? ResultVo.ok() : ResultVo.fail("密码修改失败！");
    }

    /**
     * 生成手机验证码
     * @param phone 手机号
     * @param prefix key前缀
     * @param time 有效期限
     * @param timeUnit 时间单位
     * @return ResultVo
     */
    private ResultVo createCode(String phone, String prefix, Long time, TimeUnit timeUnit) {
        // 1. 校验手机号
        if (RegexUtils.isPhoneInvalid(phone)) {
            return ResultVo.fail("手机号格式错误！");
        }
        // 2. 生成验证码
        String code = RandomUtil.randomNumbers(6);
        // 3. 生成key
        String key = prefix + phone;
        // 4. 存入Redis，并设置有效期
        stringRedisTemplate.opsForValue().set(key, code, time, timeUnit);
        // 5. 发送验证码
        System.out.println("发送验证码成功！验证码：" + code);
        return ResultVo.ok();
    }

    /**
     * 添加用户信息
     */
    private boolean saveUserInfo(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getNickname());
        return userInfoService.save(userInfo);
    }

    /**
     * 手机号登录
     * @param phone 手机号
     * @param code 验证码
     * @return ResultVo
     */
    private ResultVo phoneLogin(String phone, String code){
        // 验证验证码
        ResultVo resultVo = checkCode(LOGIN_CODE_KEY, phone, code);
        if (StrUtil.isNotBlank(resultVo.getMessage())) {
            return resultVo;
        }

        // 正确，查询数据库
        User user = query().eq("phone", phone).eq("identity", 0).one();

        // 判断用户是否存在
        // 用户不存在
        if (user == null) {
            // 新注册一个用户
            user = createUserWithPhone(phone);
        }

        // 存在，将用户信息写入Redis
        String token = saveUser(user);

        // 返回token
        return ResultVo.ok(token);
    }

    /**
     * 验证验证码
     * @param prefix key前缀
     * @param phone 手机号
     * @param code 验证码
     * @return ResultVo
     */
    private ResultVo checkCode(String prefix, String phone, String code) {
        // 校验手机号
        if (RegexUtils.isPhoneInvalid(phone)) {
            return ResultVo.fail("手机号格式错误！");
        }

        String key = prefix + phone;
        // 从Redis中获取验证码
        String cacheCode = stringRedisTemplate.opsForValue().get(key);
        // 判断验证码是否正确
        if (cacheCode == null || !code.equals(cacheCode)) {
            // 错误，返回错误信息
            return ResultVo.fail("验证码错误");
        }
        return ResultVo.ok();
    }

    /**
     * 账号登录
     * @param account 账号
     * @param password 密码
     * @return ResultVo
     */
    private ResultVo accountLogin(String account, String password) {
        // 判断账户与密码是否为空
        if (StrUtil.isBlank(password)) {
            return ResultVo.fail("密码不能为空");
        }

        // 查询数据库
        User user = query().eq("account", account).eq("password", password).eq("identity", 0).one();

        // 判断用户是否存在
        if (user == null) {
            return ResultVo.fail("账号或密码错误");
        }

        // 存在，将用户信息写入Redis
        String token = saveUser(user);

        // 返回token
        return ResultVo.ok(token);
    }

    /**
     * 保存用户
     */
    private String saveUser(User user) {
        String token = IdUtil.fastSimpleUUID()+"-"+user.getId();

        // 设置Redis的key
        String key = LOGIN_USER_KEY + token;

        // 将user拷贝成UserDTO
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);

        // 将Bean转为Map类型，并存入Redis
        Map<String, Object> map = BeanUtil.beanToMap(userDTO, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        // 自定义将值转为字符串
                        // .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString())
                        .setFieldValueEditor((fieldName, fieldValue) -> {
                            if (fieldValue == null) {
                                fieldValue = "";
                            } else {
                                fieldValue += "";
                            }
                            return fieldValue;
                        })
        );
        stringRedisTemplate.opsForHash().putAll(key, map);
        // 设置到期期限
        stringRedisTemplate.expire(key, LOGIN_USER_TTL, TimeUnit.MINUTES);

        return token;
    }

    /**
     * 注册用户
     */
    private User createUserWithPhone(String phone) {
        // 1.创建用户
        User user = new User();
        user.setPhone(phone);
        user.setAccount(USER_NICK_NAME_PREFIX + phone);
        user.setNickname(USER_NICK_NAME_PREFIX + RandomUtil.randomString(13));
        user.setIdentity(0);
        // 2.保存用户
        save(user);
        return user;
    }

}
