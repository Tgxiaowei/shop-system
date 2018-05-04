package com.shop.base.exception;

import com.shop.base.enums.ReturnCodeEnum;

public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String            code;
    private String            desc;

    public BizException(ReturnCodeEnum ret) {
        super(ret.getDesc());
        this.code = ret.getCode();
        this.desc = ret.getDesc();
    }

    public BizException(String desc) {
        super(desc);
        this.desc = desc;
    }

    public BizException(String code, String desc) {
        super(desc);
        this.code = code;
        this.desc = desc;
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

    @Override
    public String toString() {
        return "BizException [code=" + code + ", desc=" + desc + "]";
    }

}
