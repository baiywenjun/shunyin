package com.shunyin.common.apiUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ByxgjUtil {
	private static Logger log = LoggerFactory.getLogger(ByxgjUtil.class);
	//请求参数map
     private static Map<String, Object> param = new HashMap<String, Object>();
	/**
	 * 开户 requestid：整数，可不填。如填写，返回结果中也会包含此值，用于将结果与请求对应起来； sa：管理员账号 sapass：管理员密码
	 * account：待开户的子账号，账号必须为正整数，取值返回1-2147483647。也可填0，填0时由系统自动分配账号，返回结果中包含分配的账号；
	 * password：待开户账号的密码； name：待开户账号的名称（直接使用GBK编码）；
	 * group：待开户账号所属的分组，可不填。不填时子账号将被创建至第一个分组；
	 * mainaccount：待开户帐号所属主账号，可不填。不填时主账号将被指定为第一个主账号；
	 * @return {"Code":"0","Message":"成功"}
	 ***/
	public static JSONObject createaccount(String phone, String password, String userId, String name) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("requestid", userId);
		param.put("sa", sa);
		param.put("sapass", sapass);
		//param.put("account", phone);
		param.put("password", password);
		param.put("name", name);
		param.put("group", "");
		param.put("mainaccount", "");
		log.debug("{createaccount param【"+param+"】}");
		String result=HttpRequestor.getInstance().GetByGbk(createaccount, param);;
		JSONObject json=Dom4j2Json.xml2Json(result);
		return	toResult(json);
	}
	
	//用于接口还未接通时测试使用
	public static String createTestAccount(int type,String account){
	
		if(type==0){
			// todo
		   //return DateUtils.getTenNum()+"";
	     }
		
		return account;
	}

	/**
	 *查帐户资金
	 * account 待查子账号
	 * */
	public static JSONObject queryaccount(String account){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("requestid",account);
		param.put("sa", ByxgjUtil.sa);
		param.put("sapass", ByxgjUtil.sapass);
		param.put("account",account);
	String result=HttpRequestor.getInstance().GetByGbk(queryaccount,param);
	   JSONObject resultJson=Dom4j2Json.xml2Json(result);
	   log.info("{请求查询账户资金,请求结果【"+resultJson+"】}");
		return resultJson;
	} 
	
	/**
	 * 账户出入金操作
	 * account：待出入金的子账号
		amount：总金额（劣后+优先），正值表示入金，负值表示出金；
		credit：总金额中的优先资金部分，正值表示入金，负值表示出金；
		basecapital：期初投入，正值表示入金，负值表示出金。用于计算净值，可不填；
		currency：币种（USD美元；HKD港元；EUR欧元；JPY日元；GBP英镑；CNH离岸人民币）
		remark：备注（直接使用GBK编码）
	 * **/
	public static JSONObject deposit(String account,double amount,double credit,String currency, String remark ){
		param.clear();
		param.put("account", account);//：待出入金的子账号
		param.put("amount", amount);//总金额（劣后+优先），正值表示入金，负值表示出金；
		param.put("credit", credit);
		//param.put("basecapital", value);
		param.put("currency", currency);//币种（USD美元；HKD港元；EUR欧元；JPY日元；GBP英镑；CNH离岸人民币）
		param.put("remark", remark);//备注（直接使用GBK编码）
		param.put("requestid",0);
		param.put("sa",sa);
		param.put("sapass",sapass);
		
	 String result=HttpRequestor.getInstance().GetByGbk(deposit,param);
	 log.debug("{deposit methoad result==>>【"+result+"】}");
	 //{"Result":{"Error":{"Code":"0","Message":"成功"},"RequestID":"0"}}
	  JSONObject resultJson=new JSONObject();
	   JSONObject tempJson=Dom4j2Json.xml2Json(result);
	   tempJson=tempJson.getJSONObject("Result");
	   tempJson=tempJson.getJSONObject("Error");
	   
	   resultJson.clear();
	   resultJson.put("Code",tempJson.getInteger("Code"));
	   resultJson.put("Message",tempJson.getString("Message"));
	 return resultJson;
	}
	
	/**
	 * 设置手续费费率
	 * **/
	public static JSONObject setcommissionrate(Integer requestid ,String account,String source){
		param.clear();
	    param.put("requestid",requestid);
		param.put("sa", sa);
		param.put("sapass",sapass);
		param.put("account", account);
		param.put("source", source);
	String result=HttpRequestor.getInstance().GetByGbk(setcommissionrate, param);
	  return Dom4j2Json.xml2Json(result);
	}
	
	public static JSONObject setcurrencyrate(Integer requestid ,String account,String source){
		param.clear();
	    param.put("requestid",requestid);
		param.put("sa", sa);
		param.put("sapass",sapass);
		param.put("account", account);
		param.put("source", source);
	String result=HttpRequestor.getInstance().GetByGbk(setcurrencyrate, param);
	  return Dom4j2Json.xml2Json(result);
	}
	
	/**
	 * 修改子账号数据
	 * 
	 * **/
	public static JSONObject changeaccount(Integer requestid ,String account,String password,String name){
		param.clear();
	    param.put("requestid",requestid);
		param.put("sa", sa);
		param.put("sapass",sapass);
		param.put("account", account);
		if(!StringUtils.isEmpty(password)){
			param.put("password", password);
		}
		
		if(!StringUtils.isEmpty(name)){
			param.put("name", name);
		}
		
	String result=HttpRequestor.getInstance().GetByGbk(changeaccount, param);
	  return Dom4j2Json.xml2Json(result);
				
	}
	
	/**
	 * 校验子账号的账户和密码
	 * 
	 * */
	public static JSONObject checkpassword(Integer requestid,String account,String password){
		param.clear();
	    param.put("requestid",requestid);
		param.put("sa", sa);
		param.put("sapass",sapass);
		param.put("account", account);
		param.put("password", password);
		String result=HttpRequestor.getInstance().doPost(checkpassword, param);//GetByGbk(checkpassword, param);
		  return Dom4j2Json.xml2Json(result);
	}
	
	private static JSONObject toResult(JSONObject xmlJsonStr){
		JSONObject resultJson=new JSONObject();
		
		String RequestID=xmlJsonStr.getString("RequestID");
		xmlJsonStr=xmlJsonStr.getJSONObject("Result");
		if(xmlJsonStr.containsKey("Account")){
			resultJson.put("Account",xmlJsonStr.getString("Account"));
		}
		
		//判断是否存在 Summary key,存在则是查询用户资金信息
		log.debug("{xmlJsonStr==》"+xmlJsonStr.containsKey("Summary")+"}");
		if(xmlJsonStr.containsKey("Summary")){ 
			log.debug("{查询用户资金信息}");
		     JSONArray  Summary=xmlJsonStr.getJSONArray("Summary");
		     for(int i=0;i<Summary.size();i++){
		    	
		    	   JSONObject temJson=Summary.getJSONObject(i);
		    	   log.debug("{查询用户资金信息 分币种【"+temJson+"】}");
		    	   if(!temJson.containsKey("Currency")||!"USD".equals(temJson.getString("Currency"))){
		    		   continue;
		    	   }
		    	   
		    	   resultJson.put("Available", temJson.getDouble("Available"));//可用
		    	   resultJson.put("Margin", temJson.getDouble("Margin"));//保证金
		    	   resultJson.put("Commission",  temJson.getDouble("Commission"));//手续费
		    	   resultJson.put("Deposit", temJson.getDouble("Deposit"));//入金
		    	   resultJson.put("Withdraw", temJson.getDouble("Withdraw"));//出金
		     }
		     
		}//判断是否是账户资金查询
		
		xmlJsonStr=xmlJsonStr.getJSONObject("Error");
		resultJson.put("RequestID", RequestID);
		resultJson.put("Code", xmlJsonStr.getInteger("Code"));
		resultJson.put("Message", xmlJsonStr.getString("Message"));
		return resultJson;
	}

	public static void main(String[] args) {
		//开户
		//JSONObject json = createaccount("13924210306", "210306", "210306", "张珊");
		//入金
//		double amount = 10;
//		double credit = (amount * 10);
//		String currency = "USD";
//		String remark = "";
//		String account = "1120020010";
//		JSONObject json= deposit(account, amount, credit, currency, remark);

		JSONObject json = queryaccount("1120020024");

		log.debug("{结果【" + json + "】}");
	}

  
  private static String  apiRootURL="https://106.15.47.118:13134";
	// 开户
	private static String createaccount = apiRootURL+"/createaccount";
	// 设置保证金率
	private static String setmarginrate = apiRootURL+"/setmarginrate";
	// 设置手续费率
	private static String setcommissionrate = apiRootURL+"/setcommissionrate";
	// 设置汇率
	private static String setcurrencyrate =apiRootURL+"/setcurrencyrate";
	// 出入金
	private static String deposit = apiRootURL+"/deposit";
	// 查询资金
	private static String queryaccount = apiRootURL+"/queryaccount";
	//修改子账户信息
	private static String changeaccount=apiRootURL+"/changeaccount";
	//子账号密码认证
	private static String checkpassword=apiRootURL+"/checkpassword";
	private static final String sa = "sgl1120020000";
	private static final String sapass = "110506";
}
