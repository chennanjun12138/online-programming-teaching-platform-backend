package com.liu.practice.dao;

import com.liu.practice.entity.Homework;
import com.liu.practice.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface HomeworkDao extends Mapper<Homework> {
    List<Homework> findBySearch(@Param("params") Params params);
    void update(Integer id,String content);

}
