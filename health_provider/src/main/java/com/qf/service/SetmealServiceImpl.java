package com.qf.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.entity.PageResult;
import com.qf.entity.QueryPageBean;
import com.qf.mapper.SetmealMapper;
import com.qf.pojo.CheckGroup;
import com.qf.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void add(Integer[] checkgroupIds, Setmeal setmeal) {
        //1、保存套餐信息，并返回自增id
        setmealMapper.add(setmeal);

        //2、保存套餐检查组关联表
        for (Integer checkgroupId : checkgroupIds) {
            setmealMapper.addSetmeatCheckGroup(checkgroupId,setmeal.getId());
        }
    }

    @Override
    public PageResult findPage(QueryPageBean pageBean) {
        PageHelper.startPage(pageBean.getCurrentPage(), pageBean.getPageSize());
        List<Setmeal> setmealList = setmealMapper.findPage(pageBean);
        PageInfo<Setmeal> pageInfo = new PageInfo<>(setmealList);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public List<Setmeal> getAllSetmeal() {
        return setmealMapper.getAllSetmeal();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealMapper.findById(id);
    }
}
