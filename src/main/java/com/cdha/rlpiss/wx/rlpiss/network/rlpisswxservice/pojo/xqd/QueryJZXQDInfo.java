package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.xqd;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 集装箱清单信息
 * @Author HJL
 * @Date Created in 2020-05-21
 */
@Data
public class QueryJZXQDInfo  implements Serializable {
    private User user;

    private String jzxHzSj;//集装箱货主电话 （废弃----全部查询）


    private String type; //发、到类型

}
