package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.constant.MessageConstant;
import com.qf.constant.RedisConstant;
import com.qf.entity.PageResult;
import com.qf.entity.QueryPageBean;
import com.qf.entity.Result;
import com.qf.pojo.CheckGroup;
import com.qf.pojo.Setmeal;
import com.qf.service.CheckGroupService;
import com.qf.service.SetmealService;
import com.qf.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/add")
    @ResponseBody
    public Result add(Integer[] checkgroupIds, @RequestBody Setmeal setmeal){
        try {
            setmealService.add(checkgroupIds,setmeal);
            //把图片名称添加到redis
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(true, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    @RequestMapping("/upload")
    @ResponseBody
    public Result upload(MultipartFile imgFile){
        try {
            //1、图片重命名
            String filename = imgFile.getOriginalFilename();//1.jpg
            String extName = filename.substring(filename.lastIndexOf("."));//.jpg或.png
            filename = UUID.randomUUID() + extName;//abc123asdf876678798y8asdf.jpg
            //2、上传图片
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), filename);
            //把图片名称添加到redis
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,filename);
            //3、返回Reslut(图片的名字)
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, filename);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @RequestMapping("/findPage")
    @ResponseBody
    public PageResult findPage(@RequestBody QueryPageBean pageBean){
        return setmealService.findPage(pageBean);
    }
}
