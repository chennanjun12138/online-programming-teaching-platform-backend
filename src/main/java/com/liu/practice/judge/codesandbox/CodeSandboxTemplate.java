package com.liu.practice.judge.codesandbox;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.liu.practice.judge.ProcessUtils;
import com.liu.practice.judge.codesandbox.model.ExecuteCodeRequest;
import com.liu.practice.judge.codesandbox.model.ExecuteCodeResponse;
import com.liu.practice.judge.codesandbox.model.ExecuteMessage;
import com.liu.practice.judge.codesandbox.model.JudgeInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 代码沙箱模板方法的实现
 */
@Slf4j
public abstract class CodeSandboxTemplate implements CodeSandbox{

    private static final String GLOBAL_CODE_DIR_NAME = "tmpCode";

    private static final String GLOBAL_JAVA_CLASS_NAME = "Main.java";
    private static final String GLOBAL_C_CLASS_NAME = "Main.c";
    private static final long TIME_OUT = 5000L;

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        log.info(inputList.toString());
        String code = executeCodeRequest.getCode();
        String language = executeCodeRequest.getLanguage();

//        1. 把用户的代码保存为文件
        File userCodeFile = saveCodeToFile(code,language);

//        2. 编译代码
        ExecuteMessage compileFileExecuteMessage = compileFile(userCodeFile,language);
        System.out.println(compileFileExecuteMessage);
        if(compileFileExecuteMessage.getMessage()=="编译错误")
        {
            ExecuteCodeResponse notice=new ExecuteCodeResponse();
            List<String> list=new ArrayList<>();
            list.add("编译错误");
            notice.setOutputList(list);
            boolean b = deleteFile(userCodeFile);
            if (!b) {
                log.error("deleteFile error, userCodeFilePath = {}", userCodeFile.getAbsolutePath());
            }
            return  notice;
        }

