package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.constant.MessageConstant;
import com.qf.constant.RedisConstant;
import com.qf.entity.PageResult;
import com.qf.entity.QueryPageBean;
import com.qf.entity.Result;
import com.qf.pojo.CheckGroup;
import com.qf.pojo.OrderSetting;
import com.qf.pojo.Setmeal;
import com.qf.service.OrderSettingService;
import com.qf.service.SetmealService;
import com.qf.utils.POIUtils;
import com.qf.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.*;

@Controller
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    @ResponseBody
    public Result upload(MultipartFile excelFile){
        try {
            List<String[]> stringList = POIUtils.readExcel(excelFile);
            List<OrderSetting> orderSettingList = new ArrayList<>();
            for (String[] cells : stringList) {
                String orderDate = cells[0];
                String number = cells[1];
                OrderSetting orderSetting = new OrderSetting(new Date(orderDate), Integer.parseInt(number));
                orderSettingList.add(orderSetting);
            }
            orderSettingService.addOrderSetting(orderSettingList);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/getOrderSettingByMonth")
    @ResponseBody
    public Result getOrderSettingByMonth(String date){
        try {
            List<Map> mapList = orderSettingService.getOrderSettingByMonth(date);
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS,mapList);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(true, MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/editNumberByDate")
    @ResponseBody
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try {
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(true, MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
