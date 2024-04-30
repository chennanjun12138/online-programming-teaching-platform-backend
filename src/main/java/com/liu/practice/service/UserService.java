package com.liu.practice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.practice.common.JwtInterceptor;
import com.liu.practice.common.JwtTokenUtils;
import com.liu.practice.dao.*;
import com.liu.practice.entity.*;
import com.liu.practice.entity.Class;
import com.liu.practice.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sun.nio.cs.MS1250;

import javax.annotation.Resource;
import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private UserDao userDao;
    @Resource
    private ConnectDao connectDao;
    @Resource
    private NotebookDao notebookDao;
    @Resource
    private EvaluateDao evaluateDao;
    @Resource
    private SubmitDao submitDao;
    @Resource
    private MessageDao messageDao;
    @Resource
    private ClassDao classDao;
    @Resource
    private HomeworkDao homeworkDao;
    @Resource
    private QuestionbankDao questionbankDao;
    public List<User> getAll() {
        //return adminDao.getUser();
        // 3. 使用引入的包
         return userDao.selectAll();
    }
    public PageInfo<User> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
         // 接下来的查询会自动按照当前开启的分页设置来查询
         List<User> list = userDao.findBySearch(params);
        return PageInfo.of(list);
    }
    public void add(User admin) {
        // 1. 用户名一定要有，否则不让新增（后面需要用户名登录）
        if (admin.getName() == null || "".equals(admin.getName())) {
             throw new CustomException("用户名不能为空");
        }
        // 2. 进行重复性判断，同一名字的管理员不允许重复新增：只要根据用户名去数据库查询一下就可以了
        User user = userDao.findByName(admin.getName());
        if (user != null) {
            // 说明已经有了，这里我们就要提示前台不允许新增了
            throw new CustomException("该用户名已存在，请更换用户名");
        }
        // 初始化一个密码
        if (admin.getPassword() == null) {
            admin.setPassword("123456");
        }
        // 创建bcrypt密码编码器
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 对密码进行bcrypt加密
        String encryptedPassword = passwordEncoder.encode(admin.getPassword());

        // 更新用户对象的密码为加密后的密码
        admin.setPassword(encryptedPassword);
        userDao.insertSelective(admin);
    }

    public void update(User user) {
        User OldUser=userDao.findUser(user.getId());
        if(OldUser.getName()!=user.getName()) {
            if("ROLE_STUDENT".equals(user.getRole()))
            {
                Params params=new Params();
                params.setStudentid(OldUser.getId().toString());
                List<Connect> connects=connectDao.findBySearch(params);
                if(connects!= null && connects.size() > 0)
                {
                    for(Connect c:connects)
                    {
                        c.setStudentname(user.getName());
                        connectDao.updateByPrimaryKeySelective(c);
                    }
                }
                params.setUserid(OldUser.getId());
                List<Submit> submits=  submitDao.findbystudent(params);
                if(submits != null && submits.size() > 0)
                {
                    for (Submit submit:submits)
                    {
                        submit.setStudentname(user.getName());
                        submitDao.updateByPrimaryKeySelective(submit);
                    }
                }

            }
            else if("ROLE_TEACHER".equals(user.getRole()))
            {
                Params params=new Params();
                params.setAuthor(OldUser.getName());
                List<Class> classes=classDao.findBySearch(params);
                if(classes != null && classes.size() > 0)
                {
                    for (Class c:classes)
                    {
                        c.setAuthor(user.getName());
                        classDao.updateByPrimaryKeySelective(c);
                    }
                }
                Params params2=new Params();
                params2.setTeachername(OldUser.getName());
                List<Connect> connects=connectDao.findBySearch(params2);
                if(connects!= null && connects.size() > 0)
                {
                    for(Connect c:connects)
                    {
                        c.setTeachername(user.getName());
                        connectDao.updateByPrimaryKeySelective(c);
                    }
                }
                List<Evaluate> evaluates=evaluateDao.findByTeacher(OldUser.getId());
                if(evaluates!= null && evaluates.size() > 0)
                {
                    for(Evaluate e:evaluates)
                    {
                        e.setTeachername(user.getName());
                        evaluateDao.updateByPrimaryKeySelective(e);
                    }
                }
                Params params3=new Params();
                params3.setTeacher(OldUser.getName());
                List<Homework> homeworks=homeworkDao.findBySearch(params3);
                if(homeworks!= null && homeworks.size() > 0)
                {
                    for(Homework e:homeworks)
                    {
                        e.setTeacher(user.getName());
                        homeworkDao.updateByPrimaryKeySelective(e);
                    }
                }
                Params params4=new Params();
                params4.setCreator(OldUser.getName());
                List<Questionbank>questionbanks=questionbankDao.findBySearch(params4);
                if(questionbanks!= null && questionbanks.size() > 0)
                {
                    for(Questionbank e:questionbanks)
                    {
                        e.setCreator(user.getName());
                        questionbankDao.updateByPrimaryKeySelective(e);
                    }
                }
            }
            Params params=new Params();
            params.setSendname(OldUser.getName());
            List<Message> messages=messageDao.findBySearch(params);
            if(messages != null && messages.size() > 0)
            {
                for(Message message:messages)
                {
                    message.setSendname(user.getName());
                    messageDao.updateByPrimaryKeySelective(message);
                }
            }
            Params params2=new Params();
            params2.setAcceptname(OldUser.getName());
            List<Message> messages2=messageDao.findBySearch(params2);
            if(messages2 != null && messages2.size() > 0)
            {
                for(Message message:messages2)
                {
                    message.setAcceptname(user.getName());
                    messageDao.updateByPrimaryKeySelective(message);
                }
            }

        }
        userDao.updateByPrimaryKeySelective(user);
    }
    public void updatePassword(UserVo userVo) {
        User user = userDao.findByName(userVo.getName());

        if (userVo.getPasswordOld()== null ||"".equals(userVo.getPasswordOld())) {
            throw new CustomException("原密码不能为空");
        }
        if (userVo.getPasswordNew()== null ||"".equals(userVo.getPasswordNew())) {
            throw new CustomException("新密码不能为空");
        }
        if (userVo.getPasswordSure()== null ||"".equals(userVo.getPasswordSure())) {
            throw new CustomException("确认密码不能为空");
        }
        if(!userVo.getPasswordNew().equals(userVo.getPasswordSure()))
        {
            throw new CustomException("新密码和确认密码不一致");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(!passwordEncoder.matches(userVo.getPasswordOld(),user.getPassword()))
        {
            throw new CustomException("原密码输入错误");
        }
        String encryptedPassword = passwordEncoder.encode(userVo.getPasswordNew());
        user.setPassword(encryptedPassword);
        userDao.updateByPrimaryKeySelective(user);
    }
    public void delete(Integer id) {

        User user=userDao.findUser(id);
        log.info(user.getRole());
        if("ROLE_STUDENT".equals(user.getRole()))
        {
            connectDao.deleteByStudentId(id);
            notebookDao.deleteByStudentId(id);
        }
        else if("ROLE_TEACHER".equals(user.getRole()))
        {
            connectDao.deleteByTeacherId(id);
            evaluateDao.deletebyteacherid(id);
        }
         userDao.deleteByPrimaryKey(id);
    }
    public User login(User admin) {
        // 1. 进行一些非空判断
        if (admin.getName() == null || "".equals(admin.getName())) {
            throw new CustomException("用户名不能为空");
        }
        if (admin.getPassword() == null || "".equals(admin.getPassword())) {
            throw new CustomException("密码不能为空");
        }
        // 2. 从数据库里面根据这个用户名和密码去查询对应的管理员信息，
        User user = userDao.findByName(admin.getName());
        if (user == null) {
            // 如果查出来没有，那说明输入的用户名，提示用户，不允许登录
            throw new CustomException("用户名输入错误");
        }
        // 创建bcrypt密码编码器
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(!passwordEncoder.matches(admin.getPassword(),user.getPassword()))
        {
            throw new CustomException("密码输入错误");
        }
        // 如果查出来了有，那说明确实有这个管理员，而且输入的用户名和密码都对；
        //生成该登录用户对应的token,然后跟着user一起返回到前台
       String token= JwtTokenUtils.genToken(user.getId().toString(),user.getPassword());
        user.setToken(token);
        return user;
    }
    public User findById(Integer id)
    {
        return userDao.selectByPrimaryKey(id);
    }
    public User findByname(String name){
        return userDao.findByName(name);
    }
}
