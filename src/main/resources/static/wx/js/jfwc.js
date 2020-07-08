function getRequest() {


    var url = location.search; //获取url中"?"符后的字串

    var theRequest = new Object();

    if (url.indexOf("?") != -1) {

        var str = url.substr(1);

        strs = str.split("&");

        for (var i = 0; i < strs.length; i++) {

            theRequest[strs[i].split("=")[0]] = decodeURI(strs[i].split("=")[1]);

        }

    }

    return theRequest;

}

var Request = new Object();

Request = getRequest();

$(function () {

    var fzmc = Request["fzmc"];//发站名称
    var dzmc = Request["dzmc"];//到站名称
    var djbh = Request["djbh"];//票据ID
    var blzdbm = Request["blzdbm"];//车站电报码
    var blzmc = Request["blzmc"];//车站名称
    var blztms = Request["blztms"];//车站TMS
    var byjssj = Request["byjssj"];//搬运结束时间
    var bykssj = Request["bykssj"];//搬运开始时间
    var hqhwmc = Request["hqhwmc"];//货区货位
    var pmmc = Request["pmmc"];//品名名称

    // console.log("dataurl" + "__" + dataurl);
    console.log("fzmc" + "__" + fzmc);
    console.log("dzmc" + "__" + dzmc);
    console.log("djbh" + "__" + djbh);
    console.log("blzdbm" + "__" + blzdbm);
    console.log("blzmc" + "__" + blzmc);
    console.log("blztms" + "__" + blztms);
    console.log("byjssj" + "__" + byjssj);
    console.log("bykssj" + "__" + bykssj);
    console.log("hqhwmc" + "__" + hqhwmc);
    console.log("pmmc" + "__" + pmmc);

    var myDate = new Date();   //早期策略：获取当前时间
    var sj= myDate.pattern("yyyy-MM-dd");
    console.log(sj);

    $("#date-push-jfwc").html(sj);
    $("#jfwc-xqdh").html(djbh);
    $("#jfwc-pm").html(pmmc);
    // $("#jfwc-hz").html(hz);

    $("#jfwc-fz").html(fzmc);
    $("#jfwc-dz").html(dzmc);

    $("#jfwc-hqhw").html(hqhwmc);
    // $("#jf_bykssj").html("搬运开始时间:"+bykssj);
    $("#jfwx-time").html(byjssj);


});

/**
 * 发送评价到服务器
 * @param startpj 几星评价
 * @param yx 印象评价
 * @param edit  打字评价
 */
function pushPjInfoToNet(startpj,yx,edit) {
    // var dataurl = Request["url"];//安全平台url
    var fzmc = Request["fzmc"];//发站名称
    var dzmc = Request["dzmc"];//到站名称
    var djbh = Request["djbh"];//票据ID
    var blzdbm = Request["blzdbm"];//车站电报码
    var blzmc = Request["blzmc"];//车站名称
    var blztms = Request["blztms"];//车站TMS
    var byjssj = Request["byjssj"];//搬运结束时间
    var bykssj = Request["bykssj"];//搬运开始时间
    var hqhwmc = Request["hqhwmc"];//货区货位
    var pmmc = Request["pmmc"];//品名名称

    var url = geturl()+"pj/setPj";

    var formData = new FormData();
    // formData.append("url", dataurl);
    formData.append("fzmc", fzmc);
    formData.append("dzmc", dzmc);
    formData.append("djbh", djbh);
    formData.append("blzdbm", blzdbm);

    formData.append("blzmc", blzmc);
    formData.append("blztms", blztms);
    formData.append("byjssj", byjssj);
    formData.append("bykssj", bykssj);
    formData.append("hqhwmc", hqhwmc);

    formData.append("pmmc", pmmc);
    formData.append("startpj", startpj);
    formData.append("yx", yx);
    formData.append("edit", edit);

    $.ajax({
        url: url, /*接口域名地址*/
        type: 'post',
        data: formData,
        contentType: false,
        processData: false,
        success: function (res) {
            console.log(res);

            console.log('成功' + res);
            /*访问成功返回后进行的操作*/
            if (res.code === "0") {
                $.toptip('评价成功', 'success');
            }else {
                $.toptip("评价失败", 'warning');
            }

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            // weui.topTips("请求失败", 2000);
            // weui.topTips("网络异常", 2000);
            $.toptip("网络异常", 'warning');
            console.log(XMLHttpRequest.status);
            console.log(XMLHttpRequest.readyState);
            console.log(textStatus);

        }
    });

}
Date.prototype.pattern=function(fmt) {
    var o = {
        "M+" : this.getMonth()+1, //月份
        "d+" : this.getDate(), //日
        "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时
        "H+" : this.getHours(), //小时
        "m+" : this.getMinutes(), //分
        "s+" : this.getSeconds(), //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        // "S+" : this.getMilliseconds() //毫秒
    };
    var week = {
        "0" : "/u65e5",
        "1" : "/u4e00",
        "2" : "/u4e8c",
        "3" : "/u4e09",
        "4" : "/u56db",
        "5" : "/u4e94",
        "6" : "/u516d"
    };
    if(/(y+)/.test(fmt)){
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    if(/(E+)/.test(fmt)){
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);
    }
    if(/(S+)/.test(fmt)){
// 		if((RegExp.$1.length=3){
// 			fmt=fmt.replace(RegExp.$1,""+this.getMilliseconds()+"");
// 		}else if(RegExp.$1.length=2){
// 			fmt=fmt.replace(RegExp.$1,"0"+this.getMilliseconds()+"");
// 		}else if(RegExp.$1.length=1){
// 			fmt=fmt.replace(RegExp.$1,"00"+this.getMilliseconds()+"");
// 		}
        fmt=fmt.replace(RegExp.$1, ((this.getMilliseconds() <100) ? (this.getMilliseconds() < 10 ? "00" : "0") : "")+this.getMilliseconds()+"");
        console.log(this.getMilliseconds()+"_________SIZE");
        console.log(((this.getMilliseconds()<100) ? (this.getMilliseconds() < 10 ? "00" : "0") : "")+this.getMilliseconds()+"____毫秒");
    }
    for(var k in o){
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
}