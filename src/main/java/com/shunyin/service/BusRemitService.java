package com.shunyin.service;

import com.shunyin.common.util.Rt;
import com.shunyin.entity.BusRemit;
import com.baomidou.mybatisplus.service.IService;
import com.shunyin.pojo.BusRemitQuery;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wenjun
 * @since 2018-04-28
 */
public interface BusRemitService extends IService<BusRemit> {

    Boolean addRemitRecord(String realName,
                           String bankCardNum,
                           Integer money,
                           Integer dollar,
                           Float exchange,
                           String userName,
                           String toRealName,
                           String toBankName,
                           String toBankDetail,
                           String toBankCardNum,
                           HttpServletRequest request);

    Rt queryListByPage(BusRemitQuery query, int page, int limit);

    Boolean confirmReceive(Long remitId);
}
