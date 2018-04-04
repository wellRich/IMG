package com.digital.service;

import com.digital.api.LogicCheckApi;
import com.digital.dao.LogicCheckMapper;
import com.digital.dao.TemporaryDataMapper;
import com.digital.dao.ZoningDataUploadMapper;
import com.digital.entity.PreviewDataInfo;
import com.digital.entity.province.ContrastTemporary;
import com.digital.entity.province.FocusChangeFileInfo;
import com.digital.util.Common;
import com.digital.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: zhanghpj
 * @Version 1.0, 18:30 2018/3/12
 * @See
 * @Since
 * @Deprecated
 */
@Service
@Transactional
public class LogicCheckApiImpl implements LogicCheckApi {



    @Autowired
    private LogicCheckMapper logicCheckMapper;
    
    @Autowired
    private TemporaryDataMapper temporaryDataMapper;

    @Autowired
    private ZoningDataUploadMapper zoningDataUploadMapper;


    private List<PreviewDataInfo> dataInfoList = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(LogicCheckApiImpl.class);



    /**
     * @description 校验
     * @method  ContentCheck
     * @params [fileSquence：文件序号]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @exception
     */
    @Override
    public Map<String,Object> ContentCheck(FocusChangeFileInfo fileInfo) {
        //错误数据标识
        boolean checked = true;
        Map<String,Object> checkedMap = new HashMap<>();

        String fileName = fileInfo.getFileName();
        //以fileSquence为条件获取所有变更数据
        List<ContrastTemporary> temporaryList = temporaryDataMapper.queryChangeData(fileInfo.getFileSquence(),null,null);
        int index = 1;
        for (ContrastTemporary contrastTemporary : temporaryList) {
            //内容校验
            String contentMsg = this.checkedContent2(contrastTemporary,fileName);
            //不通过，更改该条数据的错误“标识状态”为'N',添加错误信息，跳过逻辑校验
            if (!contentMsg.equals("")){
                checked = false;
                temporaryDataMapper.updateChangeData(contrastTemporary.getTableNum(),"Y",contentMsg);
                continue;
            }
            String logicMsg = this.logicCheck(contrastTemporary);
            if (!logicMsg.equals("")){
                checked =false;
                temporaryDataMapper.updateChangeData(contrastTemporary.getTableNum(),"Y",logicMsg);
            }
        }
        if (checked){
            checkedMap.put("check",checked);
            checkedMap.put("temporaryList",temporaryList);
        }else {
            checkedMap.put("check",checked);
        }
        return checkedMap;
    }


    public void importFormalTable(List<ContrastTemporary> temporaryList, FocusChangeFileInfo fileInfo){
       //新增申请单

    }

    /**
     * @description 对内容进行校验
     * @method  checkedContent
     * @params [fileName：文件名, filePath：文件路径]
     * @return boolean
     * @exception
     */
    public String checkedContent2(ContrastTemporary contrastTemporary, String fileName){

        //省级区划代码
        int index = fileName.lastIndexOf("_");
        String provinceCode = fileName.substring(index - 6, index-4);
        //组序号
        Long groupNum = contrastTemporary.getGroupNum();
        //原区划名称
        String originalName = contrastTemporary.getOriginalName();
        //原区划代码
        String originalCode = contrastTemporary.getOriginalCode();
        //变更类型
        String typeCode = contrastTemporary.getTypeCode();
        //现区划名称
        String nowName = contrastTemporary.getNowName();
        //现区划代码
        String nowCode = contrastTemporary.getNowCode();
        //错误信息
        StringBuilder errorMsg = new StringBuilder();

        if (groupNum.equals("")) {
            errorMsg.append("调整编号为空;");
        }
        /*变更类型是否为空*/
        if (typeCode.equals("")) {
            errorMsg.append("变更类型为空;");
        } else if (StringUtil.isEmpty(originalCode)|| StringUtil.isEmpty(originalName)) {
            if (!Common.ADD.equals(typeCode)) {
                errorMsg.append("原区划代码或原区划名称为空;");
            }
        } else if (!StringUtil.isEmpty(originalCode) && originalCode.length() != 12 && originalCode.length() != 15) {
            errorMsg.append("原区划代码长度不是12与15；");
        }else if (StringUtil.isEmpty(originalCode)&& !originalCode.startsWith(provinceCode)){
            errorMsg.append("原区划代码和ZIP文件区划代码不一致；");
        }
        if (StringUtil.isEmpty(nowCode)) {
            errorMsg.append("现区划代码为空！;");
        } else if (nowCode.length()!= 12 && nowCode.length()!=15){
            errorMsg.append("原区划代码长度不是12与15;");
        }else if (!nowCode.startsWith(provinceCode)){
            errorMsg.append("现区划代码和ZIP文件区划代码不一致；");
        }
        if (StringUtil.isEmpty(nowName)){
            errorMsg.append("现区划名称为空；");
        }
        if (!"".equals(errorMsg.toString())){
            return errorMsg.toString();
        }
        return "";
    }

