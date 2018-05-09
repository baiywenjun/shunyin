function returnFloat(value){
	var value=Math.round(parseFloat(value)*100)/100;
	var xsd=value.toString().split(".");
	if(xsd.length==1){
		value=value.toString()+".00";
		return value;
	}
	if(xsd.length>1){
		if(xsd[1].length<2){
			value=value.toString()+"0";
		}
		return value;
	}
}
//检查登录状态
var Statdata
function checkloginstat(){
	/****************

	$.ajax({
		type: "post",
		url: "ajax_action.asp",
		dataType:"json",
		async: false,
		data: {"acttype":"checkloginstat"},
		success: function(data){
			try{$("#datatime").text(data.time)}catch(e){}
			if (data.success==0){//成功状态
				//alert(data.msg.split(",")[0]);
				Statdata=data.msg;
				for(var i=0;i<=18;i++){
					$("#dataval" + i).text(data.msg.split(",")[i]);
				}
				$("#datavalcur1").text(data.msg.split(",")[4]-data.msg.split(",")[15]);
				$("#datavalcur2").text(data.msg.split(",")[2]-data.msg.split(",")[15]);
				$("#databox2").html($("#databox1").html());
				$("#takemoneyAccount").text(Statdata.split(",")[0]);
				//$("#takemoneyBalance").text(Statdata.split(",")[2]);
				$("#takemoneyaccountMoney").text(Statdata.split(",")[2]-Statdata.split(",")[15]);
				$("#takemoneyaccountCredit").text(Statdata.split(",")[15]);
				$("#takemoneyAvailable").text(Statdata.split(",")[4]);
				rate1=parseFloat(data.conf_PayMoneyRate);
				rate2=parseFloat(data.conf_TakeMoneyRate);
				payfee=parseFloat(data.conf_PayFee);
				takefee=parseFloat(data.conf_TakeFee);
				credittype=data.CreditType;
				$("#payrate").text(rate1);
				$("#payrate2").text(rate1);
				$("#takerate").text(rate2);
				$("#fee1txt").text(payfee*100 + "%");
				$("#fee1html").text("("+payfee*100 + "%手续费)");
				$("#fee3txt").text(payfee2*100 + "%");
				$("#fee3html").text("("+payfee2*100 + "手续费)");
				$("#fee2txt").text(String(takefee));
				$("#taketime").text(data.conf_PayStarttime + " - " + data.conf_PayEndtime);
				// 授信显示
				if (credittype == 0) {
					$("#needcreditbox1").hide();
					$("#needcreditbox2").hide();
				}else{
					$("#needcreditbox1").show();
					$("#needcreditbox2").show();
				}
				
			}else{
				if (data.success>0){//错误报错状态
					alert(data.msg);
				}else{//需要重新登录状态
					document.body.innerHTML="";
					alert(data.msg);
					window.location=".";
				}
			}
		
		},
		error: function(){
			alert("读取出错。");
		}
	});	


	*******************/
}
var apcdtime
function getbankinfo(){
	/************
	$.ajax({
		type: "post",
		url: "ajax_action.asp",
		dataType:"json",
		async: true,
		data: {"acttype":"getbankinfo"},
		success: function(data){
			//try{$("#datatime").text(data.time)}catch(e){}
			if (data.success==0){//成功状态
				$("#bankval0").attr("src","images/bank_"+data.msg.split("|")[0]+".gif");
				for(var i=1;i<=6;i++){
					$("#bankval" + i).text(data.msg.split("|")[i]);
				}
				if(data.msg.split("|")[8]=="false"){//发现没有提交信息，弹出提示。
					apcdtime=3 * Number(data.msg.split("|")[9]);//倒计时，按照注册日 每天3秒
					AlertPostinfo();
					//alert(data.msg.split("|")[8]);
				};
				$("#bankinfobox").show();
				
			}else{
				//无银行信息弹框
				//AlertPostinfo();
				$("#bankinfobox").hide();
			}
		
		},
		error: function(){
			alert("读取出错。");
		}
	});	
	*******************/
}
function logout(){
    $.ajax({
        async: false,
        url: Global.baseUrl + '/auth/user/logout',
        type: 'get',
        dataType: 'json',
        success: function(data){
            if(data.code == 200){
                layer.alert("退出成功",{icon:1},function(index){
                    window.location.href = Global.baseUrl + "/";
                });
            }else{
                layer.alert("退出失败",{icon:0});
            }
        }
    });
	/*************
	$.ajax({
		type: "post",
		url: "ajax_action.asp",
		dataType:"json",
		async: false,
		data: {"acttype":"logout"},
		success: function(data){
			if (data.success==0){//成功状态
				//alert(data.msg.split(",")[0]);
				for(var i=0;i<=18;i++){
					$("#dataval" + i).text(data.msg.split(",")[i]);
				}
			}else{
				if (data.success>0){//错误报错状态
					alert(data.msg);
				}else{//需要重新登录状态
					alert(data.msg);
					window.location=".";
				}
			}
		
		},
		error: function(){
			alert("读取出错。");
		}
	});	
	*******************/
}



function getpayinfo(n,t){
	$("#PayInfo").html("");
	/***************
	jQuery.ajax({
		url		:	"ajax_action.asp",
		type	:	"POST",
		async	:	false,
		dataType:	"json",
		data	:	{acttype : "PayInfo",page:n},
		success	:	function(data){
			// 分页
			Page({
				num:data.iPageCount,	//页码数
				startnum:n,				//指定页码
				elem:$('#page1'),		//指定的元素
				callback:function(pn){	//回调函数
					getpayinfo(pn,t);
				}
			});
			if(data.iRecCount<=0){
				switch(t){
				case 3:
					appendPayRow(30);
					break;	
				default:
					appendPayRow(0);
					break;
				}
			}else{
				for (var i = 0; i < data.valuearr.length; i++) {
				//获取单个user信息
				   var rs = data.valuearr[i];
				   console.log(rs.TransactionId);
				//此处已经知道user数据的格式,故可以直接用user.username和user.age
					appendPayRow(t,rs.TransactionId,rs.TotalPrice,rs.WayFrom,rs.PayFee,rs.MoneyRate,rs.PayMoney,rs.Deposit,rs.Credit,rs.CreateTime,rs.Stat);
				
				};
			};
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alert(errorThrown);
			result = false;
		}
	});	
	*******************/
}

