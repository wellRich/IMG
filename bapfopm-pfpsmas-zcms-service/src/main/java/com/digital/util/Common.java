package com.digital.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO 应用常量
 * @Author: zhanghpj
 * @Version 1.0, 17:51 2018/3/12
 * @See
 * @Since
 * @Deprecated
 */
public class Common {

    /***********变更类型*******************/
    public static final String ADD = "11";
    public static final String CHANGE = "21";
    public static final String MERGE = "31";
    public static final String MOVE = "41";

    /**********文件校验列明信息************/
    public static final String QHDM_TITLE = "区划名称,区划代码";

    public static final String QHDM_BGDZB_TITLE = "调整编号,调整说明,原区划名称,原区划代码,调整类型,现区划名称,现区划代码";


    /***********申请单状态*******************/
    public static final String XZQH_SQDZT_WTJ = "10";//未提交
    public static final String XZQH_SQDZT_YTJ = "11";//已经提交
    public static final String XZQH_SQDZT_SHTG = "20";//审核通过
    public static final String XZQH_SQDZT_SHBTG = "21";//审核不通过
    public static final String XZQH_SQDZT_YQR = "30";//已经确认
    public static final String XZQH_SQDZT_GJYSH = "40";//国家已经审核
    public static final String XZQH_SQDZT_YFB = "50";//已经发布
    public static final String XZQH_SQDZT_SJYQY = "60";//数据已经迁移
    public static final String XZQH_SQDZT_SJCLZ = "61";//数据迁移出现错误

    /***********集中变更状态*******************/
    public static final String XZQH_JZBGZT_YSC = "10";
    public static final String XZQH_JZBGZT_DRLSBCG = "20";
    public static final String XZQH_JZBGZT_DRLSBSB = "21";
    public static final String XZQH_JZBGZT_DRLSBCLZ = "22";
    public static final String XZQH_JZBGZT_LJJYCG = "30";
    public static final String XZQH_JZBGZT_LJJYSB = "31";
    public static final String XZQH_JZBGZT_LJJYCLZ = "32";
    public static final String XZQH_JZBGZT_SQDSQCG = "40";
    public static final String XZQH_JZBGZT_SQDSQSB = "41";
    public static final String XZQH_JZBGZT_SQDSQCLZ = "42";

    /***********明细表处理状态*******************/
    public static final String XZQH_CLZT_DCL = "10";//等待处理
    public static final String XZQH_CLZT_CLZ = "20";//处理中
    public static final String XZQH_CLZT_CLCG = "30";//处理成功
    public static final String XZQH_CLZT_CLSB = "31";//处理失败

    public static final String ENTITY_PACKAGE = "com.digital.entity";


    public static final String NATION_ZONING_CODE = "000000000000000";

    //级次代码
    /*国家级*/
    public static final Integer NATION_LEVEL = 0;

    /*省级*/
    public static final Integer PROVINCE_LEVEL = 1;

    /*市级*/
    public static final Integer CITY_LEVEL = 2;

    /*县级*/
    public static final Integer COUNTY_LEVEL = 3;

    /*乡镇级*/
    public static final Integer TOWN_LEVEL = 4;

    /*村级*/
    public static final Integer VILLAGE_LEVEL = 5;

    /*组级*/
    public static final Integer GROUP_LEVEL = 6;

    /**
     * @return java.lang.String：上级行政区划代码
     * @description 获取该行政区划的上级行政区划
     * @method getSuperiorZoningCode
     * @params [xzqh_dm：行政区划代码]
     */
    public static String getSuperiorZoningCode(String xzqh_dm) {

        if (xzqh_dm == null || xzqh_dm.equals("") || xzqh_dm.length() != 15) {
            return "";
        }
        String sjxzqhdm = "";
        if (xzqh_dm.substring(0, 2).equals("00")) {
            return "";
        } else if (xzqh_dm.substring(2, 4).equals("00")) {
            sjxzqhdm = "000000000000000";
        } else if (xzqh_dm.substring(4, 6).equals("00")) {
            sjxzqhdm = xzqh_dm.substring(0, 2) + "0000000000000";
        } else if (xzqh_dm.substring(6, 9).equals("000")) {
            sjxzqhdm = xzqh_dm.substring(0, 4) + "00000000000";
        } else if (xzqh_dm.substring(9, 12).equals("000")) {
            sjxzqhdm = xzqh_dm.substring(0, 6) + "000000000";
        } else if (xzqh_dm.substring(12, 15).equals("000")) {
            sjxzqhdm = xzqh_dm.substring(0, 9) + "000000";
        } else {
            sjxzqhdm = xzqh_dm.substring(0, 12) + "000";
        }
        return sjxzqhdm;
    }

