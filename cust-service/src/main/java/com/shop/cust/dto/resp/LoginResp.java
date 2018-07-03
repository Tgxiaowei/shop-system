package com.shop.cust.dto.resp;

import com.shop.base.dto.BaseDTO;

public class LoginResp extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private String            token;
    private String            name;

    public LoginResp() {

    }

    public LoginResp(String token, String name) {
        this.token = token;
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
