package com.shop.cust.dto.req;

import javax.validation.constraints.NotNull;

import com.shop.base.dto.BaseDTO;

/**
 * 登陆请求对象
 * @author xiaowei 2018年4月2日 上午10:31:52
 */
public class LoginReq extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "手机号不能为空！")
    private String            mobile;
    private String            psw;
    private String            msgCode;

    public LoginReq() {
    }

    public LoginReq(String mobile, String psw) {
        this.mobile = mobile;
        this.psw = psw;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

}