function appendPayRow(t,pr1,pr2,pr3,pr4,pr5,pr6,pr7,pr8,pr9,pr10) {
	var ui = document.getElementById("PayInfo");
	
	//添加新行
	var newRow = ui.insertRow(ui.rows.length);
	//添加新的单元格
	switch(t){
	case 3://读取3个元素的
		newRow.insertCell(0).innerHTML = pr2 + '元';
		newRow.insertCell(1).innerHTML = pr9;
		newRow.insertCell(2).innerHTML = pr10;
		break;
	case 30:
		newRow.insertCell(0).innerHTML = "&nbsp;";
		newRow.insertCell(1).innerHTML = "&nbsp;";
		newRow.insertCell(2).innerHTML = "&nbsp;";
		break;
	case 0:
		newRow.insertCell(0).innerHTML = "&nbsp;";
		newRow.insertCell(1).innerHTML = "&nbsp;";
		newRow.insertCell(2).innerHTML = "&nbsp;";
		newRow.insertCell(3).innerHTML = "&nbsp;";
		newRow.insertCell(4).innerHTML = "&nbsp;";
		newRow.insertCell(5).innerHTML = "&nbsp;";
		newRow.insertCell(6).innerHTML = "&nbsp;";
		newRow.insertCell(7).innerHTML = "&nbsp;";
		newRow.insertCell(8).innerHTML = "&nbsp;";
		newRow.insertCell(9).innerHTML = "&nbsp;";
		break;
	default:
		newRow.insertCell(0).innerHTML = pr1;
		newRow.insertCell(1).innerHTML = pr2 + '元';
		newRow.insertCell(2).innerHTML = pr3;
		newRow.insertCell(3).innerHTML = pr4 + '元';
		newRow.insertCell(4).innerHTML = pr5;
		newRow.insertCell(5).innerHTML = pr6 + '元';
		newRow.insertCell(6).innerHTML = pr7;
		newRow.insertCell(7).innerHTML = pr8;
		newRow.insertCell(8).innerHTML = pr9;
		newRow.insertCell(9).innerHTML = pr10;
		break;
	}
}

function gettakeinfo(n,t){
	$("#TakeInfo").html("");
	/**************
		url		:	"ajax_action.asp",
		type	:	"POST",
		async	:	false,
		dataType:	"json",
		data	:	{acttype : "TakeInfo",page:n},
		success	:	function(data){
			// 分页
			Page({
				num:data.iPageCount,	//页码数
				startnum:n,				//指定页码
				elem:$('#page2'),		//指定的元素
				callback:function(pn){	//回调函数
					gettakeinfo(pn,t);
				}
			});
			if(data.iRecCount<=0){
				switch(t){
				case 3:
					appendTakeRow(30);
					break;	
				default:
					appendTakeRow(0);
					break;
				}
			}else{
				for (var i = 0; i < data.valuearr.length; i++) {
					//获取单个user信息
					   var rs = data.valuearr[i];
					//此处已经知道user数据的格式,故可以直接用user.username和user.age
					appendTakeRow(t,rs.TransactionId,rs.AccountMoney,rs.WayFrom,rs.TakeFee,rs.MoneyRate,rs.TakeMoney,rs.Deposit,rs.Credit,rs.CreateTime,rs.Stat);
				}
			};
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alert(errorThrown);
			result = false;
		}
	});	
	*******************/
}

function appendTakeRow(t,pr1,pr2,pr3,pr4,pr5,pr6,pr7,pr8,pr9,pr10) {
	var ui = document.getElementById("TakeInfo");
	//添加新行
	var newRow = ui.insertRow(ui.rows.length);
	//添加新的单元格
	switch(t){
	case 3://读取3个元素的
		newRow.insertCell(0).innerHTML = pr2 + '美元';
		newRow.insertCell(1).innerHTML = pr9;
		newRow.insertCell(2).innerHTML = pr10;
		break;
	case 30:
		newRow.insertCell(0).innerHTML = "&nbsp;";
		newRow.insertCell(1).innerHTML = "&nbsp;";
		newRow.insertCell(2).innerHTML = "&nbsp;";
		break;
	case 0:
		newRow.insertCell(0).innerHTML = "&nbsp;";
		newRow.insertCell(1).innerHTML = "&nbsp;";
		newRow.insertCell(2).innerHTML = "&nbsp;";
		newRow.insertCell(3).innerHTML = "&nbsp;";
		newRow.insertCell(4).innerHTML = "&nbsp;";
		newRow.insertCell(5).innerHTML = "&nbsp;";
		newRow.insertCell(6).innerHTML = "&nbsp;";
		newRow.insertCell(7).innerHTML = "&nbsp;";
		newRow.insertCell(8).innerHTML = "&nbsp;";
		newRow.insertCell(9).innerHTML = "&nbsp;";
		break;
	default:
		newRow.insertCell(0).innerHTML = pr1;
		newRow.insertCell(1).innerHTML = pr2 + '美元';
		newRow.insertCell(2).innerHTML = pr3;
		newRow.insertCell(3).innerHTML = pr4 + '元';
		newRow.insertCell(4).innerHTML = pr5;
		newRow.insertCell(5).innerHTML = pr6 + '元';
		newRow.insertCell(6).innerHTML = pr7;
		newRow.insertCell(7).innerHTML = pr8;
		newRow.insertCell(8).innerHTML = pr9;
		newRow.insertCell(9).innerHTML = pr10;
		break;
	}
}

function popLoading(){
	var popLayer = document.getElementById('popLayer');
	popLayer.style.width = $(window).width();
	popLayer.style.height = $(window).height();
	popLayer.style.display = "block";
	$(".loading").show();
}//end func popBox()

