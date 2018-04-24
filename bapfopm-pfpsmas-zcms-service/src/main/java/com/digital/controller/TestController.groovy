package com.digital.controller

import com.digital.dao.FormalTableMapper
import com.digital.dao.PreviewDataInfoMapper
import com.digital.service.ZoningInfoQueryApiImpl
import com.digital.util.JSONHelper
import com.digital.util.search.QueryFilter
import com.digital.util.search.QueryReq
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/test")
class TestController {
    @Autowired
    FormalTableMapper formalTableMapper

    @Autowired
    PreviewDataInfoMapper previewDataInfoMapper


    @Autowired
    private ZoningInfoQueryApiImpl zoningInfoQueryApi;

    @RequestMapping("/seek")
    @ResponseBody
    def testSeek(){
        QueryReq req = new QueryReq( "uniqueKey", "uniqueKey,zoningCode,divisionName")
        req.addFilter(new QueryFilter("zoningCode", "370102%", QueryFilter.OPR_LIKE))
        formalTableMapper.seek(req)
        return formalTableMapper.seek(req)
    }


    @RequestMapping("/testAncestralAndSub")
    @ResponseBody
    def testAncestralAndSub(){
       return previewDataInfoMapper.findAncestorsAndSubsByZoningCode("371481001400401");
    }

    @RequestMapping("/testPageHelper")
    @ResponseBody
    def testPageHelper(@RequestParam(value = "groupSeq", defaultValue = "1")String groupSeq, @RequestParam(value = "total", defaultValue = "0") int total
                       , @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex
                       , @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        JSONHelper.toJSON(zoningInfoQueryApi.pageTest(groupSeq, pageIndex, pageSize, total))
    }

}
