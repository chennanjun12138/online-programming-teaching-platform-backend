package com.liu.practice.controller;

import com.github.pagehelper.PageInfo;
import com.liu.practice.common.JwtInterceptor;
import com.liu.practice.common.Result;
import com.liu.practice.entity.Homework;
import com.liu.practice.entity.Params;
import com.liu.practice.entity.Questionbank;
import com.liu.practice.entity.User;
import com.liu.practice.service.QuersionbankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/questionbank")
public class QuestionbankController {
    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private QuersionbankService quersionbankService;
    @PostMapping("/update")
    public Result update(Params params)
    {
        log.info(params.getQuestionid());
        log.info("homeworkid:"+params.getHomeworkid().toString());
        quersionbankService.change(params.getQuestionid(),params.getHomeworkid().toString());
        return  Result.success();
    }
    @GetMapping("/search")
    public Result findBySearch(Params params) {
       // log.info("s1");
        PageInfo<Questionbank> info = quersionbankService.findBySearch(params);
        return Result.success(info);
    }
    @GetMapping("/homework")
    public Result findByhomework(Params params) {

        PageInfo<Questionbank> info = quersionbankService.findByhomework(params);
       // log.info(params.getContent().toString());
        return Result.success(info);
    }
    @GetMapping("/findbyid")
    public Result findByid(Params params) {
        log.info(params.getContent());
        Questionbank  info = quersionbankService.findbyid(params);
        // log.info(params.getContent().toString());
        return Result.success(info);
    }
    @PostMapping
    public Result save(@RequestBody Questionbank questionbank) {
     //  log.info("添加新题"+questionbank.getId().toString());
        if (questionbank.getId()== null) {
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = dateformat.format(System.currentTimeMillis());
            questionbank.setCreatetime(dateString);
            quersionbankService.add(questionbank);
        } else {
            quersionbankService.update(questionbank);
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        quersionbankService.delete(id);
        return Result.success();
    }
    @PutMapping("/delBatch")
    public Result delBatch(@RequestBody List<Questionbank> list) {
        for (Questionbank questionbank : list) {
            quersionbankService.delete(questionbank.getId());
        }
        return Result.success();
    }
}
