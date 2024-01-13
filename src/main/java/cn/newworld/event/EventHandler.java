package cn.newworld.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 一个用于方法的注解，专门用于对于事件监听的注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {
    /**
     * 事件的处理的优先级，优先级默认为NORMAL，事件优先级为枚举类
     * @return 优先级
     */
    EventPriority priority() default EventPriority.NORMAL;
}
