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
        Long memory = judgeInfo.getMemory();
        Long time = judgeInfo.getTime();
        List<String> inputList=judgeContext.getInputList();
        List<String> outputList=judgeContext.getOutputList();
        Question question=judgeContext.getQuestion();
        List<JudgeCase>judgeCaseList=judgeContext.getJudgeCaseList();
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);


//        JudgeInfoMessageEnum judgeInfoMessageEnum=JudgeInfoMessageEnum.WAITING;
        if(outputList.size()!=inputList.size())
        {
            judgeInfoMessageEnum =JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        for(int i=0;i<judgeCaseList.size();i++)
        {
            JudgeCase judgeCase=judgeCaseList.get(i);
            if (!judgeCase.getOutput().equals(outputList.get(i))) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfoResponse;
            }

        }
        //判断题目限制
        String judgeConfigSter=question.getJudgeConfig();
        JudgeConfig judgeConfig= JSONUtil.toBean(judgeConfigSter,JudgeConfig.class);
        Long memorylimit=judgeConfig.getMemoryLimit();
        Long timelimit=judgeConfig.getTimeLimit();

        if(memory>memorylimit)
        {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;

        }
        if(time>timelimit)
        {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;

        }
//        JudgeInfo judgeInfoResponse=new JudgeInfo();
        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);
        return judgeInfoResponse;
    }
}
