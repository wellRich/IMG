package com.digital.controller;

import com.digital.api.ImportFormalTableApi;
import com.digital.api.ImportTemporaryApi;
import com.digital.api.LogicCheckApi;
import com.digital.api.ZoningDataUploadApi;
import com.digital.entity.province.ContrastTemporary;
import com.digital.entity.province.FocusChangeFileInfo;
import com.digital.util.Common;
import com.digital.util.StringUtil;
import com.digital.util.resultData.Constants;
import com.digital.util.resultData.RtnData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: zhanghpj
 * @Version 1.0, 16:19 2018/3/6
 * @See
 * @Since
 * @Deprecated
 */
@Controller
@RequestMapping(value = "/importTemp")
public class ImportTemporaryController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ImportTemporaryController.class);

    @Autowired
    private ImportTemporaryApi importTemporaryApi;
    @Autowired
    private ZoningDataUploadApi zoningDataUploadApi;
    @Autowired
    private ImportFormalTableApi importFormalTableApi;

    @Autowired
    private LogicCheckApi logicCheckApi;

    /**
     * @return java.lang.String ：响应结果
     * @throws
     * @description  将集中上传的数据 导入临时表  简单校验后导入表中
     * @method importTemporary
     * @params [instructionCode：变更指令 1 导入临时表, fileSquence：上传zip文件序号, request, response]
     */
    @RequestMapping(value = "/code/decompression", method = RequestMethod.GET)
    @ResponseBody
    public String importTemporary(@RequestParam("instructionCode") String instructionCode,
                                  @RequestParam("fileSquence") Integer fileSquence, HttpServletRequest request, HttpServletResponse response) {

        if (!StringUtil.isEmpty(instructionCode) && instructionCode.equals("1")) {
            FocusChangeFileInfo fileInfo = new FocusChangeFileInfo();
            /*获取文件的基本信息*/
            fileInfo = zoningDataUploadApi.queryFocusChangeFileInfo(fileSquence);
            if (StringUtil.isEmpty(fileInfo.getFileName()) || StringUtil.isEmpty(fileInfo.getFilePath())) {
                throw new RuntimeException("数据有问题 || 或查询接口有问题");
            }
            if (!Common.XZQH_JZBGZT_DRLSBCLZ.equals(fileInfo.getStatusCode())){
                return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_IMPORT_CHEKED).toString();
            }

            /*进行内容校验*/
            boolean checked = importTemporaryApi.importTemporary(fileInfo);
            if (!checked) {
                //导入临时表失败 修改状态
                zoningDataUploadApi.updateTypeCode(fileSquence,Common.XZQH_JZBGZT_DRLSBSB);
                return new RtnData(Constants.RTN_CODE_ERROR,"内容校验失败！  具体错误将在备注中显示。。").toString();
            }
            return new RtnData().toString();
        }
        return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR, "导入临时表的过程中，发现逻辑校验出错的数据，详情请查看错误页面").toString();
    }


    /**
     * @description   校验并导入预览数据
     * @method  checkAndImport
     * @params [fileSquence:文件序号]
     * @return java.lang.String
     * @exception
     */
    @RequestMapping(value = "code/checkAndImport", method = RequestMethod.GET)
    @ResponseBody
    public String checkAndImport(@RequestParam("fileSquence") Integer fileSquence) {

        Map<String, Object> checkedMap = new HashMap<>();
        if (fileSquence != 0 && fileSquence != null) {
            //获取文件名
            FocusChangeFileInfo fileInfo = zoningDataUploadApi.queryFocusChangeFileInfo(fileSquence);
            if (fileInfo == null) {
                logger.info("没有该文件信息");
                return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR, "没有该文件信息").toString();
            }
            checkedMap = logicCheckApi.ContentCheck(fileInfo);
            //判断逻辑校验是否成功
            if (!(boolean) checkedMap.get("check")) {
                logger.error(fileInfo.getFileName()+"逻辑校验失败！");
                //修改文件信息中的集中变更状态为  失败
                zoningDataUploadApi.updateTypeCode(fileSquence,Common.XZQH_JZBGZT_LJJYSB);
                return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_CHECKED_FAIL, "校验的过程中，未通过数据，详情请查看错误信息").toString();
            }
            List<ContrastTemporary> temporaryList = (List<ContrastTemporary>) checkedMap.get("temporaryList");
            //导入预览数据表
            boolean result = importFormalTableApi.ImprotFormalT(fileInfo, temporaryList);
            if (result) {
                return new RtnData().toString();
            }
        }
            return new RtnData(Constants.RTN_CODE_ERROR, Constants.RTN_MESSAGE_ERROR, "导入正式表的过程中，发现逻辑校验出错的数据，详情请查看错误页面").toString();

    }


}

