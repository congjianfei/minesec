package com.minesec.service.service;

import com.minesec.service.dao.CustomerInfoDao;
import com.minesec.service.dao.CywallInfoDao;
import com.minesec.service.dao.UserDeviceInfoDao;

import java.util.List;

public interface SdkOtherInfoService {
    int insertCywallInfo(CywallInfoDao cywallInfoDao);
    int insertCustomerInfo(CustomerInfoDao customerInfoDao);
    int insertUserDeviceInfo(UserDeviceInfoDao userDeviceInfoDao);

    int updateCustomerInfo(CustomerInfoDao customerInfoDao);

    int updateCywallInfo(CywallInfoDao cywallInfoDao);

    List<CustomerInfoDao> findAllCustomerId();
}
