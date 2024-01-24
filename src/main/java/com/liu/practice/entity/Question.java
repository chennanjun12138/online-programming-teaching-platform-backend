package com.liu.practice.entity;

import javax.persistence.*;

@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "content")
    private String content;
    @Column(name = "input")
    private String input;
    @Column(name = "output")
    private String output;
    @Column(name="condition")
    private String condition;
    @Column(name = "exampleout")
    private String exampleout;
    @Column(name = "examplein")
    private String examplein;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getExampleout() {
        return exampleout;
    }

    public void setExampleout(String exampleout) {
        this.exampleout = exampleout;
    }

    public String getExamplein() {
        return examplein;
    }

    public void setExamplein(String examplein) {
        this.examplein = examplein;
    }
}
