package com.liu.practice.dao;

import com.liu.practice.entity.Class;
import com.liu.practice.entity.Message;
import com.liu.practice.entity.Params;
import com.liu.practice.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface MessageDao extends Mapper<Message> {
    List<Message> findBySearch(@Param("params") Params params);
    @Select("select * from message ORDER BY `sendtime` DESC")//1基于注解，第二种在Mapper里写
    List<Message> getMessage();

}
