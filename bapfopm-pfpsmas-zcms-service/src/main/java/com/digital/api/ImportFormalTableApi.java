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

    boolean ImprotFormalT(FocusChangeFileInfo fileInfo, List<ContrastTemporary> temporaryList);

    void importformalTable();
}
