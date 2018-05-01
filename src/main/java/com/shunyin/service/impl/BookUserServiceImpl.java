package com.shunyin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shunyin.common.service.AuthHandler;
import com.shunyin.common.util.MoneyUtil;
import com.shunyin.common.util.Rt;
import com.shunyin.entity.BookUser;
import com.shunyin.mapper.BookUserMapper;
import com.shunyin.service.BookUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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

    @Override
    public Boolean addBookUserFromTransferAccount(String realName,
                                                  String bankCardNum,
                                                  Integer incomeCent,
                                                  Float exchange,
                                                  String userName,
                                                  Integer depositCent,
                                                  String toRealName,
                                                  String toBankName,
                                                  String toBankDetail,
                                                  String toBankCardNum){
        BookUser bookUser = new BookUser();
        bookUser.setSerialNo(UUID.randomUUID().toString().replace("-","").toUpperCase());
        //bookUser.setUserName(Integer.parseInt(userName));
        //bookUser.setIncome(incomeCent);
        bookUser.setMonetaryUnit("元");
        bookUser.setFlowWay("汇入");
        bookUser.setTakeFee(0);
        bookUser.setExchange(exchange);
//        bookUser.setDeposit(depositCent);
//        bookUser.setRemitRealName(realName);
//        bookUser.setRemitBankCard(bankCardNum);
//        bookUser.setToRealName(toRealName);
//        bookUser.setToBankCard(toBankCardNum);
//        bookUser.setToBankName(toBankName);
//        bookUser.setToBankDetail(toBankDetail);
        bookUser.setCreateTime(new Date());
        bookUser.setStatus("转账成功");
        bookUser.setType(0);//入金
        boolean flag = this.insert(bookUser);
        // todo 对接口帐号初入金操作
        //ByxgjUtil.depositCent("查询子账号", incomeCent/100, 12.23, "CNH", null);

        return flag;
    }

    @Override
    public Boolean addBookUserFromWithdrawal(Long userName, Float moneyDollar1,Float moneyDollar2, String unit,
                                             Float exchange, Integer takeFee, HttpServletRequest request){
        BookUser bookUser = new BookUser();
        bookUser.setSerialNo(UUID.randomUUID().toString().replace("-","").toUpperCase());
        bookUser.setUserName(userName);
        bookUser.setAliasUserName(AuthHandler.getSysUserTokenInfo(request).getAliasAccount());
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
        // todo 对接口帐号初入金操作
        //ByxgjUtil.depositCent("查询子账号", incomeCent/100, 12.23, "CNH", null);

        return true;
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

    private Rt queryBookListByPage(String userName, int page, int limit, Integer type) {
        Wrapper<BookUser> wrapper = new EntityWrapper<BookUser>()
                .eq("user_name", userName).eq("type", type).orderBy("create_time",false);
        int count = this.selectCount(wrapper);
        Page<BookUser> bookUserPage = this.selectPage(new Page<BookUser>(page, limit), wrapper);
        List<BookUser> records = bookUserPage.getRecords();
        return Rt.ok(count,records);
    }


}
