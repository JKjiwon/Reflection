package com.cos.reflect.filter;

import com.cos.reflect.annotation.RequestMapping;
import com.cos.reflect.controller.UserController;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Enumeration;

public class Dispatcher implements Filter {

    private boolean isMatching = false;

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

            if (requestMapping.value().equals(endpoint)) {
                isMatching = true;
                try {
                    // Parameter가 xxxDto 1개 만 다룬다.
                    Parameter[] parameters = method.getParameters();
                    String path = null;
                    if (parameters.length != 0) {
                        // 파라메터가 있을 때
                        Object dtoInstance = parameters[0].getType().newInstance(); // dto 인스턴스를 만들어서
                        setData(dtoInstance, request); // dto 인스턴스에 값 주입
                        path = (String) method.invoke(userController, dtoInstance);

                    } else {
                        // 파라메터가 없을 때
                        path = (String) method.invoke(userController);
                    }

                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
                    requestDispatcher.forward(request, response);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        if (isMatching) {
            response.setContentType("text/html; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("잘못된 주소요청 입니다. 404 에러 ");
            out.flush();
        }

    }

    private <T> void setData(T instance, HttpServletRequest request) { // instance : loginDto
        Enumeration<String> keys = request.getParameterNames();  // (username, password)
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String methodKey = keyToMethodKey(key); // setUsername

            Method[] methods = instance.getClass().getDeclaredMethods(); // setUsername, setPassword

            for (Method method : methods) {
                if (method.getName().equals(methodKey)) {
                    try {
                        // request.getParameter(key) -> setUsername("username의 입력값") 함수 호출
                        if (request.getParameter(key).chars().allMatch(Character::isDigit)) {
                            // request.getParameter(key)의 값이 int 라면?
                            int value = Integer.parseInt(request.getParameter(key));
                            method.invoke(instance, value);
                        } else {
                            method.invoke(instance, request.getParameter(key));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private String keyToMethodKey(String key) {
        String firstKey = "set";
        String upperKey = key.substring(0, 1).toUpperCase();
        String remainKey = key.substring(1);

        return firstKey + upperKey + remainKey;
    }

    @Override
    public void destroy() {

    }
}

