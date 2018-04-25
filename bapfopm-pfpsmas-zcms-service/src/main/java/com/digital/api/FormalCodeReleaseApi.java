package com.digital.api;

import com.digital.entity.FormalCodeReleaseInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * @Description: TODO
 * @Author: zhanghpj
 * @Version 1.0, 16:47 2018/4/24
 * @See
 * @Since
 * @Deprecated
 */
public interface FormalCodeReleaseApi {

    /*
    * 查询区划发布的信息（动态）
    * */
    List<FormalCodeReleaseInfo> selectFormalReleaseByExportNum(String exportDate,String deadline);

    /*
    * 下载全国区划代码
    * */
    void downloadFormalRelease(HttpServletResponse response,String file);

}
