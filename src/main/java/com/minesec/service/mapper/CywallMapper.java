package com.minesec.service.mapper;

import com.minesec.service.dao.CywallInfoDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CywallMapper {

    int  insertCywallInfo(CywallInfoDao cywallInfoDao);

    @Update("update t_cywall_info set update_time=now() where cywall_id=#{vo.cywallId}")
    int updateCywallInfo(@Param("vo") CywallInfoDao cywallInfoDao);
}
