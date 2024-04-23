package com.liu.practice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.liu.practice.common.JwtInterceptor;
import com.liu.practice.common.Result;
import com.liu.practice.entity.*;
import com.liu.practice.exception.CustomException;
import com.liu.practice.service.HomeworkService;
import com.liu.practice.service.UserService;
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
    @Resource
    private UserService userService;
    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        log.info("查询的教师"+params.getTeacher());
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
    @GetMapping("/findsubmit")
    public Result findsubmit(Params params)
    {
        PageInfo<Submit> info = homeworkService.findsubmits(params);
        return Result.success(info);
    }
    @GetMapping("/findbystudent")
    public Result findbystudent(Params params)
    {
        PageInfo<Submit> info = homeworkService.findbystudent(params);
        return Result.success(info);
    }
    @PostMapping
    public Result save(@RequestBody Homework book) {
        User user=userService.findByname(book.getTeacher());
        if(user==null)
        {
            return Result.error("该作者不存在");
        }
        if(!"ROLE_TEACHER".equals(user.getRole()))
        {
            return Result.error("该作者不是教师");
        }
        String  values = book.getContent().substring(1, book.getContent().length() - 1);
        String[] valueArray = values.split(",");
        List<String> newlist = new ArrayList<>();
        for (String value : valueArray) {
            newlist.add(value.trim());
        }
        for(String element:newlist)
        {
            if(!element.equals(""))
            {
                Questionbank ans=homeworkService.findQuestionbank(element);
                if(ans==null)
                {
                    return Result.error("有题目不存在");
                }
            }
        }
        String oldcontent;
         if (book.getId() == null) {
             oldcontent="[]";
            homeworkService.add(book);
            book.setHomeworkid(book.getId().toString());
            homeworkService.update(book);
        } else {
             oldcontent=homeworkService.findbyid(book.getId()).getContent();
             homeworkService.update(book);
        }
         log.info("oldcontent"+oldcontent);
         log.info("newcoent"+book.getContent());
         homeworkService.changeblongid(book.getContent(),book.getHomeworkid(),oldcontent);
        return Result.success();
    }
    @PostMapping("/changesubmit")
    public  Result changesubmit(@RequestBody Submit submit)
    {
        homeworkService.changesubmit(submit);
        return Result.success();
    }
    @PostMapping("/addsubmit")
    public  Result addsubmit(@RequestBody Submit submit)
    {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = dateformat.format(System.currentTimeMillis());
        submit.setSubmittime(dateString);
        homeworkService.addsubmit(submit);
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
