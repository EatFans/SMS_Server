/**
 * 回应
 * 本类是用于客户端与服务端数据信息交流的数据模型对象类
 * 本类，用于处理客户端请求后，发送回应的数据对象类
 */
package cn.newworld.model;

public class Response {
    private final String response;
    public Response(String result){
        this.response = result;
    }

    public String getResponseBody(){
        return "HTTP/1.1 200 OK\r\n\r\n" + response;
    }

}
