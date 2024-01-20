/**
 * 该类为响应映射注解，用于映射处理请求URL
 */
package cn.newworld.controller;

public @interface RequestMapping {
    // url值
    String value();
    // 请求的方法类型
    HttpMethod method() default HttpMethod.GET;
}
