package com.shunyin.service;

import com.shunyin.common.util.Rt;
import com.shunyin.entity.BookUser;
import com.baomidou.mybatisplus.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wenjun
 * @since 2018-04-27
 */
public interface BookUserService extends IService<BookUser> {

    Boolean addBookUserFromTransferAccount(String realName,
                                           String bankCardNum,
                                           Integer income,
                                           Float exchange,
                                           String userName,
                                           Integer deposit,
                                           String toRealName,
                                           String toBankName,
                                           String toBankDetail,
                                           String toBankCardNum);

    Boolean addBookUserFromWithdrawal(Long userName, Float moneyDollar1, Float moneyDollar2, String unit,
                                      Float exchange, Integer takeFee, HttpServletRequest request);

    Rt queryInMoneyListByPage(String userName, int page, int limit);

    Rt queryOutMoneyListByPage(String userName, int page, int limit);
}
