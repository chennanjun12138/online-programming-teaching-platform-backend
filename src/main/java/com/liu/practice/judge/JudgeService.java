package com.liu.practice.judge;

import com.liu.practice.entity.Questionsubmit;
import com.liu.practice.judge.codesandbox.model.ExecuteCodeResponse;
import com.liu.practice.judge.codesandbox.model.JudgeInfo;

/**
 * 判题服务
 */

public interface JudgeService {
   /**
    * 判题
    * @param questionSubmitId
    */

   Questionsubmit doJudge(long questionSubmitId);
}
