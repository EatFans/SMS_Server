/**
 * 该类为响应映射注解，用于映射处理请求URL
 */
package cn.newworld.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    // url值
    String requestUrl();
    // 请求的方法类型
    RequestType requestType() default RequestType.GET;
}
