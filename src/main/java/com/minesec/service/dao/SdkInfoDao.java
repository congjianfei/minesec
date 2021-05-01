package com.minesec.service.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.xml.internal.ws.developer.Serialization;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Serialization
public class SdkInfoDao {
    private String sdkId;
    private long expired;
    private String version;
    private String pkN;
    private String pkE;
    private String userId;
    private String customerId;
    private Date timeStamp;
    private String cywallId;
    private String state;
    @JsonIgnore
    private boolean sdkState=true;
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;
}
