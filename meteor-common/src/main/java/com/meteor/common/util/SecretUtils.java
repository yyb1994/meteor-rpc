package com.meteor.common.util;
import com.meteor.common.core.StandardCharsets;
import java.security.MessageDigest;

/**
 * 加密解密工具类
 * 用到了sha-256+盐的方式做摘要算法
 * @author soupshy
 */
public class SecretUtils {
    private static final char[] DIGITS_LOWER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 使用sha256算法编码后转16进制字符串
     *
     * @param rawString
     * @return
     */
    public static String encodeHexSha256(String rawString) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(rawString.getBytes(StandardCharsets.UTF_8));
            return new String(encodeHex(hash, DIGITS_LOWER));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static char[] encodeHex(final byte[] data, final char[] toDigits) {
        final int l = data.length;
        final char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92".equals(SecretUtils.encodeHexSha256("123456")));
    }
}
