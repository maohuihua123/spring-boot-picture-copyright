package com.mao.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tuples.generated.Tuple6;

import java.math.BigInteger;
import java.text.SimpleDateFormat;

@Data
@ApiModel("版权")
@NoArgsConstructor
public class Copyright {

    @ApiModelProperty("序号")
    private int id;

    @ApiModelProperty("版权ID")
    private int pictureID;

    @ApiModelProperty("图片哈希")
    private String pictureHash;

    @ApiModelProperty("水印信息")
    private String waterMark;

    @ApiModelProperty("图片地址（IPFS）")
    private String picturePath;

    @ApiModelProperty("所有者地址")
    private String ethAddress;

    @ApiModelProperty("创建时间")
    private String createTime;

    public Copyright(Tuple5<BigInteger, String, String, String, BigInteger> tuple) {
        this.pictureID = tuple.component1().intValue();
        this.pictureHash = tuple.component2();
        this.waterMark = tuple.component3();
        this.picturePath = tuple.component4();
        this.createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(tuple.component5().multiply(BigInteger.valueOf(1000)));
    }

    public Copyright(Tuple6<BigInteger, String, String, String, String, BigInteger> tuple){
        this.pictureID = tuple.component1().intValue();
        this.pictureHash = tuple.component2();
        this.waterMark = tuple.component3();
        this.picturePath = tuple.component4();
        this.ethAddress = tuple.component5();
        this.createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(tuple.component6().multiply(BigInteger.valueOf(1000)));
    }
}
