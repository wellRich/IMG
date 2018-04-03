package com.digital.service;

import com.digital.dao.LogicCheckMapper;
import com.digital.dao.PreviewDataInfoMapper;
import com.digital.entity.ChangeInfo;
import com.digital.entity.PreviewDataInfo;
import com.digital.util.Common;
import com.digital.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class CommonService {
    @Autowired
    LogicCheckMapper logicCheckMapper;

    @Autowired
    PreviewDataInfoMapper previewDataInfoMapper;

    //区划代码变更的逻辑校验
    public void logicVerifyZCChange(ChangeInfo info){
        String changeType = info.getChangeType();

        //现区划代码
        String currentZoningCode = info.getTargetZoningCode();
        String currentZoningName = info.getTargetZoningName();

        //原区划代码
        String originalZoningCode = info.getOriginZoningCode();
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
                            if(isExitsSublevel(originalZoningCode)){
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
    public boolean isExitsSublevel(String zoningCode){
        return logicCheckMapper.logicCheck(null, null, zoningCode) > 0;
    }

    /**
     * 保存变更信息至预览表
     * @param info 变更信息
     */
    public void savePreviewData(ChangeInfo info){
        String changeType = info.getChangeType();
        String currentZoningCode = info.getTargetZoningCode();
        if(changeType.equals(Common.ADD)){
            String superCode = Common.getSuperiorZoningCode(currentZoningCode);
            PreviewDataInfo previewDataInfo = previewDataInfoMapper.findOneByZoningCode(superCode);
            if(previewDataInfo == null){
                throw new RuntimeException("上级区划代码：" + superCode + "不存在！");
            }else {
                addPreviewData(info, previewDataInfo.getSubordinateRelations(), previewDataInfo.getAssigningCode(), previewDataInfo.getDivisionFullName(), new Date());
            }
        }

        if(changeType.equals(Common.MERGE)) {
            previewDataInfoMapper.saveMergeData(currentZoningCode, new Date());
        }

        if(changeType.equals(Common.CHANGE) || changeType.equals(Common.MOVE)) {

            List<ChangeInfo> changeInfoList = accountChangeOrders(info);
            int size = changeInfoList.size();
            if(size > 0){
                ChangeInfo changeInfo = changeInfoList.get(size - 1);
                String sjDestXzqhdm = changeInfo.getTargetZoningCode();
                String sjDestXzqhmc = changeInfo.getTargetZoningName();
                String sjSrcXzqhdm = changeInfo.getOriginZoningCode();
                String sjSrcXzqhmc = changeInfo.getOriginZoningName();
                if(changeType.equals(Common.MOVE)){
                    //
                    sjDestXzqhmc = previewDataInfoMapper.findOneByZoningCode(Common.getSuperiorZoningCode(changeInfo.getTargetZoningCode())).getDivisionName();
                    //
                    sjSrcXzqhmc = previewDataInfoMapper.findOneByZoningCode(Common.getSuperiorZoningCode(changeInfo.getOriginZoningCode())).getDivisionName();
                }

                for (int m = 0; m < size; m++) {
                    updatePreviewData(changeInfoList.get(m), sjSrcXzqhmc, sjDestXzqhmc);
                }
            }

        }

    }

    /**
     * 更新区划数据
     * @param info 变更信息
     * @param superOriginName 原始行政区划上级名称
     * @param superTargetName 目标行政区划上级名称
     */
    public void updatePreviewData(ChangeInfo info, String superOriginName, String superTargetName){
        String originZoningCode = info.getOriginZoningCode();
        String targetZoningCode = info.getTargetZoningCode();
        String targetZoningName = info.getTargetZoningName();
        String newDate = StringUtil.formatDateTime(new Date());
        PreviewDataInfo previewDataInfo = previewDataInfoMapper.findValidOneByZoningCode(originZoningCode);
        if(previewDataInfo == null){
            throw new RuntimeException("原始行政区划代码：" + originZoningCode + "不存在！");
        }else {
            String fullName = previewDataInfo.getDivisionFullName();

            //仅仅是名称变更
            if(isJustChangeName(originZoningCode, targetZoningCode)){
                previewDataInfo.setDivisionName(targetZoningName);
                previewDataInfo.setDivisionAbbreviation(targetZoningName);
                previewDataInfo.setDivisionFullName(fullName.replace(superOriginName, superTargetName));
                previewDataInfoMapper.save(previewDataInfo);
            }else{

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
                String oldLevelCode = pf.getLevelCode();
                String newLevelCode = targetZoningCode.substring(0, oldLevelCode.length());

                //区划代码
                pf.setZoningCode(targetZoningCode);

                //区划名称
                pf.setDivisionName(targetZoningName);

                //区划简称
                pf.setDivisionAbbreviation(targetZoningName);

                //区划全称
                pf.setDivisionFullName(fullName.replace(superOriginName, superTargetName));

                //级别代码
                pf.setLevelCode(newLevelCode);

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
                pf.setCreatorDate(newDate);

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

                previewDataInfoMapper.insert(pf);
            }
        }
    }

    /**
     * 新增区划预览数据
     * @param info 变更信息
     * @param level 级次代码
     * @param relation 单位隶属信息             
     * @param fullName 区划全称
     * @param createDate 创建时间
     * @return
     */
    public String addPreviewData(ChangeInfo info, String relation, String level, String fullName, Date createDate){
        String date = StringUtil.formatDateTime(createDate);
        String zoningCode = info.getTargetZoningCode();
        PreviewDataInfo previewDataInfo = new PreviewDataInfo();
        previewDataInfo.setZoningCode(zoningCode);
        previewDataInfo.setDivisionName(info.getTargetZoningName());
        previewDataInfo.setDivisionAbbreviation(info.getTargetZoningName());
        previewDataInfo.setAssigningCode(gainLevel(level));
        previewDataInfo.setDivisionFullName(fullName);
        previewDataInfo.setLevelCode(Common.getLevelCode(zoningCode));
        previewDataInfo.setSuperiorZoningCode(Common.getSuperiorZoningCode(zoningCode));
        previewDataInfo.setChooseSign("Y");
        previewDataInfo.setUsefulSign("Y");
        previewDataInfo.setValidityStart(date);
        previewDataInfo.setAccessCode(info.getOrganizationCode());
        previewDataInfo.setEnterOneCode(info.getCreatorCode());
        previewDataInfo.setCreatorDate(date);
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
    public List<PreviewDataInfo> findChildrenZoning(String level){
        return previewDataInfoMapper.findChildrenZoning(level, "XZQH_DM,XZQH_MC,JCDM");
    }

    /**
     * 汇集变更信息
     * ，下级联动变更
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
            String originZoningCode = info.getOriginZoningCode();

            //原区划级别代码
            String  originLevelCode= Common.getLevelCode(originZoningCode);

            //现区化级别代码
            String currentLevelCode = info.getLevelCode();

            List<PreviewDataInfo> previewDataInfos = findChildrenZoning(originLevelCode);
            for (int i = 0; i < previewDataInfos.size(); i++) {
                ChangeInfo changeInfo = new ChangeInfo();
                PreviewDataInfo previewDataInfo =  previewDataInfos.get(i);
                //获取区划代码
                String zoningCode = previewDataInfo.getZoningCode();

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
                changeInfo.setTargetZoningCode(zoningCode.replaceFirst(originLevelCode, currentLevelCode));

                //权限机构代码
                changeInfo.setOrganizationCode(info.getOrganizationCode());

                //原区划名称
                changeInfo.setOriginZoningName(previewDataInfo.getDivisionName());

                //原区划代码
                changeInfo.setOrganizationCode(previewDataInfo.getZoningCode());

                //录入人代码
                changeInfo.setCreatorCode(info.getCreatorCode());

                //级次代码
                changeInfo.setAssigningCode(previewDataInfo.getAccessCode());
                result.add(changeInfo);
            }
        }
        return result;
    }




    /**
     * 检测是否为名称变更
     * @param originZoningCode 原区划代码
     * @param currentZoningCode 现区划代码
     * @return
     */
    public  boolean isJustChangeName(String originZoningCode, String currentZoningCode){
        boolean flag=false;
        if(!originZoningCode.equals("")&&!currentZoningCode.equals("")){
            if(originZoningCode.equals(currentZoningCode)){
                flag=true;
            }
        }
        return flag;
    }


}
