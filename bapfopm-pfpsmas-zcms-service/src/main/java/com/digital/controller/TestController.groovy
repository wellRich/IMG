package com.digital.controller

import com.digital.api.ZoningCodeChangeApi
import com.digital.dao.PreviewDataInfoMapper
import com.digital.entity.PreviewDataInfo
import com.digital.entity.ZCCGroup
import com.digital.util.JSONHelper
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
}
