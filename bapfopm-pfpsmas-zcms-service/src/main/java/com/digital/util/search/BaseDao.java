package com.digital.util.search;

import java.io.Serializable;
import java.util.Collection;
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
public interface BaseDao<T extends Serializable> {


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

    String insert(Object o);
    String update(Object o);
    String delete(Object o);
    String get(Object o);
    String seek(QueryReq req);
    String pageSeek(QueryReq req, int pageIndex, int pageSize);
    String countBy(String field, QueryFilter ... filters);
    String findByIds(String ids);
    String findAll();
    String batchDelete(Collection<?> keys);


}
