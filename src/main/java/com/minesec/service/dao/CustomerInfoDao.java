package com.minesec.service.dao;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerInfoDao {
    private String customerId;
    private Date createTime;
    private Date updateTime;
}
