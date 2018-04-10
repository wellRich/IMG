package com.digital.api;

import com.digital.entity.province.ContrastTemporary;
import com.digital.entity.province.FocusChangeFileInfo;

import java.util.Map;

/**
 * @Description: TODO 校验
 * @Author: zhanghpj
 * @Version 1.0, 18:31 2018/3/12
 * @See
 * @Since
 * @Deprecated
 */
public interface LogicCheckApi {

    /*
     * 校验
     * */
    Map<String,Object> ContentCheck(FocusChangeFileInfo fileInfo);

}
