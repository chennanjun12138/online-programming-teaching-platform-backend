package com.liu.practice.controller;

import com.github.pagehelper.PageInfo;
import com.liu.practice.common.JwtInterceptor;
import com.liu.practice.common.Result;
import com.liu.practice.entity.Params;
import com.liu.practice.entity.Questionbank;
import com.liu.practice.service.QuestionbankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/questionbank")
public class QuestionbankController {
    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private QuestionbankService questionbankService;
    @PostMapping("/update")
    public Result update(Params params)
    {
        log.info(params.getQuestionid());
        log.info("homeworkid:"+params.getHomeworkid().toString());
        questionbankService.change(params.getQuestionid(),params.getHomeworkid().toString());
        return  Result.success();
    }
    @GetMapping("/search")
    public Result findBySearch(Params params) {

        PageInfo<Questionbank> info = questionbankService.findBySearch(params);
        return Result.success(info);
    }
    @GetMapping("/homework")
    public Result findByhomework(Params params) {

        PageInfo<Questionbank> info = questionbankService.findByhomework(params);
        return Result.success(info);
    }
    @GetMapping("/findbyid")
    public Result findByid(Params params) {

        Questionbank  info = questionbankService.findbyid(params);

        return Result.success(info);
    }
    @GetMapping("/findbyquesionid")
    public Result findByquestionid(Params params) {

        List<Questionbank>  info = questionbankService.findbyquestionid(params);

        return Result.success(info);
    }

    @PostMapping
    public Result save(@RequestBody Questionbank questionbank) {
        Params params = new Params();
        params.setContent(questionbank.getQuestionid());

        if (questionbank.getId()== null) {
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = dateformat.format(System.currentTimeMillis());
            questionbank.setCreatetime(dateString);
            if(questionbankService.isJudge(questionbank.getQuestionid()))
            {
                questionbankService.add(questionbank);
            }
            else
            {
                return Result.error("该题号已经存在");
            }

        } else {
            if(questionbankService.findbyid(params)==null)
            {
                return Result.error("禁止修改题号");
            }
            String oldcontent= questionbankService.findbyid(params).getBelongid();
            questionbankService.update(questionbank,oldcontent);
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        questionbankService.delete(id);
        return Result.success();
    }
    @PutMapping("/delBatch")
    public Result delBatch(@RequestBody List<Questionbank> list) {
        for (Questionbank questionbank : list) {
            questionbankService.delete(questionbank.getId());
        }
        return Result.success();
    }
}
