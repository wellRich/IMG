package com.digital.util;

import com.digital.entity.ChangeInfo;
import com.digital.entity.ZCCGroup;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 处理json的工具类
 * 目前使用的是Gson库，需要换成效率更高的FastJson库
 * @author  guoyka
 * @version 2018/3/20
 */

public class JSONHelper {
    private static Gson gson = new GsonBuilder()
            .disableInnerClassSerialization()
            .serializeNulls()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    /**
     * json转化为对象
     * @param str json字符串
     * @param clazz 类型
     * @param <T> 泛型
     * @return
     */
    public static <T> T fromJsonObject(String str, Class<T> clazz) {
        Type type = new ParameterizedTypeImpl(clazz, new Class[]{clazz});
        return gson.fromJson(str, type);
    }


    /**
     * json转化成List
     * @param str json字符串
     * @param clazz 类型
     * @param <T> 泛型
     * @return list
     */
    public static <T> List<T> fromJsonArray(String str, Class<T> clazz) {
        // 生成List<T> 中的 List<T>
        Type listType = new ParameterizedTypeImpl(List.class, new Class[]{clazz});
        return gson.fromJson(str, listType);
    }



    /**
     * 对象转化为json字符串
     * @param obj 对象
     * @param type 类型
     * @return json
     */
    public static String toJSON(Object obj, Type type){
        return gson.toJson(obj, type);
    }

    /**
     * 对象转化为json字符串
     * @param obj 对象
     * @return json
     */
    public static String toJSON(Object obj){
        return gson.toJson(obj);
    }


    private static class ParameterizedTypeImpl implements ParameterizedType {
        private final Class raw;
        private final Type[] args;
        private ParameterizedTypeImpl(Class raw, Type[] args) {
            this.raw = raw;
            this.args = args != null ? args : new Type[0];
        }
        @Override
        public Type[] getActualTypeArguments() {
            return args;
        }
        @Override
        public Type getRawType() {
            return raw;
        }
        @Override
        public Type getOwnerType() {return null;}
    }
}
