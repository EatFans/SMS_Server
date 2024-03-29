package cn.newworld.http.model;

import cn.newworld.util.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 本类为请求数据模型实体对象，用于将客户端的请求消息解析，并获取解析完毕的必要数据信息
 */
public class Request {
    private final String[] requestLines;      // 请求的每一行
    private final String[] requestLineParts;    // 请求行中的元素
    private final String requestBody;       // 请求体
    private final Map<String,String> queryParams; // 查询URL中的参数
    private final Map<String,String> headers;   // 请求头部信息

    /**
     * 构造方法，初始化开始解析 requestMessage 请求消息
     * @param requestMessage 请求消息
     */
    public Request(String requestMessage){
        String[] cacheString = requestMessage.split("\r\n");
        this.requestLines = cacheString;
        this.requestLineParts = cacheString[0].split(" ");

        // 解析请求头
        this.headers = parseHeaders(requestLines);

        StringBuilder stringBuilder = new StringBuilder();
        boolean bodyStarted = false;
        for (String line : requestLines){
            if (bodyStarted){
                stringBuilder.append(line).append("\r\n");
            }
            if (line.isEmpty()){
                bodyStarted = true;
            }
        }
        this.requestBody = stringBuilder.toString();

        if ("GET".equals(getRequestType())){
            this.queryParams = parseQueryParams(requestLineParts[1]);
        } else {
            this.queryParams = new HashMap<>();
        }
    }

    /**
     * 解析查询参数
     *
     * @param queryString 包含查询参数的字符串
     * @return 包含解析后的键值对的Map
     */
    private Map<String, String> parseQueryParams(String queryString){
        Map<String, String> params = new HashMap<>();

        String[] keyValuePairs = queryString.split("\\?");
        if (keyValuePairs.length == 2) {
            String[] pairs = keyValuePairs[1].split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    try {
                        String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8.name());
                        String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8.name());
                        params.put(key, value);
                    } catch (UnsupportedEncodingException e) {
                        Logger.error(e.getMessage());
                    }
                }
            }
        }

        return params;
    }

    /**
     * 解析请求头
     * @param requestLines 请求的每一行
     * @return 返回所有的请求头
     */
    private Map<String,String> parseHeaders(String[] requestLines){
        Map<String,String> headers = new HashMap<>();
        for (int i = 1; i < requestLines.length; i++){
            String line = requestLines[i];
            if (line.isEmpty())
                break;
            String[] headerParts = line.split(": ");
            if (headerParts.length == 2)
                headers.put(headerParts[0],headerParts[1]);
        }
        return headers;

    }

    /**
     * 获取请求头
     * @return 所有的请求头
     */
    public Map<String,String> getHeaders(){
        return headers;
    }

    /**
     * 获取请求的方法
     * @return 请求方法
     */
    public String getRequestType(){
        return requestLineParts[0];
    }

    /**
     * 获取请求的URL
     * @return 请求的URL
     */
    public String getUrl(){
        return requestLineParts[1];
    }

    /**
     * 获取Url中的请求参数，本方法仅用在GET请求上
     * @return 所有的请求参数
     */
    public Map<String,String> getQueryParams(){
        return queryParams;
    }

    /**
     * 获取请求的请求体
     * @return 请求体
     */
    public String getRequestBody(){
        return requestBody;
    }



}
