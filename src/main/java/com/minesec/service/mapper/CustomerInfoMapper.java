package com.minesec.service.mapper;

import com.minesec.service.dao.CustomerInfoDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CustomerInfoMapper {
    int insertCustomerInfo(CustomerInfoDao customerInfoDao);

    @Update("update t_customer_info set update_time=now() where customer_id=#{vo.customerId}")
    int updateCustomerInfo(@Param("vo") CustomerInfoDao customerInfoDao);

    @Select("select * from t_customer_info")
    List<CustomerInfoDao> queryAllCustomer();
}
