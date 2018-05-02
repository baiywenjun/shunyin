package com.shunyin.controller;

import com.shunyin.common.util.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Title: 测试
 * Description: todoedit
 * author: wenjun
 * date: 2018/4/25 10:53
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/hello")
    public String hello(){
        System.out.println("hello");
        return "main_admin";
    }


    @RequestMapping("/test")
    @ResponseBody
    public R test(){
        return R.ok("test");
    }

}
