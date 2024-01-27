/**
 * 该类为请求类型枚举类，
 * 用于枚举各种http请求类型，
 * 其每个类型对应着它的String字符串
 */
package cn.newworld.http;

public enum RequestType {
    GET("GET"),
    POST("POST"),
    OPTIONS("OPTIONS"),
    HEAD("HEAD"),
    PUT("PUT"),
    DELETE("DELETE"),
    TRACE("TRACE"),
    CONNECT("CONNECT");
    private final String requestType;
    private RequestType(String requestType){
        this.requestType = requestType;
    }
    public String getToString(){
        return requestType;
    }
}
