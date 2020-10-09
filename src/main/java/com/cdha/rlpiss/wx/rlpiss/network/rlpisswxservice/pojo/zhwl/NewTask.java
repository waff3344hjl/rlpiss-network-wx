package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.zhwl;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ProjectName: rlpiss-network-wx
 * @Package: com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.zhwl
 * @ClassName: NewTask
 * @brief: 新的运输任务
 * @Description: java类作用描述 - 详细说明
 * @Author: HUjl
 * @CreateDate: 2020/9/25 14:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/25 14:02
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Data
public class NewTask implements Serializable {
    private String sjh;
    private String yhid;
    private String yhxm;
    private String wxopenid;
    private String cph;
    private String qshbj;
    private String yslx;
    private String ysrwid;
    private String qswcsj;
    private String blztmis;
    private String blzmc;
    private String sjxm;
    private String sjsjh;
    private String sjid;
    private List<ListHuoWu> list;


    @Data
    public class ListHuoWu {

        private String xqdh;
        private String fzm;
        private String dzm;
        private String fhr;
        private String shr;
        private String blzmc;
        private String pm;
        private String hz;
        private String qshbj;
        private String yslx;
        private String rwzt;
        private String qshsj;

    }
}
