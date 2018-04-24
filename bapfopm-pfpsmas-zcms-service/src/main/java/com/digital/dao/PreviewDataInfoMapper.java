package com.digital.dao;

import com.digital.dao.sqlMapper.PreviewDataInfoSql;
import com.digital.entity.FormalTableInfo;
import com.digital.entity.PreviewDataInfo;
import com.digital.util.search.BaseMapper;
import com.digital.util.search.QueryFilter;
import com.digital.util.search.QueryReq;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author guoyka
 * @version 2018/3/21
 */
@Mapper
public interface PreviewDataInfoMapper extends BaseMapper<PreviewDataInfo>{

    String RESULT_MAP =  "com.digital.entity.allOfPreviewDataInfo";

    @ResultType(Integer.class)
    @UpdateProvider(type = PreviewDataInfoSql.class, method = BaseMapper.UPDATE)
    int save(Object previewDataInfo);


    @ResultType(Integer.class)
    @InsertProvider(type = PreviewDataInfoSql.class, method = BaseMapper.INSERT)
    int insert(Object previewDataInfo);

    @ResultType(Integer.class)
    @DeleteProvider(type = PreviewDataInfoSql.class, method = BaseMapper.DELETE)
    int delete(Object key);


    @Override
    int batchDelete(Collection<?> keys);
    /**
     * 修改预览数据
     * @param object 预览数据实例或者是map
     * @return 被修改的个数
     */
    @UpdateProvider(type = PreviewDataInfoSql.class, method = BaseMapper.UPDATE)
    int update(Object object);

    @SelectProvider(type = PreviewDataInfoSql.class, method = BaseMapper.GET)
    @ResultMap(RESULT_MAP)
    PreviewDataInfo get(Object key);


    @SelectProvider(type = PreviewDataInfoSql.class, method = BaseMapper.SEEK)
    @ResultMap(RESULT_MAP)
    List<PreviewDataInfo> seek(QueryReq req);


    @SelectProvider(type = PreviewDataInfoSql.class, method = BaseMapper.COUNT_BY)
    int countBy(String field, QueryFilter... filters);

    @Override
    @SelectProvider(type = PreviewDataInfoSql.class, method = BaseMapper.PAGE_SEEK)
    @ResultMap(RESULT_MAP)
    List<PreviewDataInfo> pageSeek(QueryReq req, int pageNum, int pageSize);



    @SelectProvider(type = PreviewDataInfoSql.class, method = "findAncestorsAndSubsByZoningCode")
    @ResultMap(RESULT_MAP)
    List<PreviewDataInfo> findAncestorsAndSubsByZoningCode(String zoningCode);

    @SelectProvider(type = PreviewDataInfoSql.class, method = "findFamilyZoning")
    @ResultMap(RESULT_MAP)
    List<PreviewDataInfo> findFamilyZoning(String levelCode, String columns);


    /**
     * 查找同一父级区划下的，指定名称的区划数量
     *
     * @param zoningCode 区划代码
     * @param zoningName 区划名称
     * @return Integer 数量
     */
    @SelectProvider(type = PreviewDataInfoSql.class, method = "findBrothersByCodeAndName")
    int findBrothersByCodeAndName(String zoningCode, String zoningName);

    @SelectProvider(type = PreviewDataInfoSql.class, method = "findBrothersByCode")
    @ResultMap(RESULT_MAP)
    List<PreviewDataInfo> findBrothersByCode(String zoningCode);




    /**
     * 通过区划代码查找
     * 有效标志为Y、选用标志为Y的区划预览数据
     *
     * @param zoningCode 区划代码
     * @return PreviewDataInfo 单条区划预览数据
     */
    @SelectProvider(type = PreviewDataInfoSql.class, method = "findValidOneByZoningCode")
    @ResultMap(RESULT_MAP)
    PreviewDataInfo findValidOneByZoningCode(String zoningCode);


    //获取下一级区划预览数据
    @SelectProvider(type = PreviewDataInfoSql.class, method = "findSubordinateZoning")
    @ResultMap(RESULT_MAP)
    List<PreviewDataInfo> findSubordinateZoning(String zoningCode);


    //更新数据至预览数据表
    @Update("UPDATE DM_XZQH_YLSJ Y SET Y.XYBZ='N', Y.YXBZ='N', Y.YXQ_Z =#{createDate}, Y.XGSJ =#{createDate} WHERE Y.XYBZ = 'Y' AND Y.YXBZ = 'Y' AND Y.UNIQUE_KEY=#{key}")
    @ResultType(Integer.class)
    Integer saveMergeData(@Param(value = "key") String key, @Param(value = "createDate") Date createDate);

    @Select("SELECT * FROM DM_XZQH_YLSJ WHERE XYBZ = 'N' AND YXBZ = 'N' AND YXQ_Z =#{createDate, jdbcType=DATE} AND XZQH_DM=#{zoningCode, jdbcType=VARCHAR}")
    @ResultMap(RESULT_MAP)
    PreviewDataInfo findAbandoned(@Param("createDate")String createDate, @Param("zoningCode") String zoningCode);


    @UpdateProvider(type = PreviewDataInfoSql.class, method = "updateFullName")
    @ResultType(Integer.class)
    Integer updateFullNameByLevelCode(String oldFullName, String newFullName, String levelCode);


    //获取该区划所对应的的key值
    @Select("select unique_key from dm_xzqh_ylsj where xzqh_dm = #{zoningCode} and yxbz ='Y' and xybz = 'Y'")
    @ResultType(String.class)
    List<String> findKeyByZoningCode(@Param("zoningCode") String zoningCode);

    @SelectProvider(type = PreviewDataInfoSql.class,method = "findPreviewDataInfoByZoningCode")
    @ResultMap("com.digital.entity.allOfPreviewDataInfo")
    PreviewDataInfo findPreviewDataInfoByZoningCode(String zoningCode);

   /* @Select("select xzqh_dm from dm_xzqh_ylsj")
    @ResultType(String.class)
    List<String> findAll();*/



    @Insert("insert into dm_xzqh_ylsj values(0,#{uniqueKey},#{zoningCode}," +
            "#{divisionName},#{divisionAbbreviation}," +
            "#{divisionFullName},#{assigningCode},#{levelCode},#{chooseSign}," +
            "#{usefulSign},#{subordinateRelations},#{superiorZoningCode}," +
            "#{validityStart},#{validityStup}," +
            "#{virtualNode}," +
            "#{oldZoningCode},#{accessCode},#{enterOneCode},#{createDate},#{updaterCode},#{lastUpdate}," +
            "#{type})")
    @ResultType(Integer.class)
    int insertylsj(FormalTableInfo formalTableInfo);

    /*========================================================================*/
    /**
     * @description 根据区划代码获取预览数据信息
     * @method  findPreviewDataByzoningCode
     * @params String zoningCode : 区划代码
     * @return List<PreviewDataInfo></>
     * @exception
     */
    @SelectProvider(type = PreviewDataInfoSql.class,method = "seek")
    @ResultMap("com.digital.entity.allOfPreviewDataInfo")
    List<PreviewDataInfo> findPreviewDataByUseful(QueryReq queryReq);

}
