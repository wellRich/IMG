package com.digital.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import org.slf4j.LoggerFactory;

/**
 * 实体相关的工具方法
 *
 * @author guoyka
 * @version 1.0, 2018/4/20
 */
public class EntityHelper {

    protected static final org.slf4j.Logger log = LoggerFactory.getLogger(EntityHelper.class);

    /**
     * 将实体实例转化成map
     * @param entity 实体对象
     * @return [fieldName: fieldValue]
     * @throws IllegalAccessException
     */
    public static Map<String, Object> toMap(Object entity) throws IllegalAccessException {
        if (entity == null) {
            return null;
        } else {
            Map<String, Object> map = new HashMap<>();
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(entity.getClass());
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor property : propertyDescriptors) {
                    String key = property.getName();
                    // 过滤class属性
                    if (!key.equals("class")) {
                        // 得到property对应的getter方法
                        Method getter = property.getReadMethod();
                        Object value = getter.invoke(entity);
                        map.put(key, value);
                    }

                }
            } catch (Exception e) {
                System.out.println("transBeanToMap Error --> " + e.getLocalizedMessage());
                e.printStackTrace();
            }
            return map;
       /* Map<String, Object> map = new HashMap<>();
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field: fields){
            map.put(field.getName(), field.get(entity));
        }
        return map;*/
        }
    }
}
