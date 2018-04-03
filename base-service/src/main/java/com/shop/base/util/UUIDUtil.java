package com.shop.base.util;

import java.util.UUID;

public class UUIDUtil {

    public String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
