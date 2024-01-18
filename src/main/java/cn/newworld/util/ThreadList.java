/**
 * 多线程统一注册、启动以及销毁关闭
 */
package cn.newworld.util;

import java.util.ArrayList;
import java.util.List;

public class ThreadList {
    private List<Runnable> runnableList;

    private List<Thread> threadList;

    public ThreadList(){
        this.runnableList = new ArrayList<>();
        this.threadList = new ArrayList<>();
    }

    public void addRunnable(Runnable runnable){
        runnableList.add(runnable);
    }

    public void startAllThread(){
        for (Runnable runnable : runnableList){
            Thread thread = new Thread(runnable);
            threadList.add(thread);
            thread.start();
        }
    }

    public void closeAllThread(){
        for (Thread thread : threadList){
            thread.interrupt();
        }
        for (Thread thread : threadList){
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
