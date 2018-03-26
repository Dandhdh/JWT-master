package com.filter;

import com.token.Common;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class UTF8Filter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("编码过滤器");
        String encoding = "utf8";
        //设置request字符编码
        req.setCharacterEncoding(encoding);
        //设置response字符编码
        resp.setContentType("text/html;charset="+encoding);
        ((HttpServletResponse)req).setHeader("Access-Control-Allow-Origin","*");
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
