package com.shunyin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Title: todoedit
 * Description: todoedit
 * author: wenjun
 * date: 2018/4/26 14:19
 */
@Controller
public class IndexController {

    /**
     * 用户首页-默认登录页
     * @return
     */
    @RequestMapping(value={"/","index"})
    public String index(){
        return  "login";
    }

    @RequestMapping(value = {"/admin","/console"})
    public String admin(){
        return "login_admin";
    }

}
