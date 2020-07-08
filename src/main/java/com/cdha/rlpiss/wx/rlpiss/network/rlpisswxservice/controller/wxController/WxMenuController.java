package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.controller.wxController;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.UrlConfig;
import com.cdha.wechatsub.wxtools.bean.WxMenu;
import com.cdha.wechatsub.wxtools.common.WxConsts;
import com.cdha.wechatsub.wxtools.dao.serviceImpl.WxService;
import com.cdha.wechatsub.wxtools.exception.WxErrorException;
import com.cdha.wechatsub.wxtools.util.CodeUrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@RestController
@RequestMapping("/wx/menu/")
public class WxMenuController {

//    @Qualifier("IService")
    @Autowired
    private WxService iService;
    @Autowired
    private UrlConfig urlConfig;

    public String getUrl(){
        String Get_Code = "http://cdhawxgzh.easy.echosite.cn/wechat/qr_code/zc_out";
        String SCOPE = WxConsts.OAUTH2_SCOPE_USER_INFO;
        String Sata = "123";
        String url = iService.oauth2buildAuthorizationUrl(Get_Code,SCOPE,Sata);
        return url;
    }
    /**
     * <pre>
     * 自定义菜单创建接口
     * 详情请见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141013&token=&lang=zh_CN
     * 如果要创建个性化菜单，请设置matchrule属性
     * 详情请见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455782296&token=&lang=zh_CN
     * </pre>
     *
     * @return 如果是个性化菜单，则返回menuid，否则返回null
     */