    /**
     * 获取上次区划的级别代码
     *
     * @param zoningCode
     * @return String 上次区划的级别代码
     */
    public static String getSuperAssignCode(String zoningCode) {
        return removeZeroOfTailing(getSuperiorZoningCode(zoningCode));
    }

    /**
     * @return java.lang.String
     * @description 根据行政区划代码获取相应级次代码
     * @method getAssigningCode
     * @params zoningCode 区划代码
     */
    public static String getAssigningCode(String zoningCode) {

        if (zoningCode == null || zoningCode.equals("") || zoningCode.length() != 15) {
            return "";
        }
        String assigningCode = "";
        String levelCode = getLevelCode(zoningCode);
        int size = levelCode.length();
        switch (size) {
            case 0:
                assigningCode = NATION_LEVEL.toString();
                break;
            case 2:
                assigningCode = PROVINCE_LEVEL.toString();
                break;
            case 4:
                assigningCode = CITY_LEVEL.toString();
                break;
            case 6:
                assigningCode = COUNTY_LEVEL.toString();
                break;
            case 9:
                assigningCode = TOWN_LEVEL.toString();
                break;
            case 12:
                assigningCode = VILLAGE_LEVEL.toString();
                break;
            case 15:
                assigningCode = GROUP_LEVEL.toString();
                break;
            default:
                assigningCode = "";
                break;

        }
        return assigningCode;
    }

    /**
     * 获取本级区划
     * @param zoningCode 区划代码
     * @return String
     */
    public String getOwnZoningCode (String zoningCode){
        String assigningCode = getAssigningCode(zoningCode);
        if(assigningCode.equals("")){
            return "";
        }else if(assigningCode.equals("1")){
            return zoningCode.substring(0, 2);
        }else if(assigningCode.equals("2")){
            return zoningCode.substring(2, 4);
        }else if(assigningCode.equals("3")){
            return zoningCode.substring(4, 6);
        }else if(assigningCode.equals("4")){
            return zoningCode.substring(6, 9);
        }else if(assigningCode.equals("5")){
            return zoningCode.substring(9, 12);
        }else if(assigningCode.equals("6")){
            return zoningCode.substring(12);
        }else {
            return "";
        }
    }

