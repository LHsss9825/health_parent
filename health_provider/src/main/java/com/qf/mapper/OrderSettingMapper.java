package com.qf.mapper;

import com.qf.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderSettingMapper {
    void addOrderSetting(OrderSetting orderSetting);

    void editNumberByOrderDate(OrderSetting orderSetting);

    long findCountByOrderDate(Date orderDate);

    List<OrderSetting> getOrderSettingByMonth(@Param("beginDate") String beginDate, @Param("endDate") String endDate);

    OrderSetting findOrderSettingByOrderDate(Date orderDate);

    void editReservationsByOrderDate(OrderSetting orderSetting);
}
