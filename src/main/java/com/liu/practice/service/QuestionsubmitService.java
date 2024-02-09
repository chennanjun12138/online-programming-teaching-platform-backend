package com.liu.practice.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.practice.dao.QuestionsubmitDao;
import com.liu.practice.entity.Params;
import com.liu.practice.entity.Questionbank;
import com.liu.practice.entity.Questionsubmit;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class QuestionsubmitService {
       @Resource
       private QuestionsubmitDao questionsubmitDao;
    public PageInfo<Questionsubmit> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<Questionsubmit> list = questionsubmitDao.findBySearch(params);
        return PageInfo.of(list);
    }
        public Questionsubmit  getbyid(Long questionsubmitid)
        {
            Questionsubmit questionsubmit=questionsubmitDao.getbyid(questionsubmitid);
            return questionsubmit;
        }
        public void add(Questionsubmit book) {
            questionsubmitDao.insertSelective(book);
        }

        public boolean update(Questionsubmit book) {
          Integer JUDGE=  questionsubmitDao.updateByPrimaryKeySelective(book);
          if(JUDGE.equals(1))
          {
              return true;
          }
          else
          {
              return  false;
          }
         }

         public void delete(Integer id) {
             questionsubmitDao.deleteByPrimaryKey(id);
        }


}
