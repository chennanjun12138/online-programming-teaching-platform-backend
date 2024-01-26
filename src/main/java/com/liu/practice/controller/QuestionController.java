package com.liu.practice.controller;

import com.github.pagehelper.PageInfo;
import com.liu.practice.common.JwtInterceptor;
import com.liu.practice.common.Result;
import com.liu.practice.entity.Params;
import com.liu.practice.entity.Question;
import com.liu.practice.service.QuersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/question")
public class QuestionController {
    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private QuersionService quersionService;

    @GetMapping("/search")
    public Result findBySearch(Params params) {
          log.info("question");
         Question  info = quersionService.findBySearch(params);
        log.info(info.getContent());
        return Result.success(info);
    }

    @PostMapping
    public Result save(@RequestBody Question question) {
        if (question.getId() == null) {

            quersionService.add(question);
        } else {
            quersionService.update(question);
        }
        return Result.success();
    }
    @PostMapping("/add")
    public Result add(@RequestBody Question question) {

        quersionService.add(question);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        quersionService.delete(id);
        return Result.success();
    }
    @PutMapping("/delBatch")
    public Result delBatch(@RequestBody List<Question> list) {
        for (Question question : list) {
            quersionService.delete(question.getId());
        }
        return Result.success();
    }
}
