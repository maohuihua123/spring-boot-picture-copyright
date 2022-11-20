package com.mao.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.web3j.tuples.generated.Tuple4;

import java.math.BigInteger;
import java.text.SimpleDateFormat;

@Data
@ApiModel("用户")
@NoArgsConstructor
public class User {
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @ApiModelProperty("序号")
    private int id;

    @ApiModelProperty("用户以太坊地址")
    private String ethAddress;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @JsonIgnore
    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("用户以太坊余额")
    private String ethBlance;

    @ApiModelProperty("创建时间")
    private String createTime;

    public User(Tuple4<String, String, String, BigInteger> tuple) {
        this.ethAddress = tuple.component1();
        this.nickName = tuple.component2();
        this.email = tuple.component3();
        this.createTime = simpleDateFormat.format(tuple.component4().multiply(BigInteger.valueOf(1000)));
    }
}