function closeLoading(){
	var popLayer = document.getElementById('popLayer');
	if($(".mesbox").is(':hidden')){
		$("#popLayer").hide();
	}
	$(".loading").hide();
}//end func popBox()

function popAlert(msg,fun1){
	var popLayer = document.getElementById('popLayer');
	popLayer.style.width = $(window).width();
	popLayer.style.height = $(window).height();
	popLayer.style.display = "block";
	// $("#mescont").css("min-width","300px");
	// $("#mescont").css("width","300px");
	
	$("#mescont").html(msg);
	$(".mesbtn .btnok").unbind();
	$(".mesbtn .btnno").unbind();
	//alert(typeof(fun1));//undefined
	$(".mesbtn .btnok").click(function(){closeAlert();});
	if(typeof(fun1)=="function"){$(".mesbtn .btnok").click(function(){fun1();});}
	$(".mesbtn .btnno").click(function(){closeAlert();});
	$(".mesbtn .btnno").hide();
	$(".mesbox").show();
}//end func popBox()
function closeAlert(){
	if($(".loading").is(':hidden')){
		$("#popLayer").hide();
	}
	$(".mesbox").hide();
}//end func popBox()


function popConfirm(msg,fun1,fun2){
	var popLayer = document.getElementById('popLayer');
	popLayer.style.width = $(window).width();
	popLayer.style.height = $(window).height();
	popLayer.style.display = "block";
	$("#mescont").html(msg);
	// $("#mescont").css("min-width","350px");
	// $("#mescont").css("width","350px");
	$(".mesbtn .btnok").unbind();
	$(".mesbtn .btnno").unbind();
	//alert(typeof(fun1));//undefined
	$(".mesbtn .btnok").click(function(){closeConfirm();});
	if(typeof(fun1)=="function"){$(".mesbtn .btnok").click(function(){fun1();});}
	$(".mesbtn .btnno").click(function(){closeConfirm();});
	if(typeof(fun2)=="function"){$(".mesbtn .btnok").click(function(){fun2();});}
	$(".mesbtn .btnno").show();
	$(".mesbox").show();
}//end func popBox()
function closeConfirm(){
	if($(".loading").is(':hidden')){
		$("#popLayer").hide();
	}
	$(".mesbox").hide();
}//end func popBox()


function sendsumapay(){   //丰付支付
	var minmoney=0.01;
	var maxmoney=10000000;
	if($("#kj_payway").val()==""){
		popAlert("请选择要使用的银行。");
		$("#kj_payway").focus();
		return false;
	};	
	if(isNaN($("#kj_money").val()) || $("#kj_money").val()=="" || $("#kj_money").val()<=minmoney || $("#kj_money").val()>maxmoney){
		popAlert("填入的金额不正确。");
		//$("#kj_money").focus();
		return false;
	};
	//alertConfirm(function(){formtosumapay();}, '系统将会弹出【丰付支付】的转账页面。<br/><span style="color:red">【请注意】切勿修改任何转账信息，造成系统无法为您转账成功。</span>')
	popConfirm('系统将会弹出银行的网银页面。<br/>切勿修改任何转账信息，造成系统无法为您转账成功。',function(){closeConfirm();formtosumapay();},function(){closeConfirm()});
}

function formtosumapay(){
	var requestId,tradeProcess,totalBizType,totalPrice,backurl,returnurl,noticeurl,description,mersignature,goodsDesc,allowRePay,rePayTimeOut,userIdIdentity,bankCardType,payTag,sumapaySignatureSign,bankMchName,bankMchId,payType,bankCode,act,react,desid
	$("#payAmount").val($("#kj_money").val());
	/***************
	jQuery.ajax({
		url		:	"../Sumapay_Create.asp",
		type	:	"POST",
		async	:	false,
		dataType:	"json",
		data	:	{money : $("#kj_money").val(),payway :$("#kj_payway").val(),account:$("#account").html(),needcredit:$('#kj_needcredit1').val()},
		success	:	function(data){
			if(data.success){
				//alert("生成支付订单成功");
				requestId=data.requestId;
				tradeProcess=data.tradeProcess;
				totalBizType=data.totalBizType;
				totalPrice=data.totalPrice;
				backurl=data.backurl;
				returnurl=data.returnurl;
				noticeurl=data.noticeurl;
				description=data.description;
				mersignature=data.mersignature;
				goodsDesc=data.goodsDesc;
				allowRePay=data.allowRePay;
				rePayTimeOut=data.rePayTimeOut;
				userIdIdentity=data.userIdIdentity;
				bankCardType=data.bankCardType;
				payTag=data.payTag;
				sumapaySignatureSign=data.sumapaySignatureSign;
				bankMchName=data.bankMchName;
				bankMchId=data.bankMchId;
				payType=data.payType;
				bankCode=data.bankCode;
				act=data.act;
				react=data.react;
				desid=data.desid
				
			}else{
				popAlert(data.msg);
				return false;
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alert(errorThrown);
			result = false;
		}
	});	

	*******************/


	try{
		//window.open(react);
		//alert(react);
		window.location=react;
		$("#reactform").attr("action",react);
		$("#reactform").attr("method","POST");
		$("#reactform").submit();
		setTimeout('popConfirm("请在浏览器中继续完成网银转账。<br/>是否已经完成转账？",function(){checkpaymoney("'+desid+'")});',500);
		return true;
		//reactform.action=react;
		//reactform.submit();
	}catch(e){
		window.location=react;
	
	}
}


function sendpayease(){
	var minmoney=1;
	var maxmoney=10000000;
	if($("#kj_payway").val()==""){
		popAlert("请选择要使用的银行。");
		$("#kj_payway").focus();
		return false;
	};	
	if(isNaN($("#kj_money").val()) || $("#kj_money").val()=="" || $("#kj_money").val()<=0 || $("#kj_money").val()>9999999){
		popAlert("填入的金额不正确。");
		//$("#kj_money").focus();
		return false;
	};
	if($("#kj_money").val()<minmoney){
		popAlert("填入的金额不正确，转账金额必须大于"+minmoney+"。");
		//$("#kj_money").focus();
		return false;
	};

	//alertConfirm(function(){formtosumapay();}, '系统将会弹出【丰付支付】的转账页面。<br/><span style="color:red">【请注意】切勿修改任何转账信息，造成系统无法为您转账成功。</span>')
	popConfirm('系统将会弹出银行的网银页面。<br/>切勿修改任何转账信息，造成系统无法为您转账成功。',function(){formtopayease();},function(){closeConfirm()});
}

