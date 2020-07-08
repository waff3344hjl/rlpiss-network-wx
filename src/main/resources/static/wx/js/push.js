function showIMG(url) {
    console.log(url);


    var pb1 = $.photoBrowser({
        items: [url],
        onSlideChange: function (index) {
            console.log(index);
        }
    });

    pb1.open();

}


function imgScale(obj) {
    //alert(parseInt(obj.style.zoom,10));
    var zoom = parseInt(obj.style.zoom, 10) || 100;
    zoom += event.wheelDelta / 15;
    if (zoom > 0) {
        obj.style.zoom = zoom + '%';
    } else {
        return false;
    }
}


function addcylxr_jiazai() {
    // alert("xians");
    // $.toast("操作成功");
    $('#jiazai_hjxt').fadeIn(100);
}

function hidelog_jiazai() {

    $('#jiazai_hjxt').fadeOut(100);
}

function addcylxr_no() {
    console.log("xians");
    // $.toast("操作成功");
    $('#jiazai_no_hjxt').fadeIn(100);
}

function hidelog_no() {

    $('#jiazai_no_hjxt').fadeOut(100);
}

function setImgs(imgs) {
    console.log("test");
    for (var i = 0; i < imgs.length; i++) {
        $("#img_ps_hjxt").append(
            '<div  class="weui-grid js_grid">\
                <img onclick="showIMG(this.src)"  src="data:image/png;base64,' + imgs[i] + '" alt="data:image/png;base64,' + imgs[i] + '" style="height: 100px;width: 100%">\
          </div>'
        )
    }
}
function getImgsByZxwc() {


    var ch = Request["ch"];
    var pjid = Request["pjid"];

    var fz = Request["fz"];
    var dz = Request["dz"];
    var pm = Request["pm"];
    var hz = Request["hz"];
    var zxkssj = Request["zxkssj"];
    var zxjssj = Request["zxjssj"];

    var czdbm = Request["dbm"];
    var cztms = Request["tms"];
    var date = Request["date"];

    var zxbj = Request["zxlx"];


    if(zxbj === 'Z'){
        $("#title-push").html("装车完成通知");
    }
    if(zxbj === 'X'){
        $("#title-push").html("卸车完成通知");
    }
    $("#date-push").html(date);

     $("#tt_em").html(pjid);


    $("#item_1_bt").html("品名");
    $("#item_1_ve").html(pm);

    $("#item_2_bt").html("货重");
    $("#item_2_ve").html(hz);

    $("#item_3_bt").html("发站");
    $("#item_3_ve").html(fz);

    $("#item_4_bt").html("到站");
    $("#item_4_ve").html(dz);

    document.getElementById("item_5").style.display = 'none';
    $("#item_5_bt").html("发货人");
    $("#item_5_ve").html('');
    document.getElementById("item_6").style.display = 'none';
    $("#item_6_bt").html("收货人");
    $("#item_6_ve").html('');

    if(zxbj === 'Z'){
        $("#item_7_bt").html("装车开始时间");
    }
    if(zxbj === 'X'){
        $("#item_7_bt").html("卸车开始时间");
    }
    $("#item_7_ve").html(zxkssj);


    if(zxbj === 'Z'){
        $("#item_8_bt").html("装车结束时间");
    }
    if(zxbj === 'X'){
        $("#item_8_bt").html("卸车结束时间");
    }
    $("#item_8_ve").html(zxjssj);

    $("#item_9_bt").html("车号");
    $("#item_9_ve").html(ch);

    var formData = new FormData();

    formData.append("CZDBM", czdbm);
    formData.append("CZTMS", cztms);
    formData.append("PJID", pjid);
    formData.append("TYPE", zxbj);

    var url = geturl()+"zxwcimg/get_image_zxwc";
    console.log(url);
    addcylxr_jiazai();
    $.ajax({
        url: url, /*接口域名地址*/
        type: 'post',
        data: formData,
        contentType: false,
        processData: false,
        success: function (res) {
            console.log(res);
            hidelog_jiazai();//关闭加载
            console.log('成功' + res);
            /*访问成功返回后进行的操作*/
            if (res.code === "0") {
                $imgs = res.rsData;
                if ($imgs) setImgs($imgs);
            } else {
                weui.topTips("" + res.myCode, 2000);
            }

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            // weui.topTips("请求失败", 2000);
            weui.topTips("网络异常", 2000);
            hidelog_jiazai();//关闭加载
            addcylxr_no();
            console.log(XMLHttpRequest.status);
            console.log(XMLHttpRequest.readyState);
            console.log(textStatus);

        }
    });

}

