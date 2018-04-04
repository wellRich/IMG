package com.digital.service;

import java.util.*;
import java.util.stream.Collectors;

import com.digital.dao.*;
import com.digital.entity.*;
import com.digital.util.Common;
import com.digital.util.JSONHelper;
import com.digital.util.StringUtil;
import com.digital.util.search.QueryResp;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digital.api.ZoningCodeChangeApi;
import org.springframework.transaction.annotation.Transactional;



/**
 * 行政区划变更申请单的维护service
 *
 * @author guoyka
 * @version 2018/3/15
 */
@Service
@Transactional
public class ZoningCodeChangeApiImpl implements ZoningCodeChangeApi {
    private static final Logger log = LoggerFactory.getLogger(ZoningCodeChangeApiImpl.class);

    @Autowired
    ZCCRequestMapper zccRequestMapper;

    @Autowired
    ZCCGroupMapper zccGroupMapper;

    @Autowired
    ZCCDetailMapper zccDetailMapper;

    @Autowired
    PreviewDataInfoMapper previewDataInfoMapper;

    @Autowired
    LogicCheckMapper logicCheckMapper;


    @Autowired
    CommonService commonService;


    /**
     * 通过区划代码查找申请单
     * 数据用来初始化申请单创建页面
     * @param levelCode 区划级别代码
     * @param pageSize 每页数据量
     * @param pageIndex 请求的页码
     * @param totalRecord 数据总量，首次查询为空，随后的查询，就从前台传来
     * @return 申请单列表，分页
     */
    @Override
    public Map<String, Object> findZCCReqByZoningLevelCode(String levelCode, String zoningName, Integer pageIndex, Integer pageSize, Integer totalRecord) throws IllegalAccessException {
        QueryResp<ZCCRequest> queryResp = pageSeekByZoningCode(levelCode, pageIndex, pageSize, totalRecord);
        Map<String, Object> objectMap = queryResp.toMap();
        List respList = new ArrayList();
        for (ZCCRequest zccRequest:queryResp.getDataList()){
            Map cell = zccRequest.toMap();
            cell.put("zoningName", zoningName);
            respList.add(cell);
        }
        objectMap.put("dataList", respList);
        return objectMap;
    }

    private QueryResp<ZCCRequest> pageSeekByZoningCode(String levelCode, Integer pageIndex, Integer pageSize, Integer totalRecord){
        QueryResp<ZCCRequest> resp = new QueryResp<>();
        if (totalRecord == 0) {
            totalRecord = zccRequestMapper.countByZoningCode(levelCode);
        }
        resp.setTotalRecord(totalRecord);
        resp.setPageIndex(pageIndex);
        resp.setPageSize(pageSize);
        resp.setTotalPage(resp.getPageCount());
        int offset = (pageIndex - 1) * pageSize;
        resp.setDataList(zccRequestMapper.pageSeekByZoningCode(levelCode, offset, pageSize));
        return resp;
    }



    @Override
    public int addZCCRequest(ZCCRequest req) {
        List<ZCCRequest> zccRequestList = findZCCReqByZCAndStatus(req.getOwnZoningCode(), Common.XZQH_SQDZT_GJYSH);
        if(zccRequestList.size() == 0){
            if(zccRequestMapper.insert(req) >  0){
                return req.getSeq();
            }else {
                throw new RuntimeException("添加变更申请单失败！");
            }
        }else {
            throw new RuntimeException("存在未经过审核的区划变更申请单，不可继续添加区划变更申请单！");
        }
    }


    /**
     * 通过状态与区划代码查找申请单
     * 〈功能详细描述〉
     * @param zoingCode 区划代码
     * @param status 申请单状态
     * @return 申请单列表
     */
    @Override
    public List<ZCCRequest> findZCCReqByZCAndStatus(String zoingCode, String status) {

        //取得区划代码的前六位
        return zccRequestMapper.findAllByZoningCodeAndStatus(zoingCode.substring(0, 6), status);
    }

