package com.liu.practice.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.liu.practice.common.ErrorCode;
import com.liu.practice.exception.BusinessException;
import com.liu.practice.judge.codesandbox.CodeSandbox;
import com.liu.practice.judge.codesandbox.model.ExecuteCodeRequest;
import com.liu.practice.judge.codesandbox.model.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;

/*
* 远程代码沙箱（实际调用接口）
* */
public class RemoteCodeSandbox implements CodeSandbox {
    // 定义鉴权请求头和密钥
    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET = "secretKey";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest)
    {
        System.out.println("远程代码沙箱");
       String url = "http://192.168.32.129:8090/executeCode";
        //        String url="http://localhost:8090/executeCode";
        //使用json格式传送请求
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        if (StringUtils.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode remoteSandbox error, message = " + responseStr);
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);

    }

}
