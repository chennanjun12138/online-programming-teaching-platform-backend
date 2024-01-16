package com.liu.practice.controller;

import com.github.pagehelper.PageInfo;
import com.liu.practice.common.Result;
import com.liu.practice.entity.Params;
import com.liu.practice.entity.Questionbank;
import com.liu.practice.entity.User;
import com.liu.practice.service.QuersionbankService;
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
    @Resource
    private QuersionbankService quersionbankService;

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<Questionbank> info = quersionbankService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping
    public Result save(@RequestBody Questionbank questionbank) {
        if (questionbank.getId() == null) {
            //long currentTimeMillis = System.currentTimeMillis();
           // Date date=new Date(currentTimeMillis);
            //String dateString=date.toString();
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
