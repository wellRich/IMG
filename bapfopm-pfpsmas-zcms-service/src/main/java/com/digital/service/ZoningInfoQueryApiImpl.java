package com.digital.service;

import com.digital.api.ZoningInfoQueryApi;
import com.digital.dao.FormalTableMapper;
import com.digital.entity.FormalTableInfo;
import com.digital.util.Common;
import com.google.common.collect.ImmutableMap;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author guoyka
 * @version 1.0, 2018/4/13
 */
@Service
@Transactional
public class ZoningInfoQueryApiImpl implements ZoningInfoQueryApi{

    protected static final org.slf4j.Logger log = LoggerFactory.getLogger(ZoningInfoQueryApiImpl.class);

    @Autowired
    FormalTableMapper formalTableMapper;

    @Override
    public Map<String, List<FormalTableInfo>> findSubordinateZoning(String zoningCode) {

        //获取级次代码
        String assigningCode = Common.getAssigningCode(zoningCode);

        List<FormalTableInfo> formalTableInfos = formalTableMapper.findSubordinateZoning(zoningCode);

        return ImmutableMap.of(assigningCode, formalTableInfos);
    }
}
