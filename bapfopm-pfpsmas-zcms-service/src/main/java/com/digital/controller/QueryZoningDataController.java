package com.digital.controller;

import com.digital.api.ZoningCodeChangeApi;
import com.digital.api.ZoningInfoQueryApi;
import com.digital.util.Common;
import com.digital.util.resultData.Constants;
import com.digital.util.resultData.RtnData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 区划数据查询
 *
 * @author guoyka
 * @version 2018/4/10
 */
@Controller
@RequestMapping("/queryZoningData")
public class QueryZoningDataController extends BaseController {

	@Autowired
	ZoningInfoQueryApi zoningInfoQueryApi;

	@Autowired
	ZoningCodeChangeApi zoningCodeChangeApi;

	/**
	 * 初始化区划预览数据的预览界面
	 * @return json
	 */
	@RequestMapping(value = "/initPreviewZoningData", method = RequestMethod.GET)
	@ResponseBody
	public Object initPreviewZoningData(@RequestParam(value = "zoningCode", defaultValue = Common.NATION_ZONING_CODE)String zoningCode){
		try{
			return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS, zoningCodeChangeApi.findPreviewByZoningCode(zoningCode)).toString();
		}catch (Exception e) {
			log.error("checkFormalZoning.error--> " + e.getMessage());
			return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
		}
	}


	/**
	 * 初始化区划正式数据的预览界面
	 * @return json
	 */
	@RequestMapping(value = "/initFormalZoningData", method = RequestMethod.GET)
	@ResponseBody
	public Object initFormalZoningData(@RequestParam(value = "zoningCode", defaultValue = Common.NATION_ZONING_CODE)String zoningCode){
		try{
			return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS, zoningInfoQueryApi.findAncestorsAndSubsByZoningCode(zoningCode)).toString();
		}catch (Exception e) {
			log.error("checkFormalZoning.error--> " + e.getMessage());
			return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
		}
	}

	/**
	 * 取得子级区划正式数据
	 * @param zoningCode 区划代码
	 * @return json
	 */
	@RequestMapping(value="/checkFormalZoning", method= RequestMethod.GET)
	@ResponseBody
	public Object checkFormalZoning(@RequestParam(value = "zoningCode")String zoningCode){
		try{
			return new RtnData(Constants.RTN_CODE_SUCCESS, Constants.RTN_MESSAGE_SUCCESS, zoningInfoQueryApi.findSubordinateZoning(zoningCode)).toString();
		}catch (Exception e) {
			log.error("checkFormalZoning.error--> " + e.getMessage());
			return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR).toString();
		}
	}


}
