package com.shunyin.service;

import com.shunyin.common.util.Rt;
import com.shunyin.entity.BusRemitOut;
import com.baomidou.mybatisplus.service.IService;
import com.shunyin.pojo.BusRemitQuery;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wenjun
 * @since 2018-05-09
 */
public interface BusRemitOutService extends IService<BusRemitOut> {

    Boolean addRemitOutRecord(String userName, String aliasUserName, String toRealName, String toBankCardNum, String tobankeName, String toBankInfo,
                              Integer remitMoney, Integer remitDollar, String unit, Float exchange, Integer takeFee);

    Rt queryListByPage(BusRemitQuery query, Integer page, Integer limit);

    Boolean confirmSend(String remitId, HttpServletRequest request);
}
