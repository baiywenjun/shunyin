package com.shunyin.controller;

import com.google.code.kaptcha.Producer;
import com.shunyin.common.constant.AuthConst;
import com.shunyin.common.service.AuthHandler;
import com.shunyin.common.util.R;
import com.shunyin.entity.SysUser;
import com.shunyin.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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
import java.util.regex.Pattern;

/**
 * Title: 登录
 * Description: todoedit
 * author: wenjun
 * date: 2018/4/25 16:04
 */
@Controller
@RequestMapping("/auth/user")
public class AuthUserController {

    private static Logger log = LoggerFactory.getLogger(AuthUserController.class);

    private static final String LOGIN_CAPTCHA_KEY = "USER_LOGIN_CAPTCHA_KEY";
    private static final String REGISTER_CAPTCHA_KEY = "USER_REGISTER_CAPTCHA_KEY";

    @Autowired
    private Producer producer;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 跳转登录页
     * @return
     */
    @RequestMapping("/login_page")
    public String userLoginPage(){
        return "login";
    }

    @RequestMapping("/register_page")
    public String userRegister(){
        return "register";
    }

    /**
     * 获取验证码
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/register_captcha.png")
    public void captcha(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        log.debug("验证码======> " + text);
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        request.getSession().setAttribute(REGISTER_CAPTCHA_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "png", out);
    }


    /**
     * 登录验证
     * @param userName
     * @param password
     * @param captchaCode
     * @param request
     * @return
     */
    @RequestMapping(value="/login")
    @ResponseBody
    public R authorization(String userName, String password, String captchaCode,
                           HttpServletRequest request, HttpServletResponse response){

        // 登录无验证码
        // 先验证验证码
        //String contextCode = (String) request.getSession().getAttribute(LOGIN_CAPTCHA_KEY);

        SysUser sysUser = sysUserService.queryByUserNameAndPwd(userName, password);
        if (sysUser == null){
            return R.error(1,"帐号或密码错误");
        }

        // 创建token,基于cookie方式登录
        String token = AuthHandler.createToken(userName,"u");
        request.getSession().setAttribute(token,sysUser);
        Cookie cookie = new Cookie(AuthConst.USER_KEY,token);
        // 设置cookie失效时间2小时
        cookie.setMaxAge(60*120);
        // 设置作用域
        cookie.setPath(request.getContextPath()+"/user/");
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
        Cookie cookie = new Cookie(AuthConst.USER_KEY,null);
        cookie.setMaxAge(0);
        cookie.setPath(request.getContextPath() + "/user/");
        response.addCookie(cookie);
        //request.getSession().invalidate();
        return R.ok("success");
    }

    /**
     * 用户注册
     * @param userName
     * @param pwd
     * @param repeatPwd
     * @param identify
     * @param code
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    public R userRegister(String userName, String pwd, String repeatPwd, String identify, String code,
                          HttpServletRequest request){
        R r = this.validateRegister(userName, pwd, repeatPwd, identify, code, request);
        if(r!=null){
            return r;
        }
        Boolean flag = sysUserService.userRegister(userName, pwd, identify);
        return (flag)?R.ok():R.error();
    }


    /**
     * 注册表单验证
     * @param userName
     * @param pwd
     * @param repeatPwd
     * @param identify
     * @param code
     * @param request
     * @return
     */
    private R validateRegister(String userName, String pwd, String repeatPwd, String identify, String code,
                               HttpServletRequest request){
        if(StringUtils.isBlank(userName)){
            return R.error(1,"用户名不能为空");
        }
        if(StringUtils.isBlank(pwd)){
            return R.error(1,"密码不能为空");
        }
        if(StringUtils.isBlank(repeatPwd)){
            return R.error(1,"确认密码不能为空");
        }
        if(StringUtils.isBlank(identify)){
            return R.error(1,"身份证信息不能为空");
        }
        if(StringUtils.isBlank(code)){
            return R.error(1,"验证码不能为空");
        }
        if(!pwd.equals(repeatPwd)){
            return R.error(1,"两次密码不一致");
        }
        String phoneRegex = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        boolean phoneMatch = Pattern.matches(phoneRegex, userName);
        if(!phoneMatch){
            return R.error(1,"用户名要输入手机号码");
        }
        String idPattern18 = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
        String idPattern15 = "^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$";
        if(identify.length()!=18 && identify.length()!=15){
            return R.error(1,"身份证长度不正确");
        }
        if(identify.length()==18){
            boolean isMatch = Pattern.matches(idPattern18, identify);
            if(!isMatch){
                return R.error(1,"身份证格式不正确");
            }
        }
        if(identify.length()==15){
            boolean isMatch = Pattern.matches(idPattern15, identify);
            if(!isMatch){
                return R.error(1,"身份证格式不正确");
            }
        }
        String captchaCode = (String) request.getSession().getAttribute(REGISTER_CAPTCHA_KEY);
        if(!code.equals(captchaCode)){
            return R.error(1,"验证码不正确");
        }
        return null;
    }

}
