package com.shunyin.controller;

import com.google.code.kaptcha.Producer;
import com.shunyin.common.constant.AuthConst;
import com.shunyin.common.service.AuthHandler;
import com.shunyin.common.util.MD5Utils;
import com.shunyin.common.util.R;
import com.shunyin.entity.SysAdmin;
import com.shunyin.service.SysAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Title: 登录
 * Description: todoedit
 * author: wenjun
 * date: 2018/4/25 16:04
 */
@Controller
@RequestMapping("/auth/admin")
public class AuthAdminController {

    private static Logger log = LoggerFactory.getLogger(AuthAdminController.class);

    private static final String LOGIN_CAPTCHA_KEY = "ADMIN_LOGIN_CAPTCHA_KEY";

    @Autowired
    private Producer producer;

    @Autowired
    private SysAdminService sysAdminService;

    /**
     * 跳转登录页
     * @return
     */
    @RequestMapping("/login_page")
    public String userLoginPage(){
        return "login_admin";
    }

    /**
     * 获取验证码
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/login_captcha.jpg")
    public void captcha(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //生成文字验证码
        String text = producer.createText();
        log.info("验证码======> " + text);
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        request.getSession().setAttribute(LOGIN_CAPTCHA_KEY, text);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }


    /**
     * 登录验证
     * @param adminName
     * @param password
     * @param captchaCode
     * @param request
     * @return
     */
    @RequestMapping(value="/login")
    @ResponseBody
    public R authorization(String adminName, String password, String captchaCode,
                           HttpServletRequest request, HttpServletResponse response){
        // 先验证验证码
        String contextCode = (String) request.getSession().getAttribute(LOGIN_CAPTCHA_KEY);
        // FIXME 开发阶段，关闭验证码
//		if(false == contextCode.equals(captchaCode)){
//			return R.ok("验证码错误");
//		}
        try {
            password = MD5Utils.MD5_32bit1(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SysAdmin sysAdmin = sysAdminService.queryByAdminNameAndPwd(adminName, password);
        if (sysAdmin == null){
            return R.error(1,"帐号或密码错误");
        }

        // 创建token,基于cookie方式登录
        String token = AuthHandler.createToken(adminName,"a");
        request.getSession().setAttribute(token,sysAdmin);
        Cookie cookie = new Cookie(AuthConst.ADMIN_KEY,token);
        // 设置cookie失效时间2小时
        cookie.setMaxAge(60*120);
        // 设置作用域
        cookie.setPath(request.getContextPath()+"/manage/");
        response.addCookie(cookie);
        return R.ok("success");
    }

    /**
     * 登出
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    public R logout(HttpServletRequest request,HttpServletResponse response){
        Cookie cookie = new Cookie(AuthConst.ADMIN_KEY,null);
        cookie.setMaxAge(0);
        cookie.setPath(request.getContextPath()+"/manage/");
        response.addCookie(cookie);
        //request.getSession().invalidate();
        return R.ok("success");
    }

}
