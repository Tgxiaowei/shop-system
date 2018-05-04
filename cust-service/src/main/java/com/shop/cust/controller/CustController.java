package com.shop.cust.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.base.controller.BaseController;
import com.shop.base.dto.Result;
import com.shop.cust.dao.CustDAO;
import com.shop.cust.service.CustService;

@RestController
@RequestMapping("/cust")
public class CustController extends BaseController {

    @Autowired
    private CustService custService;

    @GetMapping("/test")
    public Result<CustDAO> login() {
        return Result.success(custService.selectCustByMobile("13588795912"));
    }

}