function formtopayease(){
	var v_mid,v_oid,v_rcvname,v_rcvaddr,v_rcvtel,v_rcvpost,v_ymd,v_orderstatus,v_ordername,v_moneytype,v_url,v_md5info,v_amount,v_pmode,act,react,desid
	$("#v_amount").val($("#kj_money").val());
	/**************
	jQuery.ajax({
		url		:	"../PayEase_Create.asp",
		type	:	"POST",
		async	:	false,
		dataType:	"json",
		data	:	{money : $("#kj_money").val(),payway :$("#kj_payway").val(),account:$("#dataval0").text(),needcredit:$('#kj_needcredit1').val()},
		success	:	function(data){
			if(data.success){
				//alert("生成支付订单成功");
v_mid=data.v_mid;
v_oid=data.v_oid;
v_rcvname=data.v_rcvname;
v_rcvaddr=data.v_rcvaddr;
v_rcvtel=data.v_rcvtel;
v_rcvpost=data.v_rcvpost;
v_ymd=data.v_ymd;
v_orderstatus=data.v_orderstatus;
v_ordername=data.v_ordername;
v_moneytype=data.v_moneytype;
v_url=data.v_url;
v_md5info=data.v_md5info;
v_amount=data.v_amount;
v_pmode=data.v_pmode;
react=data.react;
desid=data.desid;
				act=data.act;
			}else{
				popAlert(data.msg);
				return false;
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alert(errorThrown);
			result = false;
		}
	});	

	*******************/

	try{
		//window.open(react);
		//alert(react);
		window.location=react;
		$("#reactform").attr("action",react);
		$("#reactform").attr("method","POST");
		$("#reactform").submit();
		setTimeout('popConfirm("请在浏览器中继续完成网银转账。<br/>是否已经完成转账？",function(){checkpaymoney("'+desid+'")});',500);
		return true;
		//reactform.action=react;
		//reactform.submit();
	}catch(e){
		window.location=react;
	
	}

}


function sendyeepay(){//易宝支付
	var minmoney=0.01;
	var maxmoney=10000000;
	if($("#kj_payway").val()==""){
		popAlert("请选择要使用的银行。");
		$("#kj_payway").focus();
		return false;
	};	
	if(isNaN($("#kj_money").val()) || $("#kj_money").val()=="" || $("#kj_money").val()<=0 || $("#kj_money").val()>9999999){
		popAlert("填入的金额不正确。");
		//$("#kj_money").focus();
		return false;
	};
	if($("#kj_money").val()<minmoney){
		popAlert("填入的金额不正确，转账金额必须大于"+minmoney+"。");
		//$("#kj_money").focus();
		return false;
	};

	//alertConfirm(function(){formtosumapay();}, '系统将会弹出【丰付支付】的转账页面。<br/><span style="color:red">【请注意】切勿修改任何转账信息，造成系统无法为您转账成功。</span>')
	popConfirm('系统将会弹出银行的网银页面。<br/>切勿修改任何转账信息，造成系统无法为您转账成功。',function(){formtoyeepay();},function(){closeConfirm()});
}

function formtoyeepay(){
	var p0_Cmd,p1_MerId,p2_Order,p3_Amt,p4_Cur,p5_Pid,p6_Pcat,p7_Pdesc,p8_Url,p9_SAF,pa_MP,pd_FrpId,pr_NeedResponse,hmac,act
	$("#v_amount").val($("#kj_money").val());
	/******************
	jQuery.ajax({
		url		:	"YeePay_Create.asp",
		type	:	"POST",
		async	:	false,
		dataType:	"json",
		data	:	{money : $("#kj_money").val(),payway :$("#kj_payway").val(),account:$("#account").html(),needcredit:$('#kj_needcredit1').val()},
		success	:	function(data){
			if(data.success){
				//alert("生成支付订单成功");
p0_Cmd=data.p0_Cmd;
p1_MerId=data.p1_MerId;
p2_Order=data.p2_Order;
p3_Amt=data.p3_Amt;
p4_Cur=data.p4_Cur;
p5_Pid=data.p5_Pid;
p6_Pcat=data.p6_Pcat;
p7_Pdesc=data.p7_Pdesc;
p8_Url=data.p8_Url;
p9_SAF=data.p9_SAF;
pa_MP=data.pa_MP;
pd_FrpId=data.pd_FrpId;
pr_NeedResponse=data.pr_NeedResponse;
hmac=data.hmac;
				act=data.act;
			}else{
				popAlert(data.msg);
				return false;
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alert(errorThrown);
			result = false;
		}
	});	
	*******************/
	
	try{
		//window.open(react);
		//alert(react);
		window.location=react;
		$("#reactform").attr("action",react);
		$("#reactform").attr("method","POST");
		$("#reactform").submit();
		setTimeout('popConfirm("请在浏览器中继续完成网银转账。<br/>是否已经完成转账？",function(){checkpaymoney("'+desid+'")});',500);
		return true;
		//reactform.action=react;
		//reactform.submit();
	}catch(e){
		window.location=react;
	
	}
}

function sendlianlianpay(){//连连支付
	var minmoney=0.01;
	var maxmoney=10000000;
	if($("#kj_payway").val()==""){
		popAlert("请选择要使用的银行。");
		$("#kj_payway").focus();
		return false;
	};	
	if(isNaN($("#kj_money").val()) || $("#kj_money").val()=="" || $("#kj_money").val()<=0 || $("#kj_money").val()>9999999){
		popAlert("填入的金额不正确。");
		//$("#kj_money").focus();
		return false;
	};
	if($("#kj_money").val()<minmoney){
		popAlert("填入的金额不正确，金额不能小于"+minmoney+"。");
		//$("#kj_money").focus();
		return false;
	};

	//alertConfirm(function(){formtosumapay();}, '系统将会弹出【丰付支付】的转账页面。<br/><span style="color:red">【请注意】切勿修改任何转账信息，造成系统无法为您转账成功。</span>')
	popConfirm('系统将会弹出银行的网银页面。<br/>切勿修改任何转账信息，造成系统无法为您转账成功。',function(){formtolianlianpay();},function(){closeConfirm()});
}

