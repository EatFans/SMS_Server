package cn.newworld.controller.processor.v1;

import cn.newworld.controller.Processor;
import cn.newworld.controller.RequestMapping;
import cn.newworld.controller.RequestType;
import cn.newworld.http.ContentType;
import cn.newworld.http.HttpStatus;
import cn.newworld.http.ResponseEntity;
import cn.newworld.util.Logger;

public class AuthProcessor implements Processor {
    private final String URL = "/v1/auth";

    @RequestMapping(requestUrl = URL+"/login", requestType = RequestType.GET)
    public String userLogin(String requestBody){
        // TODO: 处理用户登录

        String test = "{\n" +
                "    \"action\": \"userLogin\",\n" +
                "    \"data\": {\n" +
                "        \"name\": \"testMan\",\n" +
                "        \"password\": \"123456\",\n" +
                "        \"token\": \"32feq13\"\n" +
                "    }\n" +
                "}";


        return ResponseEntity.createResponse()
                .setStatus(HttpStatus.OK.getCode())
                .setReasonPhrase(HttpStatus.OK.getReasonPhrase())
                .addHeader("Content-Type", ContentType.APPLICATION_JSON)
                .addHeader("Content-Length", String.valueOf(test.length()))
                .setBody(test)
                .buildResponseMessage();
    }

    @RequestMapping(requestUrl = URL+"/register", requestType = RequestType.GET)
    public String register(String requestBody){
        // TODO: 处理用户注册
        String test = "{\n" +
                "    \"action\": \"userLogin\",\n" +
                "    \"data\": {\n" +
                "        \"name\": \"testMan\",\n" +
                "        \"password\": \"123456\",\n" +
                "        \"token\": \"32feq13\"\n" +
                "    }\n" +
                "}";
        return ResponseEntity.createResponse().setBody(test).buildResponseMessage();
    }

    @RequestMapping(requestUrl = URL+"/null",requestType = RequestType.GET)
    public String testNull(String requestBody){
        Logger.info("测试返回响应值为null，是否直接关闭连接，不给予响应");
        return null;
    }
}
