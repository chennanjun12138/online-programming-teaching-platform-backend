package com.liu.practice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.practice.common.JwtInterceptor;
import com.liu.practice.dao.HomeworkDao;
import com.liu.practice.dao.QuestionbankDao;
import com.liu.practice.dao.SubmitDao;
import com.liu.practice.entity.Homework;
import com.liu.practice.entity.Params;
import com.liu.practice.entity.Questionbank;
import com.liu.practice.entity.Submit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;


@Service
public class HomeworkService {
    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

       @Resource
       private HomeworkDao homeworkDao;
       @Resource
       private SubmitDao submitDao;
       @Resource
       private QuestionbankDao questionbankDao;

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
    public PageInfo<Submit> findsubmits(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());

        List<Submit> res=submitDao.findBySearch(params.getHomeworkid());

        return PageInfo.of(res);
    }
    public PageInfo<Submit> findbystudent(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());

        List<Submit> res=submitDao.findbystudent(params);

        return PageInfo.of(res);
    }
        public void add(Homework book) {

             homeworkDao.insertSelective(book);
        }
        public void addsubmit(Submit submit)
        {
              submitDao.insertSelective(submit);
        }
        public void changesubmit(Submit submit)
        {
            log.info(submit.getScore());
            submitDao.updateByPrimaryKeySelective(submit);
        }

        public void update(Homework book) {
            homeworkDao.updateByPrimaryKeySelective(book);

         }

         public void delete(Integer id) {
             homeworkDao.deleteByPrimaryKey(id);
        }

        public void changeblongid(String list,String homeworkid)
        {
          String  values = list.substring(1, list.length() - 1);
            String[] valueArray = values.split(",");

            List<String> res = new ArrayList<>();
            for (String value : valueArray) {
                res.add(value.trim());
            }
            for(String i:res)
            {
                 Questionbank ans=questionbankDao.findByhomework(i);
                 String belongid=ans.getBelongid();

                String[] belong3= new String[0];
                if(belongid.equals("0")==false)
                 {
                     String  belongid2 = belongid.substring(1, belongid.length() - 1);
                     belong3 = belongid2.split(",");
                 }
                 String flag="0";
                List<String> list2 = new ArrayList<>();
                for(String j:belong3)
                 {
                      j=j.trim();
                    // log.info(homeworkid);
                    // log.info(j);
                      if(j.equals(homeworkid)==true)
                      {
                           flag="1";
                      }
                     list2.add(j.trim());
                 }
                   log.info(flag);
                 if(flag.equals("0"))
                 {
                     list2.add(homeworkid.trim());
                     StringJoiner joiner = new StringJoiner(",", "[", "]");
                     for (String str : list2) {
                         joiner.add(str);
                     }
                     String result = joiner.toString();
                     ans.setBelongid(result);
                     questionbankDao.updateByPrimaryKeySelective(ans);
                 }
            }
        }
}
