package com.vito.base.jsonbean;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

public class VitoJsonTemplateBean<T> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @JsonProperty("code")
    private int code;

    @JsonProperty("msg")
    private String msg;

    @JsonProperty("dateTime")
    private String dateTime;

    @JsonProperty("data")
    private T data;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
