package com.shop.base.enums;

public enum CustStatusEnum {

    NORMAL(0, "normal", "正常"),

    FREEZE(1, "freeze", "冻结"),

    BYEBYE(9, "byebye", "永久冻结"),

    ;
    private int    index;
    private String code;
    private String desc;

    private CustStatusEnum(int index, String code, String desc) {
        this.index = index;
        this.code = code;
        this.desc = desc;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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
