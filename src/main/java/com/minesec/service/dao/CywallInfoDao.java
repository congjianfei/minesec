package com.minesec.service.dao;

import lombok.Data;

import java.util.Date;

@Data
public class CywallInfoDao {
    private String cywallId;
    private Date createTime;
    private Date updateTime;
}
