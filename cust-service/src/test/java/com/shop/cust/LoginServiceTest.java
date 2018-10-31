package com.shop.cust;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.shop.cust.dto.req.RegisterReq;
import com.shop.cust.dto.resp.LoginResp;
import com.shop.cust.service.LoginService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustApplication.class)
public class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    @Test
    @Transactional
    public void testRegister() {
        RegisterReq req = new RegisterReq();
        req.setMobile("13555555555");
        req.setMsgCode("8888");
        req.setPsw("123456");
        req.setPswRept("123456");
        LoginResp resp = loginService.register(req);
        Assert.assertNotNull(resp);
    }
}
