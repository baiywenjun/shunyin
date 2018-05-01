package com.shunyin.service;

import com.shunyin.entity.SysAdmin;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wenjun
 * @since 2018-04-25
 */
public interface SysAdminService extends IService<SysAdmin> {

    /**
     * 根据用户名与密码获取帐号信息
     * @param adminName
     * @param password
     * @return
     */
    SysAdmin queryByAdminNameAndPwd(String adminName, String password);
}
