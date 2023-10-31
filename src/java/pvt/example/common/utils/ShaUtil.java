package pvt.example.common.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 类&emsp;&emsp;名：ShaUtil <br/>
 * 描&emsp;&emsp;述：SHA加密工具类
 */
public class ShaUtil {
    private static MessageDigest messageDigest;

    /**
     * 获取Sha1的值
     */
    public static String getSha1(String plainText, boolean uppercase) {
        String shaText = ShaUtil.handlerSha(plainText);
        return uppercase ? shaText.toUpperCase() : shaText; //转换大写
    }

    /**
     * 获取Sha1的值(默认转换为大写)
     */
    public static String getSha1(String plainText) {
        String shaText = ShaUtil.handlerSha(plainText);
        return shaText.toUpperCase(); //转换大写
    }

    /**
     * 将字符串处理为Sha值
     */
    private static String handlerSha(String plainText) {
        byte[] bytes = plainText.getBytes(StandardCharsets.UTF_8);
        if (messageDigest == null) {
            try {
                messageDigest = MessageDigest.getInstance("SHA-1");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        messageDigest.update(bytes);
        byte[] digest = messageDigest.digest();
        return byteArrayToHexString(digest); //字符数组转换成字符串返回
    }

    /**
     * 将字节数组转为16进制字符串
     * @param bytes 要转换的字节数组
     * @return String 16进制字符串
     */
    private static String byteArrayToHexString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            String temp = Integer.toHexString(b & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                builder.append("0");
            }
            builder.append(temp);
        }
        return builder.toString();
    }
}
