package com.yikejian.message.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * <code>JsonUtil</code>.
 * Json处理工具类
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2017/6/9 18:23
 */
public final class JsonUtils {

    /**
     * Gson对象.
     */
    private static final Gson GSON = new Gson();

    /**
     * 隐藏构造函数.
     */
    private JsonUtils() {

    }

    /**
     * 对象转换成json字符串.
     *
     * @param object 对象
     * @return 返回json字符串
     */
    public static String toJson(final Object object) {
        return GSON.toJson(object);
    }

    /**
     * json字符串转换成对象
     *
     * @param jsonString json字符串
     * @param typeOfT    对象类型
     * @param <T>        T
     * @return T
     */
    public static <T> T fromJson(final String jsonString, final Type typeOfT) {
        return GSON.fromJson(jsonString, typeOfT);
    }

}
