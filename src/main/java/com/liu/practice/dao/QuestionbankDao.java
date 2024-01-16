package com.liu.practice.dao;
import com.liu.practice.entity.Params;
import com.liu.practice.entity.Questionbank;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface QuestionbankDao extends Mapper<Questionbank> {
    List<Questionbank> findBySearch(@Param("params") Params params);


}
