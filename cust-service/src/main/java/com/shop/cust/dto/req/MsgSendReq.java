package com.shop.cust.dto.req;

import java.io.Serializable;

/**
 * 短信发送请求
 * @author xiaowei 2018年4月3日 下午4:29:02
 */
public class MsgSendReq implements Serializable {

    private static final long serialVersionUID = 1L;
    private String            mobile;
    private String            token;
    private String            picId;
    private String            picCode;
    private String            msgType;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public String getPicCode() {
        return picCode;
    }

    public void setPicCode(String picCode) {
        this.picCode = picCode;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    @Override
    public String toString() {
        return "MsgSendReq [mobile=" + mobile + ", token=" + token + ", picId=" + picId
               + ", picCode=" + picCode + ", msgType=" + msgType + "]";
    }

}
