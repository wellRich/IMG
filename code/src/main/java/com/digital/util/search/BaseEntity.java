package com.digital.util.search;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author [作者]
 * @version 2018/3/16
 * @see [相关类/方法]
 * @since [产品/模块版本]
 
 */
public interface BaseEntity<T extends Serializable> {


    default  String rename(String json, Map<String, String> renameMap) {
        if (renameMap == null) {
            return json;
        } else {
            String key;
            for(Iterator iterator = renameMap.keySet().iterator(); iterator.hasNext(); json = json.replace(key, renameMap.get(key))) {
                key = (String)iterator.next();
            }
            return json;
        }
    }

}
