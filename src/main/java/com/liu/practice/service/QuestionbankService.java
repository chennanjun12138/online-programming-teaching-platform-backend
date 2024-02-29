package com.liu.practice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.practice.common.JwtInterceptor;
import com.liu.practice.dao.HomeworkDao;
import com.liu.practice.dao.QuestionbankDao;
import com.liu.practice.entity.Homework;
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
public class QuestionbankService {
    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

        @Resource
        private QuestionbankDao questionbankDao;
        @Resource
        private HomeworkDao homeworkDao;
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
    public List<Questionbank> findbyquestionid(Params params)
    {
        List<Questionbank> ans=questionbankDao.findBySearch(params);
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

            String  questionid=book.getQuestionid();
            String list=book.getBelongid();
            String  values = list.substring(1, list.length() - 1);
            String[] valueArray = values.split(",");
            List<String> res = new ArrayList<>();
            for (String value : valueArray) {
                res.add(value.trim());
            }
            for(String j:res) {
                if(!j.equals(""))
                {
                    Homework homework = homeworkDao.findbyid(Integer.parseInt(j.trim()));
                    String ans = homework.getContent();
                    if (ans.equals("[]"))
                    {
                        List<String> list2 = new ArrayList<>();
                        list2.add(questionid);
                        StringJoiner joiner = new StringJoiner(",", "[", "]");
                        for (String str : list2) {
                            joiner.add(str);
                        }
                        String result = joiner.toString();
                        homework.setContent(result);
                        homeworkDao.updateByPrimaryKeySelective(homework);
                    }
                    else
                    {

                        String  content1 = ans.substring(1, ans.length() - 1);
                        // log.info(content1);
                        String[] content2=content1.split(",");
                        List<String> list3 = new ArrayList<>();
                        // log.info(questionid);
                        String flag="0";
                        for(String k:content2)
                        {
                            log.info("questionid："+k);
                            list3.add(k);
                            if(k.equals(questionid.trim()))
                            {

                                flag="1";
                            }
                        }
                        if(flag.equals("0"))
                        {
                            list3.add(questionid);
                        }
                        StringJoiner joiner = new StringJoiner(",", "[", "]");
                        for (String str : list3) {
                            joiner.add(str);
                        }
                        String result = joiner.toString();
                        homework.setContent(result);
                        homeworkDao.updateByPrimaryKeySelective(homework);
                    }
                }

            }
            book.setSubmitnum(0);
            book.setSolvenum(0);
            questionbankDao.insertSelective(book);
        }

        public void update(Questionbank book,String oldcotnet) {
            String  questionid=book.getQuestionid();
            String list=book.getBelongid();
            String  values = list.substring(1, list.length() - 1);
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
            for (String element : newlist) {
                if (oldlist.contains(element)) {
                    System.out.println(element + " exists in oldList.");
                } else {
                    System.out.println(element + " does not exist in oldList.");
                    Homework homework = homeworkDao.findbyid(Integer.parseInt(element.trim()));
                    String ans = homework.getContent();
                    List<String> list2 = new ArrayList<>();
                    if(ans.equals("[]"))
                    {
                        list2.add(questionid);
                        StringJoiner joiner = new StringJoiner(",", "[", "]");
                        for (String str : list2) {
                            joiner.add(str);
                        }
                        String result = joiner.toString();
                        homework.setContent(result);
                        homeworkDao.updateByPrimaryKeySelective(homework);
                    }
                    else
                    {
                        String  content1 = ans.substring(1, ans.length() - 1);
                        String[] content2=content1.split(",");
                        for(String k:content2)
                        {
                            list2.add(k);
                        }
                        list2.add(questionid);
                        StringJoiner joiner = new StringJoiner(",", "[", "]");
                        for (String str : list2) {
                            joiner.add(str);
                        }
                        String result = joiner.toString();
                        homework.setContent(result);
                        homeworkDao.updateByPrimaryKeySelective(homework);
                    }

                }
            }
            for (String element2 :oldlist) {
                if (!newlist.contains(element2))
                {
                    Homework homework = homeworkDao.findbyid(Integer.parseInt(element2.trim()));
                    String ans = homework.getContent();
                    String  content1 = ans.substring(1, ans.length() - 1);
                    String[] content2=content1.split(",");
                    List<String> list2 = new ArrayList<>();
                    for(String j:content2)
                    {
                        j=j.trim();
                        if(!j.equals(questionid.trim()))
                        {
                            list2.add(j);
                        }
                    }
                    StringJoiner joiner = new StringJoiner(",", "[", "]");
                    for (String str : list2) {
                        joiner.add(str);
                    }
                    String result = joiner.toString();
                    homework.setContent(result);
                    homeworkDao.updateByPrimaryKeySelective(homework);
                }
            }
            questionbankDao.updateByPrimaryKeySelective(book);
         }

         public void delete(Integer id) {
             Questionbank questionbank=questionbankDao.findbyid(id);
             String questionid=questionbank.getQuestionid();
             String list=questionbank.getBelongid();
             String  values = list.substring(1, list.length() - 1);
             String[] valueArray = values.split(",");
             List<String> res = new ArrayList<>();
             for (String value : valueArray) {
                 res.add(value.trim());
             }
             for(String i:res)
             {
                 Homework ans=homeworkDao.findbyid(Integer.parseInt(i));
                 String homworkcontent=ans.getContent();
                 String  content1 = homworkcontent.substring(1, homworkcontent.length() - 1);

                 String[] content2=content1.split(",");
                 List<String> list2 = new ArrayList<>();

                 for(String j:content2)
                 {
                       j=j.trim();

                       if(!j.equals(questionid.trim()))
                       {
                           log.info("questionid："+j);
                           list2.add(j);
                       }
                 }
                 StringJoiner joiner = new StringJoiner(",", "[", "]");
                 for (String str : list2) {
                     joiner.add(str);
                 }
                 String result = joiner.toString();
                 ans.setContent(result);
                homeworkDao.updateByPrimaryKeySelective(ans);
             }
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
    public void changenum(Questionbank questionbank)
    {
        questionbankDao.updateByPrimaryKeySelective(questionbank);

    }
}