    /*
     * 逻辑校验开始
     * */
    public  String logicCheck(ContrastTemporary contrastTemporary) {

        /*变更状态*/
        String typeCode = contrastTemporary.getTypeCode();
        /*原区划代码*/
        String originalCode = contrastTemporary.getOriginalCode();
        /*现区划代码*/
        String nowCode = contrastTemporary.getNowCode();
        /*原区划名称*/
        String originalName = contrastTemporary.getOriginalName();
        /*现区划名称*/
        String nowName = contrastTemporary.getNowName();
        /*错误信息*/
        StringBuilder errorMsg = new StringBuilder();

        if (Common.ADD.equals(typeCode)) {
            //获取该区划的上级代码
            String superiorZoningCode = Common.getSuperiorZoningCode(nowCode);
            if (!this.isZoningCodeExist(superiorZoningCode)) {
                logger.error("新增的区划代码[" + nowCode + "]的上级区划代码[" + superiorZoningCode + "]不存在！");
                errorMsg.append("新增的区划代码[").append(nowCode).append("]的上级区划代码[").append(superiorZoningCode).append("]不存在！;");
            } else if (this.isZoningCodeExist(nowCode)) {
                logger.error("新增的区划代码[" + nowCode + "]已存在！");
                errorMsg.append("新增的区划代码[").append(nowCode).append("]已存在！");
            }
        } else if (Common.CHANGE.equals(typeCode)) {

            if (!this.isZoningCodeExist(originalCode)) {
                logger.error("变更的原区划代码[" + originalCode + "]不存在！");
                errorMsg.append("变更的原区划代码[").append(originalCode).append("]不存在！");
            } else if (this.isZoningCodeExist(nowCode)) {
                //现区划代码存在；判断是否与原区划相同，来分别是否是名称变更
                if (!originalCode.equals(nowCode)) {
                    logger.error("变更的现区划代码[" + nowCode + "]已存在！");
                    errorMsg.append("变更的现区划代码[").append(nowCode).append("]已存在！");
                } else if (originalCode.equals(nowCode) && originalName.equals(nowName)) {
                    logger.error("变更的原区划代码、名称和现区划代码、名称完全相同！");
                    errorMsg.append("变更的原区划代码、名称和现区划代码、名称完全相同！");
                }
            }else if (!Common.getAssigningCode(originalCode).equals(Common.getAssigningCode(nowCode))) {
                logger.error("变更的原区划代码和现区划代码不是一个级别！");
                errorMsg.append("变更的原区划代码和现区划代码不是一个级别！");
            }
        } else if (Common.MERGE.equals(typeCode)) {
            if (!this.isZoningCodeExist(originalCode)) {
                logger.error("并入的原区划代码[" + originalCode + "]不存在！");
                errorMsg.append("并入的原区划代码[").append(originalCode).append("]不存在！");
            } else if (!this.isZoningCodeExist(nowCode)) {
                logger.error("并入的现行政区划代码[" + nowCode + "]不存在！");
                errorMsg.append("并入的现行政区划代码[").append(nowCode).append("]不存在！");
            } else if (!Common.getAssigningCode(originalCode).equals(Common.getAssigningCode(nowCode))) {
                logger.error("并入的原区划代码[" + originalCode + "]和现区划代码[" + nowCode + "]不是同级区划！");
                errorMsg.append("并入的原区划代码[").append(originalCode).append("]和现区划代码[").append(nowCode).append("]不是同级区划！");
            } else if (false/*判断是否有下级   下级是否全部迁移*/) {

            }

        } else if (Common.MOVE.equals(typeCode)) {
            if (!this.isZoningCodeExist(originalCode)) {
                logger.error("迁移的原区划代码[" + originalCode + "]不存在！");
                errorMsg.append("迁移的原区划代码[").append(originalCode).append("]不存在！");
            } else if (this.isZoningCodeExist(nowCode)){
                logger.error("迁移的现区划代码[" + nowCode + "]已存在！");
                errorMsg.append("迁移的现区划代码[").append(nowCode).append("]已存在！");
            } else if (!Common.getAssigningCode(originalCode).equals(Common.getAssigningCode(nowCode))){
                logger.error("迁移的原区划代码[" + originalCode + "]和现区划代码[" + nowCode + "]不是同级区划！");
                errorMsg.append("迁移的原区划代码[").append(originalCode).append("]和现区划代码[").append(nowCode).append("]不是同级区划！");
            } else if (Common.getSuperiorZoningCode(originalCode).equals(Common.getSuperiorZoningCode(nowCode))){
                logger.error("迁移的原区划代码[" + originalCode + "]和现区划代码[" + nowCode + "]的上级区划相同，必须跨级迁移！");
                errorMsg.append("迁移的原区划代码[").append(originalCode).append("]和现区划代码[").append(nowCode).append("]的上级区划相同，必须跨级迁移！");
            }else if (false/*代码是否被重用*/){

            }
        }else {
            logger.error("区划变更类型代码不是系统规定的[11 新增,21 变更,31 并入,41 迁移] 变更类型，无法完成变更！");
            errorMsg.append("变更类型错误");
        }
        if (!"".equals(errorMsg.toString())){
            return errorMsg.toString();
        }
        return "";
    }


    /**
     * @description 判断变更区划代码是否存在
     * @method  isLevelTransfer
     * @params [fileNum：文件序号, superiorZoningCode：区划代码]
     * @return boolean 存在：true   不存在：false
     * @exception
     */
    private boolean isZoningCodeExist(String zoningCode){
        int index = 0;
         index = logicCheckMapper.logicCheck(zoningCode, null, null);
        return index>0;
    }


    /**
     * @description 判断变更数据是否有下级没有迁移
     * @method  isLevelTransfer
     * @params [fileNum：文件序号, superiorZoningCode：区划代码]
     * @return boolean 以迁移：true   未迁移：false
     * @exception
     */
    private boolean isLevelTransfer (Integer fileNum,String superiorZoningCode){
        int count = 0;
         count = logicCheckMapper.logicCheck(null, null, superiorZoningCode);
        if (count==0) {
            return true;
        }
        String superiorCode = superiorZoningCode.substring(0,superiorZoningCode.indexOf("0"));
        int results =  logicCheckMapper.queryLevelExist(fileNum,superiorCode);
        return results == 0;

    }

}



