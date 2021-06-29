package com.mao.demo.entity;

import com.mao.demo.common.annotation.ExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.web3j.tuples.generated.Tuple4;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.text.SimpleDateFormat;

@Data
@ApiModel("用户")
@NoArgsConstructor
public class User {

    @ApiModelProperty("序号")
    private int id;

    @ApiModelProperty("用户以太坊地址")
    private String ethAddress;

    @ApiModelProperty("用户昵称")
    @NotNull(message = "用户昵称不能为空")
    @Size(min = 2, max = 20, message = "用户昵称必须是2-20个字符")
    @ExceptionCode(value = 100001, message = "用户昵称验证错误")
    private String nickName;

    @ApiModelProperty("用户密码")
    @NotNull(message = "用户密码不能为空")
    @Size(min = 6, max = 16, message = "密码长度必须是6-16个字符")
    @ExceptionCode(value = 100002, message = "密码验证错误")
    private String password;

    @ApiModelProperty("用户邮箱")
    @NotNull(message = "用户邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @ExceptionCode(value = 100003, message = "邮箱验证错误")
    private String email;

    @ApiModelProperty("用户以太坊余额")
    private String ethBlance;

    @ApiModelProperty("创建时间")
    private String createTime;

    public User(Tuple4<String, String, String, BigInteger> tuple) {
        this.ethAddress = tuple.component1();
        this.nickName = tuple.component2();
        this.email = tuple.component3();
        this.createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(tuple.component4().multiply(BigInteger.valueOf(1000)));
    }
}
