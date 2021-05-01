package com.minesec.service.service.impl;

import com.minesec.service.dao.SdkInfoDao;
import com.minesec.service.mapper.SdkInfoMapper;
import com.minesec.service.service.SdkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SdkServiceImpl implements SdkService {
    @Autowired
    private SdkInfoMapper sdkInfoMapper;

    @Override
    public SdkInfoDao querySdkInfoById(String customerId, String sdkId) {
        return sdkInfoMapper.querySdkInfo(customerId,sdkId);
    }

    @Override
    public SdkInfoDao querySdkInfoById(String sdkId) {
        return sdkInfoMapper.querySdkInfoBySdkId(sdkId);
    }

    @Override
    public int registerSdkInfo(SdkInfoDao sdkInfoDao) {
        try {
            return sdkInfoMapper.addSdkInfo(sdkInfoDao);
        }catch (Exception e){
            e.printStackTrace();
            log.warn("registerSdkInfo exception:"+e.toString());
            log.warn("registerSdkInfo userinfo:"+ sdkInfoDao.toString());
        }
        return 0;
    }

    @Override
    public int updateSdkInfo(SdkInfoDao sdkInfoDao) {
        try {
            return sdkInfoMapper.updateSdkInfo(sdkInfoDao);
        }catch (Exception e){
            e.printStackTrace();
            log.warn("updateSdkInfo exception:"+e.toString());
            log.warn("updateSdkInfo userinfo:"+ sdkInfoDao.toString());
        }
        return 0;
    }
}
