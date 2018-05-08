package com.shunyin.controller;

import com.shunyin.common.util.R;
import com.shunyin.common.util.Rt;
import com.shunyin.pojo.BookUserQuery;
import com.shunyin.pojo.BusRemitQuery;
import com.shunyin.service.BookUserService;
import com.shunyin.service.BusRemitService;
import com.shunyin.service.SysDictService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Title: todoedit
 * Description: todoedit
 * author: wenjun
 * date: 2018/4/28 15:23
 */
@Controller
@RequestMapping("/manage")
public class ManageController {
    private static Logger log = LoggerFactory.getLogger(ManageController.class);

    @Autowired
    private BusRemitService busRemitService;

    @Autowired
    private BookUserService bookUserService;

    @Autowired
    private SysDictService sysDictService;

    @RequestMapping("/")
    public String adminHomepage(Model model){
        return "console/admin";
    }

    @RequestMapping("/remit_record_page")
    public String remitRecorderPage(){
        return "console/remit_record";
    }

    @RequestMapping("/book_page")
    public String bookUserRecord(){
        return "console/book";
    }

    @RequestMapping("/profile_page")
    public String profilePage(Model model){
        Map<String, Object> map = sysDictService.queryMainDictConst();
        model.addAttribute("dict",map);
        return "console/profile";
    }

    /**
     * 转账记录查询
     * @param userName 用户名
     * @param aliasUserName 接口用户名
     * @param realName 真实姓名
     * @param confirmStatus 转账状态
     * @param page page
     * @param limit limit
     * @return
     */
    @RequestMapping("/remit_record")
    @ResponseBody
    public Rt remitRecordList(String userName,
                              String aliasUserName,
                              String realName,
                              String confirmStatus,
                              int page,
                              int limit){
        BusRemitQuery query = this.setBusRemitQuery(userName, aliasUserName, realName, confirmStatus);
        return busRemitService.queryListByPage(query,page,limit);
    }

    /**
     * 确认收款
     * @param remitId
     * @return
     */
    @RequestMapping("/remit_confirm")
    @ResponseBody
    public R confirmReceive(String remitId){
        if(StringUtils.isEmpty(remitId)){
            return R.error(1,"记录主键不能为空");
        }
        Boolean flag = busRemitService.confirmReceive(Long.parseLong(remitId));
        return (flag)?R.ok("操作成功"):R.error();
    }


    /**
     * 账本列表
     * @param type 0入金，1出金
     * @param userName
     * @param aliasUserName
     * @param beginTime
     * @param endTime
     * @return
     */
    @RequestMapping("/book")
    @ResponseBody
    public Rt bookUserList(String type,String userName,String aliasUserName,String serialNo,String beginTime,String endTime,
                          int page,int limit){
        BookUserQuery bookUserQuery = this.setBookUserQuery(type, userName, aliasUserName, serialNo, beginTime, endTime);
        Rt rt = bookUserService.queryBookListByPage(bookUserQuery, page, limit);
        return rt;
    }

    /**
     * 设置属性
     * @param exchange
     * @param inCharge
     * @param outCharge
     * @return
     */
    @RequestMapping("/profile")
    @ResponseBody
    public R setProfile(String exchange,String inCharge, String outCharge){
        boolean flag = sysDictService.updateDict(exchange, inCharge, outCharge);
        return (flag)?R.ok():R.error(1,"操作失败");
    }

    private BusRemitQuery setBusRemitQuery(String userName, String aliasUserName,String realName,String confirmStatus){
        BusRemitQuery query = new BusRemitQuery();
        if(StringUtils.isNotEmpty(userName)){
            query.setUserName(Long.parseLong(userName));
        }
        query.setAliasUserName(aliasUserName);
        query.setRealName(realName);
        if(StringUtils.isNotEmpty(confirmStatus)){
            query.setConfirmStatus(Integer.parseInt(confirmStatus));
        }
        return query;
    }

    private BookUserQuery setBookUserQuery(String type,String userName,String aliasUserName,String serialNo,String beginTime,String endTime){
        BookUserQuery query = new BookUserQuery();
        if(StringUtils.isNotEmpty(type)){
            query.setType(type);
        }
        query.setUserName(userName);
        query.setAliasUserName(aliasUserName);
        query.setSerialNo(serialNo);
        return query;
    }
}
