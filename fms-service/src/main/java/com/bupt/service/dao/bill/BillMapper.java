package com.bupt.service.dao.bill;

import com.bupt.service.bean.bill.Bill;
import com.bupt.service.service.bill.bean.BillStatistics;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface BillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Bill record);

    int insertSelective(Bill record);

    Bill selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Bill record);

    int updateByPrimaryKey(Bill record);

    List<Bill> getBills(@Param("userId")String userId, @Param("year")int year, @Param("month")int month);

    List<BillStatistics> statisticsBills(@Param("userId")String userId, @Param("start_time") Date startTime, @Param("end_time")Date endTime);

    List<Bill> getTypeBills(@Param("userId")String userId,@Param("type")String type,
                            @Param("start_time") Date startTime, @Param("end_time")Date endTime);
}