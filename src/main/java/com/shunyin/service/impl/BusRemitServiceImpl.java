package com.shunyin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shunyin.common.service.AuthHandler;
import com.shunyin.common.util.Rt;
import com.shunyin.entity.BusRemit;
import com.shunyin.entity.SysUser;
import com.shunyin.mapper.BusRemitMapper;
import com.shunyin.mapper.SysUserMapper;
import com.shunyin.pojo.BusRemitQuery;
import com.shunyin.service.BookUserService;
import com.shunyin.service.BusRemitService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wenjun
 * @since 2018-04-28
 */
@Service
public class BusRemitServiceImpl extends ServiceImpl<BusRemitMapper, BusRemit> implements BusRemitService {

    @Autowired
    private BookUserService bookUserService;

    @Override
    public Boolean addRemitRecord(String realName,
                                  String bankCardNum,
                                  Integer money,
                                  Integer dollar,
                                  Float exchange,
                                  String userName,
                                  String toRealName,
                                  String toBankName,
                                  String toBankDetail,
                                  String toBankCardNum,
                                  HttpServletRequest request){
        BusRemit remit = new BusRemit();
        remit.setRemitRealName(realName);
        remit.setRemitBankCard(bankCardNum);
        remit.setRemitMoney(money);
        remit.setRemitDoller(dollar);
        remit.setExchange(exchange);
        remit.setToRealName(toRealName);
        remit.setToBankName(toBankName);
        remit.setToBankDetail(toBankDetail);
        remit.setToBankCard(toBankCardNum);
        remit.setUserName(userName);
        remit.setCreateTime(new Date());
        remit.setConfirmStatus(0);//0:未确认，1已确认
        // 获取账户别名
        SysUser sysUser = AuthHandler.getSysUserTokenInfo(request);
        remit.setAliasUserName(sysUser.getAliasAccount());
        boolean insert = this.insert(remit);
        return insert;
    }

    @Override
    public Rt queryListByPage(BusRemitQuery query, int page, int limit) {
        Wrapper<BusRemit> wrapper = new EntityWrapper<BusRemit>();
        if(query.getUserName() != null){
            wrapper.eq("user_name",query.getUserName());
        }
        if(StringUtils.isNotEmpty(query.getAliasUserName())){
            wrapper.eq("alias_user_name",query.getAliasUserName());
        }
        if(StringUtils.isNotEmpty(query.getRealName())){
            wrapper.like("remit_real_name",query.getRealName());
        }
        if(query.getConfirmStatus()!=null){
            wrapper.eq("confirm_status",query.getConfirmStatus());
        }
        int count = this.selectCount(wrapper);
        Page<BusRemit> busRemitPage = this.selectPage(new Page<BusRemit>(page, limit), wrapper);
        List<BusRemit> records = busRemitPage.getRecords();
        return Rt.ok(count,records);
    }

    @Override
    public Boolean confirmReceive(Long remitId) {
        BusRemit remit = this.selectById(remitId);
        // 确认收款
        remit.setConfirmStatus(1);
        // 添加账本记录
        Boolean addBookFlag = bookUserService.addBookUserFromTransferAccount(remit.getUserName(), remit.getAliasUserName(),
                remit.getRemitMoney(), remit.getRemitDollar(), "元", remit.getExchange(), remit.getRemitTakeFee(),
                "汇入","转账成功",null);
        boolean remitFlag = this.updateById(remit);
        return (addBookFlag || remitFlag);
    }


}
