/**
 * 该类为事件监听器，处理器
 */
package cn.newworld.event;

import cn.newworld.event.EventHandler;
import cn.newworld.event.Events.Event;
import cn.newworld.event.Listener;
import cn.newworld.util.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventsManager {
    private static final List<Listener> listeners = new ArrayList<>();


    /**
     * 注册事件监听器的对象
     * @param listener 事件监听器的对象实例
     */
    public static void registerEventHandler(Listener listener){
        listeners.add(listener);
    }

    /**
     * 获取所有已经注册的事件监听器
     * @return 返回所以已经注册的事件监听器
     */
    public static List<Listener> getListeners(){
        return listeners;
    }

    /**
     * 清理所有以及注册的事件监听器
     */
    public static void close(){
        listeners.clear();
    }

    /**
     * 触发、执行某个事件
     * @param event 事件
     */
    public static void callEvent(Event event){
        // 遍历所有已注册的事件监听器
        for (Listener listener : listeners) {
            Class<?> listenerClass = listener.getClass();
            for (Method method : listenerClass.getDeclaredMethods()) {
                // 检查方法是否带有 @EventHandler 注解
                if (method.isAnnotationPresent(EventHandler.class)) {
                    // 检查方法的参数是否符合类型
                    if (method.getParameterTypes().length == 1 && method.getParameterTypes()[0].equals(event.getClass())) {
                        EventHandler annotation = method.getAnnotation(EventHandler.class);
                        int priority = annotation.priority().ordinal();

                        // 调用方法
                        invokeMethodWithPriority(listener, method, event, priority);
                    }
                }
            }
        }
    }

    /**
     * 调用方法，考虑优先级
     * @param listener 监听器
     * @param method 方法
     * @param event 事件
     * @param priority 优先级
     */
    private static void invokeMethodWithPriority(Listener listener, Method method, Event event, int priority) {
        // 在处理方法时考虑优先级
        for (Listener otherListener : listeners) {
            if (otherListener != listener) {
                for (Method otherMethod : otherListener.getClass().getDeclaredMethods()) {
                    if (otherMethod.isAnnotationPresent(EventHandler.class)) {
                        EventHandler otherAnnotation = otherMethod.getAnnotation(EventHandler.class);
                        int otherPriority = otherAnnotation.priority().ordinal();

                        // 如果优先级比当前方法高，跳过当前方法
                        if (otherPriority > priority) {
                            return;
                        }
                    }
                }
            }
        }

        // 如果没有更高优先级的方法，调用当前方法
        try {
            method.invoke(listener, event);
        } catch (IllegalAccessException | InvocationTargetException e) {
            Logger.error(e.getMessage());
        }
    }


}
