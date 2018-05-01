package com.shunyin.controller;

import com.shunyin.common.util.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Title: todoedit
 * Description: todoedit
 * author: wenjun
 * date: 2018/4/25 23:17
 */
@RestController
@RequestMapping("/logout")
public class LogoutController {

    @RequestMapping("/test")
    public R test(){
        return R.ok("redirectTest");

    }
}
