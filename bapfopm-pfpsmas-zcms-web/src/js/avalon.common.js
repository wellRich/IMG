define(['avalon'], function (avalon) {

  /**
   * null 显示 无
   */
  avalon.filters.nulltransform = function(str){
    return str = (str === null) ? "暂无" : str; 
  }

  /**
   * 录入变更明细各级区划代码截取展示
   */
  avalon.filters.substrCode = function (str, levelcode, superlevelcode) {
    var ret = levelcode.replace(superlevelcode, "");
    return ret;
  }

  /**
   * 文件上传状态中文切换展示
   */
  avalon.filters.signConversion = function (str, status) {
    switch (status) {
      case "10": status = "已上传"
        break;
      case "20": status = "导入临时表成功"
        break;
      case "21": status = "导入临时表失败"
        break;
      case "22": status = "导入临时表处理中"
        break;
      case "30": status = "逻辑校验成功"
        break;
      case "31": status = "逻辑校验失败"
        break;
      case "32": status = "逻辑校验处理中"
        break;
      case "40": status = "对照表生成成功"
        break;
      case "41": status = "对照表生成失败"
        break;
      case "42": status = "对照表生成处理中"
        break;
      default: break;
    }
    return status;
  }

  /**
   * 变更信息错误类型中文切换
   */
  avalon.filters.errorStatusConversion = function (str, status){
    return status = status === "N" ? "正确" : "错误";
  }
  
  /**
   * 文件变更类型状态中文切换展示
   */
  avalon.filters.typeCodeConversion = function (str, status) {
    status = Number(status);
    switch (status) {
      case 11: status = "新增"
        break;
      case 21: status = "变更"
        break;
      case 31: status = "并入"
        break;
      case 41: status = "迁移"
        break;
      default: break;
    }
    return status;
  }

  // avalon.filters.typeCodeConversion = function (str, status) {
  //   switch (true) {
  //     case status === 11 || status === "11": status = "新增"
  //       break;
  //     case status === 21 || status === "21": status = "变更"
  //       break;
  //     case status === 31 || status === "31": status = "并入"
  //       break;
  //     case status === 41 || status === "41": status = "迁移"
  //       break;
  //     default: break;
  //   }
  //   return status;
  // }

  /**
   * 申请单状态中文切换展示
   */
  avalon.filters.requestSignConversion = function (str, status) {
    switch (status) {
      case "10": status = "未提交"
        break;
      case "11": status = "已经提交"
        break;
      case "20": status = "审核通过"
        break;
      case "21": status = "审核不通过"
        break;
      case "30": status = "已经确认"
        break;
      case "40": status = "国家已经审核"
        break;
      case "50": status = "已经发布"
        break;
      case "60": status = "数据已经迁移"
        break;
      case "61": status = "数据迁移出现错误"
        break;
      default: break;
    }
    return status;
  }
});