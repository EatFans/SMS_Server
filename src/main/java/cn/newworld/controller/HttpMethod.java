/**
 * 枚举http协议中对于请求的类型种类
 */
package cn.newworld.controller;

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    TRACE("TRACE"),
    CONNECT("CONNECT"),
    OPTIONS("OPTIONS"),
    HEAD("HEAD");
    private final String name;

    private HttpMethod(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
