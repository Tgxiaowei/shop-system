package com.shop.base.enums;

import com.shop.base.constant.DataDict;
import com.shop.base.exception.BizException;

public enum MsgTypeEnum {

    REGISTSER(1, DataDict.REGISTER, "注册短信"),

    LOGIN(2, DataDict.LOGIN, "登陆短信"),

    RESET_LOGIN_PSW(3, DataDict.RESET_LOGIN_PSW, "重置登录密码短信"),

    RESET_PAY_PSW(4, DataDict.RESET_PAY_PSW, "重置交易密码短信"),

    ;

    private int    index;
    private String code;
    private String desc;

    private MsgTypeEnum(int index, String code, String desc) {
        this.index = index;
        this.code = code;
        this.desc = desc;
    }

    public static final MsgTypeEnum getEnumByIndex(int index) {
        for (MsgTypeEnum msgTypeEnum : MsgTypeEnum.values()) {
            if (msgTypeEnum.index == index) {
                return msgTypeEnum;
            }
        }
        throw new BizException("错误的短信类型！");
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
