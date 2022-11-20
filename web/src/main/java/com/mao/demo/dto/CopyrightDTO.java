package com.mao.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CopyrightDTO {

    @NotBlank(message = "图片哈希不能为空")
    @Size(min = 32, max = 32, message = "图片MD5哈希地址长度不正确")
    private String pictureHash;

    @NotBlank(message = "水印信息不能为空")
    private String waterMark;

    @NotBlank(message = "水印图片存储地址不能为空")
    private String picturePath;
}
