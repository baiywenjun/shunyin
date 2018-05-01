package com.shunyin.common.apiUtil;

import java.math.BigDecimal;

/**
 * 金额处理工具类
 * **/
public class MoneyUtil {
	private static  double rate=7.2;
	private static double  poundage=0.3;
	
	
	public static double getPoundage() {
		return poundage;
	}
	public static void setPoundage(double poundage) {
		MoneyUtil.poundage = poundage;
	}
	public static double getRate() {
		return rate;
	}
	public static void setRate(double rate) {
		MoneyUtil.rate = rate;
	}
	/**
	 * 金额格式化 ###，###.00
	 * */
	public static double DecimalFormat(double moneyStr) {
		java.text.DecimalFormat myformat = new java.text.DecimalFormat();
		myformat.applyPattern("#####.00");
		return Double.parseDouble(myformat.format(moneyStr));
	}
	public static String DecimalFormat(String moneyStr) {
		 java.text.DecimalFormat df = new java.text.DecimalFormat("#.00"); 
		return df.format(moneyStr);
	}
	
	public static String DecimalFormat(BigDecimal money) {
	String  moneyDoubleValue=money.setScale(2,BigDecimal.ROUND_HALF_UP).toString();
		return moneyDoubleValue+"";
	}
	
	/**
	 * 
	 * 人民币兑换美元
	 * **/
	public static Double RmbToUSD(String rmb){
		Double rmbMoney=Double.parseDouble(rmb);
       double usd=1.0/rate*rmbMoney;
		return DecimalFormat(usd);
	}
   /**
    * 计算手续费
    * **/
	public static Double MoneyPoundage(double money){
	double result=(getPoundage()/100)*money;
	return DecimalFormat(result);
	}

	public static String doubleTrans(double d){
		  if(Math.round(d)-d==0){
		   return String.valueOf((long)d);
		  }
		  return String.valueOf(d);
     }
}
