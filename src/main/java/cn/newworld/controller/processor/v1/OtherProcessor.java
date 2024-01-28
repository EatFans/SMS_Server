package cn.newworld.controller.processor.v1;

import cn.newworld.controller.Processor;
import cn.newworld.http.RequestMapping;
import cn.newworld.http.RequestType;
import cn.newworld.http.model.Request;

public class OtherProcessor implements Processor {
    @RequestMapping(requestUrl = "/favicon.ico",requestType = RequestType.GET)
    public String onGetFaviconIco(Request request){
        return null;
    }
}
