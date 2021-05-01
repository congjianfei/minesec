package com.minesec.service.mapper;

import com.minesec.service.dao.UserDeviceInfoDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {
    int insertUserDeviceInfo(UserDeviceInfoDao userDeviceInfoDao);
}
