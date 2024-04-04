package com.liu.practice.entity;

import lombok.Data;

import javax.persistence.*;

@Data
public class UserVo  {


    private String name;
    private String passwordOld;
    private String passwordNew;
    private String passwordSure;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordOld() {
        return passwordOld;
    }

    public void setPasswordOld(String passwordOld) {
        this.passwordOld = passwordOld;
    }

    public String getPasswordNew() {
        return passwordNew;
    }

    public void setPasswordNew(String passwordNew) {
        this.passwordNew = passwordNew;
    }

    public String getPasswordSure() {
        return passwordSure;
    }

    public void setPasswordSure(String passwordSure) {
        this.passwordSure = passwordSure;
    }
}
