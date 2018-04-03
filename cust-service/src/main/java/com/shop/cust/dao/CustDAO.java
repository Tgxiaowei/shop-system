package com.shop.cust.dao;

import java.util.Date;

import com.shop.base.dao.BaseDAO;

public class CustDAO extends BaseDAO {

    private static final long serialVersionUID = 1L;

    private Long              id;
    private String            mobile;
    private String            name;
    private String            sex;
    private String            loginPsw;
    private String            payPsw;
    private String            mail;
    private Integer           status;
    private Date              gmt_create;
    private Date              gmt_modified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(Date gmt_create) {
        this.gmt_create = gmt_create;
    }

    public Date getGmt_modified() {
        return gmt_modified;
    }

    public void setGmt_modified(Date gmt_modified) {
        this.gmt_modified = gmt_modified;
    }

    public String getLoginPsw() {
        return loginPsw;
    }

    public void setLoginPsw(String loginPsw) {
        this.loginPsw = loginPsw;
    }

    public String getPayPsw() {
        return payPsw;
    }

    public void setPayPsw(String payPsw) {
        this.payPsw = payPsw;
    }

    @Override
    public String toString() {
        return "CustDAO [id=" + id + ", mobile=" + mobile + ", name=" + name + ", sex=" + sex
               + ", loginPsw=" + loginPsw + ", payPsw=" + payPsw + ", mail=" + mail + ", status="
               + status + ", gmt_create=" + gmt_create + ", gmt_modified=" + gmt_modified + "]";
    }

}
