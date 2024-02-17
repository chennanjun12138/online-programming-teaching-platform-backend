package com.liu.practice.service;

import com.liu.practice.dao.EvaluateDao;
import com.liu.practice.entity.Evaluate;
import com.liu.practice.entity.Params;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class EvaluateService {
       @Resource
       private EvaluateDao evaluateDao;

       public List<Evaluate> findBySearch(Params params) {

           List<Evaluate>   evaluates = evaluateDao.findBySearch(params.getQuestion_submitid());
            return evaluates;
     }

        public void add(Evaluate book) {
            evaluateDao.insertSelective(book);
        }

        public void update(Evaluate book) {
            evaluateDao.updateByPrimaryKeySelective(book);
         }

         public void delete(Integer id) {
             evaluateDao.deleteByPrimaryKey(id);
        }
}
