package com.qf.quartz;

import com.qf.constant.RedisConstant;
import com.qf.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob {
    private JedisPool jedisPool;

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void clearImg(){
        Set<String> imgNameSet = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if(imgNameSet != null) {
            for (String imgName : imgNameSet) {
                //1、删除七牛云的垃圾图片
                QiniuUtils.deleteFileFromQiniu(imgName);
                //2、删除redis的垃圾图片：若不删，下次定时任务会重复删除垃圾图片
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, imgName);
                System.out.println("自定义任务执行，清理垃圾图片:" + imgName);
            }
        }
    }
}
