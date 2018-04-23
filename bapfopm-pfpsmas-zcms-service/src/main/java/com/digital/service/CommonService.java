package com.digital.service;

import com.digital.dao.LogicCheckMapper;
import com.digital.dao.PreviewDataInfoMapper;
import com.digital.entity.ChangeInfo;
import com.digital.entity.PreviewDataInfo;
import com.digital.util.Common;
import com.digital.util.JSONHelper;
import com.digital.util.StringUtil;
import com.digital.util.UniqueKeyUtil;
import com.digital.util.search.QueryFilter;
import com.digital.util.search.QueryReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author guoyka
 * @version 2018/3/26
 */
@Service
@Transactional
public class CommonService {
    private static final Logger log = LoggerFactory.getLogger(CommonService.class);

    @Autowired
    LogicCheckMapper logicCheckMapper;

    @Autowired
    PreviewDataInfoMapper previewDataInfoMapper;

    //区划代码变更的逻辑校验
    public void logicVerifyZCChange(ChangeInfo info){
        System.out.println("logic------>  " + info);
        String changeType = info.getChangeType();

        //现区划代码
        String currentZoningCode = info.getTargetZoningCode();

        //现区划名称
        String currentZoningName = info.getTargetZoningName();

        //原区划代码
        String originalZoningCode = info.getOriginalZoningCode();

        //原区划名称
        String originalZoningName = info.getOriginalZoningName();

        if(changeType.equals(Common.ADD)){
            String superZoningCode = Common.getSuperiorZoningCode(currentZoningCode);
            if(isExitsZoningCode(superZoningCode)){
                if(isExitsZoningCode(currentZoningCode)){
                    throw new RuntimeException("新增的现区划代码[" + currentZoningCode + "]已存在！");
                }else {
                    if(isUsedZoningName(currentZoningCode, currentZoningName)){
                        throw new RuntimeException("新增的现区划名称[" + currentZoningName + "]已存在！");
                    }
                }
            }else{
                throw new RuntimeException("新增的区划代码[" + currentZoningCode + "]的上级区划代码["
                        + superZoningCode + "]不存在！");
            }
        }else if(changeType.equals(Common.CHANGE)){
            if(isExitsZoningCode(originalZoningCode)){

                //判断是否是名称变更,名称变更：仅仅是改变区划的名称，区划代码不变
                if(Common.hasSameZoningCode(originalZoningCode, currentZoningCode)){
                    if(currentZoningName.equals(originalZoningName)){
                        throw new RuntimeException("原区划代码、原区划名称与现区划代码、现区划名称相同，不存在变更！");
                    }else {
                        if(isUsedZoningName(currentZoningCode, currentZoningName)){
                            throw new RuntimeException("变更的现区划名称[" + currentZoningName + "]已存在！");
                        }
                    }
                }else {
                    if(isExitsZoningCode(currentZoningCode)){
                        throw new RuntimeException("变更的现区划代码[" + currentZoningCode + "]已存在！");
                    }else {
                        if(!Common.getAssigningCode(originalZoningCode).equals(info.getAssigningCode())){
                            throw new RuntimeException("变更的原区划代码[" + originalZoningCode + "] 和现区划代码[" + currentZoningCode + "]不是一个级次！");
                        }else {
                            if(isUsedZoningName(currentZoningCode, currentZoningName)){
                                throw new RuntimeException("变更的现区划名称[" + currentZoningName + "]已存在！");
                            }
                        }
                    }
                }

            }else {
                throw new RuntimeException("变更的原区划代码[" + originalZoningCode + "]不存在！");
            }
        }else if(changeType.equals(Common.MERGE)){
            if(isExitsZoningCode(originalZoningCode)){
                if(isExitsZoningCode(currentZoningCode)){
                    if(currentZoningCode.equals(originalZoningCode)){
                        throw new RuntimeException("并入的原区划代码[" + originalZoningCode + "]与现区划代码[" + currentZoningCode + "]相同！");
                    }else {
                        if(Common.getAssigningCode(originalZoningCode).equals(info.getAssigningCode())){

                            //是否有下级区划
                            if(isExitsSubZoning(originalZoningCode)){
                                throw new RuntimeException("并入的原区划代码[" + originalZoningCode
                                        + "]有下级区划不能并入，请先将下级区划进行迁移！");
                            }
                        }else {
                            throw new RuntimeException("合并的原区划代码[" + originalZoningCode + "]和现区划代码[" + currentZoningCode + "]不是一个级次！");
                        }
                    }
                }else {
                    throw new RuntimeException("合并的现区划代码[" + originalZoningCode + "]不存在！");
                }
            }else {
                throw new RuntimeException("合并的原区划代码[" + originalZoningCode + "]不存在！");
            }

        }else if(changeType.equals(Common.MOVE)){
            if(isExitsZoningCode(originalZoningCode)){
                if(isExitsZoningCode(currentZoningCode)){
                    throw new RuntimeException("迁移的现区划代码[" + currentZoningCode + "]已经存在！");
                }else {
                    if(Common.getAssigningCode(originalZoningCode).equals(info.getAssigningCode())){
                        if(isUsedZoningName(currentZoningCode, currentZoningName)){
                            throw new RuntimeException("迁移的现区划名称[" + currentZoningName + "]已存在！");
                        }
                    }else {
                        throw new RuntimeException("迁移的原区划代码[" + originalZoningCode + "]和现区划代码[" + currentZoningCode + "]不是一个级次！");
                    }
                }
            }else {
                throw new RuntimeException("迁移的原区划代码[" + originalZoningCode + "]不存在！");
            }
        }else {
            throw new RuntimeException("区划变更类型代码[" + changeType
                    + "]不是系统规定的[11 新增,21 变更,31 并入,41 迁移] 变更类型，无法完成变更！");
        }
    }


