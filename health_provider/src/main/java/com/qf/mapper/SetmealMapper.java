package com.qf.mapper;

import com.qf.entity.QueryPageBean;
import com.qf.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetmealMapper {
    void add(Setmeal setmeal);

    void addSetmeatCheckGroup(@Param("checkgroupId") Integer checkgroupId, @Param("id") Integer id);

    List<Setmeal> findPage(QueryPageBean pageBean);

    List<Setmeal> getAllSetmeal();

    Setmeal findById(Integer id);
}
