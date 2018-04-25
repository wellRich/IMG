package com.digital.util;

import com.digital.dao.PreviewDataInfoMapper;
import com.digital.entity.PreviewDataInfo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.rmi.CORBA.Util;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: TODO
 * @Author: zhanghpj
 * @Version 1.0, 10:38 2018/4/13
 * @See
 * @Since
 * @Deprecated
 */
@Component
public class UniqueKeyUtil  {

    @Autowired
    private  PreviewDataInfoMapper previewDataInfoMapper;

    private static UniqueKeyUtil uniqueKeyUtil;
    @PostConstruct
    public void init(){
        uniqueKeyUtil = this;
        uniqueKeyUtil.previewDataInfoMapper = this.previewDataInfoMapper;
    }
    /**
     * @description 获取key 新增时 生成key ，其他类型的变更时 查询获取key
     * @method  getKey
     * @params zoningCode:区划代码  typeCode ： 变更类型
     * @return List<String> 包含key的集合</>
     * @exception
     */
    public static List<String> getKey(String zoningCode,String typeCode){
        String key = null;
        List<String> keyList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String prefix = dateFormat.format(new Date());
        if (typeCode == Common.ADD){
            key = zoningCode + prefix;
            keyList.add(key);
            return keyList;
        }else {
           keyList =uniqueKeyUtil.previewDataInfoMapper.findKeyByZoningCode(zoningCode);
            return keyList;
        }
    }





}
