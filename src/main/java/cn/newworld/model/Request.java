/**
 * 请求数据模型，用于解析http传输的请求数据解析
 */
package cn.newworld.model;


public class Request {
    private final String[] requestLines;  // 请求中的每一行
    private final String[] requestLineParts;  // 请求行元素
    private final String requestMessageBody;

    public Request(String requestMessage){
        String[] cacheString = requestMessage.split("\r\n");
        this.requestLines = cacheString;
        this.requestLineParts = cacheString[0].split(" ");

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
        this.requestMessageBody = stringBuilder.toString();
    }

    public String getUrl(){
        return requestLineParts[1];
    }

    public String getRequestType(){
        return requestLineParts[0];
    }

    public String getRequestMessageBody(){
        return requestMessageBody;
    }
}

