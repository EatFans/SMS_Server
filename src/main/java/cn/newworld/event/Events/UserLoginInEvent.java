package cn.newworld.event.Events;

import cn.newworld.event.Event;
import cn.newworld.event.EventsManager;
import cn.newworld.event.Listener;

public class UserLoginInEvent implements Listener {
    private static final EventsManager eventsManager = new EventsManager();
    private Event event;
    private int userId;
    private String userName;
    private String userPassword;


    public EventsManager getEventsManager(){
        return eventsManager;
    }
}
