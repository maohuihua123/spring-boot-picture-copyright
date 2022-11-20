package com.mao.demo.dto;

import com.mao.demo.common.annotation.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotBlank(message = "用户昵称不能为空")
    @Size(min = 2, max = 20, message = "用户昵称必须是2-20个字符")
    @ExceptionCode(value = 1001, message = "用户昵称验证错误")
    private String nickName;

    @NotBlank(message = "用户密码不能为空")
    @Size(min = 6, max = 16, message = "密码长度必须是6-16个字符")
    @ExceptionCode(value = 1002, message = "密码验证错误")
    private String password;

    @NotBlank(message = "用户邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @ExceptionCode(value = 1003, message = "邮箱验证错误")
    private String email;
}
