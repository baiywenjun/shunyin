var money;
var payway;
var account;
var w;
var h;
var _ind=0;
//var rate1=7.2;
var rate1=US_EXCHANGE;
//var rate2=7.2;
var rate2=US_EXCHANGE;
var payfee=0.003;
var payfee2=0.00;
//var takefee=2;
var takefee=OUT_CHARGE;
var credittype=1;
var payFun;
var payFun2;
var paywayType="unspay";
$(document).ready(function(){
	payFun2=function(){sendbankpay()};
	switch(paywayType){
	case "sumapay":
		payFun=function(){sendsumapay()};
		$("#sumpaybank").show();
		break;
	case "jiupaipay":
		payFun=function(){sendjiupaipay()};
		$("#jiupaipaybank").show();
		break;
	case "payease":
		payFun=function(){sendpayease()};
		$("#payeasebank").show();
		break;
	case "gopay":
		payFun=function(){sendgopay()};
		$("#gopaybank").show();
		break;
	case "unspay":
		payFun=function(){sendunspay()};
		$("#unspaybank").show();
		break;
	default:
		break;
	}
	
	var _height = $(document).height();
	//$('.mainLeft').height(_height);
	//$('.mainRight').height(_height);
	//获取页面的可视宽度w，h

	$('#logOut').on('click',function() {
		logout();
	})
	checkloginstat();
	getbankinfo();
	//银行转账列表，单击事件(pc端)
	$('.SideNav-pc li').eq(0).find('.border-line-left').show().end().siblings().find('.border-line-left').hide();
	$('.SideNav-pc li').eq(0).find('.icon-index-1').hide().end().siblings().find('.icon-index-2').show();
	$('.SideNav-pc li').eq(0).find('.icon-index-2').show().end().siblings().find('.icon-index-2').hide();
	$('.SideNav-pc li').on("click",function(){
		//加载显示隐藏
		popLoading();
		//样式变化
		$(this).addClass('current').siblings().removeClass('current');
		$(this).find('a').addClass('current').end().siblings().find('a').removeClass('current');
		$(this).find('.border-line-left').show().end().siblings().find('.border-line-left').hide();
		$(this).find('.icon-index-2').show().end().siblings().find('.icon-index-2').hide();
		$(this).find('.icon-index-1').hide().end().siblings().find('.icon-index-1').show();
		// $(this).find('.logo-icon').addClass('icon-cash').end().siblings().find('.logo-icon').removeClass('icon-cash');
		//选择显示
		_ind = $(this).index();
		$('.mainRight .rightCon').eq(_ind).show().siblings().hide();
		switch(_ind){
		case 0 ://选择账号状态页面
			getbankinfo();
			checkloginstat();
			setTimeout(function(){closeLoading();},300);
			break;
		case 2 ://选择入金页面
			getbankinfo();
			checkloginstat();
			setTimeout(function(){closeLoading();},300);
			break;
		case 3 ://选择出金页面，读取相关的金额状态
			getbankinfo();
			checkloginstat();
			setTimeout(function(){closeLoading();},300);
			break;
		case 4 ://入金查询
			getpayinfo(1);
            bookUser.renderInRecord();
			setTimeout(function(){closeLoading();},300);
			break;
		case 5 ://出金查询
			gettakeinfo(1);
            bookUser.renderOutRecord();
			setTimeout(function(){closeLoading();},300);
			break;
		default://
			setTimeout(function(){closeLoading();},300);
		break;
		}
	});
	//银行转账列表，单击事件(手机端)
	$('.SideNav-mob li').on("click",function(){
		//加载显示隐藏
		popLoading();
		//样式变化
		$(this).find('a').addClass('current').end().siblings().find('a').removeClass('current');
		// $(this).find('.logo-icon').addClass('icon-cash').end().siblings().find('.logo-icon').removeClass('icon-cash');
		//选择显示
		_ind = $(this).index();

		$('.mainRight .rightCon').eq(_ind).show().siblings().hide();
		switch(_ind){
		case 0 ://选择账号状态页面
			checkloginstat();
			setTimeout(function(){closeLoading();},500);
			break;
		case 2 ://选择出金页面，读取相关的金额状态
			checkloginstat();
			setTimeout(function(){closeLoading();},500);
			break;
		case 3 ://入金查询
			getpayinfo(1,3);
			setTimeout(function(){closeLoading();},500);
			break;
		case 4 ://入金查询
			gettakeinfo(1,3);
			setTimeout(function(){closeLoading();},500);
			break;
		default:
			setTimeout(function(){closeLoading();},300);
		break;
		}
	});
	//授信
	$("#kj_needcredit1").val(0);
	$("#kj_needcredit2").val(0);
	

	//选择网银途径
	$("#paysubmit").unbind();
	$("#paysubmit").click(function(){payFun()});
	$("#paysubmit2").click(function(){payFun2()});
	
	//选择银行
	$('.bank a').on('click',function(){
		$(this).addClass('cur').siblings().removeClass('cur');
		$(this).find('em').show().end().siblings().find('em').hide();
		payway = $(this).attr("value");
		$("#kj_payway").val(payway);
	});

	// 选择人民币或美元的样式变化
	$('#my-bg-1 a').eq(0).find('em').show();
	$('#my-bg-1 a').on('click',function(){
		$(this).addClass('on').siblings().removeClass('on');
		$(this).find('em').show().end().siblings().find('em').hide();
	});
	$('#my-bg-2 a').eq(0).find('em').show();
	$('#my-bg-2 a').on('click',function(){
		$(this).addClass('on').siblings().removeClass('on');
		$(this).find('em').show().end().siblings().find('em').hide();
	});
	
	// 授信显示
	if (credittype == 0) {
		$("#needcreditbox1").hide();
		$("#needcreditbox2").hide();
		$("#needcreditbox3").hide();
	}else{
		$("#needcreditbox1").show();
		$("#needcreditbox2").show();
		$("#needcreditbox3").show();
	}
	//选择授信倍数
	$('.needcredit1 a').eq(0).find('em').show();
	$('.needcredit1 a').on('click',function(){
		$(this).addClass('on').siblings().removeClass('on');
		$(this).find('em').show().end().siblings().find('em').hide();
		$("#kj_needcredit1").val($('.needcredit1 a').index($(this)));
	});
	$('.needcredit2 a').eq(0).find('em').show();
	$('.needcredit2 a').on('click',function(){
		$(this).addClass('on').siblings().removeClass('on');
		$(this).find('em').show().end().siblings().find('em').hide();
		$("#kj_needcredit2").val($('.needcredit2 a').index($(this)));
	});
	$('.needcredit3 a').eq(0).find('em').show();
	$('.needcredit3 a').on('click',function(){
		$(this).addClass('on').siblings().removeClass('on');
		$(this).find('em').show().end().siblings().find('em').hide();
		$("#kj_needcredit3").val($('.needcredit3 a').index($(this)));
	});

	//切换网银和实时汇款
	$('.NavBarOne').on('click',function(){
		$('#Wy').hide();
		$('#Remit').hide();
		$(this).addClass('addNavBar').siblings().removeClass('addNavBar');
		var thisId = $(this).attr('data-id');
		// console.log(thisId);
		// alert(thisId);
		$('#'+thisId).show();
	});
	$(".bank ul li:last-child").css('border-bottom','none');

});




