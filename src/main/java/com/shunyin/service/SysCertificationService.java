package com.shunyin.service;

import com.shunyin.entity.SysCertification;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wenjun
 * @since 2018-05-12
 */
public interface SysCertificationService extends IService<SysCertification> {

    Boolean addRecord(String idFrontFilePath, String idFrontUrl, String idBackFilePath, String idBackUrl,
                      String cardFrontFilePath, String cardFrontUrl, Long userId);

    SysCertification findOneByUserId(Long userId);
}
