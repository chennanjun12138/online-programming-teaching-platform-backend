package com.liu.practice.dao;

import com.liu.practice.entity.User;
import com.liu.practice.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
@org.apache.ibatis.annotations.Mapper
public interface UserDao extends Mapper<User> {

    // @Select("select * from user")//1基于注解，第二种在Mapper里写
    List<User> getUser();
    List<User> findBySearch(@Param("params") Params params);

    @Select("select * from user where name = #{name} limit 1")
    User findByName(@Param("name") String name);

    @Select("select * from user where name = #{name} and password = #{password} limit 1")
    User findByNameAndPassword(@Param("name") String name, @Param("password") String password);
}
