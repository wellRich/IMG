package com.digital.util.search;

import org.apache.ibatis.annotations.Results;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author guoyka
 * @version 2018/4/3
 * @see [相关类/方法]
 * @since [产品/模块版本]
 * @deprecated
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MyResults  {
    Results res = null;
}
