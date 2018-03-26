package com.token;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class Common {

    // 密钥
    public static String SECRET_KEY = "192006250b4c09247ec02edce69f6a2d";
    // sign参数的time与当前时间的最大插值，单位为秒，上线的时候改为10
    public static int APP_SIGN_TIME = 100000;
    // 同一sign参数的生命周期，单位为秒，上线的时候改为20
    public static int APP_SIGN_CACHE_TIME = 200000;

    /**
     * 检查每次app请求的数据是否合法
     */
    public static Boolean checkRequestAuth(ServletRequest request, ServletResponse response) throws IOException {

        // 转成HttpServlet容易处理
        HttpServletRequest hreq = (HttpServletRequest) request;
        HttpServletResponse hresp = (HttpServletResponse) response;
        // 设置加密的编码
        String characterEncoding = "UTF-8";
        // 有序的map
        SortedMap<Object, Object> parametersMap = new TreeMap<Object, Object>();
        // 获取所有参数添加有有序的map
        Enumeration enu = hreq.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            parametersMap.put(paraName, hreq.getParameter(paraName));
        }

        System.out.println(parametersMap);
        // 获取app传来的sign
        String sign = hreq.getHeader("sign");
        // 获取app传来的时间戳，用来验证请求是否有效
        String time = hreq.getParameter("time");
        // 在请求头中没有得到sign，说明请求是非法的
        if (sign == null) {
            System.out.println("非法请求");
            hresp.getWriter().write("\"Wrong Request!Illegality!\"");
            return false;
        }


        if (!sign.equals(createSign(characterEncoding, parametersMap))) {
            System.out.println("授权失败");

            hresp.getWriter().write("\"Authorization Fail!\"");
            return false;
        }

        // 创建缓存
        ICache cache = CacheFactory.getDefaultCache();
        if (cache.get(sign) != null) {
            System.out.println("请求已经被处理");
            hresp.getWriter().write("\"Wrong Request!Executed!\"");
            return false;
        }

        // 如果请求的time跟当前时间的time相差太大 则认定为超时
        if (Math.ceil((Math.abs(Long.valueOf(time) - (new Date()).getTime()) / 1000)) > APP_SIGN_TIME) {
            System.out.println("请求已经过期");
            hresp.getWriter().write("\"Wrong Request!Out of date!\"");
            return false;
        }

        // 新增缓存
        cache.Insert(sign, (new Date()).getTime(), APP_SIGN_CACHE_TIME);
        // 发现请求会经过过滤器两次，所以标记该请求已经被处理
        request.setAttribute("isVerify", "true");

        return true;

    }

    @SuppressWarnings("unchecked")
    public static String createSign(String characterEncoding, SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + SECRET_KEY);
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }

}
