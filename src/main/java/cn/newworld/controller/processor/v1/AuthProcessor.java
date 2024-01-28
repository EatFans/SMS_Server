package cn.newworld.controller.processor.v1;

import cn.newworld.controller.Processor;
import cn.newworld.http.RequestMapping;
import cn.newworld.http.RequestType;
import cn.newworld.http.ContentType;
import cn.newworld.http.HttpStatus;
import cn.newworld.http.model.Request;
import cn.newworld.http.model.Response;


public class AuthProcessor implements Processor {
    private final String URL = "/v1/auth";

    @RequestMapping(requestUrl = URL+"/login", requestType = RequestType.POST)
    public String userLogin(Request request){
        // TODO: 处理用户登录

        String test = "{\n" +
                "    \"action\": \"userLogin\",\n" +
                "    \"data\": {\n" +
                "        \"name\": \"testMan\",\n" +
                "        \"password\": \"123456\",\n" +
                "        \"token\": \"32feq13\"\n" +
                "    }\n" +
                "}";


        return Response.createResponse()
                .setStatus(HttpStatus.OK.getCode(),HttpStatus.OK.getReasonPhrase())
                .addHeader("Content-Type", ContentType.APPLICATION_JSON)
                .addHeader("Content-Length", String.valueOf(test.length()))
                .setBody(test)
                .buildResponseMessage();
    }

    @RequestMapping(requestUrl = URL+"/register", requestType = RequestType.GET)
    public String register(Request request){
        // TODO: 处理用户注册
        String test = "{\n" +
                "    \"action\": \"userLogin\",\n" +
                "    \"data\": {\n" +
                "        \"name\": \"testMan\",\n" +
                "        \"password\": \"123456\",\n" +
                "        \"token\": \"32feq13\"\n" +
                "    }\n" +
                "}";
        return Response.createResponse().setBody(test).buildResponseMessage();
    }


}
