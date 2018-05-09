package com.shunyin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shunyin.common.util.MoneyUtil;
import com.shunyin.common.util.Rt;
import com.shunyin.entity.BookUser;
import com.shunyin.entity.BusRemit;
import com.shunyin.entity.BusRemitOut;
import com.shunyin.mapper.BusRemitOutMapper;
import com.shunyin.pojo.BusRemitQuery;
import com.shunyin.service.BookUserService;
import com.shunyin.service.BusRemitOutService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wenjun
 * @since 2018-05-09
 */
@Service
public class BusRemitOutServiceImpl extends ServiceImpl<BusRemitOutMapper, BusRemitOut> implements BusRemitOutService {

    @Autowired
    private BookUserService bookUserService;

    @Override
    public Boolean addRemitOutRecord(String userName, String aliasUserName, String toRealName, String toBankCardNum, String tobankeName, String toBankInfo,
                                     Integer remit1, Integer remit2, String unit, Float exchange, Integer takeFee){
        BusRemitOut remitOut = new BusRemitOut();
        remitOut.setUserName(userName);
        remitOut.setAliasUserName(aliasUserName);
        remitOut.setToRealName(toRealName);
        remitOut.setToBankCard(toBankCardNum);
        remitOut.setToBankName(tobankeName);
        remitOut.setToBankDetail(toBankInfo);
        if("CNY".equals(unit)){
            remitOut.setRemitMoney(remit1);
            remitOut.setRemitDollar(remit2);
            remitOut.setRemitUnit("元");
        }
        if("USD".equals(unit)){
            remitOut.setRemitDollar(remit1);
            remitOut.setRemitMoney(remit2);
            remitOut.setRemitUnit("美元");
        }
        //remitOut.setRemitMoney(remitMoney);
        //remitOut.setRemitDollar(remitDollar);
        //remitOut.setRemitUnit(unit);
        remitOut.setExchange(exchange);
        remitOut.setRemitTakeFee(takeFee);
        remitOut.setConfirmStatus(0);
        remitOut.setCreateTime(new Date());
        boolean insert = this.insert(remitOut);
        return insert;
    }

    @Override
    public Rt queryListByPage(BusRemitQuery query, Integer page, Integer limit){
        Wrapper<BusRemitOut> wrapper = new EntityWrapper<>();
        if(query.getUserName() != null){
            wrapper.eq("user_name",query.getUserName());
        }
        if(StringUtils.isNotEmpty(query.getAliasUserName())){
            wrapper.eq("alias_user_name",query.getAliasUserName());
        }
        if(StringUtils.isNotEmpty(query.getRealName())){
            wrapper.like("to_real_name",query.getRealName());
        }
        if(query.getConfirmStatus()!=null){
            wrapper.eq("confirm_status",query.getConfirmStatus());
        }
        int count = this.selectCount(wrapper);
        wrapper.orderBy("create_time",false);
        Page<BusRemitOut> busRemitPage = this.selectPage(new Page<BusRemitOut>(page, limit), wrapper);
        List<BusRemitOut> records = busRemitPage.getRecords();
        return Rt.ok(count,records);
    }

    @Override
    public Boolean confirmSend(String remitId, HttpServletRequest request){
        BusRemitOut remitOut = this.selectById(remitId);
        remitOut.setConfirmStatus(1);
        boolean confirmFlag = this.updateById(remitOut);
        Boolean flag = bookUserService.addBookUserFromWithdrawalOffline(remitOut.getUserName(),remitOut.getAliasUserName(),
                remitOut.getRemitMoney(), remitOut.getRemitDollar(), remitOut.getRemitUnit(), remitOut.getExchange(), remitOut.getRemitTakeFee());
        return (confirmFlag && flag);
    }
}