    /* guoyuka
     * 以下的几个方法，使用频率很高，
     * 查询的是省级的数据，
     * 也许可以缩小至市级、县级，是需要优化的地方
     */

    /**
     * 判断某区划下区划名称是否已经存在
     * @param zoningCode 区划代码
     * @param zoningName 区划名称
     * @return boolean
     */
    public boolean isUsedZoningName(String zoningCode, String zoningName){
        return previewDataInfoMapper.findBrothersByCodeAndName(zoningCode, zoningName) > 0;
    }

    /**
     * 判断区划代码是否存在
     * @param zoningCode 区划代码
     * @return
     */
    public boolean isExitsZoningCode(String zoningCode){
        return logicCheckMapper.logicCheck(zoningCode, null, null) > 0;
    }


    /**
     * 判断区划代码是否存在下级区划
     * @param zoningCode 区划代码
     * @return
     */
    public boolean isExitsSubZoning(String zoningCode){
        return logicCheckMapper.logicCheck(null, null, zoningCode) > 0;
    }

    /**
     * 保存变更信息至预览表
     * @param info 变更信息
     */
    public void savePreviewData(ChangeInfo info){
        String changeType = info.getChangeType();
        //目标行政区划
        String currentZoningCode = info.getTargetZoningCode();
        //原始行政区划
        String originalZoningCode = info.getOriginalZoningCode();
        if(changeType.equals(Common.ADD)){

            //目标父级区划代码
            String currentSuperCode = Common.getSuperiorZoningCode(currentZoningCode);

            PreviewDataInfo currentSuperInfo = previewDataInfoMapper.findValidOneByZoningCode(currentSuperCode);
            log.info("savePreviewData.previewDataInfo-----> " + JSONHelper.toJSON(currentSuperInfo, PreviewDataInfo.class));
            this.addPreviewData(info, currentSuperInfo.getSubordinateRelations(), currentSuperInfo.getAssigningCode(), currentSuperInfo.getDivisionFullName(), new Date());

        }else if(changeType.equals(Common.MERGE)/*合并*/) {
            //获取合并方的数据信息
            PreviewDataInfo previewDataInfo = previewDataInfoMapper.findPreviewDataInfoByZoningCode(currentZoningCode);
            //获取被合并方代码所对应的所有的key值
            List<String> uniqueKey = UniqueKeyUtil.getKey(originalZoningCode,Common.MERGE);
            if (uniqueKey==null | uniqueKey.isEmpty()){
                throw new RuntimeException("获取key失败！！！！请检查");
            }
            for (String key : uniqueKey) {
                //逻辑删除
                int result = previewDataInfoMapper.saveMergeData(key, new Date());
                if (result==0){
                    log.error("逻辑删除失败！请检查;》》》》对应的key为:"+key);
                    throw new RuntimeException("逻辑删除失败");
                }
                previewDataInfo.setUniqueKey(key);
                //新增数据
                previewDataInfoMapper.insert(previewDataInfo);
            }
        }else if(changeType.equals(Common.CHANGE)/*变更*/ || changeType.equals(Common.MOVE)/*迁移*/) {

            //获取自身及子孙区划
            List<ChangeInfo> changeInfoList = accountChangeOrders(info);
            int size = changeInfoList.size();
            if(size > 0){

                /*
                 * 获取变动的区划名称
                 * 原区划全称 = 原上级区划全称 + 原区划名称
                 * 现区划全称 = 现上级区划全称 + 现区划名称
                 * 原子孙级区划全称 = 原上级区划全称 + 原区划名称
                 * 现子孙级区划全称 = 现上级区划全称 + 原区划名称
                 */
                //需要修改 可能查询到多条数据
                PreviewDataInfo originalPreview = previewDataInfoMapper.findValidOneByZoningCode(info.getOriginalZoningCode());

                //原区划全称
                String originalFullName = originalPreview.getDivisionFullName();

                //目标父级区划代码
                String currentSuperCode = Common.getSuperiorZoningCode(currentZoningCode);
                PreviewDataInfo currentSuperInfo = previewDataInfoMapper.findValidOneByZoningCode(currentSuperCode);

                //现区划全称
                String currentFullName = currentSuperInfo.getDivisionFullName() + info.getTargetZoningName();

                //原区划级别代码
                String originalLevelCode = info.getOriginalLevelCode();

                //目标区划级别代码
                String targetLevelCode = info.getTargetLevelCode();

                log.info("originalFullName--------> " + originalFullName + ", currentFullName-------> " + currentFullName);
                for (int i = 0; i < size; i++) {
                    //本级和下级的区划代码
                    updatePreviewData(changeInfoList.get(i), originalLevelCode, targetLevelCode, originalFullName, currentFullName);
                }
            }

        }
    }




