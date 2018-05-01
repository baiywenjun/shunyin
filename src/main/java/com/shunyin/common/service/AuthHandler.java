package com.shunyin.common.service;

import com.shunyin.common.constant.AuthConst;
import com.shunyin.common.util.DateUtil;
import com.shunyin.common.util.MD5Utils;
import com.shunyin.common.util.R;
import com.shunyin.entity.SysAdmin;
import com.shunyin.entity.SysUser;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Title: todoedit
 * Description: todoedit
 * author: wenjun
 * date: 2018/4/26 9:22
 */
public class AuthHandler {

    public static String createToken(String userName,String type){
        String dateStr = DateUtil.formatDateByFormat(new Date(), "yyyyMMddHHmmss");
        String token = null;
        try {
            token = MD5Utils.MD5_32bit(type+userName + dateStr);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return token;
    }



    public static SysUser getSysUserTokenInfo(HttpServletRequest request){
        String token = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            String cookieKey = cookie.getName();
            if(AuthConst.USER_KEY.equals(cookieKey)){
                token = cookie.getValue();
                break;
            }
        }
        if(token!=null){
            SysUser sysUser = (SysUser) request.getSession().getAttribute(token);
            return sysUser;
        }
        return null;
    }

}
