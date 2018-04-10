package com.shop.base.enums;

import com.shop.base.constant.DataDict;
import com.shop.base.exception.BizException;

public enum MsgSendEnum {

    REGISTSER(1, DataDict.register, "注册短信"),

    LOGIN(2, DataDict.login, "登陆短信"),

    RESET_LOGIN_PSW(3, DataDict.resetLoginPsw, "重置登录密码短信"),

    RESET_PAY_PSW(4, DataDict.resetPayPsw, "重置交易密码短信"),

    ;

    private int    index;
    private String code;
    private String desc;

    private MsgSendEnum(int index, String code, String desc) {
        this.index = index;
        this.code = code;
        this.desc = desc;
    }

    public static final MsgSendEnum getEnumByIndex(int index) {
        for (MsgSendEnum msgSendEnum : MsgSendEnum.values()) {
            if (msgSendEnum.index == index) {
                return msgSendEnum;
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
