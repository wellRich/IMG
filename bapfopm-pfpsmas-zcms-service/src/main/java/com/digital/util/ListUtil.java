package com.digital.util;

import com.digital.entity.province.ContrastTemporary;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.*;

/**
 * @Author: zhanghpj
 * @Version 1.0, 16:19 2018/2/27
 * @See
 * @Since
 * @Deprecated
 */
public class ListUtil {


    /**
     * @description 提取list集合中对象组编号相同的数据
     * @method  getList
     * @params [temporaryList：总集合, groupNum：组编号]
     * @return java.util.List<X>
     * @exception
     */
    public  static <X> List<X> getList(List<X> temporaryList, Long groupNum){
        List<X> resultList = new ArrayList<>();
        TreeSet<X> set = new TreeSet<>((x,y)->{
            try{
                Long mun = (Long)x.getClass().getMethod("getGroupNum").invoke(x);
                if (groupNum.longValue()==mun.longValue()){
                    resultList.add(x);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return 0;
        });
        set.addAll(temporaryList);
        return resultList;

    }


    /**
     * @description 根据省级区划代码划分出每个省的代码
     * @method  getZoningCodeList
     * @params List<X> 总数据</> 省份的区划代码(前两位数字)
     * @return Map
     * @exception
     */
    public static <X> Map<String,List<X>> getZoningCodeList(List<X> formalList,String provincialCode){
        List<X> formals = new ArrayList<>();
        Map<String,List<X>> map = new HashMap<>();
        TreeSet<X> set = new TreeSet<>((x,y)->{
            boolean check;
            try{
                String zoningCode = (String)x.getClass().getMethod("getZoningCode").invoke(x);
                if (zoningCode.startsWith(provincialCode)){
                    formals.add(x);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return 0;
        });
        set.addAll(formalList);
        //获取县一级的分组数据
        for (X formal : formals) {
            try {
                String zoningCode = (String) formal.getClass().getMethod("getZoningCode").invoke(formal);
                //获取县级区划（6位）
                String reportData = zoningCode.substring(0,6);
                if (!map.containsKey(reportData)){
                    List<X> commons = new ArrayList<>();
                    commons.add(formal);
                    map.put(reportData,commons);
                }else{
                    List<X> xList = map.get(reportData);
                    xList.add(formal);
                    map.put(reportData,xList);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }


        return map;
    }
}
