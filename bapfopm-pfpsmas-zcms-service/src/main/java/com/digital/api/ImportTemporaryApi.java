package com.digital.api;

import com.digital.entity.province.ContrastTemporary;
import com.digital.entity.province.FocusChangeFileInfo;

import java.util.List;

/**
 * @Description: TODO 导入临时表
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

    /*
    * 提供下载查询
    * */
    List<ContrastTemporary> queryTemporary(Integer fileSquence,String errorIdentification);
    /*
    * 导入数据查询
    * */
    List<ContrastTemporary> queryChangeData(Integer fileSquence,String zoningCode,String errorIndentification,String typeCode);


}