    /**
     * 更新区划数据
     * 使用场景：区划变更类型为change或者move
     * @param info 变更信息
     * @param originalFullName 原始行政区划上级名称
     * @param currentFullName 目标行政区划上级名称
     */
    public void updatePreviewData(ChangeInfo info, String originalLevelCode, String targetLevelCode, String originalFullName, String currentFullName) {
        //查询到的数据 本区划和下级区划
        String uniqueKey = info.getUniqueKey();
        //原区划代码
        String originZoningCode = info.getOriginalZoningCode();
        String originalZoningName = info.getOriginalZoningName();
        //现区划代码
        String targetZoningCode = info.getTargetZoningCode();
        String targetZoningName = info.getTargetZoningName();
        String newDate = StringUtil.formatDateTime(new Date());
        //查询预览数据中的明细
       // PreviewDataInfo previewDataInfo = previewDataInfoMapper.findValidOneByZoningCode(originZoningCode);
        QueryReq queryReq = new QueryReq();
        queryReq.addFilter(new QueryFilter("uniqueKey",uniqueKey)).addFilter(new QueryFilter("chooseSign","Y")).addFilter(new QueryFilter("usefulSign","Y"));
        List<PreviewDataInfo> previewDataInfoList = previewDataInfoMapper.findPreviewDataByUseful(queryReq);
        PreviewDataInfo previewDataInfo = previewDataInfoList.get(0);
        //变更后的区划全称
       // String fullName = previewDataInfo.getDivisionFullName().replace(originalFullName, currentFullName);
        String fullName = previewDataInfo.getDivisionFullName().replace(originalFullName, currentFullName);

        //仅仅是名称变更
        if (Common.hasSameZoningCode(originZoningCode, targetZoningCode)) {
            previewDataInfo.setDivisionName(targetZoningName);
            previewDataInfo.setDivisionAbbreviation(targetZoningName);
            previewDataInfo.setLastUpdate(newDate);
            previewDataInfo.setUpdaterCode(info.getCreatorCode());
            previewDataInfo.setDivisionFullName(fullName);
            previewDataInfoMapper.save(previewDataInfo);
        } else {

            //修改原来的数据
            previewDataInfo.setValidityStup(newDate);
            previewDataInfo.setChooseSign("N");
            previewDataInfo.setUsefulSign("N");
            previewDataInfo.setLastUpdate(newDate);
            previewDataInfo.setUpdaterCode(info.getCreatorCode());
            previewDataInfoMapper.save(previewDataInfo);

            //添加新的数据
            PreviewDataInfo pf = new PreviewDataInfo();
            //取得最新的级别代码
            String oldLevelCode = previewDataInfo.getLevelCode();
            String newLevelCode = targetZoningCode.substring(0, oldLevelCode.length());

            //变更后的区划代码
            String targetCode = targetZoningCode.replace(originalLevelCode, targetLevelCode);
            //区划代码对应的key
            pf.setUniqueKey(uniqueKey);
            //区划代码
            pf.setZoningCode(targetCode);

            //区划名称
            pf.setDivisionName(targetZoningName);

            //区划简称
            pf.setDivisionAbbreviation(targetZoningName);

            //区划全称
            pf.setDivisionFullName(fullName.replace(originalZoningName, targetZoningName));

            //级别代码
            pf.setLevelCode(newLevelCode);

            //级次代码
            pf.setAssigningCode(previewDataInfo.getAssigningCode());

            //上级区划代码
            pf.setSuperiorZoningCode(previewDataInfo.getSuperiorZoningCode());

            //选用标志
            pf.setChooseSign("Y");

            //有效标志
            pf.setUsefulSign("Y");

            //旧的区划代码
            pf.setOldZoningCode(originZoningCode);

            //生效时间
            pf.setValidityStart(newDate);

            //权限机构代码
            pf.setAccessCode(info.getOrganizationCode());

            //录入人代码
            pf.setEnterOneCode(info.getCreatorCode());

            //创建时间
            pf.setCreateDate(newDate);

            //修改人代码
            pf.setUpdaterCode(info.getCreatorCode());

            //修改机构
            pf.setAccessCode(info.getOrganizationCode());

            //修改时间
            pf.setLastUpdate(newDate);

            //单位隶属关系
            pf.setSubordinateRelations(previewDataInfo.getSubordinateRelations());

            //变更类型
            pf.setType(previewDataInfo.getType());

            log.info("js-----------> " + JSONHelper.toJSON(pf));
            previewDataInfoMapper.insert(pf);
        }
    }

