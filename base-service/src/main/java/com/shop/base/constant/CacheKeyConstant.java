package com.shop.base.constant;

public class CacheKeyConstant {

    public static final String CUST_PREFIX   = "cust|";
    public static final String TRANS_PREFIX  = "trans|";

    /** 注册短信验证码缓存 */
    public static final String registMsg     = CUST_PREFIX + DataDict.regist;
    /** 登陆短信验证码缓存 */
    public static final String loginMsg      = CUST_PREFIX + DataDict.login;
    /** 重置登录密码短信验证码缓存 */
    public static final String resetLoginPsw = CUST_PREFIX + DataDict.resetLoginPsw;
    /** 重置交易密码短信验证码缓 */
    public static final String resetPayPsw   = CUST_PREFIX + DataDict.resetPayPsw;

}
