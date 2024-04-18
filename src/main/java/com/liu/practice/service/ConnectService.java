package com.liu.practice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.practice.common.JwtInterceptor;
import com.liu.practice.dao.ConnectDao;
import com.liu.practice.entity.Connect;
import com.liu.practice.entity.Params;
import com.liu.practice.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ConnectService {
    private static final Logger log  = LoggerFactory.getLogger(JwtInterceptor.class);

       @Resource
       private ConnectDao connectDao;
        public boolean findjudge(Params params) {
            // 开启分页查询
            // 接下来的查询会自动按照当前开启的分页设置来查询
            List<Connect> list = connectDao.findjudge(params);

            if(list.isEmpty())
            {
                return  true;
            }
            else
            {
                return false;
            }

     }
    public PageInfo<Connect> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<Connect> list = connectDao.findBySearch(params);
        return PageInfo.of(list);
    }
        public List<String> findteachers(Params params)
        {
            List<String >list=connectDao.findteachers(params);
            return list;
        }
    public List<String> findbyteacherid(Integer teacherid)
    {
        List<String >list=connectDao.findbyteacherid(teacherid);
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
