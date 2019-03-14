package com.bupt.web.controller.bill;

import com.alibaba.fastjson.JSONObject;
import com.bupt.service.service.bill.bean.BillRequest;
import com.bupt.service.service.bill.BillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wechat")
public class BillController {
    private static Logger logger = LoggerFactory.getLogger(BillController.class);
    @Autowired
    BillService billService;
    @ResponseBody
    @RequestMapping("/bill/list")
    public JSONObject listBills(@RequestParam(value="userId") String userId, @RequestParam(value="year")int year, @RequestParam(value="month")int month){
        logger.info("ListBills userId:{},year:{},month:{}",userId,year,month);
        JSONObject result = billService.getBills(userId,year,month);
        logger.info("ListBills result:{}",result);
        return result;

    }
    @ResponseBody
    @RequestMapping(value = "/bill/create",method = RequestMethod.POST)
    public JSONObject createBill(@RequestBody BillRequest createBillRequest){
        logger.info("CreateBill request:{}",createBillRequest.toString());
        JSONObject result = new JSONObject();
        boolean insert = billService.createBill(createBillRequest);
        if(insert){
            result.put("status", 0);
            result.put("msg", "success");
        } else {
            result.put("status", 1);
            result.put("msg", "fail");
        }
        logger.info("createBill result:{}",result);
        return result;

    }
    @ResponseBody
    @RequestMapping(value = "/bill/delete",method = RequestMethod.GET)
    public JSONObject DeleteBill(@RequestParam("id") Long id){
        logger.info("DeleteBill request bill id:{}",id);
        JSONObject result = new JSONObject();
        boolean delete = billService.deleteBill(id);
        if(delete){
            result.put("msg", "success");
            result.put("status", 0);
        } else {
            result.put("status", 1);
            result.put("msg", "fail");
        }
        logger.info("DeleteBill result:{}",result);
        return result;

    }
    @ResponseBody
    @RequestMapping(value = "/bill/update",method = RequestMethod.POST)
    public JSONObject updateBill(@RequestBody BillRequest updateBillRequest){
        logger.info("updateBill request:{}",updateBillRequest.toString());
        JSONObject result = new JSONObject();
        boolean update = billService.updateBill(updateBillRequest);
        if(update){
            result.put("status", 0);
            result.put("msg", "success");
        } else {
            result.put("msg", "fail");
            result.put("status", 1);
        }
        logger.info("updateBill result:{}",result);
        return result;
    }
    @ResponseBody
    @RequestMapping("/bill/statistics")
    public JSONObject statisticsBills(@RequestParam(value="userId") String userId, @RequestParam(value="start_time")String start, @RequestParam(value="end_time")String  end){
        logger.info("statistics userId:{},start:{},end:{}",userId,start,end);
        JSONObject result = billService.statisticsBills(userId,start,end);
        logger.info("statistics result:{}",result);
        return result;

    }
    @ResponseBody
    @RequestMapping("/bill/type/list")
    public JSONObject listTypeBills(@RequestParam(value="userId") String userId, @RequestParam(value="type") String type,
                                    @RequestParam(value="start_time")String start, @RequestParam(value="end_time")String  end){
        logger.info("listTypeBills userId:{},type:{},start_time:{},end_time:{}",userId,type,start,end);
        JSONObject result = billService.getTypeBills(userId,type,start,end);
        logger.info("listTypeBills result:{}",result);
        return result;

    }
}
