package com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.service.user_service;

import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.RestTemplateManager;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRLStationDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.dao.IYCNRlUserDao;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseRequestData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.base.BaseResponseData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.station.StationInfo;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.User;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.user.UserRegister;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.LoginAQPTReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.LoginData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.RegisterAQPTReData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.pojo.ycn.RegisterData;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.XMLChange;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.config.YcnIP;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.hander.gm.GmManagerException;
import com.cdha.rlpiss.wx.rlpiss.network.rlpisswxservice.util.IsYcnSys;
import com.cdha.wechatsub.wxtools.common.DuanXinYZConfig;
import com.cdha.wechatsub.wxtools.util.StringUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zhenzi.sms.ZhenziSmsClient;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 用户管理
 */
@Slf4j
@Service
public class IUserService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RestTemplateManager manager;

    @Autowired
    YcnIP ycnIP;

    @Autowired
    private DuanXinYZConfig yzConfig;

    @Autowired
    IYCNRlUserDao dao;

    @Autowired
    IYCNRLStationDao stationDao;

    /**
     * 判断请求来自APP or 公众号
     *
     * @param yylx 参数 method
     * @return T/F
     */
    private boolean isGzh(String yylx) {
        return ycnIP.getGzh().equals(yylx);
    }

    /**
     * 注册  单独针对银川南
     *
     * @param baseRequestData 1
     * @return 1
     */
    public BaseResponseData<String> registerFromYCN(BaseRequestData<UserRegister> baseRequestData, BaseResponseData<User> yzm) {
        log.info("=用户注册模块=注册"+baseRequestData.toString());
        log.info("=用户注册模块=注册"+baseRequestData.getRqData().getWxopenid());
        log.info("=用户注册模块=注册"+baseRequestData.getRqData().getSjh());
        if (isNullPhone(baseRequestData)) {
            return new BaseResponseData<>("-8", "注册模块：注册手机 is null", "-8");
        }
        if (StringUtils.isEmpty(baseRequestData.getRqData().getWxopenid())) {
            throw new GmManagerException("注册模块：微信openid  is  null");
        }
        logger.info(yzm.getRsData().toString());
        logger.info(baseRequestData.getRqData().toString());

        if (yzm.getRsData().getDxyzm().equals(baseRequestData.getRqData().getDxyzm())
                && yzm.getRsData().getSjh().equals(baseRequestData.getRqData().getSjh())) {
            //缓存验证码及缓存手机号与请求验证码请求手机号校验
            //手机号与验证码均相同
            //发送注册信息至安全平台
            //用户类型 0：司机，1：货主

            if (StringUtils.isEmpty(baseRequestData.getRqData().getStationInfo().getStationTms())
                    || StringUtils.isEmpty(baseRequestData.getRqData().getStationInfo().getStationDbm())
                    || StringUtils.isEmpty(baseRequestData.getRqData().getStationInfo().getStationName())
                    || StringUtils.isEmpty(baseRequestData.getRqData().getSfhr())
                    || StringUtils.isEmpty(baseRequestData.getRqData().getSjh())
                    || StringUtils.isEmpty(baseRequestData.getRqData().getYhlx())
            ) {
                throw new GmManagerException("参数缺失");
            }
            if (ycnIP.getSj().equals(baseRequestData.getRqData().getYhlx())
                    && StringUtils.isEmpty(baseRequestData.getRqData().getCarInfo().getCph())) {
                //当为司机注册时，判断车辆信息是否为空
                //司机的参数  zz 单位为吨 长度为2；车辆类型：0or 1
                throw new GmManagerException("司机用户注册时,请填写车辆信息");
            }

            logger.info(baseRequestData.toString());
            RegisterData registerData = RegisterData.setNewRegisterData(baseRequestData, ycnIP.getWxip());


            try {
                String rqXML = XMLChange.objectToXml(registerData);
                log.info("===用户注册模块===注册参数XML||"+rqXML);
                logger.info(rqXML);
                String str = manager.getResponseByAQPT("", ycnIP.getAddHyxx(), rqXML
                        , ycnIP.getUrl());
                RegisterAQPTReData reData = XMLChange.xmlToObject(str, RegisterAQPTReData.class);
                logger.info(reData.toString());
                if (!reData.getHead().getFhzbs().equals("0")) {
                    return new BaseResponseData<>("-11", "路网服务异常：" + reData.getReturnData(), null);
                }

                BaseResponseData<String> baseResponseData = new BaseResponseData<>();

                if (!isExistPhoneNum(baseRequestData.getRqData())) {
                    int num = dao.insert(baseRequestData.getRqData());

                    baseResponseData.setCode("0");
                    baseResponseData.setMsg("会员注册成功");
                    baseResponseData.setRsData("会员注册成功");

                } else {
                    baseResponseData.setCode("0");
                    baseResponseData.setMsg("注册信息同步至" + baseRequestData.getRqData().getCycz());
                    baseResponseData.setRsData("注册信息同步至" + baseRequestData.getRqData().getCycz());
                }
                return baseResponseData;
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new GmManagerException(e.getMessage());
            }

        } else {
            return new BaseResponseData<>("-11", "验证码错误", "-11");
        }
    }

    /**
     * 注册   其他站
     *
     * @param baseRequestData 1
     * @param yzm             1
     * @return 1
     */
    public BaseResponseData<String> registerFromOther(BaseRequestData<UserRegister> baseRequestData, BaseResponseData<User> yzm) {
        if (isNullPhone(baseRequestData)) {
            return new BaseResponseData<>("-8", "参数错误", "-8");
        }
        logger.info(yzm.getRsData().toString());
        logger.info(baseRequestData.getRqData().toString());

        if (yzm.getRsData().getDxyzm().equals(baseRequestData.getRqData().getDxyzm())
                && yzm.getRsData().getSjh().equals(baseRequestData.getRqData().getSjh())) {
            return null;
        } else {
            return new BaseResponseData<>("-11", "验证码错误", "-11");
        }
    }

    /**
     * 判断参数、手机号是否为空
     *
     * @param baseRequestData 注册参数
     * @return T/F
     */
    private boolean isNullPhone(BaseRequestData<UserRegister> baseRequestData) {
        return baseRequestData == null || StringUtils.isEmpty(baseRequestData.getRqData().getSjh()) ;
    }

    /**
     * 根据手机号、微信ID判断是否已经注册
     *
     * @param user 用户
     * @return 1
     */
    private boolean isExistPhoneNum(User user) {
        return dao.selectByPhoneNum(user) != null;
    }

    /**
     * 登录--密码登录
     *
     * @param baseRequestData 1
     * @return 1
     * <p>
     * 执行登录方法并清除缓存
     */

    @CacheEvict(cacheNames = {"SMSCOD"}, key = "#baseRequestData.rqData.sjh")
    public BaseResponseData<User> login(BaseRequestData<User> baseRequestData) {
        return null;
    }

    /**
     * 登录--短信登录
     *
     * @param baseRequestData 1
     * @return 1
     * 执行登录方法并清除缓存
     */
    @CacheEvict(cacheNames = {"SMSCOD"}, key = "#baseRequestData.rqData.sjh")
    public BaseResponseData<User> login2(BaseRequestData<User> baseRequestData) {
        return null;
    }

    /**
     * 登录 == 公众号 通过微信ID自动登录
     *
     * @param baseRequestData 1
     * @return 1
     */
