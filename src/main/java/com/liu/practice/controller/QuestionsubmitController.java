package com.liu.practice.controller;

import com.github.pagehelper.PageInfo;
import com.liu.practice.common.JwtInterceptor;
import com.liu.practice.common.Result;
import com.liu.practice.entity.Params;
import com.liu.practice.entity.Questionbank;
import com.liu.practice.entity.Questionsubmit;
import com.liu.practice.judge.JudgeService;
import com.liu.practice.service.QuestionsubmitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/question_submit")
public class QuestionsubmitController {
    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private QuestionsubmitService questionsubmitService;
    @Resource
    private JudgeService judgeService;


    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<Questionsubmit> info = questionsubmitService.findBySearch(params);
        return Result.success(info);
    }
}
