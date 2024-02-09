package com.liu.practice.judge.codesandbox.impl;

import com.liu.practice.judge.codesandbox.CodeSandbox;
import com.liu.practice.judge.codesandbox.model.ExecuteCodeRequest;
import com.liu.practice.judge.codesandbox.model.ExecuteCodeResponse;

/*
* 远程代码沙箱（实际调用接口）
* */
public class RemoteCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest)
    {
        System.out.println("远程代码沙箱");
        return null;
    }
}