    @GetMapping("/create")
    public void menuCreateSample() throws WxErrorException, IOException {

        String Get_Code_gr =  urlConfig.getMUrl();//智慧物流
        String Get_Code_zc  =urlConfig.getMUrl()+ "qr_code/gywm";//关于我们
        String Get_ZC_GM  =urlConfig.getMUrl()+ "qr_code/zcmanager";//工作人员界面
        String Get_Code_ewm  = urlConfig.getMUrl()+"qr_code/czzn";//操作指南
        String SCOPE_gr = WxConsts.OAUTH2_SCOPE_USER_INFO;
        String Sata_gr = "123";
        WxMenu menu = new WxMenu();
        List<WxMenu.WxMenuButton> btnList = new ArrayList<>();
        //智慧物流
        WxMenu.WxMenuButton btn1 = new WxMenu.WxMenuButton();
        btn1.setType(WxConsts.MENU_BUTTON_VIEW);
        btn1.setName("智慧物流");
        btn1.setUrl(CodeUrlUtil.getUrl(Get_Code_gr,SCOPE_gr,Sata_gr,iService));


        //操作指南
        WxMenu.WxMenuButton btn2 = new WxMenu.WxMenuButton();
        btn2.setType(WxConsts.MENU_BUTTON_VIEW);
        btn2.setName("操作指南");
        btn2.setUrl(CodeUrlUtil.getUrl(Get_Code_ewm,SCOPE_gr,Sata_gr,iService));
        //我的
//        WxMenu.WxMenuButton btn3 = new WxMenu.WxMenuButton();
//        btn3.setType(WxConsts.MENU_BUTTON_VIEW);
//        btn3.setKey("3");
//        btn3.setName("我的");
//        List<WxMenu.WxMenuButton> subListwd = new ArrayList<>();

//        WxMenu.WxMenuButton btn3_1 = new WxMenu.WxMenuButton();
//        btn3_1.setType(WxConsts.MENU_BUTTON_VIEW);
//        btn3_1.setKey("3_1");
//        btn3_1.setName("注册");
//        btn3_1.setUrl(CodeUrlUtil.getUrl(Get_Code_zc,SCOPE_gr,Sata_gr,iService));

//        WxMenu.WxMenuButton btn3_2 = new WxMenu.WxMenuButton();
//        btn3_2.setType(WxConsts.MENU_BUTTON_VIEW);
//        btn3_2.setKey("3_2");
//        btn3_2.setName("关于我们");
//        btn3_2.setUrl(CodeUrlUtil.getUrl(Get_Code_zc,Get_Code_zc,Get_Code_zc,iService));


        WxMenu.WxMenuButton btn3 = new WxMenu.WxMenuButton();
        btn3.setType(WxConsts.MENU_BUTTON_VIEW);
        btn3.setName("关于我们");
        btn3.setUrl(CodeUrlUtil.getUrl(Get_Code_zc,SCOPE_gr,SCOPE_gr,iService));


//        subListwd.addAll(Arrays.asList( btn3_2));
//        btn3.setSub_button(subListwd);
        //将三个按钮设置进btnList
        btnList.add(btn1);
        btnList.add(btn2);
        btnList.add(btn3);
        //设置进菜单类
        menu.setButton(btnList);
        //调用API即可
        try {
            //参数1--menu  ，参数2--是否是个性化定制。如果是个性化菜单栏，需要设置MenuRule
            iService.createMenu(menu, false);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }finally {
            System.out.println(menu.toJson());
        }


    }
    @GetMapping("/delete")
    public void deleteMenu() throws WxErrorException {
        String result = iService.deleteMenu();
        System.out.println(result);
    }
    /**
     * <pre>
     * 自定义菜单创建接口
     * 详情请见： https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141013&token=&lang=zh_CN
     * 如果要创建个性化菜单，请设置matchrule属性
     * 详情请见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455782296&token=&lang=zh_CN
     * </pre>
     *
     * @param json
     * @return 如果是个性化菜单，则返回menuid，否则返回null
     */
//    @PostMapping("/createByJson")
//    public String menuCreate(@PathVariable String appid, @RequestBody String json) throws WxErrorException {
//        return WxMpConfiguration.getMpServices().get(appid).getMenuService().menuCreate(json);
//    }
//
//    /**
//     * <pre>
//     * 自定义菜单删除接口
//     * 详情请见: https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141015&token=&lang=zh_CN
//     * </pre>
//     */
//    @GetMapping("/delete")
//    public void menuDelete(@PathVariable String appid) throws WxErrorException {
//        WxMpConfiguration.getMpServices().get(appid).getMenuService().menuDelete();
//    }
//
//    /**
//     * <pre>
//     * 删除个性化菜单接口
//     * 详情请见: https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1455782296&token=&lang=zh_CN
//     * </pre>
//     *
//     * @param menuId 个性化菜单的menuid
//     */
//    @GetMapping("/delete/{menuId}")
//    public void menuDelete(@PathVariable String appid, @PathVariable String menuId) throws WxErrorException {
//        WxMpConfiguration.getMpServices().get(appid).getMenuService().menuDelete(menuId);
//    }
//
//    /**
//     * <pre>
//     * 自定义菜单查询接口
//     * 详情请见： https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141014&token=&lang=zh_CN
//     * </pre>
//     */
//    @GetMapping("/get")
//    public WxMpMenu menuGet(@PathVariable String appid) throws WxErrorException {
//        return WxMpConfiguration.getMpServices().get(appid).getMenuService().menuGet();
//    }
//
//    /**
//     * <pre>
//     * 测试个性化菜单匹配结果
//     * 详情请见: http://mp.weixin.qq.com/wiki/0/c48ccd12b69ae023159b4bfaa7c39c20.html
//     * </pre>
//     *
//     * @param userid 可以是粉丝的OpenID，也可以是粉丝的微信号。
//     */
//    @GetMapping("/menuTryMatch/{userid}")
//    public WxMenu menuTryMatch(@PathVariable String appid, @PathVariable String userid) throws WxErrorException {
//        return WxMpConfiguration.getMpServices().get(appid).getMenuService().menuTryMatch(userid);
//    }
//
//    /**
//     * <pre>
//     * 获取自定义菜单配置接口
//     * 本接口将会提供公众号当前使用的自定义菜单的配置，如果公众号是通过API调用设置的菜单，则返回菜单的开发配置，而如果公众号是在公众平台官网通过网站功能发布菜单，则本接口返回运营者设置的菜单配置。
//     * 请注意：
//     * 1、第三方平台开发者可以通过本接口，在旗下公众号将业务授权给你后，立即通过本接口检测公众号的自定义菜单配置，并通过接口再次给公众号设置好自动回复规则，以提升公众号运营者的业务体验。
//     * 2、本接口与自定义菜单查询接口的不同之处在于，本接口无论公众号的接口是如何设置的，都能查询到接口，而自定义菜单查询接口则仅能查询到使用API设置的菜单配置。
//     * 3、认证/未认证的服务号/订阅号，以及接口测试号，均拥有该接口权限。
//     * 4、从第三方平台的公众号登录授权机制上来说，该接口从属于消息与菜单权限集。
//     * 5、本接口中返回的图片/语音/视频为临时素材（临时素材每次获取都不同，3天内有效，通过素材管理-获取临时素材接口来获取这些素材），本接口返回的图文消息为永久素材素材（通过素材管理-获取永久素材接口来获取这些素材）。
//     *  接口调用请求说明:
//     * http请求方式: GET（请使用https协议）
//     * https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=ACCESS_TOKEN
//     * </pre>
//     */
//    @GetMapping("/getSelfMenuInfo")
//    public WxMpGetSelfMenuInfoResult getSelfMenuInfo(@PathVariable String appid) throws WxErrorException {
//        return WxMpConfiguration.getMpServices().get(appid).getMenuService().getSelfMenuInfo();
//    }
}
