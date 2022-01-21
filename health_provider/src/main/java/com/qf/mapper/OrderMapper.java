package com.qf.mapper;

import com.qf.pojo.Order;

public interface OrderMapper {

    Order findOrder(Order order);

    void addOrder(Order order);
}
