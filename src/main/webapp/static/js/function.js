$(document).ready(function(){
		// var urlstr=document.location.href;
        // if(urlstr.indexOf('home')){

        // }		
});

// 打开模态窗口
function openWindow(title,url,w,h,id){
    if (title == null || title == '') {
        title=false;
    };
    if (url == null || url == '') {
        url="../404.html";
    };
    if (w == null || w == '') {
        w=($(window).width()*0.9);
    };
    if (h == null || h == '') {
        h=($(window).height() - 50);
    };
    layer.open({
        type: 2,
        area: [w+'px', h +'px'],
        // fix: false, //不固定
        // maxmin: true,
        shadeClose: true,
        shade:0.4,
        title: title,
        content: url+"?id="+id,
    });
    //console.log("W:"+w+"，H:"+h);
}