package com.digital.controller

import com.digital.dao.FormalTableMapper
import com.digital.dao.PreviewDataInfoMapper
import com.digital.dao.sqlMapper.EntitySql
import com.digital.dao.sqlMapper.PreviewDataInfoSql
import com.digital.entity.FormalTableInfo
import com.digital.entity.PreviewDataInfo
import com.digital.util.search.QueryFilter
import com.digital.util.search.QueryReq
import com.digital.util.search.QueryResp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/test")
class TestController {
    @Autowired
    FormalTableMapper formalTableMapper

    @Autowired
    PreviewDataInfoMapper previewDataInfoMapper


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



}
