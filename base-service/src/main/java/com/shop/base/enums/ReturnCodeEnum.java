package com.shop.base.enums;

public enum ReturnCodeEnum {

    SUCCESS("00", "成功"),

    FAIL("01", "失败"),

    ;

    private String code;
    private String desc;

    private ReturnCodeEnum(String code, String desc) {
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

}
