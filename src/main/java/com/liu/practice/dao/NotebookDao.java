package com.liu.practice.dao;
import com.liu.practice.entity.Notebook;
import com.liu.practice.entity.Params;
import com.liu.practice.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface NotebookDao extends Mapper<Notebook> {
    Notebook findBySearch(@Param("params") Params params);

}
