package com.minesec.service.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("sdk注册返回的对象")
public class SdkRegisterResVo {
    @ApiModelProperty(value = "sdk注册的id，是一个uuid的字符串",required = true)
    private String id;
    private String cert;
}
