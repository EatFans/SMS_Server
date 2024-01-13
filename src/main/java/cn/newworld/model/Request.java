/**
 * 请求
 * 本类是用于客户端与服务端数据信息交流的数据模型对象类
 * 本类，用于客户端发送的数据，json数据解析为本类对象，即为请求对象，
 * 然后将本来对象数据进行进一步的调用
 */
package cn.newworld.model;

import cn.newworld.event.EventType;

public class Request {

    private String eventName;
    private EventType eventType;
    public Request(String jsonString){


    }

    public String getEventName(){
        return eventName;
    }

    public EventType eventType(){
      return eventType;
    }

}

