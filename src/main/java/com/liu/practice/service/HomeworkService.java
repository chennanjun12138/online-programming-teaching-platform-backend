package com.liu.practice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.practice.dao.HomeworkDao;
import com.liu.practice.entity.Homework;
import com.liu.practice.entity.Params;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class HomeworkService {
       @Resource
       private HomeworkDao homeworkDao;
        public PageInfo<Homework> findBySearch(Params params) {
            // 开启分页查询
            PageHelper.startPage(params.getPageNum(), params.getPageSize());
            // 接下来的查询会自动按照当前开启的分页设置来查询
            List<Homework> list = homeworkDao.findBySearch(params);
            return PageInfo.of(list);
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
