package com.shunyin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shunyin.common.apiUtil.ByxgjUtil;
import com.shunyin.common.constant.ConfigConst;
import com.shunyin.common.util.Rt;
import com.shunyin.entity.SysUser;
import com.shunyin.exception.RRException;
import com.shunyin.mapper.SysUserMapper;
import com.shunyin.service.SysUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
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
                .eq("password", password));
        return sysUser;
    }

    @Override
    public Rt queryByPage(String userName, String aliasName, String name, Integer page, Integer limit){
        Wrapper<SysUser> wrapper = new EntityWrapper<>();
        if(StringUtils.isNotEmpty(userName)){
            wrapper.like("user_name",userName);
        }
        if(StringUtils.isNotEmpty(aliasName)){
            wrapper.like("alias_account",aliasName);
        }
        if(StringUtils.isNotBlank(name)){
            wrapper.like("real_name",name);
        }
        int count = this.selectCount(wrapper);
        Page<SysUser> sysUserPage = this.selectPage(new Page<SysUser>(page, limit), wrapper);
        List<SysUser> records = sysUserPage.getRecords();
        return Rt.ok(count,records);
    }

    @Override
    public Boolean userRegister(String userName,String password,String identify, String name){
        SysUser sysUser = this.userRegisterReturnAliasAccount(userName, password, null,identify, name);
        return (StringUtils.isNotEmpty(sysUser.getAliasAccount()));
    }

    @Override
    @Transactional
    public SysUser userRegisterReturnAliasAccount(String userName, String password, String repeatPwd, String identify, String name){
        JSONObject createaccount = ByxgjUtil.createaccount(null, password, null, name);
        Integer code = (Integer) createaccount.get("Code");
        String childAccount = (String) createaccount.get("Account");
        if(code!=0 || StringUtils.isEmpty(childAccount)){
            throw new RRException("接口创建账号错误");
        }
        SysUser sysUser = new SysUser();
        sysUser.setUserName(userName);
        sysUser.setPassword(password);
        sysUser.setIdentity(identify);
        sysUser.setRealName(name);
        sysUser.setAliasAccount(childAccount);
        sysUser.setCreateTime(new Date());
        sysUser.setXgjPassword(repeatPwd);
        sysUser.setStatus("N");
        boolean insert = this.insert(sysUser);
        log.info("注册账号主键为:"+sysUser.getAccountId().toString());
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

        return sysUser;
    }

    @Override
    public SysUser findOneById(Long accountId){
        return  this.selectById(accountId);
    }

    @Override
    public boolean checkPassById(Long userId){
        SysUser sysUser = new SysUser();
        sysUser.setAccountId(userId);
        sysUser.setStatus("Y");
        boolean update = this.updateById(sysUser);
        return update;
    }

}
