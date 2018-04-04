package com.digital.util.resultData;

/**
 * @Description: TODO 响应的常量数据
 * @Author: zhanghpj
 * @Version 1.0, 11:45 2018/3/2
 * @See
 * @Since
 * @Deprecated
 */
public class Constants {

    /**
     * 通用成功返回码
     */
    public final static String RTN_CODE_SUCCESS = "000000";

    /**
     * 通用成功信息
     */
    public final static String RTN_MESSAGE_SUCCESS = "本次请求成功";

    /**
     * 通用错误返回码
     */
    public final static String RTN_CODE_ERROR = "999999";

    /**
     * 通用错误信息
     */
    public final static String RTN_MESSAGE_ERROR = "请求发生未知异常";
    /*
     * 上传失败的错误信息
     * */
    public final static String RTN_MESSAGE_UPLOAD = "上传过程中文本校验未通过";
    /*
    * 文件不可被删除
    * */
    public final static String RTN_MESSAGE_DELETE = "文件不可被删除";
    /*
    * 删除文件失败
    * */
    public final static String RTN_DELETE_FAIL = "文件删除失败";
    /*
    * 查询集中变更临时数据为空
    * */
    public final static String RTN_QUERY_ISEMPTY = "该条件下未查到数据";
    /*
    * 文件状态信息修改失败
    * */
    public final static String RTN_UPDATE_FAIL = "文件修改状态失败";
    /*
    * 该文件信息状态不可以导入
    * */
    public final static String RTN_IMPORT_CHEKED = "该文件状态不可以导入临时表";


}
