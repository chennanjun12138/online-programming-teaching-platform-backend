package com.liu.practice.controller;

import com.github.pagehelper.PageInfo;
import com.liu.practice.common.JwtInterceptor;
import com.liu.practice.common.Result;
import com.liu.practice.entity.Class;
import com.liu.practice.entity.Connect;
import com.liu.practice.entity.Params;
import com.liu.practice.entity.User;
import com.liu.practice.service.ConnectService;
import com.liu.practice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static cn.hutool.core.lang.Console.log;

@CrossOrigin
@RestController
@RequestMapping("/connect")
public class ConnectController {
    private static final Logger log  = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private ConnectService connectService;
    @Resource
    private UserService userService;
    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<Connect> info = connectService.findBySearch(params);
        return Result.success(info);
    }
    @GetMapping("/findteachers")
    public Result findteachers(Params params)
    {
        List<String> list=connectService.findteachers(params);
        return Result.success(list);
    }
    @PostMapping
    public Result add(@RequestBody Connect connect) {
        log.info(connect.getStudentid());
        Params params=new Params();
        params.setStudentid(connect.getStudentid());
        params.setTeachername(connect.getTeachername());
        params.setClassid(Integer.parseInt(connect.getClassid()));


        Integer flag=0;
        if(connectService.findjudge(params))
        {

             User teacher=userService.findByname(connect.getTeachername());
             if(teacher!=null)
             {   flag=1;
                 connect.setTeacherid(teacher.getId().toString());
                 connectService.add(connect);
             }
        }
        else
        {
            flag=2;
        }
        if(flag==1)
        {
            return Result.success();
        }
        else if(flag==2)
        {
            return Result.error("该关系已存在");
        }
        else
        {
            return Result.error("添加关系失败，该教师已退出平台");
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        connectService.delete(id);
        return Result.success();
    }

}
