/**
 * 该类为事件监听器，处理器
 */
package cn.newworld.event;

import cn.newworld.event.Events.Event;
import cn.newworld.util.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventsManager {
    private List<Listener> listeners;

    public EventsManager(){
        this.listeners = new ArrayList<>();
    }

    /**
     * 注册事件监听器的对象
     * @param listener 事件监听器的对象实例
     */
    public void registerEventHandler(Listener listener){
        listeners.add(listener);
    }

    /**
     * 获取所有已经注册的事件监听器
     * @return 返回所以已经注册的事件监听器
     */
    public List<Listener> getListeners(){
        return listeners;
    }

    /**
     * 清理所有以及注册的事件监听器
     */
    public void close(){
        listeners.clear();
    }

    /**
     * 触发、执行某个事件
     * @param event 事件
     */
    public void callEvent(Event event){
        // 遍历所有已经注册的事件监听器
        for (Listener listener : listeners){
            Class<?> listenerClass = listener.getClass();
            for (Method method : listenerClass.getDeclaredMethods()){
                // 检查方法是否带有 @EventHandler 注解
                if (method.isAnnotationPresent(EventHandler.class)){
                    // 检查方法的参数是否符合类型
                    if (method.getParameterTypes().
                            length == 1 && method.
                            getParameterTypes()[0].
                            equals(event.getClass())){
                        try {
                            method.invoke(listener, event);
                        } catch (IllegalAccessException | InvocationTargetException e){
                            Logger.error(e.getMessage());
                        }
                    }
                }
            }
        }
    }
}
