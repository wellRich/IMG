package com.digital.service;

import com.digital.api.ImportFormalTableApi;
import com.digital.api.ZoningCodeChangeApi;
import com.digital.dao.ZCCDetailMapper;
import com.digital.dao.ZoningDataUploadMapper;
import com.digital.entity.ChangeInfo;
import com.digital.entity.ZCCDetail;
import com.digital.entity.ZCCRequest;
import com.digital.entity.province.ContrastTemporary;
import com.digital.entity.province.FocusChangeFileInfo;
import com.digital.util.Common;
import com.digital.util.JSONHelper;
import com.digital.util.ListUtil;
import com.digital.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description: TODO
 * @Author: zhanghpj
 * @Version 1.0, 18:48 2018/3/28
 * @See
 * @Since
 * @Deprecated
 */
@Service
public class ImportFormalTableApiImpl implements ImportFormalTableApi {
    private static final Logger logger = LoggerFactory.getLogger(ImportFormalTableApiImpl.class);
    @Autowired
    private ZoningCodeChangeApi zoningCodeChangeApi;

    @Autowired
    private ZCCDetailMapper zccDetailMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ZoningDataUploadMapper zoningDataUploadMapper;


    //新增申请单
    @Override
    public boolean ImprotFormalT(FocusChangeFileInfo fileInfo, List<ContrastTemporary> temporaryList){
        //文件序号
        Integer fileSquence = fileInfo.getFileSquence();
        //录入人代码
        String enterOneCode = fileInfo.getEnterOneCode();
        //省区划代码
        String zoningCode = fileInfo.getZoningCode();
        //录入机构代码
        String organizationCode = fileInfo.getOrganizationCode();
        //存储申请单信息
        ZCCRequest zccRequest = new ZCCRequest();
        //存储组序号和组名称
        Map<BigInteger,String> groupMap = new HashMap<>();
        zccRequest.setName(zoningCode+"集中上报数据");
        zccRequest.setStatus(Common.XZQH_SQDZT_YQR);
        zccRequest.setCreatorCode(enterOneCode);
        zccRequest.setCreateDate( StringUtil.getTime());
        zccRequest.setCreatorDeptCode(organizationCode);
        zccRequest.setOwnZoningCode(zoningCode);
        //返回申请单的序号
       int seqNum = zoningCodeChangeApi.addZCCRequest(zccRequest);

       if (seqNum!=0){
           logger.info("创建申请单成功！！；主键序号："+seqNum);
           //按照组编号分组
           for (ContrastTemporary contrastTemporary : temporaryList) {
                if (!groupMap.containsKey(contrastTemporary.getGroupNum())){
                    groupMap.put(contrastTemporary.getGroupNum(),contrastTemporary.getGroupName());
                }
           }
           //存储组信息
           Map<String,Object> groupInfo = new HashMap<>();
           //创建组
           Set<Map.Entry<BigInteger, String>> entries = groupMap.entrySet();
           for (Map.Entry<BigInteger, String> entry : entries) {
                groupInfo.put("serialNumber",entry.getKey());
                groupInfo.put("name",entry.getValue());
                groupInfo.put("creatorCode",enterOneCode);
                groupInfo.put("requestSeq",seqNum);
                groupInfo.put("createDate",StringUtil.getTime());
                groupInfo.put("creatorDeptCode",organizationCode);
                int groupNum = zoningCodeChangeApi.addZCCGroup(groupInfo);
                //创建组成功
                if (groupNum!=0){
                    //获取改组的变更临时数据，导入到变更明细表中
                    List<ContrastTemporary> list ;
                    list = ListUtil.getList(temporaryList,entry.getKey());
                    ZCCDetail zccDetail = new ZCCDetail();
                    list.forEach(v->{
                        zccDetail.setGroupSeq(groupNum);
                        zccDetail.setChangeType(v.getTypeCode());
                        zccDetail.setOrderNum(v.getOrderNum());
                        zccDetail.setOriginZoningCode(v.getOriginalCode());
                        zccDetail.setOriginZoningName(v.getOriginalName());
                        zccDetail.setCurrentZoningCode(v.getNowCode());
                        zccDetail.setCurrentZoningName(v.getNowName());
                        zccDetail.setStatus(Common.XZQH_CLZT_DCL);
                        zccDetail.setCreatorCode(enterOneCode);
                        zccDetail.setCreatorDate(StringUtil.getTime());
                        zccDetail.setCreatorDeptCode(organizationCode);
                        zccDetail.setUpdaterCode(enterOneCode);
                        zccDetail.setLastUpdate(StringUtil.getTime());
                        zccDetail.setUpdaterDeptCode(organizationCode);
                        zccDetailMapper.insert(zccDetail);
                    });
                    //预览数据所需要的数据
                    ChangeInfo changeInfo = new ChangeInfo();
                    for (ContrastTemporary contrastTemporary : temporaryList) {
                        changeInfo.setOriginZoningCode(contrastTemporary.getOriginalCode());
                        changeInfo.setOriginZoningName(contrastTemporary.getOriginalName());
                        changeInfo.setRequestSeq(seqNum);
                        changeInfo.setGroupSeq(groupNum);
                        changeInfo.setChangeType(contrastTemporary.getTypeCode());
                        changeInfo.setTargetZoningCode(contrastTemporary.getNowCode());
                        changeInfo.setTargetZoningName(contrastTemporary.getNowName());
                        changeInfo.setCreatorCode(enterOneCode);
                        changeInfo.setOrganizationCode(organizationCode);
                        commonService.savePreviewData(changeInfo);
                    }

                }else {
                    //创建组失败

                    return false;
                }

           }


       }else {
           //创建申请单失败
           return false;

       }
       //将申请单序列保存到文件信息表
        zoningDataUploadMapper.updateFocusApplicationNum(fileSquence,seqNum,Common.XZQH_JZBGZT_SQDSQCG);
       return true;
    }

    public void deleteInformation(Integer applicationNum){


    }



}
