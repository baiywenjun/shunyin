package com.shunyin.controller;

import com.shunyin.common.util.R;
import com.shunyin.common.util.Rt;
import com.shunyin.entity.BusRemitBank;
import com.shunyin.pojo.BookUserQuery;
import com.shunyin.pojo.BusRemitQuery;
import com.shunyin.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
    private BusRemitOutService busRemitOutService;

    @Autowired
    private BookUserService bookUserService;

    @Autowired
    private BusRemitBankService busRemitBankService;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/")
    public String adminHomepage(Model model){
        return "console/admin";
    }

    @RequestMapping("/remit_record_page")
    public String remitRecorderPage(){
        return "console/remit_record";
    }

    @RequestMapping("/remit_out_record_page")
    public String remitOutRecorderPage(){
        return "console/remit_out_record";
    }

    @RequestMapping("/book_page")
    public String bookUserRecord(){
        return "console/book";
    }

    @RequestMapping("/user_page")
    public String userPage(){
        return "console/user_list";
    }

    @RequestMapping("/bank_set_page")
    public String bankSetPage(){
        return "console/bank_set";
    }

    @RequestMapping("/bank_set_edit_page")
    public String bankSetEditPage(Model model, String id){
        BusRemitBank one = busRemitBankService.findOneById(Long.parseLong(id));
        model.addAttribute("cardId",id);
        model.addAttribute("card",one);
        return "console/bank_set_edit";
    }

    @RequestMapping("/profile_page")
    public String profilePage(Model model){
        Map<String, Object> map = sysDictService.queryMainDictConst();
        model.addAttribute("dict",map);
        return "console/profile";
    }

    /**
     * 入金记录查询
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
    public Rt remitRecordList(String userName, String aliasUserName, String realName, String confirmStatus,
                              int page, int limit){
        BusRemitQuery query = this.setBusRemitQuery(userName, aliasUserName, realName, confirmStatus);
        return busRemitService.queryListByPage(query,page,limit);
    }

    /**
     * 出金记录查询
     * @param userName 用户名
     * @param aliasUserName 接口用户名
     * @param realName 真实姓名
     * @param confirmStatus 转账状态
     * @param page page
     * @param limit limit
     * @return
     */
    @RequestMapping("/remit_out_record")
    @ResponseBody
    public Rt remitOutRecordList(String userName, String aliasUserName, String realName, String confirmStatus,
                                 int page, int limit){
        BusRemitQuery query = this.setBusRemitQuery(userName, aliasUserName, realName, confirmStatus);
        return busRemitOutService.queryListByPage(query,page,limit);
    }

    /**
     * 确认收款
     * @param remitId remitId
     * @return r
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
     * 确认打款
     * @param remitId remitId
     * @return r
     */
    @RequestMapping("/send_confirm")
    @ResponseBody
    public R confirmSend(String remitId, HttpServletRequest request){
        if(StringUtils.isEmpty(remitId)){
            return R.error(1,"记录主键不能为空");
        }
        Boolean flag = busRemitOutService.confirmSend(remitId, request);
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
     * 用户管理
     * @param userName
     * @param aliasName
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/user_list")
    @ResponseBody
    public Rt queryUserList(String userName, String aliasName, String realName, int page, int limit){
        return sysUserService.queryByPage(userName, aliasName, realName, page, limit);
    }

    /**
     * 用户审核通过
     * @param userId id
     * @return
     */
    @RequestMapping("/user_check")
    @ResponseBody
    public R checkUserCertification(String userId){
        boolean flag = sysUserService.checkPassById(Long.parseLong(userId));
        return (flag)?R.ok():R.error();
    }

    /**
     * 帐号设置列表
     * @param cardNo
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/bank_set_list")
    @ResponseBody
    public Rt bankSetList(String cardNo, int page, int limit){
        return busRemitBankService.queryByPage(cardNo,page,limit);
    }

    /**
     * 卡号设置
     * @param id
     * @param bankName
     * @param bankDetail
     * @param cardNo
     * @param realName
     * @param sortId
     * @return
     */
    @RequestMapping("/bank_set_edit")
    @ResponseBody
    public R bankCardEdit(Long id,String bankName,String bankDetail,String cardNo,String realName,String sortId){
        R r = this.validateBankCardSet(id, bankName, bankDetail, cardNo, realName, sortId);
        if(r!=null){
            return r;
        }
        BusRemitBank card = new BusRemitBank();
        card.setCardId(id);
        card.setBankName(bankName);
        card.setBankDetail(bankDetail);
        card.setCardNo(cardNo);
        card.setRealName(realName);
        card.setSortId(Integer.parseInt(sortId));
        boolean flag = busRemitBankService.updateByPrimay(card);
        return (flag)?R.ok("操作成功"):R.error();
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

    private R validateBankCardSet(Long id,String bankName,String bankDetail,String cardNo,String realName,String sortId){
        if(id == null){
            return R.error("主键不能为空");
        }
        if(StringUtils.isEmpty(bankName)){
            return R.error("银行名称不能为空");
        }
        if(StringUtils.isEmpty(bankDetail)){
            return R.error("开户信息不能为空");
        }
        if(StringUtils.isEmpty(cardNo)){
            return R.error("银行卡号不能为空");
        }
        if(StringUtils.isEmpty(realName)){
            return R.error("真实不能为空");
        }
        if(! StringUtils.isNumeric(sortId)){
            return R.error("排序不能为空，切必须为整数");
        }
        return null;
    }
}
