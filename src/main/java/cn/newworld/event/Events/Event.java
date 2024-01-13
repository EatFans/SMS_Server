/**
 * 这是个抽象出来的Event类，这个类去衍生派生出各种事件类，相当于一个基类，
 * 无法直接创建这个Event类的对象，它提供一些事件的基础内容，然后在不同时候被调用去实现各种事件的处理
 */
package cn.newworld.event.Events;

import cn.newworld.event.EventType;

public abstract class Event {
    protected EventType eventType;

    private EventType getEventType(){
        return eventType;
    }
}
