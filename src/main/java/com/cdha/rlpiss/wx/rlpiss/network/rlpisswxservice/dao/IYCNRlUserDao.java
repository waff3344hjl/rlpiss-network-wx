package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IYCNRlUserDao {
      /**
       * 根据主键删除会员
       * @param yhid
       * @return
       */
      int deleteByPrimaryKey(String yhid);

      /**
       * 添加会员
       * @param record
       * @return
       */
      int insert(User record);

      /**
       * 根据主键查询会员
       * @param yhid
       * @return
       */
      User selectByPrimaryKey(String yhid);

      /**
       * 根据手机号或微信ID查询用户
       * @return 1
       */
      User selectByPhoneNum(User record);

      /**
       * 根据身份证号查询会员
       * @param sfzh
       * @return
       */
      User selectByIdentityNum(String sfzh);


      /**
       * 根据邮箱查询会员
       * @param yx
       * @return
       */
      User selectByEmail(String yx);

      /**
       * 根据手机号和登录密码查询会员
       * @param sjh,dlmm
       * @return
       */
      User selectByPoneNumAndPwd(@Param("sjh") String sjh,@Param("dlmm") String dlmm);

      /**
       * 根据手机号和验证码查询会员
       * @param sjh,dlmm
       * @return
       */
      User selectByPoneNumAndText(@Param("sjh") String sjh,@Param("dxyzm") String dxyzm);

      /**
       * 根据手机号和用户类型查询会员
       * @param sjh,dlmm
       * @return
       */
      User selectByPoneNumAndType(@Param("sjh") String sjh,@Param("yhlx") String dlmm);

      /**
       * 根据主键修改会员
       * @param record
       * @return
       */
      int updateByPrimaryKey(User record);

      /**
       * 根据手机号修改短信内容和有效时间
       * @param sjh,dxzym
       * @return
       */
      int updateDxBySjh(@Param(value = "sjh") String sjh,@Param(value = "dxyzm") String dxyzm);
}
