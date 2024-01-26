package cn.newworld.http;

import java.util.HashMap;
import java.util.Map;

/**
 * 响应消息实体类，用于创建初始化设置响应消息
 */
public class ResponseEntity {
    private int status;     // 响应状态码
    private String reasonPhrase;    // 描述状态码的短语
    private final Map<String,String> responseHeaders = new HashMap<>();  // 响应头
    private  String body;    // 响应体

    private ResponseEntity(){

    }

    /**
     * 创建 Response 响应
     * @return Response 实例对象
     */
    public static ResponseEntity createResponse(){
        return new ResponseEntity();
    }

    /**
     * 设置响应状态
     * @param code 状态码
     * @return Response 实例
     */
    public ResponseEntity setStatus(int code){
        this.status = code;
        return this;
    }

    /**
     * 设置形容状态码的短语
     * @param reasonPhrase 状态码短语
     * @return Response 实例
     */
    public ResponseEntity setReasonPhrase(String reasonPhrase){
        this.reasonPhrase = reasonPhrase;
        return this;
    }

    /**
     * 添加响应头
     * @param headerName 响应头
     * @param content 响应头内容
     * @return Response 实例
     */
    public ResponseEntity addHeader(String headerName, String content){
        this.responseHeaders.put(headerName,content);
        return this;
    }

    /**
     * 设置响应体
     * @param body 响应体
     * @return Response 实例
     */
    public ResponseEntity setBody(String body){
        this.body = body;
        return this;
    }

    /**
     * 构建请求消息
     * @return 返回完整的请求消息
     */
    public String buildResponseMessage(){
        if (status == 0 || reasonPhrase == null){
            status = 200;
            reasonPhrase = "OK";
        }

        String statusLine = "HTTP/1.1 " + status + " " + reasonPhrase + "\r\n";

        StringBuilder headers = new StringBuilder();
        for (Map.Entry<String, String> entry : responseHeaders.entrySet()) {
            headers.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
        }

        String emptyLine = "\r\n";

        return statusLine + (headers.length() > 0 ? headers : "Content-Type: text/plain\r\n") + emptyLine + (body != null ? body : "");
    }
}
