package com.digital.service;

import com.digital.api.ImportFormalTableApi;
import com.digital.api.ZoningCodeChangeApi;
import com.digital.dao.ZCCDetailMapper;
import com.digital.dao.ZCCGroupMapper;
import com.digital.dao.ZCCRequestMapper;
import com.digital.dao.ZoningDataUploadMapper;
import com.digital.entity.ChangeInfo;
import com.digital.entity.ZCCDetail;
import com.digital.entity.ZCCGroup;
import com.digital.entity.ZCCRequest;
import com.digital.entity.province.ContrastTemporary;
import com.digital.entity.province.FocusChangeFileInfo;
import com.digital.util.Common;
import com.digital.util.ListUtil;
import com.digital.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private ZCCGroupMapper zccGroupMapper;

    @Autowired
    private ZCCRequestMapper zccRequestMapper;




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
        Map<Long,String> groupMap = new HashMap<>();
        zccRequest.setName(zoningCode+"集中上报数据");
        zccRequest.setStatus(Common.XZQH_SQDZT_YQR);
        zccRequest.setCreatorCode(enterOneCode);
        zccRequest.setCreateDate( StringUtil.getTime());
        zccRequest.setCreatorDeptCode(organizationCode);
        zccRequest.setLevelCode(zoningCode);
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
           ZCCGroup zccGroup = new ZCCGroup();
           //创建组
           Set<Map.Entry<Long, String>> entries = groupMap.entrySet();
           for (Map.Entry<Long, String> entry : entries) {

                zccGroup.setSerialNumber(entry.getKey());
                zccGroup.setName(entry.getValue());
                zccGroup.setCreatorCode(enterOneCode);
                zccGroup.setRequestSeq(zccRequest.getSeq());
                zccGroup.setCreateDate(StringUtil.getTime());
                zccGroup.setCreatorDeptCode(organizationCode);
                int groupNum = zoningCodeChangeApi.addZCCGroup(zccGroup);
                //创建组成功
                if (groupNum!=0){
                    //获取改组的变更临时数据，导入到变更明细表中
                    List<ContrastTemporary> list ;
                    list = ListUtil.getList(temporaryList,entry.getKey());
                    ZCCDetail zccDetail = new ZCCDetail();
                    list.forEach(v->{
                        zccDetail.setGroupSeq(zccGroup.getSeq());
                        zccDetail.setChangeType(v.getTypeCode());
                        zccDetail.setOrderNum(v.getOrderNum());
                        zccDetail.setOriginalZoningCode(v.getOriginalCode());
                        zccDetail.setOriginalZoningName(v.getOriginalName());
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
                    try {
                        ChangeInfo changeInfo = new ChangeInfo();
                        for (ContrastTemporary contrastTemporary : list) {
                            changeInfo.setOriginalZoningCode(contrastTemporary.getOriginalCode());
                            changeInfo.setOriginalZoningName(contrastTemporary.getOriginalName());
                            changeInfo.setRequestSeq(zccRequest.getSeq());
                            changeInfo.setGroupSeq(zccGroup.getSeq());
                            changeInfo.setChangeType(contrastTemporary.getTypeCode());
                            changeInfo.setTargetZoningCode(contrastTemporary.getNowCode());
                            changeInfo.setTargetZoningName(contrastTemporary.getNowName());
                            changeInfo.setCreatorCode(enterOneCode);
                            changeInfo.setOrganizationCode(organizationCode);
                            commonService.savePreviewData(changeInfo);
                        }
                    }catch (RuntimeException e){
                        logger.error("导入预览数据失败！");
                        //删除该方法操作的信息
                        deleteRequest(zccRequest.getSeq());
                    }


                }else {
                    //创建组失败
                    logger.error("创建组失败"+fileInfo.getFileName());
                    //删除该方法操作的信息
                    deleteRequest(zccRequest.getSeq());
                    return false;
                }
           }


       }else {
           //创建申请单失败
           logger.error("创建申请单失败"+fileInfo.getFileName());
           zoningDataUploadMapper.updateFocusApplicationNum(fileSquence,seqNum,Common.XZQH_JZBGZT_SQDSQSB);
           return false;

       }
       //将申请单序列保存到文件信息表
        zoningDataUploadMapper.updateFocusApplicationNum(fileSquence,seqNum,Common.XZQH_JZBGZT_SQDSQCG);
       return true;
    }

    /**
     *  删除未提交或未通过审核的申请单
     *  需要删除相应的变更组与变更明细表记录
     * @param seq 申请单序号
     */
    public void deleteRequest(Integer seq){
        ZCCRequest target = zccRequestMapper.get(seq);
        String status = target.getStatus();
        if(status.equals(Common.XZQH_SQDZT_WTJ) || status.equals(Common.XZQH_SQDZT_SHBTG)){
            logger.info("删除区划变更申请单--> " + target);

            List<ZCCGroup> zccGroups = zccGroupMapper.findByRequestSeq(seq);
            zccGroups.forEach(group ->{

                //1、删除变更对照明细
                zccDetailMapper.deleteByGroupSeq(group.getSeq());

                //2、删除变更对照组
                zccGroupMapper.delete(group);
            });

            //3、删除变更申请单
            zccRequestMapper.delete(seq);
        }else {
            throw new RuntimeException("只能删除状态为“未提交”或者“审核不通过”的申请单！");
        }
    }



}
