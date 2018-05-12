package com.shunyin.controller;

import com.shunyin.common.upload.UpResult;
import com.shunyin.common.upload.UploadService;
import com.shunyin.common.util.R;
import com.shunyin.entity.SysCertification;
import com.shunyin.entity.SysUser;
import com.shunyin.service.SysCertificationService;
import com.shunyin.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Title: 实名认证审核
 * Description: todoedit
 * author: wenjun
 * date: 2018/5/12 10:35
 */
@Controller
@RequestMapping("/certification")
public class CertificationController {

    private static Logger log = LoggerFactory.getLogger(CertificationController.class);

    @Value("${ID_FRONT}")
    private String idFront;
    @Value("${ID_BACK}")
    private String idBack;
    @Value("${CARD_FRONT}")
    private String cardFront;

    @Value("${ID_FRONT_IMG}")
    private String idFrontImg;
    @Value("${ID_BACK_IMG}")
    private String idBackImg;
    @Value("${CARD_FRONT_IMG}")
    private String cardFrontImg;

    @Autowired
    private UploadService uploadservice;

    @Autowired
    private SysCertificationService sysCertificationService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 上传身份证正面
     * @param request rq
     * @param response rs
     * @return R
     */
    @RequestMapping(value = "/upload/{flag}")
    @ResponseBody
    public R uploadIdFrontImg(@PathVariable("flag") String flag, HttpServletRequest request, HttpServletResponse response){
        log.info("标识符:"+flag);
        // 区分访问
        String directory = "";
        String downUrl = "";
        if("id_front".equalsIgnoreCase(flag)){
            directory = idFront;
            downUrl = idFrontImg;
        }
        if("id_back".equalsIgnoreCase(flag)){
            directory = idBack;
            downUrl = idBackImg;
        }
        if("card_front".equalsIgnoreCase(flag)){
            directory = cardFront;
            downUrl = cardFrontImg;
        }
        String upFilePath = null;
        try {
            UpResult upResult = uploadservice.doPost(directory, request, response);
            upFilePath = upResult.getFilePath();
            downUrl += upResult.getUuidName();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.info("upFilePath"+upFilePath);
        log.info("downUrl"+downUrl);
        Map<String,Object> map = new HashMap<>();
        map.put("upFilePath",upFilePath);
        map.put("downUrl",downUrl);
        return R.ok("success",map);
    }


    @RequestMapping("/{operate}")
    public String queryCertificationInfo(Model model, @PathVariable("operate") String operate, String id){
        SysUser sysUser = sysUserService.findOneById(Long.parseLong(id));
        model.addAttribute("operate",operate);
        model.addAttribute("userId",id);
        model.addAttribute("user",sysUser);
        return "console/user_cert";
    }

    /**
     * 输出图片
     * @param type
     * @param userId
     * @param request
     * @param response
     */
    @RequestMapping(value = "/cert/{type}/{userId}")
    public void readLocalImg(@PathVariable("type") String type, @PathVariable("userId") String userId,
                             HttpServletRequest request, HttpServletResponse response){
        String filePath = "";
        SysCertification sysCertification = sysCertificationService.findOneByUserId(Long.parseLong(userId));

        if("id_front".equalsIgnoreCase(type)){
            filePath = sysCertification.getIdFrontFilePath();
        }
        if("id_back".equalsIgnoreCase(type)){
            filePath = sysCertification.getIdBackFilePath();
        }
        if("card_front".equalsIgnoreCase(type)){
            filePath = sysCertification.getCardFrontFilePath();
        }
        try {
            //读取本地图片输入流
            FileInputStream inputStream = new FileInputStream(filePath);
            int i = inputStream.available();
            //byte数组用于存放图片字节数据
            byte[] buff = new byte[i];
            inputStream.read(buff);
            //记得关闭输入流
            inputStream.close();
            //设置发送到客户端的响应内容类型
            response.setContentType("image/*");
            OutputStream out = response.getOutputStream();
            out.write(buff);
            //关闭响应输出流
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
