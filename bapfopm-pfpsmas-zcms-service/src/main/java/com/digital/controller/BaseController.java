package com.digital.controller;

import com.digital.util.resultData.Constants;
import com.digital.util.resultData.RtnData;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhanghpj
 * @Version 1.0, 11:28 2018/3/1
 * @See
 * @Since
 * @Deprecated
 */
@Controller
public class BaseController {
	protected static final org.slf4j.Logger log = LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String handleException(RuntimeException e){
        e.printStackTrace();
        return new RtnData(Constants.RTN_CODE_ERROR,e.getMessage()).toString();
    }
}
