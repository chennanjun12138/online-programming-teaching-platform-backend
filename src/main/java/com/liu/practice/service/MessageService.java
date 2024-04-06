package com.liu.practice.service;

import com.liu.practice.dao.MessageDao;
import com.liu.practice.entity.Message;
import com.liu.practice.entity.Params;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;


@Service
public class MessageService {
       @Resource
       private MessageDao messageDao;

       public List<Message> findBySearch(Params params) {

             List<Message>   messages = messageDao.findBySearch(params);
            return messages;
     }
    public List<Message> getAll() {

        List<Message>   messages = messageDao.getMessage();
        return messages;
    }
        public void add(Message book) {
               messageDao.insertSelective(book);
        }

        public void update(Message book) {
                messageDao.updateByPrimaryKeySelective(book);
         }

         public void delete(Integer id) {
             messageDao.deleteByPrimaryKey(id);
        }
}
