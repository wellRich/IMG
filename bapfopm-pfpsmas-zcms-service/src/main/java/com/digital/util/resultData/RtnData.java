package com.digital.util.resultData;

import com.digital.util.JSONHelper;

/**
 * @Description: TODO 返回数据
 * @Author: zhanghpj
 * @Version 1.0, 11:47 2018/3/2
 * @See
 * @Since
 * @Deprecated
 */
public class RtnData {


    private String rtnCode = Constants.RTN_CODE_SUCCESS;
    private String rtnMessage = Constants.RTN_MESSAGE_SUCCESS;
    private Object responseData;

    public RtnData() {
    }

    public RtnData(String rtnCode, String rtnMessage) {
        this.rtnCode = rtnCode;
        this.rtnMessage = rtnMessage;
    }

    public RtnData(String rtnCode, String rtnMessage, Object responseData) {
        this.rtnCode = rtnCode;
        this.rtnMessage = rtnMessage;
        this.responseData = responseData;
    }

    public RtnData(Object responseData) {
        this.responseData = responseData;
    }

    @Override
    public String toString() {
        return JSONHelper.toJSON( this);
    }



}
