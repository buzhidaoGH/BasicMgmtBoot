package pvt.example.common.utils;

import java.util.Arrays;

/**
 * 类&emsp;&emsp;名：PadStringUtil <br/>
 * 描&emsp;&emsp;述：字符串填充
 */
public class PadStringUtil {
    /**
     * 右边补齐
     * @param content 字符串
     * @param len     长度
     * @param ch      填充符
     * @return 拼接后字符串
     */
    public static String padRight(String content, Integer len, Character ch) {
        String res = String.format("%-" + len + "s", content);
        res = res.replaceAll("\\s", ch.toString());
        return res;
    }

    /**
     * 右边补齐(填充符默认＝)
     * @param content 字符串
     * @param len     长度
     * @return 拼接后字符串
     */
    public static String padRight(String content, Integer len) {
        return PadStringUtil.padRight(content, len, '＝');
    }

    /**
     * 左边补齐
     * @param content 字符串
     * @param len     长度
     * @param ch      填充符
     * @return 拼接后字符串
     */
    public static String padLeft(String content, Integer len, Character ch) {
        String res = String.format("%" + len + "s", content);
        res = res.replaceAll("\\s", ch.toString());
        return res;
    }

    /**
     * 右边补齐(填充符默认＝)
     * @param content 字符串
     * @param len     长度
     * @return 拼接后字符串
     */
    public static String padLeft(String content, Integer len) {
        return PadStringUtil.padLeft(content, len, '＝');
    }

    /**
     * 两边补齐
     * @param content 字符串
     * @param len     长度
     * @param ch      填充符号
     * @return 拼接后字符串
     */
    public static String padCenter(String content, Integer len, Character ch) {
        int diff = len - content.length();
        if (diff <= 0) { return content;}
        int destHalf = (int) Math.ceil((double) diff / 2);
        char[] charArr = new char[len];
        char[] charContentArr = content.toCharArray();
        Arrays.fill(charArr, ch);
        System.arraycopy(charContentArr, 0, charArr, destHalf, charContentArr.length);
        return String.valueOf(charArr);
    }

    /**
     * 两边补齐
     * @param content 字符串
     * @param len     长度
     * @return 拼接后字符串
     */
    public static String padCenter(String content, Integer len) {
        return PadStringUtil.padCenter(content, len, '＝');
    }
}
