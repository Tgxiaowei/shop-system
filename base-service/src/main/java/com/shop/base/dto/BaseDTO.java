package com.shop.base.dto;

import java.io.Serializable;

/**
 * BaseDTO，提供默认的分页属性
 * @author xiaowei 2018年3月30日 下午5:47:33
 */
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    public Integer            pageNum;

    public Integer            pageSize;

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
        return "BaseDTO [pageNum=" + pageNum + ", pageSize=" + pageSize + "]";
    }

}
