package com.minesec.service.service.impl;

import com.minesec.service.dao.CustomerInfoDao;
import com.minesec.service.dao.CywallInfoDao;
import com.minesec.service.dao.UserDeviceInfoDao;
import com.minesec.service.mapper.CustomerInfoMapper;
import com.minesec.service.mapper.CywallMapper;
import com.minesec.service.mapper.UserInfoMapper;
import com.minesec.service.service.SdkOtherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SdkOtherInfoImpl implements SdkOtherInfoService {
    @Autowired
    private CywallMapper cywallMapper;
    @Autowired
    private CustomerInfoMapper customerInfoMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;


    @Override
    public int insertCywallInfo(CywallInfoDao cywallInfoDao) {
        try {
            return cywallMapper.insertCywallInfo(cywallInfoDao);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int insertCustomerInfo(CustomerInfoDao customerInfoDao) {
        try {
            return customerInfoMapper.insertCustomerInfo(customerInfoDao);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int insertUserDeviceInfo(UserDeviceInfoDao userDeviceInfoDao) {
        try {
            return userInfoMapper.insertUserDeviceInfo(userDeviceInfoDao);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updateCustomerInfo(CustomerInfoDao customerInfoDao) {
        try {
            return customerInfoMapper.updateCustomerInfo(customerInfoDao);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updateCywallInfo(CywallInfoDao cywallInfoDao) {
        try {
            return cywallMapper.updateCywallInfo(cywallInfoDao);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<CustomerInfoDao> findAllCustomerId() {
        return customerInfoMapper.queryAllCustomer();
    }
}
