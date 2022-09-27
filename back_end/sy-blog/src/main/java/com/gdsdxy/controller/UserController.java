package com.gdsdxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdsdxy.dto.LoginFormDTO;
import com.gdsdxy.dto.PwdFormDTO;
import com.gdsdxy.dto.ResultVo;
import com.gdsdxy.dto.UserDTO;
import com.gdsdxy.entity.User;
import com.gdsdxy.service.IUserService;
import com.gdsdxy.utils.UserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/users")
@Api(tags = "用户登录接口")
public class UserController {
    @Resource
    private IUserService userService;

    @PostMapping("/register")
    @ApiOperation(value = "注册功能", notes = "用户注册接口")
    public ResultVo register(@RequestBody User user) {
        boolean result = userService.save(user);
        return result ? ResultVo.ok() : ResultVo.fail("注册失败");
    }

    @GetMapping("/code")
    @ApiOperation(value = "验证码功能", notes = "用户获取验证码接口")
    public ResultVo getCode(@RequestParam String phone) {
        return userService.sendCode(phone);
    }

    @PostMapping("/login")
    @ApiOperation(value = "登录功能", notes = "用户登录接口")
    public ResultVo login(@RequestBody LoginFormDTO loginFormDTO) {
        return userService.login(loginFormDTO);
    }

    @GetMapping("/me")
    @ApiOperation(value = "登录信息功能", notes = "获取用户登录信息接口")
    public ResultVo me() {
        UserDTO user = UserHolder.getUser();
        return ResultVo.ok(user);
    }

    @GetMapping("/authPassword")
    @ApiOperation(value = "用户是否设置账号密码验证", notes = "判断用户是否已经设置密码")
    public ResultVo authPassword() {
        return userService.authPassword();
    }

    @PutMapping("/updatePassword")
    @ApiOperation(value = "修改密码功能", notes = "用户修改登录密码")
    public ResultVo updatePassword(@RequestBody PwdFormDTO pwdFormDTO) {
        /**
         * 用户修改密码：
         * 如果用户未设置密码，则直接使用新密码作为账号密码
         * 如果用户已经设置密码，则判断旧密码是否一致后，再将旧密码改为新密码
         */
        return userService.updatePassword(pwdFormDTO);
    }
}
