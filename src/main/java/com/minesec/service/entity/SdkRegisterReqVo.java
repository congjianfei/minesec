package com.minesec.service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
@ApiOperation(value = "sdk注册请求对象")
public class SdkRegisterReqVo {
    @ApiModelProperty(value = "sdk的id")
//    @JsonProperty("Id")
    private String id;
    @ApiModelProperty(value = "客户id")
    private String customerid;
    @ApiModelProperty(value = "友商id")
    private String cywallid;//友商
    @ApiModelProperty(value = "密钥对象")
    private PkVo pk;
//    @JsonProperty("CSR")
    private String csr;
//    @JsonProperty("Time")
    private Date timestamp;

    @Data
    @NoArgsConstructor
    @ApiOperation(value = "sdk注册请求对象中的密钥对象")
    public static class PkVo{
        @JsonProperty("N")
        private String N;
        @JsonProperty("E")
        private String E;
    }
}
