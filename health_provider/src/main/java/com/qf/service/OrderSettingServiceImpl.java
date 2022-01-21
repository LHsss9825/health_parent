package com.qf.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.mapper.OrderSettingMapper;
import com.qf.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingMapper orderSettingMapper;

    @Override
    public void addOrderSetting(List<OrderSetting> orderSettingList) {
        for (OrderSetting orderSetting : orderSettingList) {
            setOrderSetting(orderSetting);
        }
    }

    private void setOrderSetting(OrderSetting orderSetting) {
        long countByOrderDate = orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
        if (countByOrderDate > 0) {
            orderSettingMapper.editNumberByOrderDate(orderSetting);
        } else {
            orderSettingMapper.addOrderSetting(orderSetting);
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        String beginDate = date + "-1";
        String endDate = date + "-31";
        List<OrderSetting> orderSettingList = orderSettingMapper.getOrderSettingByMonth(beginDate, endDate);
        List<Map> result = new ArrayList<>();
        for (OrderSetting orderSetting : orderSettingList) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("date", orderSetting.getOrderDate().getDate());
            map.put("number", orderSetting.getNumber());
            map.put("reservations", orderSetting.getReservations());
            result.add(map);
        }
        return result;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        setOrderSetting(orderSetting);
    }
}
