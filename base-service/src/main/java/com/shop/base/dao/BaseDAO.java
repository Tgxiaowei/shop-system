package com.shop.base.dao;

import java.io.Serializable;

import javax.persistence.Transient;

import com.alibaba.fastjson.JSON;

/**
 * BaseDAO
 * @author xiaowei 2018年3月30日 下午5:44:47
 */
public class BaseDAO implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Transient 表示该属性并非一个到数据库表的字段的映射,ORM框架将忽略该属性
    @Transient
    private Integer           pageNum;
    @Transient
    private Integer           pageSize;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