function formtolianlianpay(){
	var version,charset_name,oid_partner,user_id,timestamp,sign_type,sign,busi_partner,no_order,dt_order,name_goods,info_order
	var money_order,notify_url,url_return,userreq_ip,url_order,valid_order,bank_code,pay_type,risk_item,act,lianlianactway
	$("#v_amount").val($("#kj_money").val());
	/*****************
	jQuery.ajax({
		url		:	"LianlianPay_Create.asp",
		type	:	"POST",
		async	:	false,
		dataType:	"json",
		data	:	{money : $("#kj_money").val(),payway :$("#kj_payway").val(),account:$("#account").html(),needcredit:$('#kj_needcredit1').val()},
		success	:	function(data){
			if(data.success){
				//alert("生成支付订单成功");
version=data.version;
charset_name=data.charset_name;
oid_partner=data.oid_partner;
user_id=data.user_id;
timestamp=data.timestamp;
sign_type=data.sign_type;
sign=data.sign;
busi_partner=data.busi_partner;
no_order=data.no_order;
dt_order=data.dt_order;
name_goods=data.name_goods;
info_order=data.info_order;
money_order=data.money_order;
notify_url=data.notify_url;
url_return=data.url_return;
userreq_ip=data.userreq_ip;
url_order=data.url_order;
valid_order=data.valid_order;
bank_code=data.bank_code;
pay_type=data.pay_type;
risk_item=data.risk_item;
				act=data.act;
				lianlianactway=data.lianlianactway;
			}else{
				popAlert(data.msg);
				return false;
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alert(errorThrown);
			result = false;
		}
	});	
	*******************/
	
	try{
		//window.open(react);
		//alert(react);
		window.location=react;
		$("#reactform").attr("action",react);
		$("#reactform").attr("method","POST");
		$("#reactform").submit();
		setTimeout('popConfirm("请在浏览器中继续完成网银转账。<br/>是否已经完成转账？",function(){checkpaymoney("'+desid+'")});',500);
		return true;
		//reactform.action=react;
		//reactform.submit();
	}catch(e){
		window.location=react;
	
	}
}


function sendjiupaipay(){//九派支付
	var minmoney=10;
	var maxmoney=10000000;
	if($("#kj_payway").val()==""){
		popAlert("请选择要使用的银行。");
		$("#kj_payway").focus();
		return false;
	};	
	if(isNaN($("#kj_money").val()) || $("#kj_money").val()=="" || $("#kj_money").val()<=0){
		popAlert("填入的金额不正确。");
		//$("#kj_money").focus();
		return false;
	};
	if($("#kj_money").val()<minmoney){
		popAlert("填入的金额不正确，转账金额必须大于"+minmoney+"。");
		//$("#kj_money").focus();
		return false;
	};
	if($("#kj_money").val()>maxmoney){
		popAlert("填入的金额不正确，转账金额必须小于"+maxmoney+"。");
		//$("#kj_money").focus();
		return false;
	};

	//alertConfirm(function(){formtosumapay();}, '系统将会弹出【丰付支付】的转账页面。<br/><span style="color:red">【请注意】切勿修改任何转账信息，造成系统无法为您转账成功。</span>')
	//popConfirm('系统将会弹出银行的网银页面。<br/>切勿修改任何转账信息，造成系统无法为您转账成功。',function(){formtojiupaipay();},function(){closeConfirm()});
	return formtojiupaipay();
}

function formtojiupaipay(){
	var charset,version,merchantId,requestTime,requestId,service,signType,merchantCert,merchantSign,pageReturnUrl,notifyUrl,merchantName,memberId,orderTime
	var orderId,totalAmount,currency,bankAbbr,cardType,payType,validNum,validUnit,showUrl,goodsName,goodsId,goodsDesc,act,desid,react
	$("#v_amount").val($("#money").val());
	/********************
	jQuery.ajax({
		url		:	"../JiupaiPay_Create.asp",
		type	:	"POST",
		async	:	false,
		dataType:	"json",
		data	:	{money : $("#kj_money").val(),payway :$("#kj_payway").val(),account:$("#dataval0").text(),needcredit:$('#kj_needcredit1').val()},
		success	:	function(data){
			if(data.success){
				//alert("生成支付订单成功");
	charset=data.charset;
	version=data.version;
	merchantId=data.merchantId;
	requestTime=data.requestTime;
	requestId=data.requestId;
	service=data.service;
	signType=data.signType;
	merchantCert=data.merchantCert;
	merchantSign=data.merchantSign;
	pageReturnUrl=data.pageReturnUrl;
	notifyUrl=data.notifyUrl;
	merchantName=data.merchantName;
	memberId=data.memberId;
	orderTime=data.orderTime;
	orderId=data.orderId;
	totalAmount=data.totalAmount;
	currency=data.currency;
	bankAbbr=data.bankAbbr;
	cardType=data.cardType;
	payType=data.payType;
	validNum=data.validNum;
	validUnit=data.validUnit;
	showUrl=data.showUrl;
	goodsName=data.goodsName;
	goodsId=data.goodsId;
	goodsDesc=data.goodsDesc;
	desid=data.desid;
	react=data.react;
				act=data.act;
			}else{
				popAlert(data.msg);
				return false;
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alert(errorThrown);
			result = false;
		}
	});	
	
*********************/
	try{
		//window.open(react);
		//alert(react);
		window.location=react;
		//$("#reactform").attr("action",react);
		//$("#reactform").attr("method","POST");
		//$("#reactform").submit();
		//setTimeout('popConfirm("请在浏览器中继续完成网银转账。<br/>是否已经完成转账？",function(){checkpaymoney("'+desid+'")});',500);
		return true;
		//reactform.action=react;
		//reactform.submit();
	}catch(e){
		window.location=react;
	
	}
}

