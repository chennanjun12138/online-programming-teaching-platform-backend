package com.liu.practice.service;

import com.liu.practice.dao.QuestionDao;
import com.liu.practice.entity.Params;
import com.liu.practice.entity.Question;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;


@Service
public class QuestionService {
       @Resource
       private QuestionDao questionDao;

       public  Question  findBySearch(Params params) {

             Question  question = questionDao.findBySearch(params.getQuestionid());
            return question;
     }
    public  Question  findByid(Long id) {

        Question  question = questionDao.findBySearch(id.toString());
        return question;
    }
        public void add(Question book) {
           try {
               questionDao.insertSelective(book);
           }
           catch (Exception e)
           {
                log.println("该题号已存在");
           }
        }

        public void update(Question book) {
            questionDao.updateByPrimaryKeySelective(book);
         }

         public void delete(Integer id) {
             questionDao.deleteByPrimaryKey(id);
        }
}