    /**
     * 新增区划预览数据
     * @param info 变更信息
     * @param level 级次代码
     * @param relation 单位隶属信息             
     * @param superiorFullName 上级区划全称
     * @param createDate 创建时间
     * @return
     */
    public Integer addPreviewData(ChangeInfo info, String relation, String level, String superiorFullName, Date createDate){
        String date = StringUtil.formatDateTime(createDate);
        String zoningCode = info.getTargetZoningCode();
        PreviewDataInfo previewDataInfo = new PreviewDataInfo();
        //获取唯一标识的key
        String uniqueKey = UniqueKeyUtil.getKey(info.getTargetZoningCode(),Common.ADD).get(0);
        if (StringUtil.isEmpty(uniqueKey)){
            throw new RuntimeException("获取key失败！！！！请检查");
        }
        previewDataInfo.setUniqueKey(uniqueKey);
        previewDataInfo.setZoningCode(zoningCode);
        //区划全称
        previewDataInfo.setDivisionFullName(superiorFullName.concat(info.getTargetZoningName()));

        //区划名称
        previewDataInfo.setDivisionName(info.getTargetZoningName());

        //区划简称
        previewDataInfo.setDivisionAbbreviation(info.getTargetZoningName());

        previewDataInfo.setAssigningCode(gainLevel(level));
        previewDataInfo.setLevelCode(Common.getLevelCode(zoningCode));
        previewDataInfo.setSuperiorZoningCode(Common.getSuperiorZoningCode(zoningCode));
        previewDataInfo.setChooseSign("Y");
        previewDataInfo.setUsefulSign("Y");
        previewDataInfo.setValidityStart(date);
        previewDataInfo.setAccessCode(info.getOrganizationCode());
        previewDataInfo.setEnterOneCode(info.getCreatorCode());
        previewDataInfo.setCreateDate(date);
        previewDataInfo.setUpdaterCode(info.getCreatorCode());
        previewDataInfo.setLastUpdate(date);
        previewDataInfo.setSubordinateRelations(gainUnitRelationShip(relation));
        return previewDataInfoMapper.insert(previewDataInfo);
    }

    //获取单位隶属关系
    private String gainUnitRelationShip(String relation){
        if (relation.equals("20")) {
            return "40";
        }
        if (relation.equals("40")) {
            return "50";
        }
        if (relation.equals("50")) {
            return "60";
        }
        if (relation.equals("60")) {
            return "70";
        }
        if (relation.equals("70")) {
            return "80";
        }
        return "";
    }

