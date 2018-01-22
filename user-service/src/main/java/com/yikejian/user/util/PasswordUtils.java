package com.yikejian.user.util;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author jackalope
 * @Title: PasswordUtils
 * @Package com.yikejian.user.util
 * @Description: TODO
 * @date 2018/1/22 23:18
 */
public final class PasswordUtils {

    public static String encoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        return base64en.encode(md5.digest(str.getBytes("utf-8")));
    }

}
