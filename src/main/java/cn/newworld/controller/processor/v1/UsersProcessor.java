package cn.newworld.controller.processor.v1;

import cn.newworld.controller.Processor;
import cn.newworld.controller.RequestMapping;
import cn.newworld.controller.RequestType;
import cn.newworld.util.Logger;

public class UsersProcessor implements Processor {
    private final String URL = "/v1/users";

    @RequestMapping(requestUrl = URL, requestType = RequestType.GET)
    public String userLogin(String requestBody){
        Logger.info("客户端正在调用用户登录的api接口中...");
        Logger.info(requestBody);
        Logger.info("正在处理用户登录....");
        Logger.info("处理成功！用户成功登录！");
        String test = "{\n" +
                "    \"action\": \"userLogin\",\n" +
                "    \"data\": {\n" +
                "        \"name\": \"testMan\",\n" +
                "        \"password\": \"123456\",\n" +
                "        \"token\": \"32feq13\"\n" +
                "    }\n" +
                "}";

        return test;
    }

    @RequestMapping(requestUrl = "/v1/login", requestType = RequestType.GET)
    public String login(String requestBody){

        return "Hello World";
    }
}