function Calfee1(t){
	if(!isNaN(t.value)){
		var amount1=parseFloat(t.value);
		if(amount1>10000000){
			popAlert("金额输入错误。",function(){cancel()});
			return false;
		}else{
			//计算手续费和实际金额
			if ($("#kj_currency1").val()=="CNY") {
				$("#kj_money").val(amount1);
				fee1=Math.ceil(amount1*payfee*100)/100;
				amount1=Math.ceil((amount1-fee1)/rate1*100)/100;
				$("#fee1").html(fee1);
				$("#deposit1").text(amount1 + "");//入金美元
			}else{
				amount1=Math.ceil(amount1*rate1/(1-payfee)*100)/100;
				fee1=Math.ceil(amount1*payfee*100)/100;
				$("#fee1").html(fee1);
				$("#deposit1").text(amount1 + "");//入金元
				$("#kj_money").val(amount1);
			};
		}
	}else{
		popAlert("金额输入错误。",function(){cancel()});
	}
}

function Calfee2(t){
	if(!isNaN(t.value)){
		var amount2=parseFloat(t.value);
		if(amount2>10000000){
			popAlert("金额输入错误。",function(){cancel()});
			return false;
		}else{
            // fix 需求变更 用户出金费率从接口同步中获取 20180507
            takefee = syncTakeFee;

			//计算手续费和实际金额
			if ($("#kj_currency2").val()=="CNY") {
				fee2=parseFloat(takefee);
				amount2=Math.ceil((amount2+fee2)/rate2 * 100)/100;
				$("#fee2").html(fee2);
				$("#deposit2").text(amount2 + "");//出人民币，算美元
				
				$("#kj_money2").val(amount2);
			}else{
				$("#kj_money2").val(amount2);
				fee2=takefee;
                //fix 换算错误 20180427
				amount2=Math.floor(amount2*rate2*100)/100+parseInt(fee2);
				$("#fee2").html(fee2);
				$("#deposit2").text(amount2 + "");//出美元，算人民币
				
			};
		}
	}else{
		popAlert("金额输入错误。",function(){cancel()});
	}
}
function Calfee3(t){
	if(!isNaN(t.value)){
		var amount1=parseFloat(t.value);
		if(amount1>10000000){
			popAlert("金额输入错误。",function(){cancel()});
			return false;
		}else{
			//计算手续费和实际金额
			//debugger;
			if ($("#kj_currency3").val()=="CNY") {
				$("#kj_money3").val(amount1);
				fee1=Math.ceil(amount1*payfee2*100)/100;
				amount1=Math.ceil((amount1-fee1)/rate1*100)/100;
				$("#fee3").html(fee1);
				$("#deposit3").text(amount1 + "");//入金美元
			}else{
				amount1=Math.ceil(amount1*rate1/(1-payfee)*100)/100;
				fee1=Math.ceil(amount1*payfee*100)/100;
				$("#fee3").html(fee1);
				$("#deposit3").text(amount1 + "");//入金元
				$("#kj_money3").val(amount1);
			};
		}
	}else{
		popAlert("金额输入错误。",function(){cancel()});
	}
}
function Calfee4(t){
    if(!isNaN(t.value)){
        var amount1=parseFloat(t.value);
        if(amount1>10000000){
            popAlert("金额输入错误。",function(){cancel()});
            return false;
        }else{
            //计算手续费和实际金额
            //debugger;
            if ($("#kj_currency3zx").val()=="CNY") {
                $("#kj_money3zx").val(amount1);
                fee1=Math.ceil(amount1*payfee2*100)/100;
                amount1=Math.ceil((amount1-fee1)/rate1*100)/100;
                $("#fee3zx").html(fee1);
                $("#deposit3zx").text(amount1 + "");//入金美元
            }
        }
    }else{
        popAlert("金额输入错误。",function(){cancel()});
    }
}
//入金货币单位切换
function changecurrency1(cur){
	if(cur=="CNY"){
		$("#kj_currency1").val("CNY")
		$("#currency11").text("人民币");//入金币种
		$("#currency12").text("美元");//计算币种
		Calfee1(document.getElementById("paymoney"));
	}else{
		$("#kj_currency1").val("USD")
		$("#currency11").text("美元");//入金币种
		$("#currency12").text("人民币");//计算币种
		Calfee1(document.getElementById("paymoney"));
	};
}

