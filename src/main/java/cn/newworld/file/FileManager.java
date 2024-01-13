/**
 * 文件创建类，用于各种文件创建提取等操作的管理类
 * @author EatFan
 */
package cn.newworld.file;

import cn.newworld.Server;
import cn.newworld.util.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileManager {
    private static final String CURRENT_DIR = System.getProperty("user.dir");

    /**
     * 在当前jar包路径下，创建一个文件
     * @param fileName 文件名
     * @return 创建成功返回true，创建失败打印报错并返回false
     */
    public static boolean createFile(String fileName){
        File file = new File(fileName);
        if (!file.exists()){
            try {
                if (file.createNewFile())
                    return true;
            } catch (IOException e){
                Logger.error(e.getMessage());
                return false;
            }
        }
        return true;
    }

    /**
     * 批量创建文件夹目录
     * @param directories 所需要创建的文件夹名称
     */
    public static void createDirectory(String[] directories){
        try {
            for(String dirName : directories){
                Path dirPath = Paths.get(CURRENT_DIR,dirName);
                if (Files.notExists(dirPath))
                    Files.createDirectories(dirPath);
            }

        } catch (Exception e){
            Logger.error(e.getMessage());
        }
    }

    /**
     * 提取jar资源中的文件提取复制到工作根目录下
     * @param resourceFile 指定文件名
     */
    public static void extractFile(String resourceFile){
        Path jarFolderPath = Paths.get(CURRENT_DIR);
        String resourcePath = "/" + resourceFile;
        Path targetPath = jarFolderPath.resolve(resourceFile);

        if (!Files.exists(targetPath)) {
            try (InputStream resourceStream = Server.class.getResourceAsStream(resourcePath)) {
                if (resourceStream != null) {
                    Files.copy(resourceStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    Logger.error("资源不存在：" + resourcePath);
                }
            } catch (Exception e) {
                Logger.error(e.getMessage());
            }
        }
    }

    /**
     * 从jar中Resource的某个目录中提取复制指定文件到工作根目录下
     * @param resourceFile 指定文件名
     * @param resourceDirectory 文件所在的目录名 /name/
     */
    public static void extractFile(String resourceFile,String resourceDirectory){
        Path jarFolderPath = Paths.get(CURRENT_DIR);
        String resourcePath = resourceDirectory + resourceFile;
        Path targetPath = jarFolderPath.resolve(resourceFile);
        if (!Files.exists(targetPath)) {
            try (InputStream resourceStream = Server.class.getResourceAsStream(resourcePath)) {
                if (resourceStream != null) {
                    Files.copy(resourceStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    Logger.error("资源不存在：" + resourcePath);
                }
            } catch (Exception e) {
                Logger.error(e.getMessage());
            }
        }
    }

}
