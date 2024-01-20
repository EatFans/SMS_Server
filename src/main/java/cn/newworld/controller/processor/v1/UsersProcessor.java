package cn.newworld.controller.processor.v1;

import cn.newworld.controller.RequestMapping;

public class UsersProcessor {
    private final String URL = "/v1/users";

    @RequestMapping(URL+"/login")
    public String userLogin(){


        return " ";
    }
}
