/**
 * 本类用于在接收到客户端String字符串后，进行解密解析为json对象后，
 * 将json对象传入本类，本来中将进行解析处理进行判断，去触发对应的事件
 * 然后返回一个该事件操作完成后的回应给客户端的数值
 */
package cn.newworld.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DataProcessor {
    private String jsonString;
    private ObjectMapper objectMapper;

    public DataProcessor(String jsonString){
        this.objectMapper = new ObjectMapper();
        this.jsonString = jsonString;
    }

    public void processData(){

    }
}
