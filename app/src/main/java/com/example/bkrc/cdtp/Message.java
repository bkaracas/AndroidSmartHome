package com.example.bkrc.cdtp;

import java.io.Serializable;

public class Message implements Serializable {

    public String sender;
    public String receiver;
    public String mes;
    public String hour;
    public String min;

    public Message(String mes) {
        this.mes = mes;
    }

    public Message(String receiver , String sender, String mes) {
        this.sender = sender;
        this.receiver = receiver;
        this.mes = mes;
    }

    public Message(String sender, String receiver, String mes, String hour, String min) {
        this.sender = sender;
        this.receiver = receiver;
        this.mes = mes;
        this.hour = hour;
        this.min = min;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }
}
