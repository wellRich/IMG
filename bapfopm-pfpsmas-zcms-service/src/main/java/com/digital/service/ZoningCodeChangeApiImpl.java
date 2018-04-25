package com.digital.service;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.digital.util.EntityHelper;
import com.digital.util.search.QueryFilter;
import com.digital.util.search.QueryReq;
import org.apache.poi.ss.usermodel.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.digital.api.ZoningCodeChangeApi;
import com.digital.dao.*;
import com.digital.entity.*;
import com.digital.util.Common;
import com.digital.util.JSONHelper;
import com.digital.util.StringUtil;
import com.digital.util.search.QueryResp;


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
    private ZCCRequestMapper zccRequestMapper;

    @Autowired
    private ZCCGroupMapper zccGroupMapper;

    @Autowired
    private ZCCDetailMapper zccDetailMapper;

    @Autowired
    private PreviewDataInfoMapper previewDataInfoMapper;

    @Autowired
    private LogicCheckMapper logicCheckMapper;


    @Autowired
    private CommonService commonService;

    @Autowired
    private HistoricalZoningChangeMapper historicalZoningChangeMapper;



    /**
     * 通过区划级别代码查找申请单
     * 数据用来初始化申请单创建页面
     * @param zoningCode 区划代码
     * @param pageIndex 请求的页码
     * @param pageSize 每页数据量
     * @param totalRecord 数据总量，首次查询为空，随后的查询，就从前台传来
     * @return 分页封装对象QueryResp
     * @throws IllegalAccessException
     */
    public QueryResp<ZCCRequest> findZCCReqByZoningCode(String zoningCode, Integer pageIndex, Integer pageSize, Integer totalRecord)throws IllegalAccessException {
        String levelCode = Common.getLevelCode(zoningCode);
        QueryResp<ZCCRequest> queryResp = QueryResp.buildQueryResp(pageIndex, pageSize, totalRecord, new QueryReq(){{
            addFilter("levelCode", levelCode + "%", QueryFilter.OPR_LIKE);
        }}, zccRequestMapper);
        return queryResp;
    }


    @Override
    public int addZCCRequest(ZCCRequest req) {
        List<ZCCRequest> zccRequestList = findNotPassNationalCheckReq(req.getLevelCode());
        if(zccRequestList.size() == 0){
            req.setStatus(Common.XZQH_SQDZT_WTJ);
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
     * @param levelCode 区划代码
     * @return 申请单列表
     */
    public List<ZCCRequest> findNotPassNationalCheckReq(String levelCode) {
        return zccRequestMapper.findAllByZoningCodeAndStatus(levelCode, Common.XZQH_SQDZT_GJYSH);
    }


    /**
     * 查找可以修改的区划变更申请单
     * @param levelCode 区划级别代码
     * @return 申请单列表
     */
    @Override
    public List<ZCCRequest> findWritableZCCRequests(String levelCode) {
        return zccRequestMapper.findByLevelCodeAndStatuses(levelCode, Common.XZQH_SQDZT_WTJ, Common.XZQH_SQDZT_SHBTG);
    }

    /**
     * 分页查询申请单
     * 通过级别代码与状态过滤
     * @param levelCode 区划级别代码
     * @param offset 起始
     * @param pageSize 每页大小
     * @param statuses 若干状态代码
     * @return 区划列表
     */
    List<ZCCRequest> pageSeekByLevelCodeAndStatuses(String levelCode, int offset, int pageSize, String ... statuses){
        return zccRequestMapper.pageSeekByLevelCodeAndStatuses(levelCode, offset, pageSize, statuses);
    }

    /**
     * 分页查询申请单
     * @param req 查询封装对象
     * @param pageIndex 当前页
     * @param pageSize 每页大小
     * @param total 数据总量
     * @return
     */
    @Override
    public QueryResp<ZCCRequest> pageSeek(QueryReq req, int pageIndex, int pageSize, int total) {
        QueryResp<ZCCRequest> resp = QueryResp.buildQueryResp(pageIndex, pageSize, total, req, zccRequestMapper);
        return resp;
    }

    /**
     * 初始化录入明细表的数据
     * 获取区划预览数据
     * @param zoningCode 区划代码
     * @return [级次代码：相应的区划列表]
     */
    @Override
    public Map<String, List<Map>> findPreviewByZoningCode(String zoningCode) throws IllegalAccessException {
        log.info("findPreviewByZoningCode.zoningCode-------> " + zoningCode);
        List<PreviewDataInfo> previewDataInfos = previewDataInfoMapper.findAncestorsAndSubsByZoningCode(zoningCode);
        Map<String, List<Map>> result = new HashMap<>();
        for (PreviewDataInfo dataInfo : previewDataInfos) {
            if (result.containsKey(dataInfo.getAssigningCode())) {
                log.info("findPreviewByZoningCode----> " + JSONHelper.toJSON(dataInfo));
                Map mapData = EntityHelper.toMap(dataInfo);
                mapData.put("superLevelCode", Common.getLevelCode(dataInfo.getSuperiorZoningCode()));
                result.get(dataInfo.getAssigningCode()).add(mapData);
            } else {
                List<Map> cell = new ArrayList<>();
                Map mapData = EntityHelper.toMap(dataInfo);
                mapData.put("superLevelCode", Common.getLevelCode(dataInfo.getSuperiorZoningCode()));
                cell.add(mapData);
                result.put(dataInfo.getAssigningCode(), cell);
            }
        }
        return result;
    }

    /**
     * 查找下级区划
     * @param zoningCode 代码
     * @return
     */
    @Override
    public List<?> findSubordinateZoning(String zoningCode) throws IllegalAccessException {
        List<Map> result = new ArrayList<>();
        String levelCode = Common.getLevelCode(zoningCode);
        for (PreviewDataInfo info: previewDataInfoMapper.findSubordinateZoning(zoningCode)){
            Map cell = EntityHelper.toMap(info);
            cell.put("superLevelCode", levelCode);
            result.add(cell);
        }
        return result;
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
        return  previewDataInfoMapper.findValidOneByZoningCode(zoningCode);
    }

      /**
     * 根据区划代码查找同一父级下的同级区划
     * @param zoningCode 区划代码
     * @return List
     */
    public List<PreviewDataInfo> findBrothersByCode(String zoningCode){
        return previewDataInfoMapper.findBrothersByCode(zoningCode);
    }



    /**
     * 获取上级区划
     * 登录用户区划的子孙区划中级次高一级的区划，排除原父级区划
     * @param  zoningCode 区划代码
     * @param  ownZoningCode 登录人所属的区划代码
     * @return      list
     */
    public List<PreviewDataInfo> findZoningesOfUncle(String zoningCode, String ownZoningCode){

        //1、获取登录用户所属区划的级别代码，
        //用于将区划范围圈定在所属区划的子孙区划中
        String ownLevelCode = Common.getLevelCode(ownZoningCode);

        //2、获取操作的区划的级次
        Integer assigningCode = Integer.valueOf(Common.getAssigningCode(zoningCode));

        //3、上级区划的级次
        Integer superiorAssigningCode = assigningCode - 1;

        //4、获取父级区划
        String superiorZoningCode = Common.getSuperiorZoningCode(zoningCode);

        //5、拼装查询条件
        QueryReq req = new QueryReq("zoningCode,index,divisionName");
        req.addFilter("levelCode", ownLevelCode + "%", QueryFilter.OPR_LIKE);
        req.addFilter("zoningCode", superiorZoningCode, QueryFilter.OPR_IS_NOT);
        req.addFilter("assigningCode", superiorAssigningCode);
        return previewDataInfoMapper.seek(req);

    }

    /**
     * 添加变更对照明细数据
     * @param  group  里面是变更对照组的属性键值对（除主键之外的所有属性）
     * @param details 里面是json数组包含若干变更明细数据，具体的结构是ChangeInfo的属性键值对
     * @param  zoningCode 登录用户所属的区划区划代码
     * @param creatorCode 创建人代码
     * @param creatorDeptCode 创建人所在机构代码
     */
    @Override
    public void addDetails(String group, String details, String zoningCode, String creatorCode, String creatorDeptCode) {
        log.info("addDetails.zoningCode ---> " + zoningCode);

        // 1 添加变更对照组
        ZCCGroup groupInfo = JSONHelper.fromJsonObject(group, ZCCGroup.class);
        groupInfo.setCreateDate(creatorCode);
        groupInfo.setCreatorDeptCode(creatorDeptCode);
        Integer groupSeq = addZCCGroup(zoningCode.substring(0,6), groupInfo);//zccGroupMapper.insert(groupInfo);
        log.info("addDetails.groupSeq-------------> " + groupSeq);

        // 2 添加变更对照明细数据
        List<ChangeInfo> ringChangesInfo = new LinkedList();
        List<ChangeInfo> changeInfoList = JSONHelper.fromJsonArray(details, ChangeInfo.class);
        int size = changeInfoList.size();
        ChangeInfo info;
        for(int i = 0; i < size; i ++){
            info = changeInfoList.get(i);

            //补充数据
            info.setCreatorDate(new Date());
            info.setGroupSeq(groupSeq);
            info.setCreatorCode(creatorCode);
            info.setOrganizationCode(creatorDeptCode);
            info.buildAssigningCode();
            info.buildTargetLevelCode();
            info.buildOriginalLevelCode();

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
     * 单位隶属关系是个问题，不明白是个什么东西
     */
    public void saveDetail(ChangeInfo info){
        log.info("saveDetail-assigningCode----> " + info.getAssigningCode());
        //1 插入明细变更对照表
        ZCCDetail detail = info.toDetail();
        zccDetailMapper.insert(detail);

        //2 逻辑校验
        commonService.logicVerifyZCChange(info);


        //3 保存到区划代码预览表
        commonService.savePreviewData(info);

        //4 保存到变更明细历史数据表
        Map<String, Object> param = new HashMap<>();
        param.put("seq", detail.getSeq());
        param.put("groupSeq", info.getGroupSeq());
        param.put("originalZoningCode", info.getOriginalZoningCode());
        param.put("originalZoningName", info.getOriginalZoningName());
        param.put("changeType", info.getChangeType());
        param.put("targetZoningCode", info.getTargetZoningCode());
        param.put("targetZoningName", info.getTargetZoningName());
        param.put("creatorCode", info.getCreatorCode());
        param.put("createDate", info.getCreatorDate());
        log.info("saveDetalis.param--------> "  + JSONHelper.toJSON(param));
        historicalZoningChangeMapper.insert(param);

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
                            String rank = end.getTargetLevelCode();
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
     * 更新申请单
     * 省级审核、省级确认、国家审核与申请单维护，都通过这个接口实现
     * @param param 更新信息
     */
    @Override
    public void updateZCCRequest(Map<String, Object> param) {
        Optional<ZCCRequest> req = Optional.of(zccRequestMapper.get(param.get("seq")));
        req.ifPresent(e -> {
            String status = e.getStatus();
            if(status.equals(Common.XZQH_SQDZT_WTJ) || status.equals(Common.XZQH_SQDZT_SHBTG)){
                zccRequestMapper.update(param);
            }else {
                throw new RuntimeException("不能修改状态为[" + status + "]的区划变更申请单");
            }
        });
    }

    /**
     * 查找申请单下的变更对照明细
     * @param requestSeq 申请单序号
     * @param pageIndex 页码
     * @param pageSize 每页显示数量
     * @return 变更对照明细列表
     */
    @Override
    public QueryResp<?> pageSeekByGroups(Integer requestSeq, int pageIndex, int pageSize, int total)  throws IllegalAccessException {
        Map<Integer, String> groupSeqAndName = new HashMap<>();
        String seqStr = StringUtils.join(zccGroupMapper.findByRequestSeq(requestSeq).stream().map(e -> {
            groupSeqAndName.put(e.getSeq(), e.getName());
            return e.getSeq();
        }).collect(Collectors.toList()), ",");
        QueryResp<?> resp = new QueryResp<>(pageIndex, pageSize);
        log.info("pageSeekByGroups.seqStr--------> " + seqStr);
        if(seqStr == null || "".equals(seqStr)){
            return resp;
        }else {
            resp = QueryResp.buildQueryResp(pageIndex, pageSize, total, new QueryReq(){{
                addFilter("groupSeq", seqStr, QueryFilter.OPR_IN);
            }}, zccDetailMapper);
            List details = new ArrayList();
            for(ZCCDetail obj:(List<ZCCDetail>) resp.getDataList()){
                Map cell = EntityHelper.toMap(obj);
                cell.put("notes", groupSeqAndName.get(obj.getGroupSeq()));
                details.add(cell);
            }
            resp.setDataList(details);
            return resp;
        }
    }


    /**
     *  查询若干级次的区划预览数据，限定在指定根区划的子孙区划
     * @param ownZoning 登录用户所属区划
     * @param assigningCodes  若干目标级次
     * @return
     */
    public List<PreviewDataInfo> findByAssigningCodesAndRootZoning(String ownZoning, String superiorZoningCode, String assigningCodes) {
        return previewDataInfoMapper.seek(new QueryReq("assigningCode", "index,zoningCode,levelCode,divisionName,assigningCode,superiorZoningCode") {{
            addFilter("levelCode", Common.getLevelCode(ownZoning) + "%", QueryFilter.OPR_LIKE);
            if (!"".equals(assigningCodes) && assigningCodes != null) {
                addFilter("zoningCode", superiorZoningCode, QueryFilter.OPR_IS_NOT);
                addFilter("assigningCode", StringUtils.join(Arrays.asList(assigningCodes), ","), QueryFilter.OPR_IN);
            }
        }});
    }
    
    private Map<String, Object> toMap(PreviewDataInfo info){
        return new HashMap<String, Object>(){{
            put("id", info.getIndex());
            put("zoningCode", info.getZoningCode());
            put("name", info.getDivisionName() + " " + info.getLevelCode().replace(Common.getLevelCode(info.getSuperiorZoningCode()), ""));
            put("divisionName", info.getDivisionName());
        }};
    }

    /**
     * 将指定区划预览数据的上级与上级拼成两级的树形结构，
     * 数据范围限定在登录用户所属的区划的子孙区划中
     * @param ownZoningCode 登录用户所属区划代码
     * @param originalZoningCode 操作区划的原区划代码
     * @return
     */
    @Override
    public List<Map> getTowLevelTree(String ownZoningCode, String originalZoningCode) {
        log.info("getTowLevelTree.originalZoningCode----------> " + originalZoningCode);
        log.info("getTowLevelTree.ownZoningCode----------> " + ownZoningCode);

        List<Map> result = new ArrayList<>();
        try {
            int originalAssigningCode = Integer.valueOf(Common.getAssigningCode(originalZoningCode));
            String grandfatherAssCode = String.valueOf(originalAssigningCode - 2);
            String parentAssCode = String.valueOf(originalAssigningCode - 1);
            String superiorZoningCode = Common.getSuperiorZoningCode(originalZoningCode);

            //1 取得区划预览集合
            List<PreviewDataInfo> previewDataInfos = findByAssigningCodesAndRootZoning(ownZoningCode, superiorZoningCode,grandfatherAssCode + "," + parentAssCode);

            //2 一个map，用于盛装级次为grandfatherAssCode的数据，以zoningCode为键
            Map<String, PreviewDataInfo> vesselOfGrandPa = new HashMap<>();

            //3 一个map，以superiorZoningCode为键，值是一个List用于盛装级次为parentAssCode的数据转化成的map
            Map<String, List<Map>> vesselOfPapa = new HashMap<>();

            String vesselCode;

            //4 第一个循环，以级次分容器盛装数据
            for (PreviewDataInfo info : previewDataInfos) {

                if (info.getAssigningCode().equals(grandfatherAssCode)) {
                    vesselOfGrandPa.put(info.getZoningCode(), info);
                } else {
                    vesselCode = info.getSuperiorZoningCode();
                    if (vesselOfPapa.containsKey(vesselCode)) {
                        vesselOfPapa.get(vesselCode).add(toMap(info));
                    } else {
                        vesselOfPapa.put(vesselCode, new ArrayList<Map>() {{
                            add(toMap(info));
                        }});
                    }
                }
            }


            //5 第二个循环，找到父级，组装tree

            vesselOfPapa.forEach((k, v) -> {

                //5.1 找到父级
                PreviewDataInfo father = vesselOfGrandPa.get(k);
                if (father != null) {
                    Map<String, Object> cell = toMap(father);
                    cell.put("chkDisabled", true);
                    cell.put("children", v);
                    result.add(cell);
                }
            });
        } catch (Exception ex) {
            log.error("findByAssigningCodesAndRootZoning.error---> " + ex.getLocalizedMessage());
            ex.printStackTrace();
        }
        return result;
    }


    /**
     * 删除指定对变更照组序号的变更对照明细
     * @param groupSeqs 若干明细表序号，以“,”分隔
     */
    @Override
    public void deleteDetails(String groupSeqs) {

        //这一步目前只是通过查询serialNumber(编号)，以它的倒序来排序
        List<ZCCGroup> groups = zccGroupMapper.findByIds(groupSeqs);

        int size = groups.size();

        //取得编号最小的组，也是要删除的组中的最后一个变更组
        ZCCGroup lastGroup = groups.get(size - 1);
        ZCCRequest req = zccRequestMapper.get(lastGroup.getRequestSeq());
        
        Long serialNumOfLastGroup = lastGroup.getSerialNumber();
        System.out.println("serialNumOfLastGroup--> " + serialNumOfLastGroup);
        
        //该申请单下的所有变更组
        List<ZCCGroup> allGroups = zccGroupMapper.findByRequestSeq(req.getSeq());

        LinkedHashMap<ZCCGroup, List<ZCCDetail>> referData = new LinkedHashMap<>();

        //排除要删除的组与时间线在所有删除组之前的组
        for (ZCCGroup group: allGroups){
            if(!groups.contains(group) && group.getSerialNumber() > serialNumOfLastGroup){
                referData.put(group, zccDetailMapper.findByGroupSeq(group.getSeq()));
            }
        }

        for (ZCCGroup group : groups) {
            Integer groupSeq = group.getSeq();
            log.info("deleteDetails.groupSeq----------> " + groupSeq);
            for (ZCCDetail detail : zccDetailMapper.findByGroupSeq(groupSeq)) {
                log.info("deleteDetails.targetDetail ----> " + detail);
                //是否未牵涉其它的明细变更数据
                String msg = findFetterOfDetail(detail.getOriginalZoningCode(), detail.getCurrentZoningCode(), referData);
                if ("".equals(msg)) {

                    //还原预览数据
                    restoreDetail(detail);

                    //删除历史数据
                    historicalZoningChangeMapper.delete(detail.getSeq());

                } else {
                    throw new RuntimeException(msg);
                }
            }

            //3 删除明细数据
            zccDetailMapper.deleteByGroupSeq(groupSeq);

            //4 删除变更对照组
            zccGroupMapper.delete(groupSeq);
        }
    }


    /**
     * 查找与指定明细有牵扯的区划变更明细
     * 并
     * @param originalZoningCode 原区划代码
     * @param targetZoningCode   目标区划代码
     * @param referData          参照比较的明细数据
     * @return true or false
     */
    private String findFetterOfDetail(String originalZoningCode, String targetZoningCode, Map<ZCCGroup, List<ZCCDetail>> referData) {

        //原级别代码
        String levelCode = Common.getLevelCode(targetZoningCode);
        StringBuffer message = new StringBuffer();
        log.info("findFetterOfDetail.size ----------> " + referData.size());
        referData.forEach((group, v) -> {
            log.info("findFetterOfDetail.group-----> " + group.getSerialNumber());
            log.info("findFetterOfDetail.v.size-----------> " + v.size());
            v.forEach(detail -> {
                String changeType = detail.getChangeType();
                String originalCode = detail.getOriginalZoningCode();
                String targetCode = detail.getCurrentZoningCode();
                log.info("findFetterOfDetail.detail -------------> " + detail);
                if (changeType.equals(Common.ADD)) {
                    String msg = ("在调整说明“" + group + "”");

                    if (originalZoningCode.equals(targetCode)) {
                        message.append("原区划代码“")
                                .append(originalZoningCode)
                                .append("”")
                                .append(msg)
                                .append("中被再次使用，请先删除此调整说明！");
                    }

                    if (targetCode.contains(levelCode)) {
                        message.append("现区划代码“")
                                .append(targetZoningCode)
                                .append("”")
                                .append(msg)
                                .append("下有新增区划")
                                .append("，请先删除此调整说明！");
                    }
                } else if (changeType.equals(Common.CHANGE)) {
                    //名称变更，可以忽略
                    if(targetCode.equals(originalCode)){
                        System.out.println("名称变更------------》 " + detail);
                    }else {
                        String msg = ("在调整说明“" + group + "”");
                        if (originalZoningCode.equals(targetCode)) {
                            message.append("原区划代码“").append(originalZoningCode).append("”").append(msg)
                                    .append("中被再次使用，请先删除此调整说明！");
                        }

                        if (checkSuperCode(originalZoningCode, originalCode)) {
                            message.append("原区划代码“").append(originalZoningCode).append("”的上级区划“").append(originalCode).append("”")
                                    .append(msg).append("中已被变更为：").append(targetCode).append("，请先删除此调整说明！");
                        }

                        if (targetZoningCode.equals(originalCode)) {
                            message.append("现区划代码“").append(targetZoningCode).append("”").append(msg)
                                    .append("中已被变更为“").append(targetCode).append("”，请先删除此调整说明！");
                        } else {
                            if (originalCode.contains(levelCode)) {
                                message.append("现区划代码“").append(targetZoningCode).append("”的下级区划“").append(originalCode).append("”")
                                        .append(msg).append("中已被变更为：").append(targetCode).append("，请先删除此调整说明！");
                            } else if (checkSuperCode(targetZoningCode, originalCode)) {
                                message.append("现区划代码“").append(targetZoningCode).append("”的上级区划“").append(Common.getSuperiorZoningCode(originalCode)).append("”")
                                        .append(msg).append("中已被变更为：").append(targetCode).append("，请先删除此调整说明！");
                            }
                        }
                    }
                    
                    
                } else if (changeType.equals(Common.MERGE)) {
                    String msg = ("在调整说明“" + group + "”");

                    //查看是否有区划并入到此区划或者此区划下级区划的下面
                    if (targetZoningCode.equals(targetCode)) {
                        message.append("现区划代码“").append(originalCode).append("”").append(msg)
                                .append("中被并入到“").append(targetZoningCode).append("”下，请先删除此调整说明！");
                    } else {
                        if (targetCode.contains(levelCode)) {
                            message.append("现区划代码“").append(targetZoningCode).append("”");
                            if (!targetZoningCode.equals(originalCode)) {
                                message.append("的下级区划“").append(originalCode).append("”");
                            }
                            message.append(msg).append("中已被并入到“").append(targetCode).append("”下，请先删除此调整说明！");
                        }
                    }

                    if (checkSuperCode(originalZoningCode, originalCode)) {
                        message.append("原区划代码“").append(originalZoningCode).append("”的上级区划“").append(originalCode).append("”")
                                .append(msg).append("中已被并入到“").append(targetCode).append("”下，请先删除此调整说明！");
                    }

                    if (targetZoningCode.equals(targetCode)) {
                        message.append("现区划代码“").append(originalCode).append("”").append(msg)
                                .append("中已被并入到“").append(targetCode).append("下”，请先删除此调整说明！");
                    } else {
                        if (originalCode.contains(levelCode)) {
                            message.append("现区划代码“").append(targetZoningCode).append("”");
                            if (!targetZoningCode.equals(originalCode)) {
                                message.append("的下级区划“").append(originalCode).append("”");
                            }
                            message.append(msg).append("中已被并入到：").append(targetCode).append("下，请先删除此调整说明！");
                        }

                        if (checkSuperCode(targetZoningCode, originalCode)) {
                            message.append("现区划代码“").append(targetZoningCode).append("”的上级区划“").append(originalCode).append("”")
                                    .append(msg).append("中已被并入到：").append(targetCode).append("下，请先删除此调整说明！");
                        }
                    }
                } else if (changeType.equals(Common.MOVE)) {
                    String msg = "在调整说明“" + group + "”";

                    //查看是否有区划迁移到此区划或者此区划下级区划的下面
                    if (originalZoningCode.equals(targetCode)) {
                        message.append("原区划代码“").append(originalZoningCode).append("”").append(msg)
                                .append("中被再次使用，请先删除此调整说明！");
                    }

                    if (targetZoningCode.equals(Common.getSuperiorZoningCode(targetCode))) {
                        message.append("现区划代码“").append(targetZoningCode).append("”").append(msg)
                                .append("中新增迁移区划“").append(targetCode).append("”，请先删除此调整说明！");
                    } else {
                        if (targetCode.contains(levelCode)) {
                            message.append("现区划代码“").append(targetZoningCode).append("”的下级区划“").append(Common.getSuperiorZoningCode(targetCode)).append("”")
                                    .append(msg).append("中有新迁移区划：").append(targetCode).append("，请先删除此调整说明！");
                        }
                    }

                    if (checkSuperCode(originalZoningCode, originalCode)) {
                        message.append("原区划代码“").append(originalZoningCode).append("”的上级区划“").append(originalCode).append("”")
                                .append(msg).append("中已被迁移到：").append(Common.getSuperiorZoningCode(targetCode)).append("，请先删除此调整说明！");
                    }

                    if (targetZoningCode.equals(originalCode)) {
                        message.append("现区划代码“").append(targetZoningCode).append("”").append(msg)
                                .append("中已被迁移到“").append(Common.getSuperiorZoningCode(targetCode)).append("下”，请先删除此调整说明！");
                    } else {
                        if (originalCode.contains(levelCode)) {
                            message.append("现区划代码“").append(targetZoningCode).append("”的下级区划“").append(originalCode).append("”")
                                    .append(msg).append("中已被迁移到：").append(Common.getSuperiorZoningCode(targetCode)).append("下，请先删除此调整说明！");
                        }

                        if (checkSuperCode(targetZoningCode, originalCode)) {
                            message.append("现区划代码“").append(targetZoningCode).append("”的上级区划“").append(originalCode).append("”")
                                    .append(msg).append("中已被迁移到：").append(Common.getSuperiorZoningCode(targetCode)).append("下，请先删除此调整说明！");
                        }
                    }
                }
            });
        });
        log.info("findFetterOfDetail.message--------> " + message);
        return message.toString();
    }

    //查找上级区划是否和给出的区划代码相同
    private boolean checkSuperCode(String targetCode, String superiorCode) {
        log.info("checkSuperCode.sonCode ----> " + targetCode + ", superiorCode ---> " + superiorCode);
        boolean flag = false;

        String targetSuperiorCode = Common.getSuperiorZoningCode(targetCode);
        if(targetCode.equals("") || targetCode.equals(null)){
            return false;
        }else {
            while (!Common.getLevelCode(targetSuperiorCode).equals("")) {
                if (targetSuperiorCode.equals(superiorCode)) {
                    flag = true;
                    break;
                } else {
                    targetSuperiorCode = Common.getSuperiorZoningCode(targetSuperiorCode);
                }
            }
            return flag;
        }
    }


    /**
     * 恢复区划变更
     * 主要是处理区划预览数据
     *
     * @param detail 区划变更对照明细数据
     */
    private void restoreDetail(ZCCDetail detail) {
        String changeType = detail.getChangeType();
        String originalZoningCode = detail.getOriginalZoningCode();
        String originalZoningName = detail.getOriginalZoningName();
        String targetZoningCode = detail.getCurrentZoningCode();
        String targetZoningName = detail.getCurrentZoningName();

        //并入是要先将下级迁移的，所以这里并入的明细只有一条预览数据与之对应
        if (changeType.equals(Common.MERGE)) {
            recover(detail.getCreatorDate(), originalZoningCode);
        } else {

            //名称变更
            if (Common.hasSameZoningCode(originalZoningCode, targetZoningCode)) {

                //上级区划全称
                String superiorZoningFullName = previewDataInfoMapper.findValidOneByZoningCode(Common.getSuperAssignCode(originalZoningCode)).getDivisionFullName();

                //修改名称与简称
                previewDataInfoMapper.update(ImmutableMap.of("zoningCode", originalZoningCode, "divisionName", originalZoningName, "divisionAbbreviation", originalZoningName));

                //修改全称
                previewDataInfoMapper.updateFullNameByLevelCode(superiorZoningFullName + targetZoningName, superiorZoningFullName + originalZoningName, Common.getLevelCode(originalZoningCode) );

            } else {

                List<ChangeInfo> changeInfoList = getLowChangeInfo(originalZoningCode, targetZoningCode, changeType);
                int size = changeInfoList.size();
                for (int i = 0; i < size; i++) {
                    ChangeInfo changeInfo = changeInfoList.get(i);
                    String originalCode = changeInfo.getOriginalZoningCode();
                    String targetCode = changeInfo.getTargetZoningCode();
                    String type = changeInfo.getChangeType();
                    PreviewDataInfo previewDataInfo = previewDataInfoMapper.findValidOneByZoningCode(targetCode);
                    if (previewDataInfo != null) {
                        if (type.equals(Common.ADD)) {
                            previewDataInfoMapper.delete(previewDataInfo.getZoningCode());
                        } else {
                            if (Common.hasSameZoningCode(originalCode, targetCode)) {
                                continue;
                            }
                            recover(detail.getCreatorDate(), originalCode);
                        }
                    } else {
                        throw new RuntimeException("未找到区划预览：[" + originalCode + "]");
                    }
                }
            }
        }
    }

    /**
     * 恢复一条预览数据
     * @param createDate
     * @param zoningCode
     */
    private void recover(String createDate, String zoningCode){
        PreviewDataInfo info = previewDataInfoMapper.findAbandoned(createDate, zoningCode);
        if(info != null){
            Map param = new HashMap();
            param.put("zoningCode", zoningCode);
            param.put("lastUpdate", StringUtil.getTime());
            param.put("validityStup", "");
            param.put("chooseSign", "Y");
            param.put("usefulSign", "Y");
            previewDataInfoMapper.update(param);
        }else {
            throw new RuntimeException("未找到区划：[" + zoningCode +  "]");
        }
    }

    /**
     * 获取本身及下级的变更信息
     * @param originalZoningCode 原区划代码
     * @param targetZoningCode 目标区划代码
     * @param changeType 变更类型
     * @return ChangeInfo列表
     */
    public List<ChangeInfo> getLowChangeInfo(String originalZoningCode,String targetZoningCode,String changeType){
        String originalLevelCode = Common.getLevelCode(originalZoningCode);
        String targetLevelCode = Common.getLevelCode(targetZoningCode);
        return previewDataInfoMapper.findFamilyZoning(targetLevelCode, "*").stream().map(info -> {
            ChangeInfo changeInfo = new ChangeInfo();
            changeInfo.setTargetZoningName(info.getDivisionName());
            changeInfo.setChangeType(changeType);
            changeInfo.setTargetZoningCode(info.getZoningCode());
            changeInfo.setOriginalZoningCode(originalZoningCode);
            return changeInfo;
        }).collect(Collectors.toList());
    }

    /**
     * 提交申请单
     * @param  seq 申请单序号
     */
    @Override
    public void submitZCCRequest(Integer seq) {
        Optional<ZCCRequest> req = Optional.of(zccRequestMapper.get(seq));
        req.ifPresent(e -> {
            String status = e.getStatus();
            if(status.equals(Common.XZQH_SQDZT_WTJ) || status.equals(Common.XZQH_SQDZT_SHBTG)){
                //修改申请单状态为已经提交
                zccRequestMapper.update(ImmutableMap.of("status", Common.XZQH_SQDZT_YTJ, "seq", seq));
            }else {
                throw new RuntimeException("只能提交状态为未提交的或者审核不通过的申请单，当前申请的状态是[" + status + "]！");
            }
        });
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

    /**
     * 审批申请单
     * @param seqStr 若干申请单序号
     * @param status 需要修改成的目标状态
     * @param msg 审批意见
     * @param expectedStatus 期望的状态
     */
    private void checkZCCRequest(String seqStr, String status, String msg, String expectedStatus){
        for(Object seq: seqStr.split(",")){
            Optional<ZCCRequest> req = Optional.of(zccRequestMapper.get(seq));
            req.ifPresent(e -> {
                if(e.getStatus().equals(expectedStatus)){
                    zccRequestMapper.update(ImmutableMap.of("seq", e.getSeq(), "status", status, "approvalOpinion", msg));
                }else {
                    throw new RuntimeException("申请单状态为[" + e.getStatus() + "]，请传入状态为[" + expectedStatus + "]的申请单序号！");
                }
            });
        }
    }

    /**
     * 省级审核变更申请单
     * @param seqStr 以逗号分隔的若干申请单序号
     * @param isPassed 是否通过审核
     * @param msg 审核意见
     */
    @Override
    public void provincialCheck(String seqStr, boolean isPassed, String msg) {
        String status = isPassed ? Common.XZQH_SQDZT_SHTG : Common.XZQH_SQDZT_SHBTG;
        checkZCCRequest(seqStr, status, msg, Common.XZQH_SQDZT_YTJ);
    }

    /**
     * 省级确认变更申请单
     * @param seqStr 以逗号分隔的若干申请单序号
     * @param isPassed 是否审批通过
     * @param msg 审批意见
     */
    @Override
    public void provincialConfirm(String seqStr, boolean isPassed, String msg) {
        String status = isPassed ? Common.XZQH_SQDZT_YQR : Common.XZQH_SQDZT_SHBTG;
        checkZCCRequest(seqStr, status, msg, Common.XZQH_SQDZT_SHTG);
    }


    /**
     * 国家审核变更申请单
     * @param seqStr 以逗号分隔的若干申请单序号
     * @param isPassed 是否通过审核
     * @param msg 审批意见
     */
    @Override
    public void nationalCheck(String seqStr, boolean isPassed, String msg) {
        String status = isPassed ? Common.XZQH_SQDZT_GJYSH : Common.XZQH_SQDZT_SHBTG;
        checkZCCRequest(seqStr, status, msg, Common.XZQH_SQDZT_YQR);
    }

    /**
     * 国家用户查看未提交的申请单
     * @return list
     */
    @Override
    public QueryResp<ZCCRequest> checkNotSubmitReq(String levelCode, Integer pageIndex, Integer pageSize, Integer total) {
        /*QueryResp<ZCCRequest> resp = new QueryResp<>(pageIndex, pageSize);

        *//*
        未提交的申请单状态：未提交的、审批不通过的
         *//*
        resp.query(() -> zccRequestMapper.pageSeekByStatusesAndLevelCode(levelCode, resp.getOffset(), pageSize, Common.XZQH_SQDZT_SHBTG, Common.XZQH_SQDZT_WTJ));

        if(total == 0){
            resp.count(() -> zccRequestMapper.countByStatuses(Common.XZQH_SQDZT_SHBTG + "," +  Common.XZQH_SQDZT_WTJ));
        }else {
            resp.setTotalRecord(total);
        }*/
        QueryReq req = new QueryReq("seq,levelCode,zoningName"){{
            addFilter("status", Common.XZQH_SQDZT_SHBTG + "," +  Common.XZQH_SQDZT_WTJ, QueryFilter.OPR_IN);
        }};

        QueryResp<ZCCRequest> queryResp = QueryResp.buildQueryResp(pageIndex, pageSize, total, req, zccRequestMapper);
        return queryResp;
    }

    /**
     *  导出变更申请单的变更对照明细数据
     * @param seq 申请单序号
     * @param response  响应
     */
    @Override
    public void exportDetailsOfReq(Integer seq, HttpServletResponse response) {
        Workbook workbook = getHSSFWorkbookOfReq(seq);
        ZCCRequest zccRequest = zccRequestMapper.get(seq);
        try {
            setResponseHeader(response, zccRequest.getName() + ".xls");
            OutputStream os = response.getOutputStream();
            workbook.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 发送响应流方法
     * @param response 响应对象
     * @param fileName 文件命名
     */
    private void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * 获取excel对象
     *
     * @param seq 申请单序号
     * @return Workbook
     */
    public Workbook getHSSFWorkbookOfReq(Integer seq) {
        Workbook wb = new HSSFWorkbook();
        //标题 样式
        CellStyle titleStyle = wb.createCellStyle();
        titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBorderLeft(BorderStyle.THIN);
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setBorderTop(BorderStyle.THIN);
        Font titleFont = wb.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);

        //固定内容
        CellStyle leftStyle = wb.createCellStyle();
        leftStyle.setBorderBottom(BorderStyle.THIN);
        leftStyle.setBorderLeft(BorderStyle.THIN);
        leftStyle.setBorderRight(BorderStyle.THIN);
        leftStyle.setBorderTop(BorderStyle.THIN);
        leftStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font hardFont = wb.createFont();
        hardFont.setFontHeightInPoints((short) 11);
        leftStyle.setFont(hardFont);

        CellStyle centerStyle = wb.createCellStyle();
        centerStyle.setBorderBottom(BorderStyle.THIN);
        centerStyle.setBorderLeft(BorderStyle.THIN);
        centerStyle.setBorderRight(BorderStyle.THIN);
        centerStyle.setBorderTop(BorderStyle.THIN);
        centerStyle.setFont(hardFont);
        centerStyle.setAlignment(HorizontalAlignment.CENTER);

        //数据样式
        CellStyle dataStyle = wb.createCellStyle();
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        Font dataFont = wb.createFont();
        dataFont.setItalic(true);
        dataFont.setFontHeightInPoints((short) 11);
        dataFont.setFontName("Arial");
        dataStyle.setFont(dataFont);
        dataStyle.setAlignment(HorizontalAlignment.CENTER);

        Sheet sheet = wb.createSheet("变更对照表详细信息");
        int width = 18 * 256;
        for (int i = 0; i < 7; i++) {
            sheet.setColumnWidth(i, width);
        }

        //1 设置标题
        Row titleRow = sheet.createRow(1);
        createCell(titleRow, 1, titleStyle).setCellValue("人口计生系统区划代码单位变更明细");

        //创建空白的单元格
        for (int i = 2; i <= 6; i++) {
            createCell(titleRow, i, titleStyle);
        }

        //合并标题单元格
        sheet.addMergedRegion(new CellRangeAddress(
                1, //first row (0-based)
                1, //last row  (0-based)
                1, //first column (0-based)
                6  //last column  (0-based)
        ));

        //2 签字栏
        Row signatureRow = sheet.createRow(2);
        signatureRow.setHeightInPoints((short) 18);

        //2.1 领导签名
        createCell(signatureRow, 1, leftStyle).setCellValue("领导人签字：");

        //创建空白的单元格
        createCell(signatureRow, 2, leftStyle);
        createCell(signatureRow, 3, leftStyle);
        sheet.addMergedRegion(new CellRangeAddress(
                2, //first row (0-based)
                2, //last row  (0-based)
                2, //first column (0-based)
                3  //last column  (0-based)
        ));

        //日期栏
        createCell(signatureRow, 4, centerStyle).setCellValue("          年           月           日         ");

        //创建空白的单元格
        for (int i = 5; i <= 6; i++) {
            createCell(signatureRow, i, centerStyle);
        }
        sheet.addMergedRegion(new CellRangeAddress(
                2, //first row (0-based)
                2, //last row  (0-based)
                4, //first column (0-based)
                6  //last column  (0-based)
        ));

        //从第4行开始是迭代内容
        int rowIndex = 3;

        //遍历变更对照组
        List<ZCCGroup> groups = zccGroupMapper.findByRequestSeq(seq);

        for(ZCCGroup group: groups){

            //调整内容
            Row accountRow = sheet.createRow(rowIndex);
            createCell(accountRow, 1, leftStyle).setCellValue("调整说明");

            createCell(accountRow, 2, dataStyle).setCellValue("说明说明");
            //创建空白的单元格
            for (int k = 3; k <= 6; k++) {
                createCell(accountRow, k, dataStyle);
            }
            sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 2, 6));

            //表头
            int headRowIndex = rowIndex + 1;
            Row headRow = sheet.createRow(headRowIndex);
            createCell(headRow, 1, centerStyle).setCellValue("原区划代码及名称");

            //创建空白的单元格
            createCell(headRow, 2, centerStyle);
            sheet.addMergedRegion(new CellRangeAddress(headRowIndex, headRowIndex, 1, 2));

            createCell(headRow, 3, centerStyle).setCellValue("调整类型");

            createCell(headRow, 4, centerStyle).setCellValue("现区划代码及名称");
            createCell(headRow, 5, centerStyle);
            sheet.addMergedRegion(new CellRangeAddress(headRowIndex, headRowIndex, 4, 5));

            createCell(headRow, 6, centerStyle).setCellValue("备注");


            //遍历变更组中的明细对照数据
            List<ZCCDetail> details = zccDetailMapper.findByGroupSeq(group.getSeq());
            int loopStartIndex = rowIndex + 2;
            for (ZCCDetail detail: details) {

                //区划变更内容
                Row dataRow = sheet.createRow(loopStartIndex);

                //原区划代码
                createCell(dataRow, 1, dataStyle).setCellValue(detail.getOriginalZoningCode());

                //原区划名称
                createCell(dataRow, 2, dataStyle).setCellValue(detail.getOriginalZoningName());

                //变更类型，文字
                createCell(dataRow, 3, dataStyle).setCellValue(detail.displayChangeType());

                //目标区划代码
                createCell(dataRow, 4, dataStyle).setCellValue(detail.getCurrentZoningCode());

                //目标区划名称
                createCell(dataRow, 5, dataStyle).setCellValue(detail.getCurrentZoningName());

                //备注
                createCell(dataRow, 6, dataStyle).setCellValue(detail.getNotes());
                loopStartIndex = loopStartIndex + 1;
            }
            //行数增加，数量为变更组中变更明细对照数据的数量
            rowIndex = loopStartIndex;
        }
        return wb;
    }


    private Cell createCell(Row row, int col, CellStyle cellStyle){
        Cell cell = row.createCell(col);
        cell.setCellStyle(cellStyle);
        return cell;
    }
}
