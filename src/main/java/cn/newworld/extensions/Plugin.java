package cn.newworld.extensions;

/**
 * 插件接口，用于插件拓展补丁继承JavaPlugin，实现以下方法，进行插件逻辑
 */
public interface Plugin {
    void onLoad();

    void onLoop();
}
