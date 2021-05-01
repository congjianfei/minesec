package com.minesec.service.mapper;

import com.minesec.service.dao.SdkInfoDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SdkInfoMapper {
    @Select("select * from t_sdk_info where sdk_id=#{sdkId} and customer_id=#{customerId}")
    SdkInfoDao querySdkInfo(@Param("customerId") String customerId,
                            @Param("sdkId") String sdkId);

    @Select("select * from t_sdk_info where sdk_id=#{sdkId}")
    SdkInfoDao querySdkInfoBySdkId(@Param("sdkId") String sdkId);

    int addSdkInfo(SdkInfoDao sdkInfoDao);

    int updateSdkInfo(SdkInfoDao sdkInfoDao);
}
