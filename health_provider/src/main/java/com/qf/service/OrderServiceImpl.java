package com.qf.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.constant.MessageConstant;
import com.qf.constant.RedisMessageConstant;
import com.qf.entity.Result;
import com.qf.mapper.MemberMapper;
import com.qf.mapper.OrderMapper;
import com.qf.mapper.OrderSettingMapper;
import com.qf.pojo.Member;
import com.qf.pojo.Order;
import com.qf.pojo.OrderSetting;
import com.qf.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.Map;
@Service
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private OrderSettingMapper orderSettingMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Result submit(Map map) throws Exception {
        //1、检查验证码是否一致
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        String validateCodeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        if(validateCodeInRedis==null || !validateCode.equals(validateCodeInRedis)){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

        //2、检查预约日期是否进行了预约设置
        Date orderDate = DateUtils.parseString2Date((String) map.get("orderDate"));
        OrderSetting orderSetting = orderSettingMapper.findOrderSettingByOrderDate(orderDate);
        if(orderSetting == null){
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

        //3、检查预约日期是否预已约满了
        int number = orderSetting.getNumber();
        int reservations = orderSetting.getReservations();
        if(number == reservations){
            return new Result(false, MessageConstant.ORDER_FULL);
        }

        //4、检查当前用户是否是会员，若不是则注册
        Member member = memberMapper.findMemberByPhoneNumber(telephone);
        if(member == null){
            member = new Member();
            member.setName((String) map.get("name"));
            member.setSex((String) map.get("sex"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setRegTime(new Date());
            memberMapper.addMember(member);
        }

        //5、检查该用户在同一天是否预约过同一个套餐
        Integer setmealId = Integer.parseInt((String)map.get("setmealId"));
        Order order = new Order(member.getId(), orderDate, null, null, setmealId);
        order = orderMapper.findOrder(order);
        if(order != null){
            return new Result(false, MessageConstant.HAS_ORDERED);
        }else{
            //6、保存预约信息
            order = new Order(member.getId(), orderDate, Order.ORDERTYPE_WEIXIN,
                    Order.ORDERSTATUS_NO, setmealId);
            orderMapper.addOrder(order);
            orderSetting.setReservations(orderSetting.getReservations()+1);
            orderSettingMapper.editReservationsByOrderDate(orderSetting);
        }
        return new Result(true, MessageConstant.ORDER_SUCCESS, order.getId());
    }

    public static void main(String[] args) throws Exception {
        Date orderDate = DateUtils.parseString2Date("2022-1-21");
        System.out.println(orderDate);
    }
}
