package com.shop.cust.dto.req;

import javax.validation.constraints.NotNull;

import com.shop.base.dto.BaseDTO;

public class RegisterReq extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String            mobile;
    @NotNull
    private String            msgCode;
    @NotNull
    private String            psw;
    @NotNull
    private String            pswRept;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getPswRept() {
        return pswRept;
    }

    public void setPswRept(String pswRept) {
        this.pswRept = pswRept;
    }

}
