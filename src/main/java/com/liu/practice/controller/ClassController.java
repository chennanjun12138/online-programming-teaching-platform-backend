package com.liu.practice.controller;

import com.github.pagehelper.PageInfo;
import com.liu.practice.common.JwtInterceptor;
import com.liu.practice.common.Result;
import com.liu.practice.entity.Class;
import com.liu.practice.entity.Params;
import com.liu.practice.service.ClassService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/class")
public class ClassController {
    @Resource
    private ClassService classService;

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<Class> info = classService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping
    public Result save(@RequestBody Class book) {
        if (book.getId() == null) {
            classService.add(book);
        } else {
            classService.update(book);
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        classService.delete(id);
        return Result.success();
    }
}
