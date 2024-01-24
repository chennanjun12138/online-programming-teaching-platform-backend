package com.liu.practice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.practice.common.JwtInterceptor;
import com.liu.practice.dao.HomeworkDao;
import com.liu.practice.entity.Homework;
import com.liu.practice.entity.Params;
import com.liu.practice.entity.Questionbank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class HomeworkService {
    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
       private HomeworkDao homeworkDao;
        public PageInfo<Homework> findBySearch(Params params) {
            // 开启分页查询
            PageHelper.startPage(params.getPageNum(), params.getPageSize());
            // 接下来的查询会自动按照当前开启的分页设置来查询
            List<Homework> list = homeworkDao.findBySearch(params);
            return PageInfo.of(list);
     }
    public PageInfo<Homework> findByteacher(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询

        //  List<String> list = JSON.parseArray(steelGrade, String.class);
        String values =params.getTeacher(); // 去除空格
        //分割字符串
        String[] valueArray = values.split(",");

        List<String> list = new ArrayList<>();
        for (String value : valueArray) {
            list.add(value.trim());

        }
        List<Homework> res=new ArrayList<>();
        for(String value:list)
        {
            List<Homework>  ans=homeworkDao.findByteacher(value.trim());

             for(Homework s1:ans)
             {
                 if(s1!=null)
                 {
                     res.add(s1);
                 }
             }

        }
         return PageInfo.of(res);
    }
        public void add(Homework book) {

             homeworkDao.insertSelective(book);
        }

        public void update(Homework book) {
            homeworkDao.updateByPrimaryKeySelective(book);
         }

         public void delete(Integer id) {
             homeworkDao.deleteByPrimaryKey(id);
        }
}
