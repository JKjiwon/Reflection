package com.cos.reflect.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Dispatcher implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String endPoint = request.getRequestURI().replaceAll(request.getContextPath(), "");
        System.out.println("엔드포인트 : " + endPoint);


        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/"); // 필터를 다시 안거친다.
        // 필터를 통해서 requset, response객체를 만드는데, RequestDispatcher는 requset, response객체를
        // 재활용함.
        requestDispatcher.forward(request, response);
    }

    @Override
    public void destroy() {

    }
}

