package com.liu.practice.entity;


import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "questionbank")
public class Questionbank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "questionid")
    private String questionid;
    @Column(name = "name")
    private String name;
    @Column(name = "creator")
    private String creator;
    @Column(name = "type")
    private String type;
    @Column(name = "description")
    private String description;
    @Column(name = "createtime")
    private String createtime;
    @Column(name = "belongid")
    private Integer belongid;

    public String getQuestionid() {
        return questionid;
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Integer getBelongid() {
        return belongid;
    }

    public void setBelongid(Integer belongid) {
        this.belongid = belongid;
    }
}
