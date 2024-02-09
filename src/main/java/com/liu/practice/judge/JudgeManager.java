package com.liu.practice.judge;

import com.liu.practice.entity.Questionsubmit;
import com.liu.practice.judge.codesandbox.model.JudgeInfo;
import com.liu.practice.judge.strategy.DefaultJudgeStrategy;
import com.liu.practice.judge.strategy.JavalanguageJudgeStrategy;
import com.liu.practice.judge.strategy.JudgeContext;
import com.liu.practice.judge.strategy.JudgeStrategy;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 */
@Service

public class JudgeManager {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        Questionsubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavalanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }

}
