package com.cos.reflect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD}) // 어노테이션 설정 위치
@Retention(RetentionPolicy.RUNTIME) // 어노테이션이 실행 시점에 작동
public @interface RequestMapping {
    String value(); // 어노테이션에 파라메터 추가
}
