package com.shop.trans.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.base.controller.BaseController;

@RequestMapping("/trans")
@RestController
public class TransController extends BaseController{
    
    @GetMapping("/getjson")
    public String getJson(){
        return "hello world!";
    }

}
