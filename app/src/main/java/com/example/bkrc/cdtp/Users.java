package com.example.bkrc.cdtp;

import java.io.Serializable;

public class Users implements Serializable {

    public String name;
    public String password;
    public String realname;
    public String surname;
    public int watt;
    public int id;

    public Users(){}

    public Users(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Users(String name,String password,int watt){
        this.name=name;
        this.password=password;
        this.watt=watt;

    }

    public Users(String name, String password, String realname, String surname, int watt, int id) {
        this.name = name;
        this.password = password;
        this.realname = realname;
        this.surname = surname;
        this.watt = watt;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public int getWatt() {
        return watt;
    }

    public void setWatt(int watt) {
        this.watt = watt;
    }

}
