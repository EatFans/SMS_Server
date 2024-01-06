/**
 * 我将服务端与客户端之间的交流交互的各种行为，称为事件，由单独的事件处理器去处理服务端各种事件
 * 并且，如果后续要开发插件，可以通过Listener接口，去改写各种事件拓展各种事件，每个事件必须要对应一个String类型的
 * 名称数据，方便服务端与客户端之间的数据传输
 */
package cn.newworld.event;

public enum Event {
    UserLoginInEvent("UserLoginInEvent"),
    UserRegistrationEvent("UserRegistrationEvent");
    private final String eventName;
    private Event(String EventName){
        this.eventName = EventName;
    }
    public String getEventName(){
        return eventName;
    }
}
