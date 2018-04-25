package com.digital.api;

import com.digital.entity.province.ContrastTemporary;
import com.digital.entity.province.FocusChangeFileInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: TODO 导入到正式表
 * @Author: zhanghpj
 * @Version 1.0, 18:48 2018/3/28
 * @See
 * @Since
 * @Deprecated
 */

public interface ImportFormalTableApi {

    /*
    * 导入预览数据表中
    * */
    boolean ImprotFormalT(FocusChangeFileInfo fileInfo, List<ContrastTemporary> temporaryList);

    /*
    * 预览表数据导入到正式表和历史表
    * */
    void importformalTable();

    /*
    * 区划发布 将全国每个省的区划查询并写入到txt中
    * */
    boolean writerZoningCodeToTxt();

}