//    @CacheEvict(cacheNames = {"SMSCOD"}, key = "#baseRequestData.rqData.sjh")
    public BaseResponseData<User> login3(BaseRequestData<User> baseRequestData) {

        log.info("===用户登录模块===传入参数"+baseRequestData.toString());
        if (isNullOpenid(baseRequestData)) {
            return new BaseResponseData<>("-8", "参数错误", null);
        }
        try {
            User user = dao.selectByPhoneNum(User.setUserByOpenid(baseRequestData.getRqData().getWxopenid()));
            if (user == null) {
                logger.error("用户未注册");
                return new BaseResponseData<>("-99", "用户未注册", null);
            }
            if (StringUtils.isEmpty(user.getCycz())) {
                logger.error("cycz is null");
                return new BaseResponseData<>("-100", "cycz is null", null);
            }
            StationInfo stationInfo = stationDao.selectCzByZm(user.getCycz());
            if (stationInfo == null) {
                logger.error("stationInfo for cycz is null");
                return new BaseResponseData<>("-9", "无此战数据", null);
            }
            logger.info(user.toString());
            if (IsYcnSys.isYcnUser(user.getCycz())) {
                //服务区为 银川南
                return login3ByYCN(baseRequestData.getRqData().getWxopenid(), stationInfo);
            } else {
                //DO 服务区为  其他车站
                return login3ByOther(baseRequestData.getRqData().getWxopenid(), stationInfo);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }


    }

    /**
     * 银川南获取用户信息
     *
     * @param wxopenid 微信ID
     * @return 用户信息
     */
    private BaseResponseData<User> login3ByYCN(String wxopenid, StationInfo stationInfo) {
        String rqXML = LoginData.getXML(wxopenid, ycnIP.getWxip(), stationInfo);
        log.info("===用户登录模块===传入路网参数XML"+rqXML);
        String reXML = manager.getResponseByAQPT("",
                ycnIP.getGetHyxx(),
                rqXML,
                ycnIP.getUrl());

        User userRe = LoginAQPTReData.getUser(reXML);
        return userRe == null ? new BaseResponseData<>("-99", "用户未注册", null) : new BaseResponseData<>("0", "", userRe);
    }

    /**
     * 其他车站微信通过openid获取用户信息
     *
     * @param wxopenid 微信ID
     * @return 用户信息
     */
    private BaseResponseData<User> login3ByOther(String wxopenid, StationInfo stationInfo) {
        return null;
    }


    /**
     * 判断微信ID是否为空
     *
     * @param baseRequestData 1
     * @return T/F
     */
    private boolean isNullOpenid(BaseRequestData<User> baseRequestData) {
        return baseRequestData.getRqData() == null || StringUtils.isEmpty(baseRequestData.getRqData().getWxopenid());
    }

    /**
     * 构造短信验证码访问参数
     * @param phone 电话
     * @param verifyCode 验证码
     * @return 1
     */
    private Map<String, Object> getParams(String phone,String verifyCode) {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("number", phone);
        params.put("templateId", "40");
        String[] templateParams = new String[2];
        templateParams[0] = verifyCode;
        templateParams[1] = "5分钟";
        params.put("templateParams", templateParams);

        return params;
    }

    /**
     * 获取短信验证码
     *
     * @param data 手机号
     * @return 短信验证码
     * 发送验证码并将验证码存入缓存
     */
    @CachePut(cacheNames = {"SMSCOD"}, key = "#data.rqData")
    public BaseResponseData<User> getSMSCod(BaseRequestData<String> data) {

        //生成6位验证码
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        //构造client
        ZhenziSmsClient client = new ZhenziSmsClient(yzConfig.getApiurl(), yzConfig.getAppID(), yzConfig.getAppSecret());

        try {
            //构造短信参数

            //发送client
            String result = client.send(getParams(data.getRqData(),verifyCode));
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = (JsonObject) parser.parse(result);
            System.out.println("jsonObject_" + jsonObject);
            if (jsonObject.get("code").getAsInt() != 0) {
                //短信平台返回：发送失败
                return new BaseResponseData<User>("-99", "短信发送失败", null);
            } else {
                BaseResponseData<User> responseData = new BaseResponseData<>();
                User user = new User();
                user.setSjh(data.getRqData());
                user.setDxyzm(verifyCode);
                responseData.setCode("0");
                responseData.setRsData(user);
                logger.info(responseData.toString());
                return responseData;
            }

        } catch (Exception e) {
//            e.printStackTrace();
            throw new GmManagerException(e.getMessage());
        }
    }

    /**
     * 获取phone对应缓存下的验证码
     *
     * @param data 手机号码
     * @return 验证码
     */
    @Cacheable(cacheNames = {"SMSCOD"}, key = "#data.rqData", unless = "true")
    public BaseResponseData<User> getSMSCodByChaCha(BaseRequestData<String> data) {

        return new BaseResponseData<User>("-1", "-1", null);
    }

    /**
     * DESC：注销用户
     *
     * @param data 参数：method表示注销方式   rqData表示注销用户信息
     * @return 是否注销成功
     */

    public BaseResponseData<String> deleteUser(BaseRequestData<User> data) {

        boolean isOk = data == null || data.getMethod() == null || data.getRqData() == null;
        if (isOk) {
            //参数错误

            throw new GmManagerException("参数错误");
        } else {
            //DO 发送注销信息至安全平台
            return null;
        }
    }

    /**
     * 用户更新个人信息
     *
     * @param baseRequestData
     * @return
     */
    public BaseResponseData<String> upUser(BaseRequestData<UserRegister> baseRequestData) {
        logger.info(baseRequestData.getRqData().getWxopenid());
        if (StringUtils.isEmpty(baseRequestData.getRqData().getWxopenid())) {
            return new BaseResponseData<>("-8", "用户更新个人信息参数错误:openid为空", null);
        }
        try {
            User user = dao.selectByPhoneNum(User.setUserByOpenid(baseRequestData.getRqData().getWxopenid()));
            if (user == null) {
                return new BaseResponseData<>("-99", "用户更新个人信息:用户未注册", null);
            }
            StationInfo stationInfo = stationDao.selectCzByZm(user.getCycz());
            if (stationInfo == null) {
                return new BaseResponseData<>("-9", "用户更新个人信息:无此战数据", null);
            }
            logger.info(user.toString());
            if (IsYcnSys.isYcnUser(user.getCycz())) {
                //服务区为 银川南
                return upUserByYCN(baseRequestData, stationInfo);
            } else {
                //DO 服务区为  其他车站
                return upUserByOther(baseRequestData, stationInfo);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GmManagerException(e.getMessage());
        }

    }


    private BaseResponseData<String> upUserByYCN(BaseRequestData<UserRegister> baseRequestData, StationInfo stationInfo) {
        baseRequestData.getRqData().setStationInfo(stationInfo);
        RegisterData registerData = RegisterData.setNewRegisterData(baseRequestData, ycnIP.getWxip());
        String rqXML = XMLChange.objectToXml(registerData);
        logger.info(rqXML);
        String reXML = manager.getResponseByAQPT("", ycnIP.getUpdateHyxx(), rqXML, ycnIP.getUrl());
        RegisterAQPTReData reData = XMLChange.xmlToObject(reXML, RegisterAQPTReData.class);
        if (!"0".equals(reData.getHead().getFhzbs())) {
            return new BaseResponseData<>("-9", "更新个人信息失败", null);
        }

        return new BaseResponseData<>("0", "更新个人信息成功", null);
    }

    private BaseResponseData<String> upUserByOther(BaseRequestData<UserRegister> baseRequestData, StationInfo stationInfo) {
        return null;

    }

    /**
     * 根据id获取employee
     * * 原理：
     * *   1、自动配置类；CacheAutoConfiguration
     * *   2、缓存的配置类
     * *   org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
     * *   org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration
     * *   org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
     * *   org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration
     * *   org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
     * *   org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
     * *   org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
     * *   org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration
     * *   org.springframework.boot.autoconfigure.cache.GuavaCacheConfiguration
     * *   org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration【默认】
     * *   org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration
     * *   3、哪个配置类默认生效：SimpleCacheConfiguration；
     * *
     * *   4、给容器中注册了一个CacheManager：ConcurrentMapCacheManager
     * *   5、可以获取和创建ConcurrentMapCache类型的缓存组件；他的作用将数据保存在ConcurrentMap中；
     * *
     * *   运行流程：
     * *   @Cacheable：
     * *   1、方法运行之前，先去查询Cache（缓存组件），按照cacheNames指定的名字获取；
     * *      （CacheManager先获取相应的缓存），第一次获取缓存如果没有Cache组件会自动创建。
     * *   2、去Cache中查找缓存的内容，使用一个key，默认就是方法的参数；
     * *      key是按照某种策略生成的；默认是使用keyGenerator生成的，默认使用SimpleKeyGenerator生成key；
     * *          SimpleKeyGenerator生成key的默认策略；
     * *                  如果没有参数；key=new SimpleKey()；
     * *                  如果有一个参数：key=参数的值
     * *                  如果有多个参数：key=new SimpleKey(params)；
     * *   3、没有查到缓存就调用目标方法；
     * *   4、将目标方法返回的结果，放进缓存中
     * *
     * *   @Cacheable标注的方法执行之前先来检查缓存中有没有这个数据，默认按照参数的值作为key去查询缓存，
     * *   如果没有就运行方法并将结果放入缓存；以后再来调用就可以直接使用缓存中的数据；
     * *
     * *   核心：
     * *      1）、使用CacheManager【ConcurrentMapCacheManager】按照名字得到Cache【ConcurrentMapCache】组件
     * *      2）、key使用keyGenerator生成的，默认是SimpleKeyGenerator
     * *
     * *
     * *   几个属性：
     * *      cacheNames/value：指定缓存组件的名字;将方法的返回结果放在哪个缓存中，是数组的方式，可以指定多个缓存；
     * *
     * *      key：缓存数据使用的key；可以用它来指定。默认是使用方法参数的值  1-方法的返回值
     * *              编写SpEL； #i d;参数id的值   #a0  #p0  #root.args[0]
     * *              getEmp[2]
     * *
     * *      keyGenerator：key的生成器；可以自己指定key的生成器的组件id
     * *              key/keyGenerator：二选一使用;
     * *
     * *
     * *      cacheManager：指定缓存管理器；或者cacheResolver指定获取解析器
     * *
     * *      condition：指定符合条件的情况下才缓存；
     * *              ,condition = "#id>0"
     * *          condition = "#a0>1"：第一个参数的值》1的时候才进行缓存
     * *
     * *      unless:否定缓存；当unless指定的条件为true，方法的返回值就不会被缓存；可以获取到结果进行判断
     * *              unless = "#result == null"
     * *              unless = "#a0==2":如果第一个参数的值是2，结果不缓存；
     * *      sync：是否使用异步模式
     *
     * @Cacheable 在方法执行之前判断，缓存中是否有值，有则不执行方法了
     * key：#baseRequestData.method 取参数对象属性值
     */
    @Cacheable(cacheNames = {"SMSCOD"}, key = "#data.rqData", unless = "true") //在方法执行之前，判断缓存中是否有值，有则不执行方法了
    public BaseResponseData<User> getDate(BaseRequestData<String> data) {

        return new BaseResponseData<User>("-1", "-1", null);
    }

    /**
     * 更新employee
     *
     * @CachePut 方法先执行，再将方法的返回结果放到缓存中（方法没出异常）
     * key：#baseRequestData.method 取参数对象属性值
     */
    @CachePut(cacheNames = {"time"}, key = "#baseRequestData.method") //方法先执行，再将方法的返回结果放到缓存中（方法没出异常）
    public String CachePut(BaseRequestData<Map<String, String>> baseRequestData) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 模拟删除，清空所有缓存
     *
     * @param baseRequestData 1
     * @return String
     * @CacheEvict 默认在方法执行之后（方法没出异常），清除指定缓存
     * key：#baseRequestData.method 取参数对象属性值
     */
    @CacheEvict(cacheNames = {"time"}, key = "#baseRequestData.method") //方法先执行，再将方法的返回结果放到缓存中（方法没出异常）
    public String CacheEvict(BaseRequestData<Map<String, String>> baseRequestData) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        return dateFormat.format(calendar.getTime());
    }


}
