package com.liu.practice.entity;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.persistence.*;

/**
 * 题目提交
 * @TableName question_submit
 */
@Table(name ="question_submit")
@Data
public class Questionsubmit  {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 编程语言
     */
    @Column(name = "language")
    private String language;

    /**
     * 用户代码
     */
    @Column(name = "code")
    private String code;

    /**
     * 判题信息（json 对象）
     */
    @Column(name = "judgeInfo")
    private String judgeInfo;

    /**
     * 判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 题目 id
     */
    @Column(name = "questionid")
    private Long questionid;

    /**
     * 创建用户 id
     */
    @Column(name = "userid")
    private Long userid;

    /**
     * 创建时间
     */
    @Column(name = "createTime")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "updateTime")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getJudgeInfo() {
        return judgeInfo;
    }

    public void setJudgeInfo(String judgeInfo) {
        this.judgeInfo = judgeInfo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getQuestionid() {
        return questionid;
    }

    public void setQuestionid(Long questionid) {
        this.questionid = questionid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
