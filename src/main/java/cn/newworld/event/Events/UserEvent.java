/**
 * 本类继承了Event抽象类，在
 */
package cn.newworld.event.Events;

import cn.newworld.model.entity.User;

public abstract class UserEvent extends Event {
    protected User user;
    public UserEvent(User user){
        this.user = user;
    }


}
