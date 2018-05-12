package com.shunyin.service;

import com.shunyin.common.util.Rt;
import com.shunyin.entity.BusRemitBank;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wenjun
 * @since 2018-05-10
 */
public interface BusRemitBankService extends IService<BusRemitBank> {

    List<BusRemitBank> queryUsedBanks();

    Rt queryByPage(String cardNo, Integer page, Integer limit);

    BusRemitBank findOneById(Long id);

    Boolean updateByPrimay(BusRemitBank busRemitBank);
}
