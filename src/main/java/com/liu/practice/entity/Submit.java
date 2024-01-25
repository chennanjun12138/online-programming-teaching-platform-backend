package com.liu.practice.entity;

import javax.persistence.*;

@Table(name = "submit")
public class Submit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "userid")
    private Integer userid;
    @Column(name = "studentname")
    private String studentname;
    @Column(name = "homeworkid")
    private Integer homeworkid;
    @Column(name = "submittime")
    private String submittime;
    @Column(name = "score")
    private String score;
    @Column(name = "teacherevaluate")
    private String teacherevaluate;
    @Column(name = "content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public Integer getHomeworkid() {
        return homeworkid;
    }

    public void setHomeworkid(Integer homeworkid) {
        this.homeworkid = homeworkid;
    }

    public String getSubmittime() {
        return submittime;
    }

    public void setSubmittime(String submittime) {
        this.submittime = submittime;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTeacherevaluate() {
        return teacherevaluate;
    }

    public void setTeacherevaluate(String teacherevaluate) {
        this.teacherevaluate = teacherevaluate;
    }
}
