package com.qf.service;

import com.qf.entity.PageResult;
import com.qf.entity.QueryPageBean;
import com.qf.pojo.Setmeal;

import java.util.List;

public interface SetmealService {
    void add(Integer[] checkgroupIds, Setmeal setmeal);

    PageResult findPage(QueryPageBean pageBean);

    List<Setmeal> getAllSetmeal();

    Setmeal findById(Integer id);
}
