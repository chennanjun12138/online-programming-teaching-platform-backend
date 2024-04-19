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
import com.liu.practice.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
        List<Homework>  ans=homeworkDao.findByteacher(list);
         return PageInfo.of(ans);
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
            Homework homework=homeworkDao.findbyid(submit.getHomeworkid());
            String dateTimeStr = submit.getSubmittime(); // 假设 submit.getSubmittime() 返回的是完整日期时间字符串

            // 提取日期部分并转换为 LocalDate 对象
            LocalDate localDate = LocalDate.parse(dateTimeStr.substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // 提取作业的起始日期和结束日期，并转换为 LocalDate 对象
            LocalDate startDate = LocalDate.parse(homework.getStarttime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate endDate = LocalDate.parse(homework.getEndtime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // 判断给定日期是否在规定范围内
            if (localDate.isAfter(startDate.minusDays(1)) && localDate.isBefore(endDate.plusDays(1))) {
                submitDao.insertSelective(submit);
            } else {
                throw new CustomException("提交时间不在规定时间内");
            }

        }
        public void changesubmit(Submit submit)
        {
            log.info(submit.getScore());
            submitDao.updateByPrimaryKeySelective(submit);

        }

        public void update(Homework book) {
            homeworkDao.updateByPrimaryKeySelective(book);
         }
         public Homework findbyid(Integer id)
         {
             Homework work=homeworkDao.findbyid(id);
             return work;
         }
         public void delete(Integer id) {

             Homework work=homeworkDao.findbyid(id);
             String list=work.getContent();
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
                 if(belongid.equals("[]")==false)
                 {
                     String  belongid2 = belongid.substring(1, belongid.length() - 1);
                     belong3 = belongid2.split(",");
                 }
                 List<String> list2 = new ArrayList<>();
                 for(String j:belong3)
                 {
                     j=j.trim();
                     if(!j.equals(id.toString().trim()))
                     {
                        // log.info(j);
                        list2.add(j);
                     }
                 }
                 StringJoiner joiner = new StringJoiner(",", "[", "]");
                 for (String str : list2) {
                     joiner.add(str);
                 }
                 String result = joiner.toString();
                 ans.setBelongid(result);
                 questionbankDao.updateByPrimaryKeySelective(ans);
             }
             homeworkDao.deletebyhomeworkid(id);
             submitDao.deletebyhomeworkid(id);
        }
        public Questionbank findQuestionbank(String element)
        {
            return  questionbankDao.findByhomework(element);
        }

        public void changeblongid(String newcotent,String homeworkid,String oldcotnet)
        {
            String  values = newcotent.substring(1, newcotent.length() - 1);
            String[] valueArray = values.split(",");
            List<String> newlist = new ArrayList<>();
            for (String value : valueArray) {
                newlist.add(value.trim());
            }
            String o1=oldcotnet.substring(1,oldcotnet.length()-1);
            String[] o2=o1.split(",");
            List<String> oldlist=new ArrayList<>();
            for (String value : o2) {
                oldlist.add(value.trim());
            }
            for(String element:newlist)
            {
                log.info("element"+ element);
                if(!element.equals(""))
                {
                    if (oldlist.contains(element)) {
                        System.out.println(element + " exists in oldList.");
                    }
                    else
                    {
                        Questionbank ans=questionbankDao.findByhomework(element);
                        if(ans==null)
                        {
                            throw new CustomException("有题目不存在");
                        }
                        String belongid=ans.getBelongid();
                        List<String> list2 = new ArrayList<>();
                        if(belongid.equals("[]"))
                        {
                            list2.add(homeworkid);
                            StringJoiner joiner = new StringJoiner(",", "[", "]");
                            for (String str : list2) {
                                joiner.add(str);
                            }
                            String result = joiner.toString();
                            ans.setBelongid(result);
                            questionbankDao.updateByPrimaryKeySelective(ans);
                        }
                        else
                        {
                            String  content1 = belongid.substring(1, belongid.length() - 1);
                            String[] content2=content1.split(",");
                            for(String k:content2)
                            {
                                list2.add(k);
                            }
                            list2.add(homeworkid);
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
            for (String element2 :oldlist) {
                log.info("element2"+element2);
                if(!element2.equals(""))
                {
                    if (!newlist.contains(element2))
                    {
                        Questionbank questionbank=questionbankDao.findByhomework(element2);
                        String ans = questionbank.getBelongid();

                        String  content1 = ans.substring(1, ans.length() - 1);
                        String[] content2=content1.split(",");
                        List<String> list2 = new ArrayList<>();
                        for(String j:content2)
                        {
                            j=j.trim();
                            if(!j.equals(homeworkid.trim()))
                            {
                                list2.add(j);
                            }
                        }
                        StringJoiner joiner = new StringJoiner(",", "[", "]");
                        for (String str : list2) {
                            joiner.add(str);
                        }
                        String result = joiner.toString();
                        questionbank.setBelongid(result);
                        questionbankDao.updateByPrimaryKeySelective(questionbank);
                    }
                }

            }
        }
}
