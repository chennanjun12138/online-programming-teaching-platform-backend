package com.liu.practice.entity;


import javax.persistence.*;

@Table(name = "evaluate")
public class Evaluate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "question_submitid")
    private Integer question_submitid;
    @Column(name = "teacherid")
    private Integer teacherid;
    @Column(name = "teachername")
    private String teachername;
    @Column(name="content")
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestion_submitid() {
        return question_submitid;
    }

    public void setQuestion_submitid(Integer question_submitid) {
        this.question_submitid = question_submitid;
    }

    public Integer getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(Integer teacherid) {
        this.teacherid = teacherid;
    }

    public String getTeachername() {
        return teachername;
    }

    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
