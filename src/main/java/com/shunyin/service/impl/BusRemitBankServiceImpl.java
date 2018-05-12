package com.shunyin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shunyin.common.util.Rt;
import com.shunyin.entity.BusRemitBank;
import com.shunyin.exception.RRException;
import com.shunyin.mapper.BusRemitBankMapper;
import com.shunyin.mapper.BusRemitMapper;
import com.shunyin.service.BusRemitBankService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wenjun
 * @since 2018-05-10
 */
@Service
public class BusRemitBankServiceImpl extends ServiceImpl<BusRemitBankMapper, BusRemitBank> implements BusRemitBankService {
    private static Logger log = LoggerFactory.getLogger(BusRemitBankServiceImpl.class);

    @Autowired
    private BusRemitBankMapper busRemitBankMapper;

    @Override
    public List<BusRemitBank> queryUsedBanks(){
        Wrapper<BusRemitBank> wrapper = new EntityWrapper<>();
        wrapper.eq("status",1);
        wrapper.orderBy("sort_id",true);
        int count = this.selectCount(wrapper);
        if(count<2){
            String errMsg = "设置收款的银行数量小于2个";
            log.error(errMsg);
            throw new RRException(errMsg);
        }
        Page<BusRemitBank> busRemitBankPage = this.selectPage(new Page<BusRemitBank>(1, 2), wrapper);
        return  busRemitBankPage.getRecords();
    }

    @Override
    public Rt queryByPage(String cardNo, Integer page, Integer limit){
        Wrapper<BusRemitBank> wrapper = new EntityWrapper<>();
        if(StringUtils.isNotEmpty(cardNo)){
            wrapper.eq("card_no",cardNo);
        }
        int count = this.selectCount(wrapper);
        Page<BusRemitBank> busRemitBankPage = this.selectPage(new Page<BusRemitBank>(page, limit), wrapper);
        List<BusRemitBank> records = busRemitBankPage.getRecords();
        return Rt.ok(count,records);
    }

    @Override
    public BusRemitBank findOneById(Long id){
        BusRemitBank busRemitBank2 = this.selectOne(new EntityWrapper<BusRemitBank>().eq("card_id", id));
        return busRemitBank2;
    }

    @Override
    public Boolean updateByPrimay(BusRemitBank busRemitBank){
        return this.update(busRemitBank, new EntityWrapper<BusRemitBank>().eq("card_id", busRemitBank.getCardId()));
    }
}
