/**
 * 本类继承了Event抽象类，在
 */
package cn.newworld.event.Events;

import cn.newworld.model.User;

public abstract class UserLoginEvent extends Event {
    protected User user;
    public UserLoginEvent(User user){
        this.user = user;
    }


}