function showXQDInfo() {
    var czdbm = Request["czdbm"];
    var cztms = Request["cztms"];
    var fhrjbsj = Request["fhrjbsj"];
    var ydhs =  Request["ydhs"];
    var date = Request["date"];

    var myDate = new Date();   //早期策略：获取当前时间 date
    var sj = myDate.pattern("yyyy-MM-dd");

    $("#title-push").html("需求单受理通知");

    $("#date-push").html(date);


    var formData = new FormData();

    formData.append("CZDBM", czdbm);
    formData.append("CZTMS", cztms);
    formData.append("FHRJBSJ", fhrjbsj);
    formData.append("YDHS",ydhs);
    var url = geturl()+"getpush/xqdinfo";
    console.log(url);
    addcylxr_jiazai();
    $.ajax({
        url: url , /*接口域名地址*/
        type: 'post',
        data: formData,
        contentType: false,
        processData: false,
        success: function (res) {
            console.log(res);
            hidelog_jiazai();//关闭加载
            console.log('成功' + res);
            /*访问成功返回后进行的操作*/
            if (res.code === "0") {
                $xqds = res.rsData;
                if ($xqds) showXqdInfoView($xqds);
            } else {
                weui.topTips("" + res.myCode, 2000);
            }

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            // weui.topTips("请求失败", 2000);
            weui.topTips("网络异常", 2000);
            hidelog_jiazai();//关闭加载
            addcylxr_no();
            console.log(XMLHttpRequest.status);
            console.log(XMLHttpRequest.readyState);
            console.log(textStatus);

        }
    });
}

function getImgsByJcm(type) {


    document.getElementById("item_7").style.display = 'none';
    document.getElementById("item_8").style.display = 'none';
    document.getElementById("item_9").style.display = 'none';

    // var dataurl =   Request["url"];
    var datatms =   Request["tms"];
    var datadbm =   Request["dbm"];
    var datajcmsj =   Request["jcmsj"];
    var datasbbh =   Request["sbbh"];
    var datazpmc =   Request["tpmc"];
    var datacph =   Request["cph"];
    var datajcbj =   Request["jcbj"];
    var dataczm =   Request["czm"];
    var dataTme =  Request["date"];

    var secinfo = "你预约的车牌号--"+datacph+",已于"+datajcmsj+"到达"+dataczm;

    var formData = new FormData();
    // formData.append("URL", dataurl);
    formData.append("TMS", datatms);
    formData.append("JCMSJ", datajcmsj);
    formData.append("SBBH", datasbbh);
    formData.append("ZPMC", datazpmc);
    formData.append("DBM", datadbm);


    if(type === '6'){//进
        $("#title-push").html("汽车进门通知");
        $("#item_2_bt").html("进站时间");
        $("#item_2_ve").html(datajcmsj);
        document.getElementById("item_3").style.display = 'none';
        document.getElementById("item_4").style.display = 'none';
        document.getElementById("item_5").style.display = 'none';
        document.getElementById("item_6").style.display = 'none';
    }
    if(type === '7'){//出
        $("#title-push").html("汽车出门通知");
        $("#item_2_bt").html("出站时间");
        $("#item_2_ve").html(datajcmsj);
        document.getElementById("item_3").style.display = '';
        document.getElementById("item_4").style.display = '';
        document.getElementById("item_5").style.display = '';
        document.getElementById("item_6").style.display = '';

        var dataMz =    Request["bdmz"];
        var dataMzTime =  Request["bdmzsj"];
        var dataPz =    Request["bdpz"];
        var dataPzTime =  Request["bdpzsj"];

        $("#item_3_bt").html("毛重");
        $("#item_3_ve").html(dataMz);

        $("#item_4_bt").html("毛重称重时间");
        $("#item_4_ve").html(dataMzTime);

        $("#item_5_bt").html("皮重");
        $("#item_5_ve").html(dataPz);

        $("#item_6_bt").html("皮重称重时间");
        $("#item_6_ve").html(dataPzTime);
    }


    $("#date-push").html(dataTme);

    $("#tt_lab").html("车牌号");
    $("#tt_em").html(datacph);

    $("#item_1_bt").html("车站名");
    $("#item_1_ve").html(dataczm);







   var url = geturl()+"jcmimg/get_image_jcm";

    addcylxr_jiazai();//开启加载图标

    $.ajax({
        url: url, /*接口域名地址*/
        type: 'post',
        data: formData,
        contentType: false,
        processData: false,
        success: function (res) {
            console.log(res);
            hidelog_jiazai();//关闭加载
            console.log('成功' + res);
            /*访问成功返回后进行的操作*/
            if (res.code === "0") {
                $imgs = res.rsData;
                if ($imgs) setImgs($imgs);
            }else {
                weui.topTips(""+res.myCode, 2000);
            }

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            // weui.topTips("请求失败", 2000);
            hidelog_jiazai();//关闭加载
            addcylxr_no();
            weui.topTips("网络异常", 2000);
            console.log(XMLHttpRequest.status);
            console.log(XMLHttpRequest.readyState);
            console.log(textStatus);

        }
    });

    
}

