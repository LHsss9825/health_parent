package com.qf.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.entity.PageResult;
import com.qf.entity.QueryPageBean;
import com.qf.mapper.CheckGroupMapper;
import com.qf.pojo.CheckGroup;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupMapper checkGroupMapper;

    @Override
    public void add(Integer[] checkitemIds, CheckGroup checkGroup) {
        //1、保存检查组
        checkGroupMapper.add(checkGroup);

        //2、保存关联表
        addCheckGroupCheckItem(checkitemIds, checkGroup);
    }

    private void addCheckGroupCheckItem(Integer[] checkitemIds, CheckGroup checkGroup) {
        Integer checkGroupId = checkGroup.getId();
        for (Integer checkitemId : checkitemIds) {
            checkGroupMapper.addCheckGroupCheckItem(checkGroupId,checkitemId);
        }
    }

    @Override
    public PageResult findPage(QueryPageBean pageBean) {
        PageHelper.startPage(pageBean.getCurrentPage(), pageBean.getPageSize());
        List<CheckGroup> checkGroupList = checkGroupMapper.findPage(pageBean);
        PageInfo<CheckGroup> pageInfo = new PageInfo<>(checkGroupList);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupMapper.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupMapper.findCheckItemIdsByCheckGroupId(id);
    }

    @Override
    public void edit(Integer[] checkitemIds, CheckGroup checkGroup) {
        //1、修改检查组
        checkGroupMapper.edit(checkGroup);

        //2、修改关联表：
        //删除当前检查组的所有记录
        checkGroupMapper.deleteCheckGroupCheckItemByCheckGroupId(checkGroup.getId());
        //从新插入
        addCheckGroupCheckItem(checkitemIds, checkGroup);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupMapper.findAll();
    }
}
