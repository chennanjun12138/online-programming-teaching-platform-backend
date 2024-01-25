package com.liu.practice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.practice.dao.ClassDao;
import com.liu.practice.dao.CourseDao;
import com.liu.practice.entity.Class;
import com.liu.practice.entity.Course;
import com.liu.practice.entity.Params;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ClassService {
       @Resource
       private ClassDao classDao;
        @Resource
       private CourseDao courseDao;
        public PageInfo<Class> findBySearch(Params params) {
            // 开启分页查询
            PageHelper.startPage(params.getPageNum(), params.getPageSize());
            // 接下来的查询会自动按照当前开启的分页设置来查询
            List<Class> list = classDao.findBySearch(params);
            return PageInfo.of(list);
         }
        public Course findcourse(Params params)
        {

            Course course=courseDao.findBySearch(params.getClassid());
            return course;
        }
        public void addcourse(Course course)
        {
            courseDao.insertSelective(course);
        }
        public void updatecourse(Course course)
        {
            courseDao.updateByPrimaryKeySelective(course);
        }
         public void add(Class book) {
                classDao.insertSelective(book);
        }

        public void update(Class book) {
            classDao.updateByPrimaryKeySelective(book);
         }

         public void delete(Integer id) {
             classDao.deleteByPrimaryKey(id);
             courseDao.deleteByPrimaryKey(id);
        }
}
