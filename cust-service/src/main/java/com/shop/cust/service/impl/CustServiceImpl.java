package com.shop.cust.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.base.enums.CustStatusEnum;
import com.shop.base.util.Assert;
import com.shop.cust.dao.CustDAO;
import com.shop.cust.mapper.CustMapper;
import com.shop.cust.service.CustService;

@Service("custService")
public class CustServiceImpl implements CustService {

    @Autowired
    private CustMapper custMapper;

    @Override
    public CustDAO selectCustByMobile(String mobile) {

        Assert.notEmpty(mobile, "查询失败，手机号码不能为空！");

        CustDAO cust = new CustDAO();
        cust.setMobile(mobile);
        return custMapper.selectOne(cust);
    }

    @Override
    public boolean insertCust(CustDAO custDAO) {

        Assert.notNull(custDAO, "客户注册失败，参数为空！");
        custDAO.setStatus(CustStatusEnum.NORMAL.getIndex());
        custDAO.setGmtCreate(new Date());
        return custMapper.insert(custDAO) > 0;
    }

}
