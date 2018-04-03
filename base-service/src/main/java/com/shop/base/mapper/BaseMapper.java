package com.shop.base.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * BaseMapper，继承通用mapper等
 * @author xiaowei 2018年3月30日 下午5:52:17
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
