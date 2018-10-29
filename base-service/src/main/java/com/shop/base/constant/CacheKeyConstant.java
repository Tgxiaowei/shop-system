package com.shop.base.constant;

public class CacheKeyConstant {

    public static final String CUST_PREFIX     = "cust|";
    public static final String TRANS_PREFIX    = "trans|";

    /** token缓存     key = this+mobile */
    public static final String TOKEN           = CUST_PREFIX + DataDict.TOKEN;
    /** 注册短信验证码缓存     key = this+mobile */
    public static final String REGIST_MSG      = CUST_PREFIX + DataDict.REGISTER;
    /** 登陆短信验证码缓存     key = this+mobile */
    public static final String LOGIN_MSG       = CUST_PREFIX + DataDict.LOGIN;
    /** 重置登录密码短信验证码缓存     key = this+mobile */
    public static final String RESET_LOGIN_PSW = CUST_PREFIX + DataDict.RESET_LOGIN_PSW;
    /** 重置交易密码短信验证码缓     key = this+mobile */
    public static final String RESET_PAY_PSW   = CUST_PREFIX + DataDict.RESET_PAY_PSW;

}
