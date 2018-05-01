package com.shunyin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.shunyin.entity.SysDict;
import com.shunyin.mapper.SysDictMapper;
import com.shunyin.service.SysDictService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wenjun
 * @since 2018-04-27
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    @Override
    public Map<String,Object> queryMainDictConst(){
        List<SysDict> sysDicts = this.selectList(new EntityWrapper<SysDict>().eq("remark", "const"));
        Map<String,Object> map = new HashMap<>();
        for (SysDict sysDict : sysDicts) {
            String dictCode = sysDict.getDictCode();
            String dictValue = sysDict.getDictValue();
            map.put(dictCode,dictValue);
        }
        return map;
    }

}
