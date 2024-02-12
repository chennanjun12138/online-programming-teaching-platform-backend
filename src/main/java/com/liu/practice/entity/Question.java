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
    @Column(name="judgeConfig")
    private String judgeConfig;

    @Column(name = "answer")
    private String answer;
    @Column(name = "judgeCase")
    private String judgeCase;

    public String getJudgeCase() {
        return judgeCase;
    }

    public void setJudgeCase(String judgeCase) {
        this.judgeCase = judgeCase;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

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

    public String getJudgeConfig() {
        return judgeConfig;
    }

    public void setJudgeConfig(String judgeConfig) {
        this.judgeConfig = judgeConfig;
    }


}
