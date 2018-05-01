package com.shunyin.service;

import com.shunyin.entity.SysDict;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wenjun
 * @since 2018-04-27
 */
public interface SysDictService extends IService<SysDict> {

    Map<String,Object> queryMainDictConst();
}
