package com.liu.practice.dao;

import com.liu.practice.entity.Params;
import com.liu.practice.entity.Questionsubmit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface QuestionsubmitDao extends Mapper<Questionsubmit> {
    List<Questionsubmit> findBySearch(@Param("params") Params params);
    Questionsubmit getbyid(Long id);
    Long findbyothers(Long userId,Long questionId,String language,String code);
    List<Questionsubmit> findByteachid(@Param("params") Params params);
}
