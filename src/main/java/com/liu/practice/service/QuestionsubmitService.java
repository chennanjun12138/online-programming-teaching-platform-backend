package com.liu.practice.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.practice.common.ErrorCode;
import com.liu.practice.common.JwtInterceptor;
import com.liu.practice.dao.QuestionbankDao;
import com.liu.practice.dao.QuestionsubmitDao;
import com.liu.practice.entity.*;
import com.liu.practice.enums.QuestionSubmitLanguageEnum;
import com.liu.practice.enums.QuestionSubmitStatusEnum;
import com.liu.practice.exception.BusinessException;
import com.liu.practice.judge.JudgeService;
import com.liu.practice.judge.codesandbox.CodeSandboxTemplate;
import com.liu.practice.judge.codesandbox.model.ExecuteCodeRequest;
import com.liu.practice.judge.codesandbox.model.ExecuteCodeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
public class QuestionsubmitService extends CodeSandboxTemplate {
    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
       private QuestionsubmitDao questionsubmitDao;
        @Resource
        private JudgeService judgeService;
    @Resource
    private QuestionService questionService;
    @Resource
    private QuestionbankDao questionbankDao;
    public long doQuestionSubmit(Questionsubmit questionsubmit) {
        // 校验编程语言是否合法
        String language = questionsubmit.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误");
        }
        long questionId = questionsubmit.getQuestionid();
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.findByid(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已提交题目
        long userId =questionsubmit.getUserid();
        // 每个用户串行提交题目
        Questionsubmit questionSubmit = new Questionsubmit();
        questionSubmit.setUserid(userId);
        questionSubmit.setQuestionid(questionId);
        questionSubmit.setCode(questionsubmit.getCode());
        questionSubmit.setLanguage(language);
        // 设置初始状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean save = this.save(questionSubmit);
        if (!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据插入失败");
        }
        Long questionSubmitId = questionSubmit.getId();
//        Long questionSubmitId=Long.parseLong("2");
        // 执行判题服务
        CompletableFuture.runAsync(() -> {

            judgeService.doJudge(questionSubmitId);
        });
        return questionSubmitId;
    }
    public  List<String>  runSubmit(Questionsubmit questionsubmit) {
        // 校验编程语言是否合法
        String language = questionsubmit.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误");
        }
        // 执行判题服务
        String code = questionsubmit.getCode();

        List<String> inputlist = new ArrayList<>();
        if(questionsubmit.getInputcontent()!=null)
        {
            String[] lines = questionsubmit.getInputcontent().split("\n");
            for (String line : lines) {
                String[] parts = line.split("\n");
                inputlist.addAll(Arrays.asList(parts));
            }
        }
        ExecuteCodeRequest executeCodeRequest=ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputlist)
                .build();
        ExecuteCodeResponse executeCodeResponse=super.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();
        log.info("判题成功");
        return  outputList;
    }
    private boolean save(Questionsubmit questionSubmit) {

               if(questionsubmitDao.findbyothers(questionSubmit.getUserid(),questionSubmit.getQuestionid(),questionSubmit.getLanguage(),questionSubmit.getCode())==null)
              {

                  add(questionSubmit);
                  return true;
               }
              else
              {

                  return false;
              }

    }

    public PageInfo<Questionsubmit> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<Questionsubmit> list = questionsubmitDao.findBySearch(params);
        return PageInfo.of(list);
    }
    public List<Questionsubmit> getall(Params params) {

        List<Questionsubmit> list = questionsubmitDao.findBySearch(params);
        return list;
    }
    public PageInfo<Questionsubmit> findByteachid(Params params) {
         // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());

//        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<Questionsubmit> list = questionsubmitDao.findByteachid(params);


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
          log.info(JUDGE.toString());
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
             Questionsubmit questionsubmit=questionsubmitDao.getbyid(Long.valueOf(id));
             Long questionid=questionsubmit.getQuestionid();
             Questionbank questionbank=questionbankDao.findByhomework(questionid.toString());
             Integer subitnum=questionbank.getSubmitnum();
             questionbank.setSubmitnum(subitnum-1);
             questionbankDao.updateByPrimaryKeySelective(questionbank);
             String judgeinfo=questionsubmit.getJudgeInfo();
             log.info("返回"+judgeinfo.contains("Accepted"));
             if(judgeinfo.contains("Accepted"))
             {
                 log.info("进入");
                 Integer solvenum=questionbank.getSolvenum();
                 questionbank.setSolvenum(solvenum-1);
             }
             questionbankDao.updateByPrimaryKeySelective(questionbank);
             questionsubmitDao.deleteByPrimaryKey(id);
        }
    public Questionsubmit findbyid(Integer id)
    {
      Questionsubmit questionsubmit=  questionsubmitDao.selectByPrimaryKey(id);
      return  questionsubmit;
    }

 }
