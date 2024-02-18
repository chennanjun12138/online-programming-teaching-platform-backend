package com.liu.practice.controller;

import com.github.pagehelper.PageInfo;
import com.liu.practice.common.JwtInterceptor;
import com.liu.practice.common.Result;
import com.liu.practice.entity.*;
import com.liu.practice.entity.Class;
import com.liu.practice.service.ClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/class")
public class ClassController {
    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private ClassService classService;

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<Class> info = classService.findBySearch(params);
        return Result.success(info);
    }
    @GetMapping("/findall")
    public Result findall(Params params) {
        List<Class> info = classService.findall(params);
        return Result.success(info);
    }
    @GetMapping("/getcourse")
    public Result findcourse(Params params) {
        Course info = classService.findcourse(params);
        return Result.success(info);
    }
    @GetMapping("/getcontract")
    public Result findcontract(Params params) {
        List<Contract> info = classService.findcontract(params);
        return Result.success(info);
    }
    @PostMapping
    public Result save(@RequestBody  Class book) {
        if (book.getId() == null) {
            classService.add(book);

        } else {
            classService.update(book);
        }
        return Result.success();
    }

    @PostMapping("/course")
    public Result savecourse(@RequestBody Course coures)
    {
        if (coures.getId() == null) {
            classService.addcourse(coures);
        }
        else
        {
            classService.updatecourse(coures);
        }
        return Result.success();
    }
    @PostMapping("/contract")
    public Result addcontract(@RequestBody Contract contract)
    {
            Integer res=0;
           if(classService.judgecontract(contract.getClassid(),contract.getQuestionid()))
           {
               classService.addcontract(contract);
               res=1;
           }
            return Result.success(res);
    }
    @PostMapping("/judge")
    public Result judgecontact(@RequestBody Contract contract)
    {
        Integer res=0;
        log.info(contract.getQuestionid().toString());
        if(classService.judgecontract(contract.getClassid(),contract.getQuestionid()))
        {
              res=contract.getQuestionid();
        }
        return Result.success(res);
    }
    @PutMapping("/addBatch")
    public Result addBatch(@RequestBody List<Contract> list) {
        for (Contract cl : list) {
            if(classService.judgecontract(cl.getClassid(),cl.getQuestionid()))
            {
                log.info("课程："+cl.getClassid().toString());
                log.info("题号："+cl.getQuestionid().toString());
                classService.addcontract(cl);
            }
        }
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        classService.delete(id);
        return Result.success();
    }
    @PutMapping("/delBatch")
    public Result delBatch(@RequestBody List<Class> list) {
        for (Class cl : list) {
            classService.delete(cl.getId());
        }
        return Result.success();
    }
}
