package com.yikejian.payment.util;

import com.google.common.base.Charsets;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <code>HashUtil</code>.
 * Hash工具类
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2017/6/9 15:03
 */
public final class HashUtils {

    /**
     * 隐藏构造函数
     */
    private HashUtils() {
    }

    /**
     * 转换字节数组为16进制字符串
     *
     * @param bytes 略
     * @return 略
     */
    private static String byteArrayToHexString(final byte[] bytes) {
        StringBuilder ret = new StringBuilder(bytes.length << 1);
        for (int i = 0; i < bytes.length; i++) {
            ret.append(Character.forDigit((bytes[i] >> 4) & 0xf, 16));
            ret.append(Character.forDigit(bytes[i] & 0xf, 16));
        }
        return ret.toString();
    }

    /**
     * MD5编码
     *
     * @param string 原始字符串
     * @return 经过MD5加密之后的结果 -- 16进制字符串
     * @throws UnsupportedEncodingException 略
     * @throws NoSuchAlgorithmException     略
     */
    public static String md5Encode(final String string) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return md5Encode(string.getBytes(Charsets.UTF_8));
    }

    /**
     * @param bytes 原始字节数组
     * @return 经过MD5加密之后的结果 -- 16进制字符串
     * @throws NoSuchAlgorithmException 略
     */
    public static String md5Encode(final byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(bytes);
        return byteArrayToHexString(md.digest());
    }

    /**
     * SHA编码
     *
     * @param string 原始字符串
     * @return 经过SHA加密之后的结果 -- 16进制字符串
     * @throws NoSuchAlgorithmException     略
     * @throws UnsupportedEncodingException 略
     */
    public static String shaEncode(final String string) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return shaEncode(string.getBytes(Charsets.UTF_8));
    }

    /**
     * SHA编码
     *
     * @param bytes 原始字节数组
     * @return 经过SHA加密之后的结果 -- 16进制字符串
     * @throws NoSuchAlgorithmException     略
     * @throws UnsupportedEncodingException 略
     */
    public static String shaEncode(final byte[] bytes) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest sha = MessageDigest.getInstance("SHA");
        sha.update(bytes);
        return byteArrayToHexString(sha.digest());
    }

}
