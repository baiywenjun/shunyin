package com.shunyin.auth;

import com.shunyin.common.constant.AuthConst;
import com.shunyin.entity.SysAdmin;
import com.shunyin.entity.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthUserInterceptor implements HandlerInterceptor {

    private static Logger log = LoggerFactory.getLogger(AuthUserInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = null;
        SysUser sysUser = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            String cookieKey = cookie.getName();
            if(AuthConst.USER_KEY.equals(cookieKey)){
                token = cookie.getValue();
                break;
            }
        }
        if(token==null){
            log.warn("USER TOKEN IS NULL");
        }
        if(token!=null){
            log.info(token);
            sysUser = (SysUser) request.getSession().getAttribute(token);
        }
        if(sysUser==null){
            log.warn("USER TOKEN INFO IS NULL");
            // 令牌是空直接重定向
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/auth/user/login_page");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //log.debug("AuthAdminInterceptor test postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //log.debug("AuthAdminInterceptor afterCompletion");
    }
}
