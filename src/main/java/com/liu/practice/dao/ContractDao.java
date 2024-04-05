package com.liu.practice.dao;

import com.liu.practice.entity.Contract;
import com.liu.practice.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
@org.apache.ibatis.annotations.Mapper
public interface ContractDao extends Mapper<Contract> {


    List<Contract> findBySearch(Integer classid);
    Contract judgefind(Integer classid,Integer questionid);
    void deletebyclassid(Integer classid);
    void deleteBy(Integer classid,Integer questionid);
}
