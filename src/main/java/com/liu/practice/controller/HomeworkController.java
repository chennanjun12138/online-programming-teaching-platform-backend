package com.liu.practice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.liu.practice.common.JwtInterceptor;
import com.liu.practice.common.Result;
import com.liu.practice.entity.Homework;
import com.liu.practice.entity.Params;
import com.liu.practice.service.HomeworkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/homework")
public class HomeworkController {
    @Resource
    private HomeworkService homeworkService;
    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        log.info(params.getTeacher());
        PageInfo<Homework> info = homeworkService.findBySearch(params);
        return Result.success(info);
    }
    @GetMapping("/findteachers")
    public Result findteachers(Params params)
    {
        log.info(params.getTeacher());
        PageInfo<Homework> info = homeworkService.findByteacher(params);
        return Result.success(info);
    }
    @PostMapping
    public Result save(@RequestBody Homework book) {

        if (book.getId() == null) {
            String sdateString = book.getStarttime().substring(0, 10);
            String edateString =  book.getEndtime().substring(0, 10);
            book.setStarttime(sdateString);
            book.setEndtime(edateString);
            homeworkService.add(book);
            book.setHomeworkid(book.getId().toString());
            homeworkService.update(book);
        } else {
            homeworkService.update(book);
        }
        return Result.success();
    }
    @PostMapping("/update")
    public Result update(@RequestBody Homework work)
    {
        log.info(work.getContent());
        homeworkService.update(work);
        return  Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        homeworkService.delete(id);
        return Result.success();
    }
    @PutMapping("/delBatch")
    public Result delBatch(@RequestBody List<Homework> list) {
        for (Homework cl : list) {
            homeworkService.delete(cl.getId());
        }
        return Result.success();
    }
}
