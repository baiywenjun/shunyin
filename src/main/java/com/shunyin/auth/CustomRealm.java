package com.shunyin.auth;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.shunyin.entity.SysUser;
import com.shunyin.service.SysUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomRealm extends AuthorizingRealm{

	private static Logger log = LoggerFactory.getLogger(CustomRealm.class);

	@Autowired
	private SysUserService sysUserService;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String usercode = (String) token.getPrincipal();

        // 通过 usercode 获取用户
		SysUser user = sysUserService.selectOne(new EntityWrapper<SysUser>().eq("user_name", usercode));

		if(user == null || user.getUserName() == null){
			//返回null，认证器接收到null，抛出异常UnknownAccountException
			return null;
		}

		// 执行到这里说明账号存在
		// 根据账号从数据库查询正确的密码
		String pwd = user.getPassword();
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, pwd, "customRealm");
		return simpleAuthenticationInfo;
	}

}
