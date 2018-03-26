package com.filter;

import com.token.Common;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.SortedMap;
import java.util.TreeMap;

@WebFilter(urlPatterns="/api/*")
public class SignFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

//            String qs = "token=123655&username=123&password=122&time=" + req.getParameter("time");resp.getWriter().write(AESUtil.encrypt(qs, Common.SECRET_KEY));



        if("true".equals(req.getAttribute("isVerify"))){
            chain.doFilter(req, resp);
        }else{
            if (Common.checkRequestAuth(req, resp)) {
                chain.doFilter(req, resp);
            }
        }


    }

    public void init(FilterConfig config) throws ServletException {

    }

    /**
     * 跨域
     *
     * @param response
     */
    static void setCors(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600"); //设置过期时间
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, client_id, uuid, Authorization");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 支持HTTP 1.1.
        response.setHeader("Pragma", "no-cache");
    }

}
