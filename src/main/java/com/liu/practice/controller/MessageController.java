package com.liu.practice.controller;

import com.liu.practice.common.JwtInterceptor;
import com.liu.practice.common.Result;
import com.liu.practice.entity.Message;
import com.liu.practice.entity.Params;
import com.liu.practice.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/message")
public class MessageController {
    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private MessageService messageService;

    @GetMapping("/search")
    public Result findBySearch(Params params) {

         List<Message>  info = messageService.findBySearch(params);

        return Result.success(info);
    }
    @GetMapping("/getAll")
    public Result getAll() {

        List<Message>  info = messageService.getAll();

        return Result.success(info);
    }
    @PostMapping
    public Result save(@RequestBody List<Message> list) {
        for(Message message:list)
        {
            if (message.getId() == null) {
                messageService.add(message);
            } else {
                messageService.update(message);
            }
        }
        return Result.success();
    }
    @PostMapping("/add")
    public Result add(@RequestBody Message message) {
        messageService.add(message);
        return Result.success();
    }
    @PostMapping("/addBatch")
    public Result addBatch(@RequestBody List<Message> list) {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = dateformat.format(System.currentTimeMillis());

        for (Message message : list) {
            log.info(message.getContent());
            message.setSendtime(dateString);
            messageService.add(message);
        }
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        messageService.delete(id);
        return Result.success();
    }

}
