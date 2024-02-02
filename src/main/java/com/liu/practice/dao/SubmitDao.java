package com.liu.practice.dao;

import com.liu.practice.entity.Params;
import com.liu.practice.entity.Submit;
import com.liu.practice.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SubmitDao extends Mapper<Submit> {
     List<Submit> findBySearch(Integer homeworkid);
     List<Submit> findbystudent(@Param("params") Params params);
     void deletebyhomeworkid(Integer homeworkid);
}
