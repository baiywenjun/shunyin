package com.shunyin.controller;

import cn.com.sandpay.api.gateway.SandpayConstants;
import cn.com.sandpay.api.gateway.request.GatewayOrderPayRequest;
import cn.com.sandpay.api.gateway.response.GatewayOrderPayResponse;
import cn.com.sandpay.cashier.sdk.SandpayClient;
import cn.com.sandpay.cashier.sdk.SandpayRequestHead;
import cn.com.sandpay.cashier.sdk.SandpayResponseHead;
import cn.com.sandpay.cashier.sdk.util.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.shunyin.common.util.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Title: 测试
 * Description: todoedit
 * author: wenjun
 * date: 2018/4/25 10:53
 */
@Controller
@RequestMapping("/test")
public class TestController {

    private static Logger log = LoggerFactory.getLogger(TestController.class);

    public static final String TEST_SERVER = "http://61.129.71.103:8003/gateway/api";
    public static final String PRODUCTION_SERVER = "https://cashier.sandpay.com.cn/gateway/api";

    @RequestMapping("/hello")
    public String hello(){
        System.out.println("hello");
        return "main_admin";
    }


    @RequestMapping("/test")
    @ResponseBody
    public R test(){
        return R.ok("test");
    }


    /**
     * 联调三德支付
     * @return
     */
    @RequestMapping("/pay")
    public String sandPayPage(){
        return "test/sandPay";
    }

    @RequestMapping("/payoff")
    public String sandPay(HttpServletRequest request, HttpServletResponse response, Model model){

        String qryString = request.getQueryString();
        log.info("qryString:"+qryString);
        log.debug("method[{}]", new Object[] { request.getMethod() });
        log.debug("agent[{}]", new Object[] { request.getHeader("user-agent") });

        Map<String, String[]> params = request.getParameterMap();
        JSONObject reqObj = new JSONObject();
        Map<String,String> map = new HashMap<String,String>();
        for (String key : params.keySet()) {
            String[] values = params.get(key);
            if (values.length > 0) {
                reqObj.put(key, values[0]);
                map.put(key, values[0]);
            }
        }

        // 组后台报文
        SandpayRequestHead head = new SandpayRequestHead();
        GatewayOrderPayRequest.GatewayOrderPayRequestBody body = new GatewayOrderPayRequest.GatewayOrderPayRequestBody();

        GatewayOrderPayRequest gwOrderPayReq = new GatewayOrderPayRequest();
        gwOrderPayReq.setHead(head);
        gwOrderPayReq.setBody(body);

        head.setVersion(SandpayConstants.DEFAULT_VERSION);
        head.setMethod("sandpay.trade.pay");
        // 产品编码
        head.setProductId("00000007");
        head.setAccessType("1");

        // 商户id
        head.setMid("10891008");
        head.setSubMid("");
        head.setSubMidAddr("");
        head.setSubMidName("");
        head.setChannelType("07");
        head.setReqTime(DateUtil.getCurrentDate14());

        body.setOrderCode(map.get("orderCode"));
        body.setTotalAmount(map.get("totalAmount"));
        body.setSubject(map.get("subject"));
        body.setBody(map.get("body"));
        body.setTxnTimeOut(map.get("txnTimeOut"));
        body.setPayMode(map.get("payMode"));
        body.setPayExtra(map.get("payExtra"));
        body.setClientIp(map.get("clientIp"));
        body.setNotifyUrl(map.get("notifyUrl"));
        body.setFrontUrl(map.get("frontUrl"));
        body.setStoreId(map.get("storeId"));
        body.setTerminalId(map.get("terminalId"));
        body.setOperatorId(map.get("operatorId"));
        body.setBizExtendParams(map.get("bizExtendParams"));
        body.setMerchExtendParams(map.get("merchExtendParams"));
        body.setExtend(map.get("extend"));

        try {
            //外网测试
           // GatewayOrderPayResponse gwPayResponse = SandpayClient.execute(gwOrderPayReq, "http://61.129.71.103:8003/gateway/api/order/pay");
            GatewayOrderPayResponse gwPayResponse = SandpayClient.execute(gwOrderPayReq, PRODUCTION_SERVER);
            //本地测试
            //GatewayOrderPayResponse gwPayResponse = SandpayClient.execute(gwOrderPayReq, "http://127.0.0.1:8080/pay-client/gateway/api/order/pay");
            //测试地址
            //GatewayOrderPayResponse gwPayResponse = SandpayClient.execute(gwOrderPayReq, "http://172.28.250.242:8084/gateway/api/order/pay");
            SandpayResponseHead respHead = gwPayResponse.getHead();

            if(SandpayConstants.SUCCESS_RESP_CODE.equals(respHead.getRespCode())) {
                log.info("txn success.");

                GatewayOrderPayResponse.GatewayOrderPayResponseBody respBody = gwPayResponse.getBody();
                String credential = respBody.getCredential();

                //request.setAttribute("JWP_ATTR", credential);
                //String url = "test/sandPay";
                //request.getRequestDispatcher(url).forward(request, response);
                model.addAttribute("JWP_ATTR",credential);

            } else {
                log.error("txn fail respCode[{}],respMsg[{}].", respHead.getRespCode(), respHead.getRespMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       log.info("req ok");
        return "test/result";
    }


}
