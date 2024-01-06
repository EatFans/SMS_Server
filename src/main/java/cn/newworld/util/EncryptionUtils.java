/**
 * 加密静态工具类，本类用于加密字符串或数字等
 */
package cn.newworld.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtils {
    /**
     * 哈希 SHA-256 加密字符串
     * @param input 需要加密的字符串
     * @return 返回已经加密的字符串
     */
    public static String sha256Hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            // 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 哈希SHA-256加密，加入了指定盐进行加密
     * @param originalData 需要加密的字符串
     * @param salt 盐
     * @return 已经加密好的字符串
     */
    public static String hashWithSalt(String originalData, String salt) {
        try {
            // 将原始数据和盐拼接
            String dataWithSalt = originalData + salt;
            // 使用 SHA-256 算法创建 MessageDigest 对象
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // 将数据转换为字节数组并更新 MessageDigest
            md.update(dataWithSalt.getBytes());
            // 计算哈希值
            byte[] hashedBytes = md.digest();
            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexStringBuilder.append('0');
                }
                hexStringBuilder.append(hex);
            }

            return hexStringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
