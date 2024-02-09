package com.liu.practice.judge.codesandbox.impl;

import com.liu.practice.enums.JudgeInfoMessageEnum;
import com.liu.practice.enums.QuestionSubmitStatusEnum;
import com.liu.practice.judge.codesandbox.CodeSandbox;
import com.liu.practice.judge.codesandbox.model.ExecuteCodeRequest;
import com.liu.practice.judge.codesandbox.model.ExecuteCodeResponse;
import com.liu.practice.judge.codesandbox.model.JudgeInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/*
* 示例代码沙箱
* */
@Slf4j
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest)
    {
        List<String>inputList=executeCodeRequest.getInputList();
       ExecuteCodeResponse executeCodeResponse=new ExecuteCodeResponse();
       executeCodeResponse.setOutputList(inputList);
       executeCodeResponse.setMessage("执行成功");
       executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo=new JudgeInfo();

        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
       executeCodeResponse.setJudgeInfo(judgeInfo);
    return null;
    }
}