    private String gainLevel(String level) {
        String result;
        switch (level) {
            case "0":
                result = "1";
                break;
            case "1":
                result = "2";
                break;
            case "2":
                result = "3";
                break;
            case "3":
                result = "4";
                break;
            case "4":
                result = "5";
                break;
            case "5":
                result = "6";
                break;
            default:
                throw new RuntimeException(level + "是不合规的级次代码！" );
        }
        return result;
    }

    /**
     * 查找子级（包括自身）区划
     * @param level 区划级别代码
     * @return List
     */
    public List<PreviewDataInfo> findFamilyZoning(String level){
        return previewDataInfoMapper.findFamilyZoning(level, "*");
    }

    /**
     * 汇集变更信息
     * 取得自身与子孙区划
     * @param info 变更信息
     * @return
     */
    public List<ChangeInfo> accountChangeOrders(ChangeInfo info){
        List<ChangeInfo> result = new ArrayList<>();
        String changeType = info.getChangeType();

        if(changeType.equals(Common.ADD) || changeType.equals(Common.MERGE)){
            result.add(info);
        }

        // 变更与迁移
        else {
            //原区划代码
            String originalZoningCode = info.getOriginalZoningCode();

            //原区划级别代码
            String originalLevelCode = Common.getLevelCode(originalZoningCode);

            //现区化级别代码
            String currentLevelCode = info.getTargetLevelCode();

            List<PreviewDataInfo> previewDataInfos = findFamilyZoning(originalLevelCode);
            log.info("accountChangeOrders.previewDataInfos.size----> " + previewDataInfos.size());
            for (int i = 0; i < previewDataInfos.size(); i++) {
                ChangeInfo changeInfo = new ChangeInfo();
                PreviewDataInfo previewDataInfo =  previewDataInfos.get(i);
                System.out.println("accountChangeOrders.previewDataInfo----> " + JSONHelper.toJSON(previewDataInfo));

                //获取区划代码
                String zoningCode = previewDataInfo.getZoningCode();
                //获取key
                changeInfo.setUniqueKey(previewDataInfo.getUniqueKey());
                //目标区划代码
                if(i == 0){
                    //第一个区划代码是本区划代码的目标区划名称
                    changeInfo.setTargetZoningName(info.getTargetZoningName());
                }else {
                    //之后的代码是查询到的区划名称
                    changeInfo.setTargetZoningName(previewDataInfo.getDivisionName());
                }

                //变更组序号
                changeInfo.setGroupSeq(info.getGroupSeq());

                //变更类型
                changeInfo.setChangeType(info.getChangeType());

                //申请单序号
                changeInfo.setRequestSeq(info.getRequestSeq());

                //目标区划代码
                changeInfo.setTargetZoningCode(zoningCode.replaceFirst(originalLevelCode, currentLevelCode));

                //权限机构代码
                changeInfo.setOrganizationCode(info.getOrganizationCode());

                //原区划名称
                changeInfo.setOriginalZoningName(previewDataInfo.getDivisionName());

                //原区划代码
                changeInfo.setOriginalZoningCode(previewDataInfo.getZoningCode());

                //级次代码
                changeInfo.setAssigningCode(previewDataInfo.getAssigningCode());
                if(changeInfo.getAssigningCode() == null || changeInfo.getAssigningCode() == ""){
                    changeInfo.setAssigningCode(Common.getAssigningCode(previewDataInfo.getZoningCode()));
                }

                //原级别代码
                changeInfo.setOriginalLevelCode(previewDataInfo.getLevelCode());

                //目标级别代码
                changeInfo.setTargetLevelCode(currentLevelCode);

                //录入人代码
                changeInfo.setCreatorCode(info.getCreatorCode());

                //录入时间
                changeInfo.setCreatorDate(new Date());

                //变更说明
                changeInfo.setNotes(info.getNotes());

                //是否环状变更
                changeInfo.setRingFlag(info.getRingFlag());


                System.out.println("accountChangeOrders.changeInfo.jcdm----> " + changeInfo.getAssigningCode());

                System.out.println("accountChangeOrders.changeInfo----> " + JSONHelper.toJSON(changeInfo));
                result.add(changeInfo);
            }
        }
        return result;
    }







}
