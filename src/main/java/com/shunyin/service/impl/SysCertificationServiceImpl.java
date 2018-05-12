package com.shunyin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.shunyin.entity.SysCertification;
import com.shunyin.mapper.SysCertificationMapper;
import com.shunyin.service.SysCertificationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wenjun
 * @since 2018-05-12
 */
@Service
public class SysCertificationServiceImpl extends ServiceImpl<SysCertificationMapper, SysCertification> implements SysCertificationService {

    @Override
    public Boolean addRecord(String idFrontFilePath, String idFrontUrl, String idBackFilePath, String idBackUrl,
                             String cardFrontFilePath, String cardFrontUrl, Long userId){
        SysCertification sysCertification = new SysCertification();
        sysCertification.setIdFrontFilePath(idFrontFilePath);
        sysCertification.setIdFrontUrl(idFrontUrl);
        sysCertification.setIdBackFilePath(idBackFilePath);
        sysCertification.setIdBackUrl(idBackUrl);
        sysCertification.setCardFrontFilePath(cardFrontFilePath);
        sysCertification.setCardFrontUrl(cardFrontUrl);
        sysCertification.setCreateTime(new Date());
        sysCertification.setIsUserId(userId);
        boolean insert = this.insert(sysCertification);
        return insert;
    }

    @Override
    public SysCertification findOneByUserId(Long userId){
        SysCertification sysCertification = this.selectOne(new EntityWrapper<SysCertification>().eq("is_user_id", userId));
        return sysCertification;
    }

}
