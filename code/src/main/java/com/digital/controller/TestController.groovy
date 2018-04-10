package com.digital.controller

import com.digital.api.ZoningCodeChangeApi
import com.digital.dao.HistoricalZoningChangeMapper
import com.digital.dao.PreviewDataInfoMapper
import com.digital.dao.ZCCGroupMapper
import com.digital.dao.ZCCRequestMapper
import com.digital.entity.HistoricalZoningChange
import com.digital.entity.PreviewDataInfo
import com.digital.entity.ZCCGroup
import com.digital.entity.ZCCRequest
import com.digital.util.JSONHelper
import com.digital.util.StringUtil
import org.apache.ibatis.annotations.Param
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author guoyka
 * @version 2018/4/3
 * @see [ 相关类/方法]
 * @since [产品/模块版本]
 */
@Controller
@RequestMapping("/test")
class TestController {
    @Autowired
    PreviewDataInfoMapper previewDataInfoMapper

    @Autowired
    ZoningCodeChangeApi zoningCodeChangeApi;

    @Autowired
    HistoricalZoningChangeMapper historicalZoningChangeMapper;

    @RequestMapping("/testPreviewDataInfoMapper")
    @ResponseBody
    def testPreviewDataInfoMapper() {
        def previewDataInfo = previewDataInfoMapper.findAllByZoningCode('370102006701000');

        Map a = [:]
        a.put("name", "999")
        a.put("requestSeq", 1)
        a.put("creatorCode", "9527" )
        a.put("creatorDeptCode", "1000")
        zoningCodeChangeApi.addZCCGroup(a);
        JSONHelper.toJSON(previewDataInfo)
    }
    @Autowired
    ZCCGroupMapper zccGroupMapper;

    @RequestMapping("/addGroup")
    @ResponseBody
    def addGroup(){
        def s = '{"seq":null,"serialNumber":null,"name":"天竺","creatorCode":"9527","createDate":null,"creatorDeptCode":"1000","requestSeq":1000,"orderNum":null}'
        Class[] classes = [String.class, java.lang.Object.class]
        def group = JSONHelper.toMap(s, classes)
        group.createDate = StringUtil.getTime();
        println("group--------> " + group)
        //ZCCGroup group = JSONHelper.fromJsonObject(s, ZCCGroup.class)
        int key = zoningCodeChangeApi.addZCCGroup(group)
        println "key----------> " + key
        println("group-------------> " + JSONHelper.toJSON(group))
    }

    @Autowired
    ZCCRequestMapper zccRequestMapper;

    @RequestMapping("/updateZCCRequest")
    @ResponseBody
    def updateZCCRequest(@Param("seq")Integer seq, @Param("name")String name, @Param("notes")String notes){
        zoningCodeChangeApi.updateZCCRequest(seq, name, notes)
        ZCCRequest req = zccRequestMapper.get(seq)
        return req;
    }

    @RequestMapping("/insertHistory")
    @ResponseBody
    def insertHistory(){
        HistoricalZoningChange change = new HistoricalZoningChange()
        change.seq = 10;
        change.changeType ='11'
        change.createDate = StringUtil.getTime();
        change.creatorCode = '9527'
        change.groupSeq = 100
        change.originalZoningCode = '1000'
        change.originalZoningName = '238878'
        change.targetZoningCode = '2000'
        change.targetZoningName = '098'
        historicalZoningChangeMapper.insert(change)
    }


    @RequestMapping("/deleteHistory")
    @ResponseBody
    def deleteHistory(){
        historicalZoningChangeMapper.delete(1)
    }
}
