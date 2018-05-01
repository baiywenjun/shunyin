package com.shunyin.common.service;

import com.alibaba.fastjson.JSONObject;
import com.shunyin.common.apiUtil.ByxgjUtil;
import com.shunyin.entity.SysUser;
import com.shunyin.exception.RRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Title: todoedit
 * Description: todoedit
 * author: wenjun
 * date: 2018/4/26 17:00
 */
@Service("/apiService")
public class ApiHandler {

    private static Logger log = LoggerFactory.getLogger(ApiHandler.class);
    /**
     * 查询账号资金信息
     * @param request
     * @return
     */
    public Map<String,Object> queryAccountFund(HttpServletRequest request){
        SysUser sysUser = AuthHandler.getSysUserTokenInfo(request);
        JSONObject resultJson = ByxgjUtil.queryaccount(sysUser.getAliasAccount());
        log.info("[USERNAME]"+sysUser.getUserName());
        log.info("[ACCOUNT FUND]"+resultJson.toJSONString());
        Map result = (Map) resultJson.get("Result");
        String code = (String) ((Map)result.get("Error")).get("Code");
        if(!"0".equals(code)){
            throw new RRException("接口返回错误");
        }
        return result;
    }
}
