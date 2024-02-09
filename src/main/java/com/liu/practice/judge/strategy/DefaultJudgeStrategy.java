package com.liu.practice.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.liu.practice.dto.JudgeCase;
import com.liu.practice.dto.JudgeConfig;
import com.liu.practice.entity.Question;
import com.liu.practice.enums.JudgeInfoMessageEnum;
import com.liu.practice.judge.codesandbox.model.JudgeInfo;

import java.util.List;

/**
 * 默认判题策略
 */
public class DefaultJudgeStrategy implements JudgeStrategy
{
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfo=judgeContext.getJudgeInfo();
        List<String> inputList=judgeContext.getInputList();
        List<String> outputList=judgeContext.getOutputList();
        Question question=judgeContext.getQuestion();
        List<JudgeCase>judgeCaseList=judgeContext.getJudgeCaseList();


        JudgeInfoMessageEnum judgeInfoMessageEnum=JudgeInfoMessageEnum.WAITING;
        if(outputList.size()!=inputList.size())
        {
            judgeInfoMessageEnum =JudgeInfoMessageEnum.WRONG_ANSWER;
            return null;
        }
        for(int i=0;i<judgeCaseList.size();i++)
        {
            JudgeCase judgeCase=judgeCaseList.get(i);
            if(!judgeCase.getOutput().equals(outputList.get(i)))
            {
                judgeInfoMessageEnum =JudgeInfoMessageEnum.WRONG_ANSWER;
                return null;
            }
        }
        //判断题目限制

        Long memory=judgeInfo.getMemory();
        Long time=judgeInfo.getTime();
        String judgeConfigSter=question.getTiaojian();
        JudgeConfig judgeConfig= JSONUtil.toBean(judgeConfigSter,JudgeConfig.class);
        Long memorylimit=judgeConfig.getMemoryLimit();
        Long timelimit=judgeConfig.getTimeLimit();

        if(memory>memorylimit)
        {
            judgeInfoMessageEnum =JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            return null;
        }
        if(time>timelimit)
        {
            judgeInfoMessageEnum =JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            return null;
        }
        JudgeInfo judgeInfoResponse=new JudgeInfo();
        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);
        return judgeInfoResponse;
    }
}
