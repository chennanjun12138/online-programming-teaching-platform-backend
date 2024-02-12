package com.liu.practice.judge;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageInfo;
import com.liu.practice.common.ErrorCode;
import com.liu.practice.common.JwtInterceptor;
import com.liu.practice.dto.JudgeCase;
import com.liu.practice.dto.JudgeConfig;
import com.liu.practice.entity.Question;
import com.liu.practice.entity.Questionsubmit;
import com.liu.practice.enums.JudgeInfoMessageEnum;
import com.liu.practice.enums.QuestionSubmitStatusEnum;
import com.liu.practice.exception.BusinessException;
import com.liu.practice.judge.codesandbox.CodeSandbox;
import com.liu.practice.judge.codesandbox.CodeSandboxFactory;
import com.liu.practice.judge.codesandbox.CodeSandboxProxy;
import com.liu.practice.judge.codesandbox.model.ExecuteCodeRequest;
import com.liu.practice.judge.codesandbox.model.ExecuteCodeResponse;
import com.liu.practice.judge.codesandbox.model.JudgeInfo;
import com.liu.practice.judge.strategy.DefaultJudgeStrategy;
import com.liu.practice.judge.strategy.JudgeContext;
import com.liu.practice.judge.strategy.JudgeStrategy;
import com.liu.practice.service.QuestionService;
import com.liu.practice.service.QuestionsubmitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class JudgeServiceImpl implements JudgeService {
    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Value("${codesandbox.type:example}")
    private  String type;
    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionsubmitService questionsubmitService;

    @Resource
    private  JudgeManager judgeManager;

    @Override
    public Questionsubmit doJudge(long questionSubmitId) {

        Questionsubmit questionsubmit=questionsubmitService.getbyid(questionSubmitId);
        if(questionsubmit==null)
        {
            throw  new BusinessException(ErrorCode.NOT_FOUND_ERROR,"提交信息不存在");
        }
        Long questionid=questionsubmit.getQuestionid();
        Question question=questionService.findByid(questionid);

        if(question==null)
        {
            throw  new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目不存在");
        }

        //如果不为等待状态
        if(!questionsubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue()))
        {

            throw  new BusinessException(ErrorCode.OPERATION_ERROR,"题目正在判题中");
        }

        Questionsubmit questionsubmitupdate=new Questionsubmit();
        questionsubmitupdate.setId(questionSubmitId);
        questionsubmitupdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());

        boolean update=questionsubmitService.update(questionsubmitupdate);
        if(!update)
        {
            throw  new BusinessException(ErrorCode.SYSTEM_ERROR,"题目状态更新错误");
        }
        //调用代码沙箱

        CodeSandbox codeSandbox= CodeSandboxFactory.newInstance("example");
        codeSandbox=new CodeSandboxProxy(codeSandbox);
//        List<String>judgeCaseSrt=new ArrayList<>();
//        judgeCaseSrt.add(question.getExamplein());
//        judgeCaseSrt.add(question.getExampleout()) ;
//        JSONArray jsonArray = new JSONArray();
//
//        // 遍历 List，并将每个字符串添加到 JSONArray 中
//        for (String str : judgeCaseSrt) {
//            log.info(str);
//            jsonArray.put(str);
//        }
//        log.info(jsonArray.toString());
        String judgeCaseStr = question.getJudgeCase();
        JSONArray jsonArray = JSONUtil.parseArray(judgeCaseStr);

        List<JudgeCase> judgeCaseList = jsonArray.toList(JudgeCase.class);

       // List<JudgeCase> judgeCaseList = JSONUtil.toList(jsonArray,JudgeCase.class);
        log.info(judgeCaseList.toString());
        String language = questionsubmit.getLanguage();
        String code = questionsubmit.getCode();
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());

        ExecuteCodeRequest executeCodeRequest=ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse= codeSandbox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();
        // 5）根据沙箱的执行结果，设置题目的判题状态和信息
        log.info(inputList.toString());
        log.info("代码沙箱执行结果"+outputList);
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionsubmit);

        JudgeInfo judgeInfo=judgeManager.doJudge(judgeContext);

        //修改数据库中的判题结果
        questionsubmitupdate = new Questionsubmit();
        questionsubmitupdate.setId(questionSubmitId);
        questionsubmitupdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionsubmitupdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        log.info(questionsubmitupdate.getJudgeInfo());
        update = questionsubmitService.update(questionsubmitupdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        Questionsubmit questionSubmitResult = questionsubmitService.getbyid(questionid);
        return questionSubmitResult;
    }
}