function showXqdInfoView(xqds) {
    for (var i = 0; i < xqds.length; i++) {
        $("#view_info").append('\
     <li>\
               <div class="weui_cells huanhang">\
                <div class="weui-form-preview">\
                    <div class="weui-form-preview__hd">\
                        <label class="weui-form-preview__label">运单号</label>\
                        <em class="weui-form-preview__value" STYLE="font-size: 1.2em;">'+xqds[i].ydydhoo+'</em>\
                    </div>\
                    <div class="weui-form-preview__bd">\
                       <div class="weui-form-preview__item">\
                            <label class="weui-form-preview__label">品名</label>\
                            <span class="weui-form-preview__value">'+xqds[i].ydpmhz1+'</span>\
                       </div>\
                       <div class="weui-form-preview__item">\
                            <label class="weui-form-preview__label">货重</label>\
                            <span class="weui-form-preview__value">'+xqds[i].ydfz00+'</span>\
                       <div class="weui-form-preview__item">\
                            <label class="weui-form-preview__label">发站</label>\
                            <span class="weui-form-preview__value">'+xqds[i].ydfzmco+'</span>\
                        </div>\
                        <div class="weui-form-preview__item">\
                            <label class="weui-form-preview__label">到站</label>\
                            <span class="weui-form-preview__value">'+xqds[i].yddzmco+'</span>\
                        </div>\
                        <div class="weui-form-preview__item">\
                            <label class="weui-form-preview__label">发货人</label>\
                            <span class="weui-form-preview__value">'+xqds[i].fhr+'</span>\
                        </div>\
                        <div class="weui-form-preview__item">\
                            <label class="weui-form-preview__label">收货人</label>\
                            <span class="weui-form-preview__value">'+xqds[i].shr+'</span>\
                        </div>\
                    </div>\
                    <div class="weui-form-preview__ft">\
                    </div>\
                </div>\
            </div>\
     </li>'
        );
    }
}

$(function () {

    var type = Request["type"];
    $url = geturl();
    if (type === "1") {
        //先取type 判断网页类型
        // 1 = 需求单受理详情
        document.getElementById("view_info_item1").style.display = 'none';
        showXQDInfo();
    }
    if(type === "2"){
        //装卸完成
        document.getElementById("view_info_item1").style.display = '';

        getImgsByZxwc();
    }
    if(type === "3"){
        //交付完成

    }
    if(type === "4"){
        //预约推送货主

    }
    if(type === "5"){
         //预约推送司机

    }
    if(type === "6"){
        //进门模版
        document.getElementById("view_info_item1").style.display = '';
        getImgsByJcm("6");
    }

    if(type === "7"){
        //出门模版
        document.getElementById("view_info_item1").style.display = '';
        getImgsByJcm("7");
    }

});


function getRequest() {


    var url = location.search; //获取url中"?"符后的字串
    // alert("---URL"+url);
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

Date.prototype.pattern = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        // "S+" : this.getMilliseconds() //毫秒
    };
    var week = {
        "0": "/u65e5",
        "1": "/u4e00",
        "2": "/u4e8c",
        "3": "/u4e09",
        "4": "/u56db",
        "5": "/u4e94",
        "6": "/u516d"
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    if (/(E+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f" : "/u5468") : "") + week[this.getDay() + ""]);
    }
    if (/(S+)/.test(fmt)) {
// 		if((RegExp.$1.length=3){
// 			fmt=fmt.replace(RegExp.$1,""+this.getMilliseconds()+"");
// 		}else if(RegExp.$1.length=2){
// 			fmt=fmt.replace(RegExp.$1,"0"+this.getMilliseconds()+"");
// 		}else if(RegExp.$1.length=1){
// 			fmt=fmt.replace(RegExp.$1,"00"+this.getMilliseconds()+"");
// 		}
        fmt = fmt.replace(RegExp.$1, ((this.getMilliseconds() < 100) ? (this.getMilliseconds() < 10 ? "00" : "0") : "") + this.getMilliseconds() + "");
        console.log(this.getMilliseconds() + "_________SIZE");
        console.log(((this.getMilliseconds() < 100) ? (this.getMilliseconds() < 10 ? "00" : "0") : "") + this.getMilliseconds() + "____毫秒");
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}



