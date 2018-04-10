package com.digital.util;

import com.digital.entity.province.ContrastTemporary;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

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
        ArrayList<X> resultList = new ArrayList<>();
        TreeSet<X> set = new TreeSet<>((x,y)->{
            boolean check;
            try{
                Long mun = (Long)x.getClass().getMethod("getGroupNum").invoke(x);
                if (groupNum==mun){
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
}
