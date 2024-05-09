package com.liu.practice.judge.strategy;

import com.liu.practice.judge.codesandbox.model.JudgeInfo;

/**
 * 判题策略
 */

public interface JudgeStrategy {

    /**
     * 执行判题
     * @param judgeContext
     */
    JudgeInfo doJudge(JudgeContext judgeContext);


}
