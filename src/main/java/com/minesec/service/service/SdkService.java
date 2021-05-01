package com.minesec.service.service;

import com.minesec.service.dao.SdkInfoDao;

public interface SdkService {
    SdkInfoDao querySdkInfoById(String customerId, String sdkId);
    SdkInfoDao querySdkInfoById(String sdkId);
    int registerSdkInfo(SdkInfoDao sdkInfoDao);
    int updateSdkInfo(SdkInfoDao sdkInfoDao);
}
