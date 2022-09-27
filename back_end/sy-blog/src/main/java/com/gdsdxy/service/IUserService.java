package com.gdsdxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdsdxy.dto.LoginFormDTO;
import com.gdsdxy.dto.PwdFormDTO;
import com.gdsdxy.dto.ResultVo;
import com.gdsdxy.entity.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IUserService extends IService<User> {
    /**
     * 用户注册
     * @param user 用户注册信息
     * @return ResultVo
     */
    ResultVo register(User user);

    /**
     * 登录：发送验证码
     * @param phone 手机号
     * @return ResultVo
     */
    ResultVo sendCode(String phone);

    /**
     * 用户登录
     * @param loginFormDTO 登录信息
     * @return ResultVo
     */
    ResultVo login(LoginFormDTO loginFormDTO);

    /**
     * 查询用户是否设置密码
     * @return ResultVo
     */
    ResultVo isPassword();

    /**
     * 设置/修改密码
     * @param pwdFormDTO 修改密码信息
     * @return ResultVo
     */
    ResultVo setPassword(PwdFormDTO pwdFormDTO);

    /**
     * 修改用户头像
     * @param icon 头像地址
     * @return ResultVo
     */
    ResultVo updateIcon(String icon);

    /**
     * 修改用户昵称
     * @param nickname 昵称
     * @return ResultVo
     */
    ResultVo updateNickName(String nickname);

    /**
     * 查看用户是否未修改过账号
     * @return ResultVo
     */
    ResultVo isNotUpdateAccount();

    /**
     * 修改用户账号
     * @param account 账号
     * @return ResultVo
     */
    ResultVo updateAccount(String account);

    /**
     * 用户登出
     * @param token token
     * @return ResultVo
     */
    ResultVo logOut(String token);

    /**
     * 忘记密码：发送验证码
     * @param phone 手机号
     * @return ResultVo
     */
    ResultVo codePassword(String phone);

    /**
     * 忘记密码：验证验证码
     * @param phone 手机号
     * @param code 验证码
     * @return ResultVo
     */
    ResultVo checkCodePassword(String phone, String code);

    /**
     * 忘记密码：修改密码
     * @param pwdFormDTO 密码信息
     * @return ResultVo
     */
    ResultVo updatePassword(PwdFormDTO pwdFormDTO);
}
