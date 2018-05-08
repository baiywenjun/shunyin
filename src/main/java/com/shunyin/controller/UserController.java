package com.shunyin.controller;

import ch.qos.logback.core.joran.conditional.ThenOrElseActionBase;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.shunyin.common.service.ApiHandler;
import com.shunyin.common.service.AuthHandler;
import com.shunyin.common.util.DateUtil;
import com.shunyin.common.util.MoneyUtil;
import com.shunyin.common.util.R;
import com.shunyin.common.util.Rt;
import com.shunyin.entity.BookUser;
import com.shunyin.entity.SysDict;
import com.shunyin.entity.SysUser;
import com.shunyin.service.BookAdminService;
import com.shunyin.service.BookUserService;
import com.shunyin.service.BusRemitService;
import com.shunyin.service.SysDictService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Title: todoedit
 * Description: todoedit
 * author: wenjun
 * date: 2018/4/26 15:02
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ApiHandler apiHandler;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private BusRemitService busRemitService;

    @Autowired
    private BookUserService bookUserService;


    @RequestMapping("/")
    public String mainPage(Model model, HttpServletRequest request){
        // 获取用户名
        SysUser sysUser = AuthHandler.getSysUserTokenInfo(request);
        // 获取字典信息
        Map<String, Object> map = sysDictService.queryMainDictConst();
        model.addAttribute("userName",sysUser.getUserName());
        model.addAttribute("aliasName",sysUser.getAliasAccount());
        model.addAttribute("dict",map);
        model.addAttribute("sysTime", DateUtil.formatDateByFormat(new Date(),"yyyy/MM/dd HH:mm:ss"));
        return "main";
    }

    /**
     * 资金查询
     * @param request
     * @return
     */
    @RequestMapping("/fund")
    @ResponseBody
    public R queryAccountFund(HttpServletRequest request){
        Map<String, Object> map = apiHandler.queryAccountFund(request);
        return R.ok(map);
    }

    /**
     * 在线充值
     * @param userName
     * @param money
     * @param dollar
     * @param exchange
     * @param takeFee
     * @return
     */
    @PostMapping("/transaction")
    @ResponseBody
    public R transactionOnline(String userName,String money,String dollar,String exchange,String takeFee,HttpServletRequest request){
        log.info("用户["+userName+"],进行了在线充值:"+money);
        Integer moneyCent = MoneyUtil.toCent(Float.parseFloat(money));
        Integer dollarCent = MoneyUtil.toCent(Float.parseFloat(dollar));
        Integer takeFeeCent = MoneyUtil.toCent(Float.parseFloat(takeFee));
        float exchangeF = Float.parseFloat(exchange);
        // 获取接口帐号
        SysUser sysUser = AuthHandler.getSysUserTokenInfo(request);
        String aliasAccount = null;
        if(sysUser!=null){
            aliasAccount = sysUser.getAliasAccount();
        }
        // TODO 在线充值，接入第三方支付，跳转到支付成功页面 20180505

        // TODO 第三方支付成功，此处应支付成功后的页面，再添加进账本中,返回支付流水 20180505
        Boolean flag = bookUserService.addBookUserFromTransferAccount(userName, aliasAccount, moneyCent, dollarCent, "元",
                exchangeF, takeFeeCent,"在线充值","充值成功","00000");
        return (flag)?R.ok("success"):R.error(1,"失败，请联系管理员");
    }

    /**
     * 转账操作
     * @return
     */
    @PostMapping("/transferAccountApply")
    @ResponseBody
    public R transferAccountApply(String realName,
                             String bankCardNum,
                             String money,
                             String dollar,
                             String exchange,
                             String userName,
                             String toRealName,
                             String toBankName,
                             String toBankDetail,
                             String toBankCardNum,
                             HttpServletRequest request
                             ){

        if(StringUtils.isBlank(realName)){
            return R.error(1,"用户名不能为空");
        }
        if(StringUtils.isBlank(bankCardNum)){
            return R.error(1,"银行卡号不能为空");
        }
        String moneyRegex = "(^[1-9]([0-9]+)?(\\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\\.[0-9]([0-9])?$)";
        if(! Pattern.matches(moneyRegex, money)){
            return R.error(1,"金额格式不正确");
        }
        Float moneyF = Float.parseFloat(money);
        if(moneyF<=0){
            return R.error("金额请输入正数");
        }
        // 人民币分
        Integer remitMoney = moneyF.intValue() * 100;
        // 美元分
        Integer remitDollar = ((Float)Float.parseFloat(dollar)).intValue() * 100;
        Float exchangeF = Float.parseFloat(exchange);
        Boolean flag = busRemitService.addRemitRecord(realName, bankCardNum,remitMoney,remitDollar, exchangeF, userName,
                toRealName, toBankName, toBankDetail, toBankCardNum, request);
        return (flag)?R.ok("提交成功"):R.error();
    }

    /**
     * 提现操作
     * @return
     */
    @PostMapping("/withdrawal")
    @ResponseBody
    public R withdrawal(String userName,String moneyDollar1,String moneyDollar2,String unit,
                        String exchange,String takeFee,HttpServletRequest request){
        Float exchangeF = Float.parseFloat(exchange);
        Float takeFeeF = Float.parseFloat(takeFee);
        Integer takeFeeCentInt = takeFeeF.intValue()*100;
        Boolean flag = bookUserService.addBookUserFromWithdrawal(userName,
                Float.parseFloat(moneyDollar1),
                Float.parseFloat(moneyDollar2),
                unit, exchangeF, takeFeeCentInt,request);
        return (flag)?R.ok("提交成功"):R.error();
    }

    /**
     * 入金列表
     * @param userName
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/inMoneyList")
    @ResponseBody
    public Rt inMoneyList(String userName,int page, int limit){
        return bookUserService.queryInMoneyListByPage(userName,page,limit);
    }

    /**
     * 出金列表
     * @param userName
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/outMoneyList")
    @ResponseBody
    public Rt outMoneyList(String userName,int page, int limit){
        return bookUserService.queryOutMoneyListByPage(userName,page,limit);
    }



}
