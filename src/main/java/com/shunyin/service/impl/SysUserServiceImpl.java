package com.shunyin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.shunyin.common.apiUtil.ByxgjUtil;
import com.shunyin.entity.SysUser;
import com.shunyin.exception.RRException;
import com.shunyin.mapper.SysUserMapper;
import com.shunyin.service.SysUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wenjun
 * @since 2018-04-25
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser queryByUserNameAndPwd(String userName, String password){
        SysUser sysUser = this.selectOne(new EntityWrapper<SysUser>().
                eq("user_name", userName)
                .and("password", password));
        return sysUser;
    }


    @Override
    public Boolean userRegister(String userName,String password,String identify){
        JSONObject createaccount = ByxgjUtil.createaccount(null, password, null, userName);
        Integer code = (Integer) createaccount.get("Code");
        String childAccount = (String) createaccount.get("Account");
        if(code!=0 || StringUtils.isEmpty(childAccount)){
            throw new RRException("接口创建账号错误");
        }
        SysUser sysUser = new SysUser();
        sysUser.setUserName(userName);
        sysUser.setPassword(password);
        sysUser.setIdentity(identify);
        sysUser.setAliasAccount(childAccount);
        boolean insert = this.insert(sysUser);
        return insert;
    }

}
