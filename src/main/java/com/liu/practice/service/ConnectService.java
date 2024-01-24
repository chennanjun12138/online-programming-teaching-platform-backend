package com.liu.practice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.practice.dao.ConnectDao;
import com.liu.practice.entity.Connect;
import com.liu.practice.entity.Params;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ConnectService {
       @Resource
       private ConnectDao connectDao;
        public boolean findBySearch(Params params) {
            // 开启分页查询

            // 接下来的查询会自动按照当前开启的分页设置来查询
            List<Connect> list = connectDao.findBySearch(params);
            if(list.isEmpty())
            {
                return  true;
            }
            else
            {
                return false;
            }

     }
        public List<String> findteachers(Params params)
        {
            List<String >list=connectDao.findteachers(params);
            return list;
        }

        public void add(Connect book) {
            connectDao.insertSelective(book);
        }

        public void update(Connect book) {
            connectDao.updateByPrimaryKeySelective(book);
         }

         public void delete(Integer id) {
             connectDao.deleteByPrimaryKey(id);
        }
}