function sendgopay(){//国付宝支付
	var minmoney=1;
	var maxmoney=10000000;
	if($("#kj_payway").val()==""){
		popAlert("请选择要使用的银行。");
		$("#kj_payway").focus();
		return false;
	};	
	if(isNaN($("#kj_money").val()) || $("#kj_money").val()=="" || $("#kj_money").val()<=0){
		popAlert("填入的金额不正确。");
		//$("#kj_money").focus();
		return false;
	};
	if($("#kj_money").val()<minmoney){
		popAlert("填入的金额不正确，转账金额必须大于"+minmoney+"。");
		//$("#kj_money").focus();
		return false;
	};
	if($("#kj_money").val()>maxmoney){
		popAlert("填入的金额不正确，转账金额必须小于"+maxmoney+"。");
		//$("#kj_money").focus();
		return false;
	};

	//popConfirm('系统将会弹出银行的网银页面。<br/>切勿修改任何转账信息，造成系统无法为您转账成功。',function(){formtogopay();},function(){closeConfirm()});
	return formtogopay();
}

function formtogopay(){
	var version,charset,language,signType,tranCode,merchantID,merOrderNum,tranAmt,feeAmt,currencyType,frontMerUrl,backgroundMerUrl,tranDateTime,virCardNoIn,tranIP,isRepeatSubmit
	var goodsName,goodsDetail,buyerName,buyerContact,merRemark1,merRemark2,signValue,buyerRealMobile,buyerRealName,buyerRealCertNo,buyerRealBankAcctNum,gopayServerTime,bankCode,userType,act,desid,react
	$("#v_amount").val($("#money").val());
	/*****************
	jQuery.ajax({
		url		:	"../Gopay_Create.asp",
		type	:	"POST",
		async	:	false,
		dataType:	"json",
		data	:	{money : $("#kj_money").val(),payway :$("#kj_payway").val(),account:$("#dataval0").text(),needcredit:$('#kj_needcredit1').val()},
		success	:	function(data){
			if(data.success){
				//alert("生成支付订单成功");
	version=data.version;
	charset=data.charset;
	language=data.language;
	signType=data.signType;
	tranCode=data.tranCode;
	merchantID=data.merchantID;
	merOrderNum=data.merOrderNum;
	tranAmt=data.tranAmt;
	feeAmt=data.feeAmt;
	currencyType=data.currencyType;
	frontMerUrl=data.frontMerUrl;
	backgroundMerUrl=data.backgroundMerUrl;
	tranDateTime=data.tranDateTime;
	virCardNoIn=data.virCardNoIn;
	tranIP=data.tranIP;
	isRepeatSubmit=data.isRepeatSubmit;
	goodsName=data.goodsName;
	goodsDetail=data.goodsDetail;
	buyerName=data.buyerName;
	buyerContact=data.buyerContact;
	merRemark1=data.merRemark1;
	merRemark2=data.merRemark2;
	signValue=data.signValue;
	buyerRealMobile=data.buyerRealMobile;
	buyerRealName=data.buyerRealName;
	buyerRealCertNo=data.buyerRealCertNo;
	buyerRealBankAcctNum=data.buyerRealBankAcctNum;
	gopayServerTime=data.gopayServerTime;
	bankCode=data.bankCode;
	userType=data.userType;
	
	desid=data.desid;
	react=data.react;
				act=data.act;
			}else{
				popAlert(data.msg);
				return false;
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alert(errorThrown);
			result = false;
		}
	});	
	
	*******************/

	try{
		//window.open(react);
		//alert(react);
		window.location=react;
		$("#reactform").attr("action",react);
		$("#reactform").attr("method","POST");
		$("#reactform").submit();
		setTimeout('popConfirm("请在浏览器中继续完成网银转账。<br/>是否已经完成转账？",function(){checkpaymoney("'+desid+'")});',500);
		return true;
		//reactform.action=react;
		//reactform.submit();
	}catch(e){
		window.location=react;
	
	}
	//alert(detectOS()+','+$.browser.msie+','+$.browser.version);
}

function sendunspay(){//银生宝支付
	var minmoney=1;
	var maxmoney=10000000;
	if($("#kj_payway").val()==""){
		popAlert("请选择要使用的银行。");
		$("#kj_payway").focus();
		return false;
	};	
	if(isNaN($("#kj_money").val()) || $("#kj_money").val()=="" || $("#kj_money").val()<=0){
		popAlert("填入的金额不正确。");
		//$("#kj_money").focus();
		return false;
	};
	if($("#kj_money").val()<minmoney){
		popAlert("填入的金额不正确，转账金额必须大于"+minmoney+"。");
		//$("#kj_money").focus();
		return false;
	};
	if($("#kj_money").val()>maxmoney){
		popAlert("填入的金额不正确，转账金额必须小于"+maxmoney+"。");
		//$("#kj_money").focus();
		return false;
	};

	//popConfirm('系统将会弹出银行的网银页面。<br/>切勿修改任何转账信息，造成系统无法为您转账成功。',function(){formtogopay();},function(){closeConfirm()});
	return formtounspay();
}

