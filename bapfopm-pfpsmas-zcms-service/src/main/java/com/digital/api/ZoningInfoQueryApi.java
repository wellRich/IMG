package com.digital.api;

import com.digital.entity.FormalTableInfo;

import java.util.List;
import java.util.Map;

/**
 * 区划信息查询api
 * 对区划正式表、区划预览表和区划变更明细对照表的查询接口
 * @author guoyka
 * @version 1.0, 2018/4/13
 */
public interface ZoningInfoQueryApi {

    /////////////////////////////区划代码预览////////////////////////////////////////
    /**
     * 通过区划代码获取下一级区划数据
     * @param zoningCode 区划代码
     * @return [级次代码：所有的子级区划]
     */
    Map<String, List<FormalTableInfo>> findSubordinateZoning(String zoningCode);


    /**
     * 获取祖上、自身、子级区划数据
     * @param zoningCode 登录用户所属的区划代码
     * @return  [级次代码：所有的子级区划]
     */
    Map<String, List<FormalTableInfo>> findAncestorsAndSubsByZoningCode(String zoningCode);


    //////////////////////////////变更对照查询///////////////////////////////////////////////




    ///////////////////////////////区划状态查询///////////////////////////////////////////////////////



    ////////////////////////////////变更情况汇总查询////////////////////////////////////////////////////////


    //////////////////////////////市级监督///////////////////////////////////////////////
}
