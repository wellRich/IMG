package com.digital.controller

import com.digital.dao.FormalTableMapper
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
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/test")
class TestController {
    @Autowired
    FormalTableMapper formalTableMapper

    @RequestMapping("/seek")
    @ResponseBody
    def testSeek(){
        EntitySql sql = new PreviewDataInfoSql<PreviewDataInfo>()
        QueryReq req = new QueryReq( "uniqueKey", "uniqueKey,zoningCode,divisionName")
        req.addFilter(new QueryFilter("zoningCode", "370102%", QueryFilter.OPR_LIKE))
        formalTableMapper.findAll();
        return formalTableMapper.seek(req)
    }
}
