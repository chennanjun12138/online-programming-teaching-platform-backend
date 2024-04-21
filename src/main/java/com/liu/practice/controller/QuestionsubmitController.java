package com.liu.practice.controller;

import com.github.pagehelper.PageInfo;
import com.liu.practice.common.ErrorCode;
import com.liu.practice.common.JwtInterceptor;
import com.liu.practice.common.Result;
import com.liu.practice.entity.*;
import com.liu.practice.exception.BusinessException;
import com.liu.practice.service.EvaluateService;
import com.liu.practice.service.QuestionbankService;
import com.liu.practice.service.QuestionsubmitService;
import com.liu.practice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@CrossOrigin
@RestController
@RequestMapping("/question_submit")
public class QuestionsubmitController {
    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private QuestionsubmitService questionsubmitService;
    @Resource
    private EvaluateService evaluateService;
    @Resource
    private UserService userService;
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
    @GetMapping("/search")
    public Result findBySearch(Params params) {
        if(params.getName()!="")
        {
            if(isInteger(params.getName()))
            {
                params.setUserid(Integer.parseInt(params.getName()));
            }
            else
            {
                log.info("name:"+params.getName());
                User user=userService.findByname(params.getName());
                if(user==null)
                {
                    return Result.error("请输入完整用户名");
                }
                params.setUserid(user.getId());
            }

        }
        PageInfo<Questionsubmit> info = questionsubmitService.findBySearch(params);
        return Result.success(info);
    }
    @GetMapping("/getall")
    public Result getall(Params params) {
        List<Questionsubmit> info = questionsubmitService.getall(params);
        return Result.success(info);
    }
    @GetMapping("/getsubmitbyteachers")
    public Result getsubmitbyteachers(Params params) {
        if(params.getName()!="")
        {
            if(isInteger(params.getName()))
            {
                params.setUserid(Integer.parseInt(params.getName()));
            }
            else
            {
                log.info("name:"+params.getName());
                User user=userService.findByname(params.getName());
                if(user==null)
                {
                    return Result.error("没有该用户名");
                }
                params.setUserid(user.getId());
            }

        }
        PageInfo<Questionsubmit> info = questionsubmitService.findByteachid(params);
        return Result.success(info);
    }
        /**
     * 提交题目
     *

     * @return 提交记录的 id
     */
    @PostMapping("/submit")
    public Result doQuestionSubmit(@RequestBody Questionsubmit questionsubmit) {
        if (questionsubmit == null || questionsubmit.getQuestionid() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = dateformat.format(System.currentTimeMillis());
        try {
            Date date = dateformat.parse(dateString);
            questionsubmit.setCreateTime(date);
            // 现在，'date' 对象包含了转换后的日期值
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long questionSubmitId = questionsubmitService.doQuestionSubmit(questionsubmit);
        log.info("提交成功");



        return Result.success(questionSubmitId);
    }
    @PostMapping("/submit_class")
    public Result doClassSubmit(@RequestBody Questionsubmit questionsubmit) {
        List<String>  judgeInfo = questionsubmitService.runSubmit(questionsubmit);
        if(judgeInfo.get(0)=="编译错误")
        {
            return Result.error("编译错误");
        }
        if(judgeInfo.get(0)=="读取超时")
        {
            return Result.error("读取超时");
        }
        return Result.success(judgeInfo);
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        questionsubmitService.delete(id);
        return Result.success();
    }

    @PostMapping("/addevaluate")
    public Result addevaluate(@RequestBody Evaluate evaluate) {
        log.info("开始添加");
        evaluateService.add(evaluate);
        return Result.success();
    }
    @GetMapping("/getevaluate")
    public Result findevaluate(Params params) {

        List<Evaluate> info = evaluateService.findBySearch(params);

        return Result.success(info);
    }

}
