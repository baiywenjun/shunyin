package com.shunyin.auth;

import com.shunyin.common.constant.AuthConst;
import com.shunyin.entity.SysAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthAdminInterceptor implements HandlerInterceptor {

    private static Logger log = LoggerFactory.getLogger(AuthAdminInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = null;
        SysAdmin sysAdmin = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            String cookieKey = cookie.getName();
            if(AuthConst.ADMIN_KEY.equals(cookieKey)){
                token = cookie.getValue();
                break;
            }
        }
        if(token==null){
            log.warn("ADMIN TOKEN IS NULL");
        }
        if(token!=null){
            log.info(token);
            sysAdmin = (SysAdmin) request.getSession().getAttribute(token);
        }
        if(sysAdmin==null){
            log.warn("ADMIN TOKEN INFO IS NULL");
            // 令牌是空直接重定向
            response.sendRedirect(request.getContextPath()+"/auth/admin/login_page");
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
