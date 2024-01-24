package com.liu.practice.dao;

import com.liu.practice.entity.Connect;
import com.liu.practice.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
@org.apache.ibatis.annotations.Mapper
public interface ConnectDao extends Mapper<Connect> {


    List<Connect> findBySearch(@Param("params") Params params);
    List<String> findteachers(@Param("params") Params params);
}
