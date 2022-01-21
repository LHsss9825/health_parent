package com.qf.mapper;

import com.qf.entity.QueryPageBean;
import com.qf.pojo.Member;
import com.qf.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberMapper {
    Member findMemberByPhoneNumber(String telephone);

    void addMember(Member member);
}
