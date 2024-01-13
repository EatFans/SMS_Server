/**
 * 该类为事件监听器，处理器
 */
package cn.newworld.event;

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
}