    /**
     *  根据区划代码查找申请单
     * @param zoningCode
     * @return
     */
    @Override
    public List<ZCCRequest> findZCCReqByZoningCode(String zoningCode) {
        return zccRequestMapper.findAllByZoningCode(zoningCode);
    }

    /**
     * 初始化录入明细表的数据
     * 获取区划预览数据
     * @param zoningCode
     * @return
     */
    @Override
    public Map<String, List<PreviewDataInfo>> findPreviewByZoningCode(String zoningCode) {
        log.info("findPreviewByZoningCode.zoningCode-------> " + zoningCode);
        List<PreviewDataInfo> previewDataInfos = previewDataInfoMapper.findAllByZoningCode(zoningCode);
        Map<String, List<PreviewDataInfo>> result = new HashMap<>();
        for (PreviewDataInfo dataInfo : previewDataInfos) {
            if (result.containsKey(dataInfo.getAssigningCode())) {
                result.get(dataInfo.getAssigningCode()).add(dataInfo);
            } else {
                List<PreviewDataInfo> cell = new ArrayList<>();
                cell.add(dataInfo);
                result.put(dataInfo.getAssigningCode(), cell);
            }
        }
        return result;
    }

    @Override
    public List<PreviewDataInfo> findSubordinateZoning(String zoningCode) {
        return previewDataInfoMapper.findSubordinateZoning(zoningCode);
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
            log.info("删除区划变更申请单--> " + target);

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

    /**
     * 通过区划代码查找区划预览数据
     * @param zoningCode 区划代码
     * @return PreviewDataInfo
     */
    public PreviewDataInfo findOneByZoningCode(String zoningCode){
        return previewDataInfoMapper.findOneByZoningCode(zoningCode);
    }

    public List<PreviewDataInfo> findBrothersByCode(String zoningCode){
       return previewDataInfoMapper.findBrothersByCode(zoningCode);
    }

    @Override
    public void addDetails(String group, String details, String zoningCode) {
        log.info("addDetails.zoningCode ---> " + zoningCode);
        // 1 添加变更对照组
        ZCCGroup groupInfo = JSONHelper.fromJsonObject(group, ZCCGroup.class);
        Integer groupSeq = addZCCGroup(zoningCode.substring(0,6), groupInfo);//zccGroupMapper.insert(groupInfo);
        log.info("addDetails.groupSeq-------------> " + groupSeq);

        // 2 添加变更对照明细数据
        List<ChangeInfo> ringChangesInfo = new LinkedList();
        List<ChangeInfo> changeInfoList = JSONHelper.fromJsonArray(details, ChangeInfo.class);
        int size = changeInfoList.size();
        ChangeInfo info;
        for(int i = 0; i < size; i ++){
            info = changeInfoList.get(i);
            System.out.println("addDetails.info---------> " + info);
            info.setCreatorDate(new Date());
            info.setGroupSeq(groupSeq);

            //2.1 名称变更，直接插入变更对照明细表、区划预览表以及变更明细历史记录
            if(Common.hasSameZoningCode(info.getOriginalZoningCode(), info.getTargetZoningCode())){
                saveDetail(info);
            }

            //2.2 代码变更
            else {

                //2.2.1 存在环状变更
                if(info.getRingFlag().equals("1")){
                    ringChangesInfo.add(info);
                }else {
                    saveDetail(info);
                }
            }
        }

        //2.2.2 解析环状数据
        splitRingData(ringChangesInfo).forEach(this::saveDetail);

    }


    /**
     * 保存变更
     */
    public void saveDetail(ChangeInfo info){

        //1 插入明细变更对照表
        int key = zccDetailMapper.insert(info.toDetail());
        System.out.println("saveDetail().key---------------> " + key);

        //2 逻辑校验
        commonService.logicVerifyZCChange(info);


        //3 保存到区划代码预览表
        commonService.savePreviewData(info);

        //4 保存到变更明细历史数据表
        Map<String, Object> param = new HashMap<>();
        param.put("requestReq", info.getRequestSeq());
        param.put("groupSeq", info.getGroupSeq());
        param.put("originCode", info.getOriginalZoningCode());
        param.put("originName", info.getOriginalZoningName());
        param.put("changeType", info.getChangeType());
        param.put("currentCode", info.getTargetZoningCode());
        param.put("currentName", info.getTargetZoningName());
        param.put("creatorCode", info.getCreatorCode());
        param.put("createDate", info.getCreatorDate());
        previewDataInfoMapper.insertHistoryData(param);

    }


    /**
     * 解开环状数据
     * @param changeInfos 变更信息列表
     * @return
     */
    public List<ChangeInfo> splitRingData(List<ChangeInfo> changeInfos) {
        List<ChangeInfo> result = new ArrayList<>();
        int count = 0;

        int total = changeInfos.size();
        for(int i = 0; i < total; i ++){
            ChangeInfo element = changeInfos.get(i);
            Set<ChangeInfo> cell = new LinkedHashSet<>();
            findBackward(element, changeInfos, cell);
            findForward(element, changeInfos, cell);
            System.out.println("--------分割线------------> ");

            int size = cell.size();
            cell.forEach(System.out::println);
            ChangeInfo[] changes = cell.toArray(new ChangeInfo[size]);

            //数量大于1，则是链或者环
            if (size > 1) {
                ChangeInfo start = changes[0];
                ChangeInfo end = changes[size - 1];

                //首元素的原区划等于尾元素的目标区划，则这是一个环
                if (end.getTargetZoningCode().equals(start.getOriginalZoningCode())) {
                    count = count + 1;//记录第几个环
                    String tempZoningCode = "";
                    for(int j = 0; j < size; j ++){
                        ChangeInfo info = changes[j];
                        if(j == size - 1){
                            String rank = end.getLevelCode();
                            int length = rank.length();
                            if(count < 10){
                                if (length <= 6 && length >= 2) {
                                    tempZoningCode = rank.substring(0, length - 2)
                                            + "L" + count;
                                } else if (length > 6
                                        && length <= 15) {
                                    tempZoningCode = rank.substring(0, length - 3)
                                            + "L0" + count;
                                }
                                tempZoningCode = Common.addZeroAtTail(tempZoningCode);

                                int tempCount = count;
                                while (commonService.isExitsZoningCode(tempZoningCode)){
                                    tempCount ++;
                                    int index = tempZoningCode.indexOf("L");
                                    if (index > -1 && index < 6) {
                                        String str = tempZoningCode.substring(index, index + 2);
                                        if (tempCount < 10) {
                                            tempZoningCode = tempZoningCode.replaceAll(str, "L"
                                                    + (tempCount));
                                        } else {
                                            throw new RuntimeException("代码变更环数超过9个！");
                                        }
                                    }

                                    if (index >= 6 && tempZoningCode.indexOf("L") < 15) {
                                        String str = tempZoningCode.substring(index, index + 3);
                                        if (tempCount < 10) {
                                            tempZoningCode = tempZoningCode.replaceAll(str, "L0"
                                                    + (tempCount));
                                        } else {
                                            tempZoningCode = tempZoningCode.replaceAll(str, "L"
                                                    + (tempCount));
                                        }
                                    }

                                    try {
                                        ChangeInfo changeInfo = info.clone();
                                        changeInfo.setTargetZoningName(tempZoningCode);
                                        result.add(changeInfo);
                                    }catch (CloneNotSupportedException ex){
                                        log.error("splitRingData----> " + ex.getMessage());
                                    }
                                }

                            }else {
                                throw new RuntimeException("代码变更环数超过9个！");
                            }
                        }else {
                            result.add(info);
                        }
                    }

                }

                //这是一个链
                else {
                    String targetCode = end.getTargetZoningCode();
                    // 如果最后一个节点的目标代码已存在，则此链不符合业务要求
                    if (commonService.isExitsZoningCode(targetCode)) {
                        throw new RuntimeException("原行政区划代码由:" + end.getOriginalZoningCode() + "变更为："
                                + targetCode + "时出错，目标代码已存在！");
                    } else {
                        cell.forEach(result::add);
                    }
                }
            }

            //单个元素
            else {
                ChangeInfo changeInfo = changes[0];
                boolean isExits = commonService.isExitsZoningCode(changeInfo.getOriginalZoningCode());
                if(changeInfo.getChangeType().equals(Common.MERGE)){
                    if(!isExits){
                        throw new RuntimeException("原行政区划代码由:" + changeInfo.getOriginalZoningCode() + "变更为："
                                + changeInfo.getTargetZoningCode() + "时出错，目标代码不存在！");
                    }
                }else {
                    if(isExits){
                        throw new RuntimeException("原行政区划代码由:" + changeInfo.getOriginalZoningCode() + "变更为："
                                + changeInfo.getTargetZoningCode() + "时出错，目标代码已存在！");
                    }
                }
                result.add(changeInfo);
            }
        }
        return result;
    }




    /**
     *  找下一链节
     * @param info 变更主体
     * @param infoList 未排列成链的环状、链状变更
     * @param changeInfos 存放链条的容器
     * @return
     */
    public Set<ChangeInfo> findForward(ChangeInfo info, List<ChangeInfo> infoList, Set<ChangeInfo>  changeInfos) {
        ChangeInfo target = null;
        String targetZoningCode = info.getTargetZoningCode();

        for (ChangeInfo changeInfo : infoList) {
            if (targetZoningCode.equals(changeInfo.getOriginalZoningCode())) {
                target = changeInfo;
            }
        }

        if (target == null) {
            changeInfos.add(info);
            infoList.remove(info);
        } else {
            changeInfos.add(info);
            changeInfos.add(target);
            infoList.remove(info);
            findForward(target, infoList, changeInfos);
        }
        return changeInfos;
    }

    /**
     * 找上一链节
     *
     * @param info        变更主体
     * @param infoList    未排列成链的环状、链状变更
     * @param changeInfos 存放链条的容器
     * @return
     */
    public Set<ChangeInfo> findBackward(ChangeInfo info, List<ChangeInfo> infoList, Set<ChangeInfo> changeInfos) {
        ChangeInfo origin = null;
        String originZoningCode = info.getOriginalZoningCode();
        for (ChangeInfo changeInfo : infoList) {
            if (changeInfo.getTargetZoningCode().equals(originZoningCode)) {
                origin = changeInfo;
            }
        }
        if (origin == null) {
            changeInfos.add(info);
            infoList.remove(info);
        } else {
            changeInfos.add(origin);
            changeInfos.add(info);
            infoList.remove(info);
            findBackward(origin, infoList, changeInfos);
        }
        return changeInfos;
    }



    /**
     * 更新区划代码变更申请单
     * @param req 申请单序号
     */
    @Override
    public void updateZCCRequest(Map<String, Object> req) {
        zccRequestMapper.update(req);
    }

    /**
     * 查找申请单下的变更对照明细
     * @param requestSeq 申请单序号
     * @param pageIndex 页码
     * @param pageSize 每页显示数量
     * @return 变更对照明细列表
     */
    @Override
    public QueryResp<ZCCDetail> pageSeekByGroups(Integer requestSeq, int pageIndex, int pageSize) {
        String seqStr = StringUtils.join(zccGroupMapper.findByRequestSeq(requestSeq).stream().map(group -> group.getSeq()).collect(Collectors.toList()), ",");
        ZCCRequest req = zccRequestMapper.get(requestSeq);
        QueryResp<ZCCDetail> detailQueryResp = new QueryResp<>(pageIndex, pageSize);
        detailQueryResp.setTotalRecord(zccDetailMapper.countByGroups(seqStr));
        detailQueryResp.setTotalPage(detailQueryResp.getPageCount());
        int offset = (pageIndex - 1) * pageSize;
        detailQueryResp.setDataList(zccDetailMapper.pageSeekByGroups(seqStr, offset, pageSize));
       return detailQueryResp;
    }

    /**
     * 删除指定对变更照组序号的变更对照明细
     * @param groupSeq 明细表序号
     */
    @Override
    public void deleteDetails(Integer groupSeq) {
        zccDetailMapper.deleteByGroupSeq(groupSeq);
    }

    /**
     * 提交申请单
     * @param  seq 申请单序号
     */
    @Override
    public void submitZCCRequest(Integer seq) {
        ZCCRequest req = zccRequestMapper.get(seq);
        if(req.getStatus().equals(Common.XZQH_SQDZT_WTJ)){
            //修改申请单状态为已经提交
            zccRequestMapper.update(ImmutableMap.of("status", Common.XZQH_SQDZT_YTJ));
        }else {
            throw new RuntimeException("只能提交未提交的申请单！");
        }
    }


    /**
     * 撤销
     *
     * @param seq 申请单序号
     */
    @Override
    public void revokeZCCRequest(Integer seq) {
        ZCCRequest req = zccRequestMapper.get(seq);
        if(req.getStatus().equals(Common.XZQH_SQDZT_YTJ)){

            /*
             * 修改申请单状态为未提交
             * 增加修改人信息、修改时间与修改机构信息
             */
            req.setStatus(Common.XZQH_SQDZT_WTJ);
            req.setUpdaterCode("000");
            req.setUpdaterDeptCode("000");
            req.setLastUpdate(StringUtil.getTime());
            zccRequestMapper.update(req);
        }else {
            throw new RuntimeException("只能撤销已提交的申请单！");
        }
    }


    /**
     * 添加变更对照组
     * @param group
     * @return 新增组的主键
     */
    public int addZCCGroup(ZCCGroup group){
        Long maxOrderNum = getMaxOrderNum(ZCCGroup.tableName);
        group.setOrderNum(maxOrderNum.toString());
        if(zccGroupMapper.insert(group) > 0){
            return group.getSeq();
        }else {
            throw new RuntimeException("新增变更对照组失败！");
        }

    }

    @Override
    public int addZCCGroup(String levelCode, ZCCGroup group) {
        log.info("addZCCGroup.zoningCode----> " + levelCode);
        Long maxSerialNumber = zccGroupMapper.getMaxSerialNumber(levelCode);
        if(maxSerialNumber == null){
            Long serialNumber = Long.valueOf(levelCode + "001");
            group.setSerialNumber(serialNumber);
        }else {
            Long serialNumber = maxSerialNumber + 1;
            group.setSerialNumber(serialNumber);
        }
        Long maxOrderNum = getMaxOrderNum(ZCCGroup.tableName);
        group.setOrderNum(maxOrderNum.toString());
        group.setCreateDate(StringUtil.getTime());
        if(zccGroupMapper.insert(group) > 0){
            return group.getSeq();
        }else {
            throw new RuntimeException("新增变更对照组失败");
        }

    }

    /**
     * 获取表中某排序列的最大值
     * @param tableName
     * @return
     */
    public Long getMaxOrderNum(String tableName){
        Long max = zccGroupMapper.getMaxOrderNum(tableName);
        log.info("getMaxOrderNum.max------> " + max);
        if(max == null){
            zccGroupMapper.insertMaxOrderNum(ImmutableMap.of("tableName", tableName, "tableNote", "", "tableMax", 1L));
        }else {
            zccGroupMapper.updateMaxOrderNum(tableName);
        }
        return max == null ? 1L : max;
    }
}
