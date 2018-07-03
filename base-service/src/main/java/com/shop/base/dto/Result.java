package com.shop.base.dto;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.shop.base.enums.ReturnCodeEnum;

public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String            code;
    private String            desc;
    private T                 data;

    public Result() {
        this(ReturnCodeEnum.SUCCESS, null);
    }

    public Result(T data) {
        this(ReturnCodeEnum.SUCCESS, data);
    }

    public Result(String code, String desc) {
        this(code, desc, null);
    }

    public Result(ReturnCodeEnum ret, T data) {
        this.code = ret.getCode();
        this.desc = ret.getDesc();
        this.data = data;
    }

    public Result(String code, String desc, T data) {
        this.code = code;
        this.desc = desc;
        this.data = data;
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> fail(T data) {
        return new Result<>(ReturnCodeEnum.FAIL, data);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
