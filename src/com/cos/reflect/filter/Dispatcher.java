package com.cos.reflect.filter;

import com.cos.reflect.annotation.RequestMapping;
import com.cos.reflect.controller.UserController;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class Dispatcher implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

//		System.out.println("컨텍스트패스 : " + request.getContextPath()); // 프로젝트 시작주소
//		System.out.println("식별자주소 : " + request.getRequestURI()); // 끝주소
//		System.out.println("전체주소 : " + request.getRequestURL()); // 전체주소

        String endpoint = request.getRequestURI().replace(request.getContextPath(), "");
        System.out.println("endpoint = " + endpoint);

        // 실제 Spring 프레임워크에서는 IoC 가 싱글톤으로 관리한다.
        UserController userController = new UserController();

        // 리플랙션 -> 메서드를 런타임 시점에 찾아내서 실행
        Method[] methods = userController.getClass().getDeclaredMethods();
        // 그 파일의 메서드만. getMethod -> 상속된 메서드까지.

        // Annotation을 활용한 분기
        for (Method method : methods) {
            Annotation annotation = method.getDeclaredAnnotation(RequestMapping.class);
            RequestMapping requestMapping = (RequestMapping) annotation;

            if(requestMapping.value().equals(endpoint)){
                try{
                    String path = (String) method.invoke(userController);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
                    requestDispatcher.forward(request, response);

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }
        }


    }

    @Override
    public void destroy() {

    }
}

