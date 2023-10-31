package pvt.example.common.utils;

import java.util.Random;

/**
 * 类&emsp;&emsp;名：StringGeneratorUtil <br/>
 * 描&emsp;&emsp;述：字符串随机生成工具
 */
public class StringGeneratorUtil {
    private static final char[] allowedSpecialCharacters = {
            '`', '~', '@', '#', '$', '%', '^', '&',
            '*', '(', ')', '-', '_', '=', '+', '[',
            '{', '}', ']', '\\', '|', ';', ':', '"',
            '\'', ',', '<', '.', '>', '/', '?'};//密码能包含的特殊字符
    private static final int spCharacterRange = allowedSpecialCharacters.length;

    /**
     * 随机字符串
     * @param length 长度
     * @return 拼接后的字符串
     */
    public static String getRandomString(Integer length) {
        Random randomRoot = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = randomRoot.nextInt(3);
            Character tempChar = StringGeneratorUtil.getRandomChar(number, randomRoot);
            sb.append(tempChar);
        }
        return sb.toString();
    }

    /**
     * 随机密码
     * @param length 长度
     * @return 拼接后的随机密码
     */
    public static String getRandomPwd(Integer length) {
        Random randomRoot = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = randomRoot.nextInt(4);
            Character tempChar = StringGeneratorUtil.getRandomChar(number, randomRoot);
            sb.append(tempChar);
        }
        return sb.toString();
    }

    /**
     * @param type   数据类型
     * @param random random对象
     * @return 随机一个Char字符
     */
    private static Character getRandomChar(Integer type, Random random) {
        Character tempChar = null;
        switch (type) {
            case 0:
                tempChar = (char) (random.nextInt(26) + 65);
                break;
            case 1:
                tempChar = (char) (random.nextInt(26) + 97);
                break;
            case 2:
                tempChar = (char) (random.nextInt(10) + '0');
                break;
            case 3:
                tempChar = StringGeneratorUtil
                        .allowedSpecialCharacters
                        [random.nextInt(StringGeneratorUtil.spCharacterRange)];
        }
        return tempChar;
    }
}
