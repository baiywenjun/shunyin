package com.shunyin.service;

import com.shunyin.common.util.Rt;
import com.shunyin.entity.BookUser;
import com.baomidou.mybatisplus.service.IService;
import com.shunyin.pojo.BookUserQuery;

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

    Boolean addBookUserFromTransferAccount(String userName, String aliasName, Integer moneyDollar1, Integer moneyDollar2, String unit,
                                           Float exchange, Integer takeFee, String flowWay, String status, String payNo);

    Boolean addBookUserFromWithdrawalOffline(String userName, String aliasName, Integer money, Integer dollar, String unit,
                                             Float exchange, Integer takeFee);

    Boolean addBookUserFromWithdrawal(String userName, Integer moneyDollar1, Integer moneyDollar2, String unit,
                                      Float exchange, Integer takeFee, HttpServletRequest request);

    Rt queryInMoneyListByPage(String userName, int page, int limit);

    Rt queryOutMoneyListByPage(String userName, int page, int limit);

    // 提供admin
    Rt queryBookListByPage(BookUserQuery query, int page, int limit);
}
