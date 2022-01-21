package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.constant.MessageConstant;
import com.qf.constant.RedisMessageConstant;
import com.qf.entity.Result;
import com.qf.pojo.Setmeal;
import com.qf.service.SetmealService;
import com.qf.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.List;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 发送验证码
     * @param telephone
     * @return Result
     */
    @RequestMapping("/send4Order")
    public Result getAllSetmeal(String telephone){
        try {
            //1、生成验证码
            Integer code = ValidateCodeUtils.generateValidateCode(4);

            //2、发送短信
            System.out.println("给"+telephone+"手机号发送了短信："+code);

            //3、把验证码存redis、并设置失效时间
            jedisPool.getResource().set(telephone+ RedisMessageConstant.SENDTYPE_ORDER, code.toString());
            jedisPool.getResource().expire(telephone+ RedisMessageConstant.SENDTYPE_ORDER,30);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