function formtounspay(){
	var version,merchantId,merchantUrl,responseMode,orderId,currencyType,amount,assuredPay,time,remark,mac,bankCode,b2b,commodity,orderUrl,cardAssured,merchantKey,frontURL
	var act,desid,react
	$("#v_amount").val($("#money").val());
	/***************
	jQuery.ajax({
		url		:	"../Unspay_Create.asp",
		type	:	"POST",
		async	:	false,
		dataType:	"json",
		data	:	{money : $("#kj_money").val(),payway :$("#kj_payway").val(),account:$("#dataval0").text(),needcredit:$('#kj_needcredit1').val()},
		success	:	function(data){
			if(data.success){
				//alert("生成支付订单成功");
				version=data.version;
				merchantId=data.merchantId;
				merchantUrl=data.merchantUrl;
				responseMode=data.responseMode;
				orderId=data.orderId;
				currencyType=data.currencyType;
				amount=data.amount;
				assuredPay=data.assuredPay;
				time=data.time;
				remark=data.remark;
				mac=data.mac;
				bankCode=data.bankCode;
				b2b=data.b2b;
				commodity=data.commodity;
				orderUrl=data.orderUrl;
				cardAssured=data.cardAssured;
				merchantKey=data.merchantKey;
				frontURL=data.frontURL;
				desid=data.desid;
				react=data.react;
							act=data.act;
				try{
					//window.open(react);
					//alert(react);
					window.location=react;
					$("#reactform").attr("action",react);
					$("#reactform").attr("method","POST");
					$("#reactform").submit();
					setTimeout('popConfirm("请在浏览器中继续完成网银转账。<br/>是否已经完成转账？",function(){checkpaymoney("'+desid+'")});',500);
					return true;
					//reactform.action=react;
					//reactform.submit();
				}catch(e){
					window.location=react;
				}
			}else{
				popAlert(data.msg);
				return false;
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alert(errorThrown);
			result = false;
		}
	});	
	*******************/


	//alert(detectOS()+','+$.browser.msie+','+$.browser.version);
}

// 转入资金提交
function sendbankpay(){//线下汇款
	var minmoney=1;
	var maxmoney=10000000;

	if($(".clearfix .bank_on .companyName").text()==""){
		popAlert("请选择您要汇入的银行。");
		//$("#kj_money3").focus();
		return false;
	}
	if(isNaN($("#kj_money3").val()) || $("#kj_money3").val()=="" || $("#kj_money3").val()<=0){
		popAlert("填入的金额不正确。"+$("#kj_money3").val());
		//$("#kj_money3").focus();
		return false;
	};
	if($("#kj_money3").val()<minmoney){
		popAlert("填入的金额不正确，转账金额必须大于"+minmoney+"。");
		//$("#kj_money3").focus();
		return false;
	};
	if($("#kj_money3").val()>maxmoney){
		popAlert("填入的金额不正确，转账金额必须小于"+maxmoney+"。");
		//$("#kj_money3").focus();
		return false;
	};

	//popConfirm('系统将会弹出银行的网银页面。<br/>切勿修改任何转账信息，造成系统无法为您转账成功。',function(){formtogopay();},function(){closeConfirm()});
	return formtobankpay();
}

