package com.shop.cust.dao;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import com.shop.base.dao.BaseDAO;

@Table(name = "t_cust_customer")
public class CustDAO extends BaseDAO {

    private static final long serialVersionUID = 1L;
    @Id
    //    @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(generator = "JDBC")
    private Long              id;
    /** 客户号 */
    private String            custNo;
    /** 手机号 */
    @Length(min = 11, max = 11, message = "请输入正确的手机号码！")
    private String            mobile;
    /** 姓名 */
    private String            name;
    /** 性别 */
    private String            sex;
    /** 登录密码 */
    private String            loginPsw;
    /** 交易密码 */
    private String            payPsw;
    /** 邮箱 */
    private String            email;
    /** 客户状态 */
    private Integer           status;
    private Date              gmt_create;
    private Date              gmt_modified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

}
