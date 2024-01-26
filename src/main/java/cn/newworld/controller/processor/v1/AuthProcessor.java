package cn.newworld.controller.processor.v1;

import cn.newworld.controller.Processor;
import cn.newworld.controller.RequestMapping;
import cn.newworld.controller.RequestType;
import cn.newworld.http.ContentType;
import cn.newworld.http.HttpStatus;
import cn.newworld.http.RequestEntity;
import cn.newworld.http.ResponseEntity;
import cn.newworld.util.Logger;

import java.util.Map;

public class AuthProcessor implements Processor {
    private final String URL = "/v1/auth";

    @RequestMapping(requestUrl = URL+"/login", requestType = RequestType.GET)
    public String userLogin(RequestEntity requestEntity){
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
    public String register(RequestEntity requestEntity){
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

    @RequestMapping(requestUrl = URL+"/test",requestType = RequestType.GET)
    public String test(RequestEntity requestEntity){
        Map<String, String> queryParams = requestEntity.getQueryParams();
        String test = "Hi! EatFan";
        // 查询url 中的数据
        String name = queryParams.get("name");
        if (name == null){
            Logger.info("请求的参数为空");
            return null;
        }
        return ResponseEntity.createResponse()
                .setStatus(HttpStatus.OK.getCode())
                .setReasonPhrase(HttpStatus.OK.getReasonPhrase())
                .addHeader("Content-Type", ContentType.TEXT_PLAIN)
                .addHeader("Content-Length", String.valueOf(test.length()))
                .setBody(test)
                .buildResponseMessage();
    }
}
