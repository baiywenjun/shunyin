package com.shunyin.service;

import com.shunyin.common.util.Rt;
import com.shunyin.entity.SysUser;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wenjun
 * @since 2018-04-25
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据帐号密码查询
     * @param userName
     * @param password
     * @return
     */
    SysUser queryByUserNameAndPwd(String userName, String password);

    Rt queryByPage(String userName, String aliasName, String name, Integer page, Integer limit);

    /**
     * 注册账号
     * @param userName
     * @param password
     * @param identify
     * @return
     */
    Boolean userRegister(String userName, String password, String identify, String name);

    @Transactional
    SysUser userRegisterReturnAliasAccount(String userName, String password, String repeatPwd, String identify, String name);

    SysUser findOneById(Long accountId);

    boolean checkPassById(Long userId);
}
