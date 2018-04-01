package com.digital.api;

import com.digital.entity.province.FocusChangeFileInfo;

/**
 * @Description: TODO
 * @Author: zhanghpj
 * @Version 1.0, 16:14 2018/3/6
 * @See
 * @Since
 * @Deprecated
 */
public interface ImportTemporaryApi {

    /*
     * 导入临时表
     * */
    boolean importTemporary(FocusChangeFileInfo fileInfo);
}