    /**
     * 检测原区划代码是否与现区划代码相同
     *
     * @param originZoningCode  原区划代码
     * @param currentZoningCode 现区划代码
     * @return
     */
    public static boolean hasSameZoningCode(String originZoningCode, String currentZoningCode) {
        boolean flag = false;
        if (!originZoningCode.equals("") && !currentZoningCode.equals("")) {
            if (originZoningCode.equals(currentZoningCode)) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 获取区划代码中的级别代码
     *
     * @param zoningCode 区划代码
     * @return 级别代码
     */
    public static String getLevelCode(String zoningCode) {
        if (zoningCode == null || zoningCode.equals("") || zoningCode.length() != 15) {
            return "";
        }
        return removeZeroOfTailing(zoningCode);
    }


    /**
     * 获取级别代码
     * 去除区划字符串尾部的0
     *
     * @param zoningCode 完整的区划代码
     * @return
     */
    public static String removeZeroOfTailing(String zoningCode) {
        return zoningCode.replaceAll("000000000000000|0000000000000|00000000000|000000000|000000|000$", "");
    }

    /**
     * 给区划代码补齐0
     *
     * @param code 位数不足15位的区划代码
     * @return
     */
    public static String addZeroAtTail(String code) {
        if (code == null || code.equals("")) {
            throw new RuntimeException("传入的区划代码为空！");
        } else {
            int num = 15 - code.length();
            StringBuilder vessel = new StringBuilder(code);
            for (int i = 0; i < num; i++) {
                vessel.append("0");
            }
            return vessel.toString();
        }
    }

    /**
     * 用于取得上级与上上级的区划代码，加上自身称之为父系区划代码
     *
     * @param zoningCode 区划代码
     * @param level      级次
     * @return map
     */
    public static Map<Integer, String> getPaternalCodeMap(String zoningCode, int level) {
        if (zoningCode == null || zoningCode.equals("")) {
            throw new RuntimeException("传入的区划代码为空！");
        } else {
            int length = zoningCode.length();
            if (length != 15) {
                throw new RuntimeException("长度为" + length + "不合规范的区划代码");
            } else {
                Map<Integer, String> zoningCodeAndLevel = new HashMap<>();
                switch (level) {
                    case 0:
                        zoningCodeAndLevel.put(0, "000000000000000");
                        break;
                    //省级
                    case 1:
                        zoningCodeAndLevel.put(PROVINCE_LEVEL, addZeroAtTail(zoningCode));
                        break;
                    //市级
                    case 2:
                        zoningCodeAndLevel.put(PROVINCE_LEVEL, addZeroAtTail(zoningCode.substring(0, 3)));
                        zoningCodeAndLevel.put(CITY_LEVEL, addZeroAtTail(zoningCode));
                        break;
                    //县级
                    case 3:
                        zoningCodeAndLevel.put(PROVINCE_LEVEL, addZeroAtTail(zoningCode.substring(0, 3)));
                        zoningCodeAndLevel.put(CITY_LEVEL, addZeroAtTail(zoningCode.substring(0, 5)));
                        zoningCodeAndLevel.put(COUNTY_LEVEL, addZeroAtTail(zoningCode));
                        break;
                    //乡镇级
                    case 4:
                        zoningCodeAndLevel.put(PROVINCE_LEVEL, addZeroAtTail(zoningCode.substring(0, 3)));
                        zoningCodeAndLevel.put(CITY_LEVEL, addZeroAtTail(zoningCode.substring(0, 5)));
                        zoningCodeAndLevel.put(COUNTY_LEVEL, addZeroAtTail(zoningCode.substring(0, 7)));
                        zoningCodeAndLevel.put(TOWN_LEVEL, addZeroAtTail(zoningCode));
                        break;
                    //村级
                    case 5:
                        zoningCodeAndLevel.put(PROVINCE_LEVEL, addZeroAtTail(zoningCode.substring(0, 3)));
                        zoningCodeAndLevel.put(CITY_LEVEL, addZeroAtTail(zoningCode.substring(0, 5)));
                        zoningCodeAndLevel.put(COUNTY_LEVEL, addZeroAtTail(zoningCode.substring(0, 7)));
                        zoningCodeAndLevel.put(TOWN_LEVEL, addZeroAtTail(zoningCode.substring(0, 10)));
                        zoningCodeAndLevel.put(VILLAGE_LEVEL, addZeroAtTail(zoningCode));
                        break;

                    //组级
                    case 6:
                        zoningCodeAndLevel.put(PROVINCE_LEVEL, addZeroAtTail(zoningCode.substring(0, 3)));
                        zoningCodeAndLevel.put(CITY_LEVEL, addZeroAtTail(zoningCode.substring(0, 5)));
                        zoningCodeAndLevel.put(COUNTY_LEVEL, addZeroAtTail(zoningCode.substring(0, 7)));
                        zoningCodeAndLevel.put(TOWN_LEVEL, addZeroAtTail(zoningCode.substring(0, 10)));
                        zoningCodeAndLevel.put(VILLAGE_LEVEL, addZeroAtTail(zoningCode.substring(0, 13)));
                        zoningCodeAndLevel.put(VILLAGE_LEVEL, addZeroAtTail(zoningCode));
                        break;
                    default:
                        throw new RuntimeException("级次为" + level + "是不合规范的级次代码");
                }
                return zoningCodeAndLevel;
            }
        }
    }

    /**
     * 用于取得上级与上上级的区划代码，加上自身称之为父系区划代码
     *
     * @param zoningCode 区划代码
     * @return map
     */
    public static Map<Integer, String> getPaternalCodeMap(String zoningCode) {
        if (zoningCode == null || zoningCode.equals("")) {
            throw new RuntimeException("传入的区划代码为空！");
        } else {
            String code = removeZeroOfTailing(zoningCode);
            int length = code.length();
            Map<Integer, String> zoningCodeAndLevel = new HashMap<>();
            switch (length) {
                case 0:
                    zoningCodeAndLevel.put(0, Common.NATION_ZONING_CODE);
                    break;
                //省级
                case 2:
                    zoningCodeAndLevel.put(PROVINCE_LEVEL, addZeroAtTail(zoningCode));
                    break;
                //市级
                case 4:
                    zoningCodeAndLevel.put(PROVINCE_LEVEL, addZeroAtTail(zoningCode.substring(0, 3)));
                    zoningCodeAndLevel.put(CITY_LEVEL, addZeroAtTail(zoningCode));
                    break;
                //县级
                case 6:
                    zoningCodeAndLevel.put(PROVINCE_LEVEL, addZeroAtTail(zoningCode.substring(0, 3)));
                    zoningCodeAndLevel.put(CITY_LEVEL, addZeroAtTail(zoningCode.substring(0, 5)));
                    zoningCodeAndLevel.put(COUNTY_LEVEL, addZeroAtTail(zoningCode));
                    break;
                //乡镇级
                case 9:
                    zoningCodeAndLevel.put(PROVINCE_LEVEL, addZeroAtTail(zoningCode.substring(0, 3)));
                    zoningCodeAndLevel.put(CITY_LEVEL, addZeroAtTail(zoningCode.substring(0, 5)));
                    zoningCodeAndLevel.put(COUNTY_LEVEL, addZeroAtTail(zoningCode.substring(0, 7)));
                    zoningCodeAndLevel.put(TOWN_LEVEL, addZeroAtTail(zoningCode));
                    break;
                //村级
                case 12:
                    zoningCodeAndLevel.put(PROVINCE_LEVEL, addZeroAtTail(zoningCode.substring(0, 3)));
                    zoningCodeAndLevel.put(CITY_LEVEL, addZeroAtTail(zoningCode.substring(0, 5)));
                    zoningCodeAndLevel.put(COUNTY_LEVEL, addZeroAtTail(zoningCode.substring(0, 7)));
                    zoningCodeAndLevel.put(TOWN_LEVEL, addZeroAtTail(zoningCode.substring(0, 10)));
                    zoningCodeAndLevel.put(VILLAGE_LEVEL, addZeroAtTail(zoningCode));
                    break;

                //村级
                case 15:
                    zoningCodeAndLevel.put(PROVINCE_LEVEL, addZeroAtTail(zoningCode.substring(0, 3)));
                    zoningCodeAndLevel.put(CITY_LEVEL, addZeroAtTail(zoningCode.substring(0, 5)));
                    zoningCodeAndLevel.put(COUNTY_LEVEL, addZeroAtTail(zoningCode.substring(0, 7)));
                    zoningCodeAndLevel.put(TOWN_LEVEL, addZeroAtTail(zoningCode.substring(0, 10)));
                    zoningCodeAndLevel.put(VILLAGE_LEVEL, addZeroAtTail(zoningCode.substring(0, 13)));
                    zoningCodeAndLevel.put(VILLAGE_LEVEL, addZeroAtTail(zoningCode));
                    break;
                default:
                    throw new RuntimeException("长度为" + zoningCode.length() + "不合规范的区划代码");
            }
            return zoningCodeAndLevel;
        }
    }

    //用于取得上级与上上级的区划代码，加上自身称之为祖系区划代码
    public static List<String> getPaternalCodes(String zoningCode) {
        if (zoningCode == null || zoningCode.equals("")) {
            throw new RuntimeException("传入的区划代码为空！");
        } else {
            String code = removeZeroOfTailing(zoningCode);
            int length = code.length();
            List<String> zoningCodes = new ArrayList<>();
            System.out.println("code-----------> " + code);
            System.out.println("length-----------> " + length);
            switch (length) {
                case 0:
                    zoningCodes.add(Common.NATION_ZONING_CODE);
                    break;
                //省级
                case 2:
                    zoningCodes.add(zoningCode);
                    break;
                //市级
                case 4:
                    zoningCodes.add(addZeroAtTail(zoningCode.substring(0, 2)));
                    zoningCodes.add(addZeroAtTail(zoningCode.substring(0, 3)));
                    zoningCodes.add(addZeroAtTail(zoningCode));
                    break;
                //县级
                case 6:
                    zoningCodes.add(addZeroAtTail(zoningCode.substring(0, 2)));
                    zoningCodes.add(addZeroAtTail(zoningCode.substring(0, 3)));
                    zoningCodes.add(addZeroAtTail(zoningCode.substring(0, 5)));
                    zoningCodes.add(addZeroAtTail(zoningCode));
                    break;
                //乡镇级
                case 9:
                    zoningCodes.add(addZeroAtTail(zoningCode.substring(0, 2)));
                    zoningCodes.add(addZeroAtTail(zoningCode.substring(0, 3)));
                    zoningCodes.add(addZeroAtTail(zoningCode.substring(0, 5)));
                    zoningCodes.add(addZeroAtTail(zoningCode.substring(0, 7)));
                    zoningCodes.add(addZeroAtTail(zoningCode));
                    break;
                //村级
                case 12:
                    zoningCodes.add(addZeroAtTail(zoningCode.substring(0, 2)));
                    zoningCodes.add(addZeroAtTail(zoningCode.substring(0, 3)));
                    zoningCodes.add(addZeroAtTail(zoningCode.substring(0, 5)));
                    zoningCodes.add(addZeroAtTail(zoningCode.substring(0, 7)));
                    zoningCodes.add(addZeroAtTail(zoningCode.substring(0, 10)));
                    zoningCodes.add(addZeroAtTail(zoningCode));
                    break;

                //组级
                case 15:
                    zoningCodes.add(addZeroAtTail(zoningCode.substring(0, 2)));
                    zoningCodes.add(addZeroAtTail(zoningCode.substring(0, 3)));
                    zoningCodes.add(addZeroAtTail(zoningCode.substring(0, 5)));
                    zoningCodes.add(addZeroAtTail(zoningCode.substring(0, 7)));
                    zoningCodes.add(addZeroAtTail(zoningCode.substring(0, 10)));
                    zoningCodes.add(addZeroAtTail(zoningCode.substring(0, 13)));
                    zoningCodes.add(addZeroAtTail(zoningCode));
                    break;
                default:
                    throw new RuntimeException("长度为" + zoningCode.length() + "不合规范的区划代码");
            }
            return zoningCodes;
        }
    }
}
