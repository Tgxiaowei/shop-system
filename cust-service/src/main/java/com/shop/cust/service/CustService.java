package com.shop.cust.service;

import com.shop.cust.dao.CustDAO;

public interface CustService {

    CustDAO selectCustByMobile(String mobile);

}
