package com.shunyin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shunyin.entity.SysAdmin;
import com.shunyin.mapper.SysAdminMapper;
import com.shunyin.service.SysAdminService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wenjun
 * @since 2018-04-25
 */
@Service
public class SysAdminServiceImpl extends ServiceImpl<SysAdminMapper, SysAdmin> implements SysAdminService {

    @Override
    public SysAdmin queryByAdminNameAndPwd(String adminName,String password){
        SysAdmin sysAdmin = this.selectOne(new EntityWrapper<SysAdmin>()
                .eq("admin_name", adminName)
                .and("password", password));
        return sysAdmin;
    }

}
