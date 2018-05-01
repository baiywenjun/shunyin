package com.shunyin.controller;

import com.shunyin.common.util.R;
import com.shunyin.common.util.Rt;
import com.shunyin.pojo.BusRemitQuery;
import com.shunyin.service.BusRemitService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping("/")
    public String adminHomepage(Model model){
        return "main_admin";
    }

    /**
     * 转账记录查询
     * @param userName
     * @param aliasUserName
     * @param realName
     * @param confirmStatus
     * @param page
     * @param limit
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
}
