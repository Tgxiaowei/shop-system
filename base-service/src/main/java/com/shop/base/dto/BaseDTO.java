package com.shop.base.dto;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * BaseDTO，提供默认的分页属性
 * @author xiaowei 2018年3月30日 下午5:47:33
 */
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 默认不分页
    public Integer            pageNum;

    public Integer            pageSize;

    public Integer getPageNum() {
        return this.pageNum = pageNum == null || pageNum == 0 ? 1 : pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize == null || pageSize == 0 ? Integer.MAX_VALUE : pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
