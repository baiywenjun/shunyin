package com.shunyin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shunyin.common.apiUtil.ByxgjUtil;
import com.shunyin.common.service.AuthHandler;
import com.shunyin.common.util.MoneyUtil;
import com.shunyin.common.util.Rt;
import com.shunyin.entity.BookUser;
import com.shunyin.exception.RRException;
import com.shunyin.mapper.BookUserMapper;
import com.shunyin.pojo.BookUserQuery;
import com.shunyin.service.BookUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wenjun
 * @since 2018-04-27
 */
@Service
public class BookUserServiceImpl extends ServiceImpl<BookUserMapper, BookUser> implements BookUserService {

    private static Logger log = LoggerFactory.getLogger(BookUserServiceImpl.class);

    /**
     * 入金操作
     * @param userName 用户名
     * @param aliasName 接口用户名
     * @param moneyDollar1 人民币
     * @param moneyDollar2 美元
     * @param unit 货币单位：元/美元
     * @param exchange 汇率
     * @param takeFee 手续费
     * @param flowWay 流转方式：汇入/自动转账/在线充值
     * @param status 状态：转账成功，充值成功
     * @return boolean
     */
    @Override
    public Boolean addBookUserFromTransferAccount(String userName, String aliasName, Integer moneyDollar1, Integer moneyDollar2, String unit,
                                                  Float exchange, Integer takeFee, String flowWay, String status, String payNo){
        BookUser bookUser = new BookUser();
        bookUser.setSerialNo(UUID.randomUUID().toString().replace("-","").toUpperCase());
        bookUser.setPayNo(payNo);
        bookUser.setUserName(userName);
        bookUser.setAliasUserName(aliasName);
        bookUser.setMoney(moneyDollar1);
        bookUser.setDollar(moneyDollar2);
        bookUser.setMonetaryUnit(unit);
        bookUser.setFlowWay(flowWay);
        bookUser.setTakeFee(takeFee);
        bookUser.setExchange(exchange);
        bookUser.setCreateTime(new Date());
        bookUser.setStatus(status);
        bookUser.setType(0);//入金
        boolean flag = this.insert(bookUser);
        // 对接口帐号出入金操作
        inOutOperate(aliasName, moneyDollar1,"CNH");
        return flag;
    }

    @Override
    public Boolean addBookUserFromWithdrawal(String userName, Float moneyDollar1,Float moneyDollar2, String unit,
                                             Float exchange, Integer takeFee, HttpServletRequest request){
        String aliasAccount = AuthHandler.getSysUserTokenInfo(request).getAliasAccount();
        BookUser bookUser = new BookUser();
        bookUser.setSerialNo(UUID.randomUUID().toString().replace("-","").toUpperCase());
        bookUser.setUserName(userName);
        bookUser.setAliasUserName(aliasAccount);
        if("CNY".equals(unit)){
            bookUser.setMoney(MoneyUtil.toCent(moneyDollar1));
            bookUser.setDollar(MoneyUtil.toCent(moneyDollar2));
            bookUser.setMonetaryUnit("元");
        }
        if("USD".equals(unit)){
            bookUser.setDollar(MoneyUtil.toCent(moneyDollar1));
            bookUser.setMoney(MoneyUtil.toCent(moneyDollar2));
            bookUser.setMonetaryUnit("美元");
        }
        bookUser.setFlowWay("自动转账");
        bookUser.setTakeFee(takeFee);
        bookUser.setExchange(exchange);
        bookUser.setCreateTime(new Date());
        bookUser.setStatus("转账成功");
        bookUser.setType(1);//出金
        boolean flag = this.insert(bookUser);
        // 对接口帐号出入金操作
        Integer amount = -MoneyUtil.toCent(moneyDollar1);
        if("CNY".equals(unit)){
            this.inOutOperate(aliasAccount,amount,"CNH");
        }
        if("USD".equals(unit)){
            this.inOutOperate(aliasAccount,amount,"USD");
        }
        // todo 调用代付接口 20180503

        return flag;
    }

    @Override
    public Rt queryInMoneyListByPage(String userName, int page, int limit) {
        Integer type = 0;
        return queryBookListByPage(userName, page, limit, type);
    }

    @Override
    public Rt queryOutMoneyListByPage(String userName, int page, int limit) {
        Integer type = 1;
        return queryBookListByPage(userName, page, limit, type);
    }

    // 提供admin
    @Override
    public Rt queryBookListByPage(BookUserQuery query, int page, int limit){
        EntityWrapper<BookUser> wrapper = new EntityWrapper<>();
        if(query.getType() != null){
            wrapper.eq("type",query.getType());
        }
        if(StringUtils.isNotEmpty(query.getUserName())){
            wrapper.eq("user_name",query.getUserName());
        }
        if(StringUtils.isNotEmpty(query.getSerialNo())){
            wrapper.eq("serial_no",query.getSerialNo());
        }
        // todo 其他查询条件 20180502
        wrapper.orderBy("create_time",false);
        int count = this.selectCount(wrapper);
        Page<BookUser> bookUserPage = this.selectPage(new Page<BookUser>(page, limit), wrapper);
        List<BookUser> records = bookUserPage.getRecords();
        return Rt.ok(count,records);
    }

    // 提供user
    private Rt queryBookListByPage(String userName, int page, int limit, Integer type) {
        Wrapper<BookUser> wrapper = new EntityWrapper<BookUser>()
                .eq("user_name", userName).eq("type", type).orderBy("create_time",false);
        int count = this.selectCount(wrapper);
        Page<BookUser> bookUserPage = this.selectPage(new Page<BookUser>(page, limit), wrapper);
        List<BookUser> records = bookUserPage.getRecords();
        return Rt.ok(count,records);
    }


    /**
     * 操作出入金
     * @param aliasName 帐号
     * @param moneyDollar1 金额
     * @param currency 币种（USD美元；HKD港元；EUR欧元；JPY日元；GBP英镑；CNH离岸人民币）
     */
    private void inOutOperate(String aliasName, Integer moneyDollar1, String currency) {
        log.info("账号["+aliasName+"]:"+moneyDollar1+"-"+currency);
        JSONObject result = ByxgjUtil.deposit(aliasName, (double)moneyDollar1 / 100, (double)moneyDollar1 / 10, currency, null);
        if(! result.get("Code").equals(0)){
            String errMsg = (String) result.get("Message");
            log.error("接口帐号[" + aliasName + "],:" +errMsg);
            throw new RRException(errMsg);
        }
    }

}
