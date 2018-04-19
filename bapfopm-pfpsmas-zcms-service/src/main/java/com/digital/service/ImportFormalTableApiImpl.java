package com.digital.service;

import com.digital.api.ImportFormalTableApi;
import com.digital.api.ZoningCodeChangeApi;
import com.digital.dao.*;
import com.digital.entity.*;
import com.digital.entity.province.ContrastTemporary;
import com.digital.entity.province.FocusChangeFileInfo;
import com.digital.util.Common;
import com.digital.util.ListUtil;
import com.digital.util.StringUtil;
import com.digital.util.search.QueryFilter;
import com.digital.util.search.QueryReq;
import com.digital.util.search.QueryResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
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

    @Autowired
    private PreviewDataInfoMapper previewDataInfoMapper;
    @Autowired
    private HistoricalDataMapper historicalDataMapper;

    @Autowired
    private FormalTableMapper formalTableMapper;




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
        try {
            zoningCodeChangeApi.addZCCRequest(zccRequest);
        }catch (RuntimeException e){
            e.printStackTrace();
            logger.error("创建申请单失败"+fileInfo.getFileName()+";错误信息"+e.getMessage());
            zoningDataUploadMapper.updateFocusApplicationNum(fileSquence,null,Common.XZQH_JZBGZT_SQDSQSB);
            throw  e;

        }
       logger.info("创建申请单成功！！；主键序号："+zccRequest.getSeq());
       //按照组编号分组
       for (ContrastTemporary contrastTemporary : temporaryList) {
            if (!groupMap.containsKey(contrastTemporary.getGroupNum())){
                groupMap.put(contrastTemporary.getGroupNum(),contrastTemporary.getGroupName());
            }
       }

       //创建组
       Set<Map.Entry<Long, String>> entries = groupMap.entrySet();
       for (Map.Entry<Long, String> entry : entries) {
           //存储组信息
           ZCCGroup zccGroup = new ZCCGroup();
            zccGroup.setSerialNumber(entry.getKey());
            zccGroup.setName(entry.getValue());
            zccGroup.setCreatorCode(enterOneCode);
            zccGroup.setRequestSeq(zccRequest.getSeq());
            zccGroup.setCreateDate(StringUtil.getTime());
            zccGroup.setCreatorDeptCode(organizationCode);
            try {
                 zoningCodeChangeApi.addZCCGroup(zccGroup);
            }catch (RuntimeException e){
                e.printStackTrace();
                //创建组失败
                logger.error("创建组失败"+fileInfo.getFileName()+"；错误信息："+e.getMessage());
                //删除该方法操作的信息
                deleteRequest(zccRequest.getSeq());
               throw e;
            }
            //创建组成功
            //获取改组的变更临时数据，导入到变更明细表中
            List<ContrastTemporary> list ;
            list = ListUtil.getList(temporaryList,entry.getKey());
            ZCCDetail zccDetail = new ZCCDetail();
            list.forEach(v->{
                zccDetail.setSeq(0);
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
                zoningDataUploadMapper.updateFocusApplicationNum(fileSquence,null,Common.XZQH_JZBGZT_SQDSQSB);
                throw e;
            }
       }

       //将申请单序列保存到文件信息表
        zoningDataUploadMapper.updateFocusApplicationNum(fileSquence,zccRequest.getSeq(),Common.XZQH_JZBGZT_SQDSQCG);
       return true;
    }

    /**
     *  删除出现错误的申请单
     *  需要删除相应的变更组与变更明细表记录
     * @param seq 申请单序号
     */
    public void deleteRequest(Integer seq){
        ZCCRequest target = zccRequestMapper.get(seq);
        List<ZCCGroup> zccGroups = zccGroupMapper.findByRequestSeq(seq);
        zccGroups.forEach(group ->{

            //1、删除变更对照明细
            zccDetailMapper.deleteByGroupSeq(group.getSeq());

            //2、删除变更对照组
            zccGroupMapper.delete(group);
        });

        //3、删除变更申请单
        zccRequestMapper.delete(seq);

    }


    /*
    * 导入正式表
    * */
    public void importformalTable(){
        //获取预览表中最终版数据
        QueryReq queryReq1 = new QueryReq();
        queryReq1.addFilter(new QueryFilter("chooseSign","Y")).addFilter(new QueryFilter("usefulSign","Y"));
        List<PreviewDataInfo> previewDataInfoList = previewDataInfoMapper.findPreviewDataByUseful(queryReq1);
        //查看历史数据表中最高版本号
        QueryReq queryReq = new QueryReq(null,"max(version)");
        int maxVersion = historicalDataMapper.findMaxVersion(queryReq);
        // 将其数据导入到历史数据表中、版本号+1
        HistoricalDataInfo historicalDataInfo = new HistoricalDataInfo();
        historicalDataInfo.setVersion(maxVersion+1);
        previewDataInfoList.forEach(v->{
            historicalDataInfo.PreviewDataToHistoricalDataInfo(v);
            int result = historicalDataMapper.insertIntoHistorical(historicalDataInfo);
            if (result==0){
                logger.error("添加历史数据的过程追中出错！！");
                throw new RuntimeException("出错数据为："+v.getUniqueKey());
            }
        });
        //导入成功时清空 正式数据表 （成功与否 可以判断导入的条数）
        formalTableMapper.delete();
        //导入到正式表中
        FormalTableInfo formalTableInfo = new FormalTableInfo();
        previewDataInfoList.forEach(v->{
            formalTableInfo.previewDataToForaml(v);
            int result = formalTableMapper.insertFormatTableData(formalTableInfo);
            if (result==0){
                logger.error("添加正式数据的过程追中出错！！");
                throw new RuntimeException("出错数据为："+v.getUniqueKey());
            }
        });

    }



}
