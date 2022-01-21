package com.qf.service;

import com.qf.entity.PageResult;
import com.qf.entity.QueryPageBean;
import com.qf.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    void add(Integer[] checkitemIds, CheckGroup checkGroup);

    PageResult findPage(QueryPageBean pageBean);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(Integer[] checkitemIds, CheckGroup checkGroup);

    List<CheckGroup> findAll();

}
