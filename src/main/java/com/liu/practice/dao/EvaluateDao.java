package com.liu.practice.dao;

import com.liu.practice.entity.Contract;
import com.liu.practice.entity.Evaluate;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface EvaluateDao extends Mapper<Evaluate> {
     List<Evaluate> findBySearch(Integer question_submitid);
     List<Evaluate> findByTeacher(Integer teacherid);
     void deletebyteacherid(Integer teacherid);

}