function formtobankpay(){
	var msg
	$("#v_amount").val($("#money").val());

	jQuery.ajax({
		url		:	Global.baseUrl+"/user/transferAccountApply",
		type	:	"POST",
		async	:	false,
		dataType:	"json",
		data	:	{
			money : $("#kj_money3").val(),
			dollar : $("#deposit3").text(),
			//income:$("#paymoney2").val(),
			// 转账姓名
            realName :$("#realname").val(),
			// 银行卡号
            bankCardNum :$("#bankcardnum").val(),
			// 帐号id
            userName:$("#dataval0").text(),
			// 汇率
            exchange:$("#payrate2").text(),
			// 存款
            //balance:$("#dataval4").text(),
			// needcredit:$('#kj_needcredit1').val() ,
            toRealName:$(".clearfix .bank_on .companyName").text() ,
            toBankName:$(".clearfix .bank_on .bankCardlogo").attr("title") ,
			//ToBankCode:$(".clearfix .bank_on .bankCardlogo").attr("src") ,
			// ToBankProvince:"" ,
			// ToBankCity:"" ,
            toBankDetail:$(".clearfix .bank_on .bankName").text() ,
            toBankCardNum:$(".clearfix .bank_on .cardNum").text()
		},
		success	:	function(data){
			if(data.code==200){
				//popConfirm(data.msg,function(){checkpaymoney("'+desid+'")});
				popAlert(data.msg);
				$("#paymoney2").val("");
				$("#kj_money3").val("");
				$("#realname").val("");
				$("#bankcardnum").val("");
				return true;
			}else{
				popAlert(data.msg);
				return false;
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alert(errorThrown);
			result = false;
		}
	});	

	

	try{
		//window.open(react);
		//alert(react);
		//window.location=react;
		//$("#reactform").attr("action",react);
		//$("#reactform").attr("method","POST");
		//$("#reactform").submit();
		//setTimeout('popConfirm("请在浏览器中继续完成网银转账。<br/>是否已经完成转账？",function(){checkpaymoney("'+desid+'")});',500);
		return true;
		//reactform.action=react;
		//reactform.submit();
	}catch(e){
		//window.location=react;
	
	}
	//alert(detectOS()+','+$.browser.msie+','+$.browser.version);
}


// 出金提交
function sendtakemoney(){
	var minmoney=10;
	//var maxmoney=1000000;
	var maxmoney=100000;
	if(isNaN($("#kj_money2").val()) || $("#kj_money2").val()=="" || $("#kj_money2").val()<=0){
		popAlert("填入的金额不正确。");
		//$("#takemoney").focus();
		return false;

	};
	if($("#kj_money2").val()<minmoney){
		popAlert("填入的金额不正确，金额必须大于"+minmoney+"。");
		//$("#takemoney").focus();
		return false;
	};
	if(parseFloat($("#kj_money2").val())*rate2-takefee>maxmoney){
		popAlert("填入的金额不正确，单笔提款金额不能大于"+maxmoney+"元人民币。");
		//$("#takemoney").focus();
		return false;
	};
    var toRealName = $("#outRealname").val();
    var toBankCardNum = $("#outBankcardnum").val();
    var toBankInfo = $("#outBankInfo").val();
    if(toRealName == null || toRealName == ''){
        popAlert("转入姓名不能空");
        return false;
	}
    if(toBankCardNum == null || toBankCardNum == ''){
        popAlert("银行卡号不能为空");
        return false;
    }
    if(toBankInfo == null || toBankInfo == ''){
        popAlert("银行开户行信息不能为空");
        return false;
    }
	popConfirm('如果账户内有持仓或者委托挂单，系统将无法为您出金。',function(){popLoading();closeConfirm();setTimeout(function(){formtotakemoney();},50)},function(){closeConfirm()});
}

// 出金操作
function formtotakemoney(){
	var requestId,tradeProcess,totalBizType,totalPrice,backurl,returnurl,noticeurl,description,mersignature,goodsDesc,allowRePay,rePayTimeOut,userIdIdentity,bankCardType,payTag,sumapaySignatureSign,bankMchName,bankMchId,payType,bankCode,act
	//
    //var outMoney1 = $("#kj_money2").val();
	var outMoney1 =  $("#takemoney").val();
    var outMoney2 = $("#deposit2").text();
    var locAccount = $("#dataval0").text();
    var currency = $("#kj_currency2").val();
    var toRealName = $("#outRealname").val();
    var toBankCardNum = $("#outBankcardnum").val();
    var toBankInfo = $("#outBankInfo").val();
    //console.log(outMoney1);
    //console.log(outMoney2);
	jQuery.ajax({
		url		:	Global.baseUrl + "/user/withdrawalApply",
		type	:	"POST",
		async	:	false,
		dataType:	"json",
		data	:	{
			moneyDollar1 : outMoney1,
			moneyDollar2: outMoney2,
			unit : currency,
			userName : locAccount,
			takeFee : OUT_CHARGE,
			exchange : US_EXCHANGE,
			toRealName : toRealName,
            toBankCardNum : toBankCardNum,
            toBankInfo : toBankInfo
		},
		success	:	function(data){
			if(data.code==200){
				console.log("success");
				closeLoading();
				//requestId=data.requestId;
				popAlert(data.msg);
				clearOutFund();
				return true;
			}else{
				closeLoading();
				popAlert(data.msg);
				return false;
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alert(errorThrown);
			result = false;
		}
	});	

	$("#requestId").val(requestId);

}

// 清除出金操作的输入框
function clearOutFund(){
    //$("#takemoney").val("");
    //$("#deposit2").text("");
    //$("#dataval0").text("");
    //$("#kj_currency2").val("");
    $("#outRealname").val("");
    $("#outBankcardnum").val("");
    $("#outBankInfo").val("");
}

function checkpaymoney(id){
	$("#payAmount").val($("#kj_money").val());
	/*****************
	jQuery.ajax({
		url		:	"../AJax_PayInfo.asp",
		type	:	"POST",
		async	:	false,
		dataType:	"json",
		data	:	{act : "PayCheck",account:$("#dataval0").text(),desid:id},
		success	:	function(data){
			if(data.success){
				alert("银行转账成功。（转账单号："+data.RequestId+"）");
			}else{
				alert("未能查询到您的转账记录。（转账单号："+data.RequestId+"）");
				return false;
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			alert(errorThrown);
			result = false;
		}
	});	
	*******************/

};

function DX(n){  
            if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n))  
                 return"...";    
      var unit="千百拾亿千百拾万千百拾元角分",str="";  
                 n += "00";  
      var p= n.indexOf('.');  
      if(p >=0)  
         n=n.substring(0,p)+n.substr(p+1,2);  
         unit=unit.substr(unit.length-n.length);  
         for(var i=0; i<n.length; i++)  
          str +='零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i))+unit.charAt(i);  
         return str.replace(/零(千|百|拾|角)/g,"零").replace(/(零)+/g,"零").replace(/零(万|亿|元)/g,"$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g,"").replace(/元$/g,"元整");  
}

function detectOS() {
    var sUserAgent = navigator.userAgent;
    var isWin = (navigator.platform == "Win32") || (navigator.platform == "Windows");
    var isMac = (navigator.platform == "Mac68K") || (navigator.platform == "MacPPC") || (navigator.platform == "Macintosh") || (navigator.platform == "MacIntel");
    if (isMac) return "Mac";
    var isUnix = (navigator.platform == "X11") && !isWin && !isMac;
    if (isUnix) return "Unix";
    var isLinux = (String(navigator.platform).indexOf("Linux") > -1);
    if (isLinux) return "Linux";
    if (isWin) {
        var isWin2K = sUserAgent.indexOf("Windows NT 5.0") > -1 || sUserAgent.indexOf("Windows 2000") > -1;
        if (isWin2K) return "Win2000";
        var isWinXP = sUserAgent.indexOf("Windows NT 5.1") > -1 || sUserAgent.indexOf("Windows XP") > -1;
        if (isWinXP) return "WinXP";
        var isWin2003 = sUserAgent.indexOf("Windows NT 5.2") > -1 || sUserAgent.indexOf("Windows 2003") > -1;
        if (isWin2003) return "Win2003";
        var isWinVista= sUserAgent.indexOf("Windows NT 6.0") > -1 || sUserAgent.indexOf("Windows Vista") > -1;
        if (isWinVista) return "WinVista";
        var isWin7 = sUserAgent.indexOf("Windows NT 6.1") > -1 || sUserAgent.indexOf("Windows 7") > -1;
        if (isWin7) return "Win7";
    }
    return "other";
}
function AlertPostinfo(){
	var qrhref;
	$("#infoqrimg").attr("src","/googleqr.asp?chs=180x180&chl="+window.location.protocol+"//"+window.location.host+"/QRScan/A"+$("#dataval0").text());
	qrhref=$("#dataval0").text().toString();
	//console.log(window.location.protocol + "//" + window.location.host + "/QRScan/A" + qrhref);
	$("#infoqrhref").attr("href",window.location.protocol + "//" + window.location.host + "/QRScan/A" + qrhref);
	$("#detailWin").show();
	$("#startMask").show();
	$("#closeDetail").unbind("click");
	$("#closeDetail").on("click",function(){if($("#closeDetail").text()=="关闭"){$("#detailWin").hide();$("#startMask").hide()}})
	//apcdtime=3;
	AlertPostinfoCD();
};

function AlertPostinfoCD(){
	if (apcdtime>0){
		setTimeout("AlertPostinfoCD();",1000);
		$("#closeDetail").text(apcdtime+" 秒后进行关闭");
	}else{
	
		$("#closeDetail").text("关闭");
	}
	apcdtime=apcdtime-1
};



