
$(function(){
    account.queryFund();
    bookUser.renderInRecord();
    bookUser.renderOutRecord();

})

// 账户
var account = {
    // 查询账户资金
    queryFund:function(){
        $.ajax({
            async: true,
            url: Global.baseUrl + '/user/fund',
            type: 'get',
            beforeSend: function(){
                // 显示遮罩层
                popLoading();
            },
            dataType: 'json',
            success: function(data){
                // 关闭遮罩层
                closeLoading();
                //console.log(data);
                if(data.code == 200){
                    var summary = data.Summary;
                    var c_summary = account.checkSummary(summary);
                    //console.log("summary");
                    //console.log(summary);
                    //console.log("c_summary");
                    //console.log(c_summary);
                    account.renderFund(c_summary);
                    syncTakeFee = c_summary.Commission;
                    //console.log("getTakeFee ok");
                }else{
                    alert(data.msg);
                }
            },
            complete: function(){
                // 关闭遮罩层
                closeLoading();
            },
            error : function(XMLHttpRequest, textStatus, errorThrown){
                // 关闭遮罩层
                closeLoading();
                alert(errorThrown);
            }
        });
    },
    checkSummary:function(data){
        if(data.length == 7){
            return data[0];
        }
        return data;
    },
    // 渲染账户资金
    renderFund:function(data){
        // 渲染时间
        var sysTime = new Date();
        $("#datatime").text(sysTime.format("yyyy/MM/dd hh:mm:ss"));
        // 当前权益
        $("#dataval2").text(data.Balance);
        //$("#datavalcur2").text(data.);
        //$("#datavalcur1").text(data.);
        // 保证金
        $("#dataval5").text(data.Margin);
        // 开仓冻结保证金
        $("#dataval6").text(data.MarginFrozen);
        // 可用资金
        $("#dataval4").text(data.Available);
        // 持仓浮动盈亏
        $("#dataval8").text(data.PositionProfitFloat);
        // 平仓浮动盈亏
        $("#dataval9").text(data.CloseProfitFloat);
        // 挂单冻结手续费
        $("#dataval7").text(data.CommissionFrozen);
        // 持仓盯市盈亏
        $("#dataval11").text(data.PositionProfit);
        // 平仓盯市盈亏
        $("#dataval12").text(data.CloseProfit);
        // 手续费
        $("#dataval10").text(data.Commission);
        // 同步到出金的地方
        $("#fee2").text(data.Commission);
        // 上日权益
        $("#dataval3").text(data.PreBalance);

        //$("#dataval15").text(data.PreBalance);
        // 入金
        $("#dataval13").text(data.Deposit);
        // 历史最大权益
        $("#dataval17").text(data.EverMaxBalance);
        // 当日最大权益
        $("#dataval18").text(data.MaxBalance);
        // 出金
        $("#dataval14").text(data.Withdraw);
    }

}

// 在线充值
var transaction = {
    online:function(){
        var money = $("#kj_money3zx").val();
        var dollar = $("#deposit3zx").text();
        var exchange = US_EXCHANGE;
        var takeFee = IN_CHARGE;

        $.ajax({
            async: false,
            url: Global.baseUrl + '/user/transaction',
            type: 'post',
            data : {
                "userName":userName,
                "money":money,
                "dollar":dollar,
                "exchange":exchange,
                "takeFee":takeFee
            },
            dataType: 'json',
            success: function(data){
                if(data.code == 200){
                    popAlert("充值成功");
                    $("#kj_money3zx").val("");
                    $("#paymoney2zx").val("");
                }else{
                    popAlert(data.msg);
                }
            },
            error : function(XMLHttpRequest, textStatus, errorThrown){
                alert(errorThrown);
            }
        });
    }
}

// 用户账本
var bookUser = {

    // 渲染入金表格
    renderInRecord:function(){
        layui.use('table', function() {
            var table = layui.table;
            var options = {
                elem: '#inRecordTable'
                ,url: Global.baseUrl + '/user/inMoneyList' //数据接口
                ,where : {
                    userName:userName
                }
                , page: true //开启分页
                , limits: [5, 10, 20,50]
                ,limit: 5
                ,id: 'tableId'
                ,cols: [[ //表头
                    //{ type: 'numbers', title: 'No'}
                    { field: 'serialNo', title: '流水号', align: 'center' }
                    , { field: 'dollar', title: '转出', align: 'center',templet: '#dollarTpl'}
                    , { field: 'flowWay', title: '方式', width:60, align: 'center' }
                    , { field: 'takeFee', title: '手续费', width:60, align: 'center',templet: '#takeFeeTpl'}
                    , { field: 'exchange', title: '汇率', width:60, align: 'center' }
                    , { field: 'money', title: '入金', align: 'center',templet: '#moneyTpl' }
                    , { field: 'balance', title: '自有', width:60, align: 'center',templet: '#balanceTpl' }
                    //, { field: 'xxx', title: '授信', align: 'center' }
                    , { field: 'createTime', title: '时间', width:115, align: 'center',templet: '#dateFmt' }
                    , { field: 'status', title: '状态', width:75, align: 'center' }
                    //, { title: '操作', align: 'center', toolbar: '#toolbar', width: 150 }
                ]]
            }
            var tableIns = table.render(options);
        });
    },
    // 渲染出金表格
    renderOutRecord:function(){
        layui.use('table', function() {
            var table = layui.table;
            var options = {
                elem: '#outRecordTable'
                ,url: Global.baseUrl + '/user/outMoneyList' //数据接口
                ,where : {
                    userName:userName
                }
                , page: true //开启分页
                , limits: [5, 10, 20,50]
                ,limit: 5
                ,id: 'tableId'
                ,cols: [[ //表头
                    //{ type: 'numbers', title: 'No'}
                     { field: 'serialNo', title: '流水号', align: 'center' }
                    , { field: 'dollar', title: '转出', align: 'center',templet: '#dollarTpl'}
                    , { field: 'flowWay', title: '方式', width:60, align: 'center' }
                    , { field: 'takeFee', title: '手续费', width:60, align: 'center',templet: '#takeFeeTpl'}
                    , { field: 'exchange', title: '汇率', width:60, align: 'center' }
                    , { field: 'money', title: '出金', align: 'center',templet: '#moneyTpl' }
                    , { field: 'balance', title: '自有', width:60, align: 'center',templet: '#balanceTpl' }
                    //, { field: 'xxx', title: '授信', align: 'center' }
                    , { field: 'createTime', title: '时间', width:115, align: 'center',templet: '#dateFmt' }
                    , { field: 'status', title: '状态', width:75, align: 'center' }
                    //, { title: '操作', align: 'center', toolbar: '#toolbar', width: 150 }
                ]]
            }
            var tableIns = table.render(options);
        });
    }
}