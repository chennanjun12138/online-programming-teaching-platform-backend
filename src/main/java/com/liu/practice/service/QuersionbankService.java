package com.liu.practice.service;

import cn.hutool.json.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.practice.dao.QuestionbankDao;
import com.liu.practice.entity.Params;
import com.liu.practice.entity.Questionbank;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class QuersionbankService {
       @Resource
       private QuestionbankDao questionbankDao;
        public PageInfo<Questionbank> findBySearch(Params params) {
            // 开启分页查询
            PageHelper.startPage(params.getPageNum(), params.getPageSize());
            // 接下来的查询会自动按照当前开启的分页设置来查询
            List<Questionbank> list = questionbankDao.findBySearch(params);
            return PageInfo.of(list);
     }
    public PageInfo<Questionbank> findByhomework(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        String steelGrade = params.getContent();
      //  List<String> list = JSON.parseArray(steelGrade, String.class);
        String values =params.getContent().trim(); // 去除空格
        //分割字符串
        String[] valueArray = values.split(",");

        List<String> list = new ArrayList<>();
        for (String value : valueArray) {
            list.add(value.trim());
        }
        List<Questionbank> res=new ArrayList<>();
        for(String value:list)
        {
            Questionbank ans=questionbankDao.findByhomework(value);
            res.add(ans);
        }
//        List<Questionbank> ans = questionbankDao.findByhomework(params.getContent());
        return PageInfo.of(res);
    }
        public void add(Questionbank book) {
            questionbankDao.insertSelective(book);
        }

        public void update(Questionbank book) {
            questionbankDao.updateByPrimaryKeySelective(book);
         }

         public void delete(Integer id) {
             questionbankDao.deleteByPrimaryKey(id);
        }
}