//出金货币单位切换
function changecurrency2(cur){
	if(cur=="CNY"){
		$("#kj_currency2").val("CNY")
		$("#currency21").text("人民币");//出金币种
		$("#currency22").text("美元");//计算币种
		Calfee2(document.getElementById("takemoney"));
	}else{
		$("#kj_currency2").val("USD")
		$("#currency21").text("美元");//出金币种
		$("#currency22").text("人民币");//计算币种
		Calfee2(document.getElementById("takemoney"));
	};
}







	// 管理添加银行
	function addcard(){
		$("#addBankcard").hide();
		$("#bankChoice").show();
	}

	function IDcard(){
		var len = $(".IDcard").val().length;
		if(len > 18 || len < 15){
			$("#idcard-word").show();
		}else{
			$("#idcard-word").hide();
		}
	}

	function cardNum(){
		var cardNum = $(".cardNum").val();
		// 暂定卡号为123123
		if (cardNum == "123123") {
			$("#cardnum").show();
			$("#cardpass").show();
			$("#nobank").hide();
		}else{
			$("#cardnum").hide();
			$("#cardpass").hide();
			$("#nobank").show();
		}
	}

	function phone(){
		var tel = $(".phone").val();
		// 暂定预留手机号为123123
		if (tel == "123123") {
			$("#phonepass").show();
			$("#nophone").hide();
		}else{
			$("#nophone").show();
			$("#phonepass").hide();
		}
	}

	function nextBtn1(){
		var username = $(".username").val();
		var IDcard = $(".IDcard").val();
		var cardNum = $(".cardNum").val();
		var phone =$(".phone").val();
		if (username == "" || IDcard == "" || cardNum == "" || phone == "") {
			popAlert("请完善信息");
		}else{
			$("#bankChoice").hide();
			$("#backCard").show();
			// $.ajax({
			// 	type: "post",
			// 	url: "test.asp",
			// 	dataType:"json",
			// 	async: false,
			// 	data: {"username":$(".username").val(),"IDcard":$(".IDcard").val(),"cardNum":$(".cardNum").val(),"phone":$(".phone").val()},
			// 	success: function(data){
			// 	if (data) {
			// 		alert(data.money+","+data.payway+","+data.needcredit1+","+data.account);}
			// 	},
			// 	error: function(){
			// 		alert("信息错误，请重新填写");
			// 	}
			// });
		}
	}

	function phone_verify(){
		// 暂定验证码为123123
		var v = $("#phone_verify").val();
		if (v == 123123) {
			$("#verifypass").show();
			$("#noverify").hide();
			$(".verifyBtn").hide();
		}else{
			$("#verifypass").hide();
			$("#noverify").show();
			$(".verifyBtn").show();
		}
	}

	function nextBtn2(){
		var v = $("#phone_verify").val();
		if (v != 123123) {
			popAlert("请完善信息");
		}else{
			$("#backCard").hide();
			$("#bankSucc").show();

			var time=3;
			var t=setInterval(function  () {
				time--;
				$(".backText").html(time+"s秒后自动跳转到添加银行卡页面");
				if (time==0) {
					clearInterval(t);
					$("#bankSucc").hide();
					$("#addBankcard").show();
					$(".backText").html("3s秒后自动跳转到添加银行卡页面");
				}
			},1000)

			// $.ajax({
			// 	type: "post",
			// 	url: "test.asp",
			// 	dataType:"json",
			// 	async: false,
			// 	data: {"username":$(".username").val(),"IDcard":$(".IDcard").val(),"cardNum":$(".cardNum").val(),"phone":$(".phone").val()},
			// 	success: function(data){
			// 	if (data) {
			// 		alert(data.money+","+data.payway+","+data.needcredit1+","+data.account);}
			// 	},
			// 	error: function(){
			// 		alert("信息错误，请重新填写");
			// 	}
			// });
		}
	}

	function backBtn(){
		$("#bankSucc").hide();
		$("#addBankcard").show();
	}
//转入资金选择银行卡
function f_choseBank(obj){
	var _nowCard = $(obj);
	if(_nowCard.hasClass('bank_on')){
		nochoseCard(obj);
	}else{
		choseCard(obj);
	}
}
function choseCard(obj){
	payway = $(obj).attr("value");	
	$("#kj_payway").val(payway);
	$(obj).addClass("bank_on").siblings().removeClass('bank_on');
}
function nochoseCard(obj){
	$("#kj_payway").val('');
	$(obj).removeClass("bank_on");
}
$(function(){
	$('#bankCardlist li').on('click',function(){
		f_choseBank(this);
	})
});