package com.bupt.service.service.bill;

import com.alibaba.fastjson.JSONObject;
import com.bupt.common.DateUtil;
import com.bupt.service.bean.bill.Bill;
import com.bupt.service.dao.bill.BillMapper;
import com.bupt.service.service.bill.bean.BillDto;
import com.bupt.service.service.bill.bean.BillRequest;
import com.bupt.service.service.bill.bean.BillStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("billService")
public class BillService {
    private static Logger logger = LoggerFactory.getLogger(BillService.class);
    @Autowired
    BillMapper billMapper;
    public JSONObject getBills(String userId, int year, int month){
        JSONObject result = new JSONObject();
        float consumeCount = 0;
        float incomeCount = 0;
        List<BillDto> list = new ArrayList<>();
        List<Bill> bills = billMapper.getBills(userId,year,month);
        for(Bill bill : bills) {
            String date = DateUtil.convertToDayWithDashString(bill.getGmtModified());
            BillDto billDto = new BillDto(bill.getId(),date,bill.getConsumeType(),bill.getConsumeInfo(),bill.getMoney(),(int) bill.getStatus());
            list.add(billDto);
            if(bill.getStatus() == 0) {
                consumeCount+=bill.getMoney().floatValue();
            } else {
                incomeCount+=bill.getMoney().floatValue();
            }
        }
        result.put("consumeCount",consumeCount);
        result.put("incomeCount",incomeCount);
        result.put("list",list);
        return result;
    }

    public boolean createBill(BillRequest createBillRequest) {
        Bill bill = null;
        try {
            Date date = DateUtil.convertFromDayWithDash(createBillRequest.getDate());
            Calendar calendar = Calendar.getInstance();//日历对象
            calendar.setTime(date);//设置当前日期
            int year = calendar.get(Calendar.YEAR);//获取年份
            int month = calendar.get(Calendar.MONTH) + 1;//获取月份
            bill = new Bill(createBillRequest.getUserId(),createBillRequest.getType(),createBillRequest.getMoney(),createBillRequest.getDesc(),
                    date,date,year,month,createBillRequest.getStatus().byteValue());
            int result = billMapper.insertSelective(bill);
            if(result != 1) {
                return false;
            }
        } catch (ParseException e) {
            logger.warn("createBill exception:{}",e.toString());
            return false;
        }
        logger.warn("createBill success.{}",bill.toString());
        return true;
    }
    public boolean deleteBill(Long id){
        int result = billMapper.deleteByPrimaryKey(id);
        if(result != 1) {
            return false;
        }
        return true;
    }
    public boolean updateBill(BillRequest updateBillRequest){
        Bill bill = null;
        try {
            Date date = DateUtil.convertFromDayWithDash(updateBillRequest.getDate());
            Calendar calendar = Calendar.getInstance();//日历对象
            calendar.setTime(date);//设置当前日期
            int year = calendar.get(Calendar.YEAR);//获取年份
            int month = calendar.get(Calendar.MONTH) + 1;//获取月份
            bill = new Bill(updateBillRequest.getUserId(),updateBillRequest.getType(),updateBillRequest.getMoney(),updateBillRequest.getDesc(),
                    date,date,year,month,updateBillRequest.getStatus().byteValue());
            bill.setId(updateBillRequest.getId());
            bill.setGmtModified(null);
            int result = billMapper.updateByPrimaryKeySelective(bill);
            if(result != 1) {
                return false;
            }
        } catch (ParseException e) {
            logger.warn("updateBill exception:{}",e.toString());
            return false;
        }
        logger.warn("updateBill success.{}",bill.toString());

        return true;
    }
    public JSONObject statisticsBills(String userId, String start, String end ){
        JSONObject result = new JSONObject();
        float consumeCount = 0;
        float incomeCount = 0;
        List<BillStatistics> consumeList = new ArrayList<>();
        List<BillStatistics> incomeList = new ArrayList<>();
        try {
            Date startTime = DateUtil.convertFromDayWithDash(start);
            Date endTime = DateUtil.convertFromDayWithDash(end);
            List<BillStatistics> billStatisticsList = billMapper.statisticsBills(userId, startTime,endTime);
            for(BillStatistics billStatistics : billStatisticsList) {
                if(billStatistics.getStatus() == 0) {
                    consumeCount+=billStatistics.getSum().floatValue();
                    consumeList.add(billStatistics);
                } else {
                    incomeCount+=billStatistics.getSum().floatValue();
                    incomeList.add(billStatistics);
                }
            }
        } catch (ParseException e) {
            logger.warn("createBill exception:{}",e.toString());
        }
        result.put("consumeCount", consumeCount);
        result.put("incomeCount", incomeCount);
        result.put("consumeList", consumeList);
        result.put("incomeList", incomeList);
        return result;
    }

    public JSONObject getTypeBills(String userId, String type,  String start, String end){
        JSONObject result = new JSONObject();
        List<BillDto> list = new ArrayList<>();
        try {
            Date startTime = DateUtil.convertFromDayWithDash(start);
            Date endTime = DateUtil.convertFromDayWithDash(end);
            List<Bill> bills = billMapper.getTypeBills(userId,type,startTime,endTime);
            for(Bill bill : bills) {
                String date = DateUtil.convertToDayWithDashString(bill.getGmtModified());
                BillDto billDto = new BillDto(bill.getId(),date,bill.getConsumeType(),bill.getConsumeInfo(),bill.getMoney(),(int) bill.getStatus());
                list.add(billDto);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        result.put("list",list);
        return result;
    }
}
