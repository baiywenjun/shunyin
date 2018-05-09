package com.shunyin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.shunyin.common.apiUtil.ByxgjUtil;
import com.shunyin.common.constant.ConfigConst;
import com.shunyin.entity.SysUser;
import com.shunyin.exception.RRException;
import com.shunyin.mapper.SysUserMapper;
import com.shunyin.service.SysUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    private static Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Override
    public SysUser queryByUserNameAndPwd(String userName, String password){
        SysUser sysUser = this.selectOne(new EntityWrapper<SysUser>().
                eq("user_name", userName)
                .and("password", password));
        return sysUser;
    }


    @Override
    public Boolean userRegister(String userName,String password,String identify, String name){
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
        sysUser.setName(name);
        sysUser.setAliasAccount(childAccount);
        boolean insert = this.insert(sysUser);
        // 对一个账号进行同步费率设置
        JSONObject setCmsrResult = ByxgjUtil.setcommissionrate(null, childAccount, ConfigConst.FLOW_ACCOUNT);
        JSONObject setmarginrate = ByxgjUtil.setmarginrate(null, childAccount, ConfigConst.FLOW_ACCOUNT);
        log.info(setCmsrResult.toJSONString());
        log.info(setmarginrate.toJSONString());
        Object resultCode = ((Map)((Map)setCmsrResult.get("Result")).get("Error")).get("Code");
        if(! "0".equals(resultCode)){
            throw new RRException("接口同步费率错误");
        }
        Object resultCode_b = ((Map)((Map)setmarginrate.get("Result")).get("Error")).get("Code");
        if(! "0".equals(resultCode_b)){
            throw new RRException("接口同步保障金错误");
        }
        return insert;
    }

}