        // 3. 执行代码，得到输出结果
        List<ExecuteMessage> executeMessageList = runFile(userCodeFile,language,inputList);
        System.out.println("最终结果："+executeMessageList);
//        4. 收集整理输出结果
        ExecuteCodeResponse outputResponse = getOutputResponse(executeMessageList);

//        5. 文件清理
        boolean b = deleteFile(userCodeFile);
        if (!b) {
            log.error("deleteFile error, userCodeFilePath = {}", userCodeFile.getAbsolutePath());
        }
        System.out.println(outputResponse);
        return outputResponse;
    }


    /**
     * 1. 把用户的代码保存为文件
     * @param code 用户代码
     */
    public File saveCodeToFile(String code,String language) {
        String userDir = System.getProperty("user.dir");
        String globalCodePathName = userDir + File.separator + GLOBAL_CODE_DIR_NAME;
        // 判断全局代码目录是否存在，没有则新建
        if (!FileUtil.exist(globalCodePathName)) {
            FileUtil.mkdir(globalCodePathName);
        }

        // 把用户的代码隔离存放
        String userCodeParentPath = globalCodePathName + File.separator + UUID.randomUUID();
        String userCodePath = "";
        if("java".equals(language))
        {
              userCodePath = userCodeParentPath + File.separator + GLOBAL_JAVA_CLASS_NAME;
        }
        else if("c".equals(language))
        {

            userCodePath=userCodeParentPath + File.separator+GLOBAL_C_CLASS_NAME;
        }
        log.info("path:"+userCodePath);
        File userCodeFile = FileUtil.writeString(code, userCodePath, StandardCharsets.UTF_8);
        return userCodeFile;
    }

    /**
     * 2、编译代码
     * @param userCodeFile
     */
    public ExecuteMessage compileFile(File userCodeFile,String language) {

        String compileCmd = "";
        if("java".equals(language))
        {
            compileCmd = String.format("javac -encoding utf-8 %s", userCodeFile.getAbsolutePath());
        }
        else if("c".equals(language))
        {
            compileCmd= String.format("gcc %s -o Main", userCodeFile.getAbsolutePath());
            log.info(compileCmd);
        }

        try {
            Process compileProcess = Runtime.getRuntime().exec(compileCmd);
            ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(compileProcess, "编译");
            if (executeMessage.getExitValue() != 0) {
                try {
                    throw new RuntimeException("编译错误");
                } catch (RuntimeException e) {
                    // 处理异常
                    System.out.println("发生异常：" + e.getMessage());
                    executeMessage.setMessage("编译错误");
                    return executeMessage;
                }
            }
            return executeMessage;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 3、执行文件，获得执行结果列表
     * @param userCodeFile
     */
    public List<ExecuteMessage> runFile(File userCodeFile,String language,List<String> inputList) {
        String userCodeParentPath = userCodeFile.getParentFile().getAbsolutePath();

        List<ExecuteMessage> executeMessageList = new ArrayList<>();
        if(inputList==null||inputList.isEmpty())
        {

            String runCmd ="";
            if("java".equals(language))
            {
                runCmd=String.format("java -Xmx256m -Dfile.encoding=UTF-8 -cp %s Main ", userCodeParentPath);
            }
            else if("c".equals(language))
            {
                runCmd=String.format("Main", userCodeParentPath);
                log.info(runCmd);
            }
            try {
                Process runProcess = Runtime.getRuntime().exec(runCmd);
                ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(runProcess, "运行");
                System.out.println(executeMessage);
                executeMessageList.add(executeMessage);
            } catch (Exception e) {
                throw new RuntimeException("执行错误", e);
            }
        }
        else {
                String ans="";
                for(String item:inputList)
                {
                        ans+=item;
                        ans+=" ";
                }
            String runCmd ="";
            if("java".equals(language))
            {
                    runCmd=String.format("java -Xmx256m -Dfile.encoding=UTF-8 -cp %s Main %s", userCodeParentPath,ans);
            }
            else if("c".equals(language))
            {

                runCmd=String.format("Main",userCodeParentPath);
                log.info(runCmd);
            }
            try {
                Process runProcess = Runtime.getRuntime().exec(runCmd);
                System.out.println("输入内容"+ans);
                if(ans!="")
                {
                    ExecuteMessage  executeMessage = ProcessUtils.runInteractProcessAndGetMessage(runProcess, ans);
                    System.out.println("返回结果"+executeMessage.getMessage());
                    executeMessageList.add(executeMessage);
                    System.out.println(executeMessageList);
                }

            } catch (Exception e) {
                throw new RuntimeException("执行错误", e);
            }
        }

        return executeMessageList;
    }

    /**
     * 4、获取输出结果
     * @param executeMessageList
     */
    public ExecuteCodeResponse getOutputResponse(List<ExecuteMessage> executeMessageList) {
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        List<String> outputList = new ArrayList<>();

        for (ExecuteMessage executeMessage : executeMessageList) {
            System.out.println("executeMessage.getMessage()："+executeMessage.getMessage());
            String str=executeMessage.getMessage();
            Integer ans=str.length() - str.replaceAll("\n", "").length();
            System.out.println("行数"+ans);
            String errorMessage = executeMessage.getErrorMessage();
            if (StrUtil.isNotBlank(errorMessage)) {
                // 用户提交的代码执行中存在错误
                break;
            }
            outputList.add(executeMessage.getMessage());
            System.out.println("输出"+outputList);
        }
        // 正常运行完成
        executeCodeResponse.setOutputList(outputList);
        System.out.println("输出2"+executeCodeResponse.getOutputList());
        return executeCodeResponse;
    }

    /**
     * 5、删除文件
     * @param userCodeFile
     */
    public boolean deleteFile(File userCodeFile) {
        if (userCodeFile.getParentFile() != null) {
            String userCodeParentPath = userCodeFile.getParentFile().getAbsolutePath();
            boolean del = FileUtil.del(userCodeParentPath);
            System.out.println("删除" + (del ? "成功" : "失败"));
            return del;
        }
        return true;
    }

    /**
     * 6、获取错误响应
     * @param e
     */
    private ExecuteCodeResponse getErrorResponse(Throwable e) {
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(new ArrayList<>());
        executeCodeResponse.setMessage(e.getMessage());
        // 表示代码沙箱错误
        executeCodeResponse.setStatus(2);
        executeCodeResponse.setJudgeInfo(new JudgeInfo());
        return executeCodeResponse;
    }

}
