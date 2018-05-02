package com.shunyin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.shunyin.entity.SysDict;
import com.shunyin.mapper.SysDictMapper;
import com.shunyin.service.SysDictService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SysDictMapper sysDictMapper;

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

    @Override
    public boolean updateDict(String exchange, String inCharge, String outCharge){
        SysDict exchangeDict = new SysDict();
        exchangeDict.setDictValue(exchange);
        EntityWrapper<SysDict> exchageWrapper = new EntityWrapper<>();
        exchageWrapper.eq("dict_code","US_EXCHANGE");
        boolean update_1 = this.update(exchangeDict, exchageWrapper);

        SysDict inChargeDict = new SysDict();
        inChargeDict.setDictValue(inCharge);
        EntityWrapper<SysDict> inWrapper = new EntityWrapper<>();
        inWrapper.eq("dict_code","IN_CHARGE");
        boolean update_2 = this.update(inChargeDict, inWrapper);

        SysDict outChargeDict = new SysDict();
        outChargeDict.setDictValue(outCharge);
        EntityWrapper<SysDict> outWrapper = new EntityWrapper<>();
        outWrapper.eq("dict_code","OUT_CHARGE");
        boolean update_3 = this.update(outChargeDict, outWrapper);

        return (update_1 || update_2 || update_3);
    }
}
