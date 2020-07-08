//package com.cdha.rlpiss.wx.wxservice.wxservice.config;
//
//import java.util.Arrays;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//
///**
// * @Description 拦截器
// * @Author HJL
// * @Date Created in 2020-05-29
// */
//
////@Configuration
//public class WebInterceptor implements HandlerInterceptor {
//    private List<Integer> errorCodeList = Arrays.asList(404, 403, 500);
//
//    /**
//     * 在请求被处理之前调用
//     *
//     * @param request
//     * @param response
//     * @param handler
//     * @return
//     * @throws Exception
//     */
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//            throws Exception {
//        // 检查每个到来的请求对应的session域中是否有登录标识
////        Object loginName = request.getSession().getAttribute("userName");
////        if (null == loginName || !(loginName instanceof String)) {
////            // 未登录，重定向到登录页
////            response.sendRedirect("/login");
////            return false;
////        }
//        // 当用户访问的地址在本项目中没有的时候  也就是找不到页面   重定向404页面
//        if (errorCodeList.contains(response.getStatus())) {
//            // 捕获异常后进行重定向，controller对应的requestMapping为/error/
//            response.sendRedirect("/");
//            return false;
//        }
////		String userName = (String) loginName;
//        // System.out.println("当前用户已登录，登录的用户名为： " + userName);
//        return true;
//    }
//
//    /**
//     * 在请求被处理后，视图渲染之前调用
//     *
//     * @param request
//     * @param response
//     * @param handler
//     * @param modelAndView
//     * @throws Exception
//     */
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//                           ModelAndView modelAndView) throws Exception {
//
//    }
//
//    /**
//     * 在整个请求结束后调用
//     *
//     * @param request
//     * @param response
//     * @param handler
//     * @param ex
//     * @throws Exception
//     */
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//            throws Exception {
//
//    }
//}
