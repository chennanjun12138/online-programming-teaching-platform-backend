package com.liu.practice.judge.strategy;

import com.liu.practice.dto.JudgeCase;
import com.liu.practice.entity.Question;
import com.liu.practice.entity.Questionsubmit;
import com.liu.practice.judge.codesandbox.model.JudgeInfo;
import lombok.Data;

import java.util.List;

/**
 * 上下文（用于定义在策略中传递的参数）
 */
@Data

public class JudgeContext {
    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private Questionsubmit questionSubmit;



}
