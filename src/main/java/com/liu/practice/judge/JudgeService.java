package com.liu.practice.judge;

import com.liu.practice.entity.Questionsubmit;
import com.liu.practice.judge.codesandbox.model.ExecuteCodeResponse;
/**
 * 判题服务
 */

public interface JudgeService {
   /**
    * 判题
    * @param questionSubmitId
    * @return
    */

   Questionsubmit doJudge(long questionSubmitId);

}
