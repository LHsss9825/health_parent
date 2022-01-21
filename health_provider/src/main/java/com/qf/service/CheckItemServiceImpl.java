package com.qf.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.entity.PageResult;
import com.qf.entity.QueryPageBean;
import com.qf.entity.Result;
import com.qf.mapper.CheckItemMapper;
import com.qf.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemMapper checkItemMapper;

    @Override
    public void add(CheckItem checkItem) {
        checkItemMapper.add(checkItem);
    }

    @Override
    public PageResult findPage(QueryPageBean pageBean) {
        //1、设置分页条件
        PageHelper.startPage(pageBean.getCurrentPage(), pageBean.getPageSize());
        //2、正常查询
        List<CheckItem> checkItemList = checkItemMapper.findPage(pageBean);
        //3、获得分页查询的数据
        PageInfo<CheckItem> pageInfo = new PageInfo<>(checkItemList);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void delete(Integer id) {
        checkItemMapper.delete(id);
    }

    @Override
    public CheckItem findById(Integer id) {
        return checkItemMapper.findById(id);
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemMapper.edit(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemMapper.findAll();
    }
}
