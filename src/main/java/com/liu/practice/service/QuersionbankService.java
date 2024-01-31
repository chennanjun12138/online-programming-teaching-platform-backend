package com.liu.practice.service;

import cn.hutool.json.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.practice.common.JwtInterceptor;
import com.liu.practice.dao.QuestionbankDao;
import com.liu.practice.entity.Params;
import com.liu.practice.entity.Questionbank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;


@Service
public class QuersionbankService {
    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
       private QuestionbankDao questionbankDao;
        public PageInfo<Questionbank> findBySearch(Params params) {
            // 开启分页查询
            PageHelper.startPage(params.getPageNum(), params.getPageSize());
            // 接下来的查询会自动按照当前开启的分页设置来查询
            List<Questionbank> list = questionbankDao.findBySearch(params);
            return PageInfo.of(list);
     }
     public Questionbank findbyid(Params params)
     {
         Questionbank ans=questionbankDao.findByhomework(params.getContent());
         return  ans;
     }
    public PageInfo<Questionbank> findByhomework(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询

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
            if(ans!=null)
            {
                res.add(ans);
            }

        }
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

    public void change(String questionid,String homeworkid) {
        Questionbank book=questionbankDao.findByhomework(questionid);
        String belongid=book.getBelongid();

        String[] belong3= new String[0];
        if(belongid.equals("0")==false)
        {
            String  belongid2 = belongid.substring(1, belongid.length() - 1);
            belong3 = belongid2.split(",");
        }
        List<String> list2 = new ArrayList<>();
        for(String j:belong3)
        {
                     j=j.trim();
            if(j.equals(homeworkid)==false)
            {
                log.info(j);
                list2.add(j.trim());
            }
        }
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        for (String str : list2) {
            joiner.add(str);
        }
        String result = joiner.toString();
        book.setBelongid(result);
        questionbankDao.updateByPrimaryKeySelective(book);
    }
}
