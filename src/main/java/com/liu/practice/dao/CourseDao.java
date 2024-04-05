package com.liu.practice.dao;

import com.liu.practice.entity.Course;
import com.liu.practice.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CourseDao extends Mapper<Course> {
     Course findBySearch(Integer classid);

}
