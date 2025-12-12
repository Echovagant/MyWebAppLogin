package com.mypro.common;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * EncodingFilter 过滤器：统一设置所有请求和响应的字符编码为UTF-8
 * 确保中文在整个应用中正确显示
 */
public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        System.out.println("EncodingFilter: 请求URL: " + req.getRequestURL());
        System.out.println("EncodingFilter: 请求原始编码: " + req.getCharacterEncoding());

        // 设置请求编码为UTF-8
        req.setCharacterEncoding("UTF-8");
        System.out.println("EncodingFilter: 设置请求编码为UTF-8后: " + req.getCharacterEncoding());

        // 设置响应编码为UTF-8
        System.out.println("EncodingFilter: 响应原始编码: " + res.getCharacterEncoding());
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=UTF-8");
        System.out.println("EncodingFilter: 设置响应编码为UTF-8后: " + res.getCharacterEncoding());
        System.out.println("EncodingFilter: 响应内容类型: " + res.getContentType());

        // 继续执行过滤器链
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("EncodingFilter initialized.");
    }

    @Override
    public void destroy() {
        System.out.println("EncodingFilter destroyed.");
    }
}