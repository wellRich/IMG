define(['avalon', 'jquery', 'bootstrap', 'ajaxConfig', 'text!./rzc_chaxun.js'], function (avalon, $, _rzc_chaxun) {
  avalon.templateCache._rzc_chaxun = _rzc_chaxun;

  var rzc_chaxun_vm = avalon.define({
    $id: "rzc_chaxun",
    data: {
      navbar: [
        {
          name: "区划数据上传",
          routerPath: "#!/rzc/rzc_shangchuan",
          imgPath: "./src/modules/rzc/img/gray.png"
        },
        {
          name: "导入临时表",
          routerPath: "#!/rzc/rzc_linshi",
          imgPath: "./src/modules/rzc/img/gray.png"
        },
        {
          name: "导入数据查询",
          routerPath: "#!/rzc/rzc_chaxun",
          imgPath: "./src/modules/rzc/img/blue.png"
        },
        {
          name: "正式表",
          routerPath: "#!/rzc/rzc_zhengshi",
          imgPath: "./src/modules/rzc/img/black.png"
        },
      ],
      upload: [], //  上传文件数据
      temporaryData: [],  //  临时表展示数据
      error_sign: "--请选择--", //  错误类型显示
      adjustment_type: "--请选择--",  //  变更类型

      //  查询信息
      totalCount: "", //  总条数
      pageSize: 4,  //  每页条数
      pageCount: 0,  //  总页数
      pageNum: 1,   //  当前页数
      date: "",
      zoningCode: "000000",

      //  文件状态修改
      typeCode: 32,

      //  查询变更临时数据
      temp_fileSquence: "", //  文件序号,必传
      temp_zoningCode: "",  //  区划代码,必传
      temp_errorStatus: "", //  错误类型,默认为空
      temp_typeCode: "",  //  变更类型,默认为空
      temp_pageNum: 1,  //  当前页数
      temp_pageSize: 10,  //  每页条数
      temp_pageCount: 0,  //  总页数
      temp_totalCount: "",  //  总条数
    },

    /**
     * 查询上传文件信息
     */
    getqueryFocusChangeFileInfo: function(){
      var pageNum = rzc_chaxun_vm.data.pageNum = 1;
      var pageSize = rzc_chaxun_vm.data.pageSize;
      var date = rzc_chaxun_vm.data.date;
      var zoningCode = rzc_chaxun_vm.data.zoningCode;

      date = $("#date").val();
      zoningCode = $("#zoningCode").val();
      queryFocusChangeFileInfo(pageNum, pageSize, date, zoningCode);
      
      rzc_chaxun_vm.data.date = date; 
      rzc_chaxun_vm.data.zoningCode = zoningCode;
    },

    /**
     * 向下翻页
     * 文件上传展示
     */
    getNextDate: function () {
      var date = rzc_chaxun_vm.data.date;
      var zoningCode = rzc_chaxun_vm.data.zoningCode;
      var pageNum = rzc_chaxun_vm.data.pageNum;
      var pageSize = rzc_chaxun_vm.data.pageSize;
      var pageCount = rzc_chaxun_vm.data.pageCount;

      pageNum = (pageNum === pageCount) ? pageNum : pageNum + 1;
      queryFocusChangeFileInfo(pageNum, pageSize, date, zoningCode);
      rzc_chaxun_vm.data.pageNum = pageNum;
    },

    /**
     * 临时变更数据
     */
    getNextTempDate: function(){
      var fileSquence = rzc_chaxun_vm.data.temp_fileSquence;
      var zoningCode  = rzc_chaxun_vm.data.temp_zoningCode;
      var errorStatus = rzc_chaxun_vm.data.temp_errorStatus;
      var typeCode = rzc_chaxun_vm.data.temp_typeCode;
      var pageNum = rzc_chaxun_vm.data.temp_pageNum;
      var pageSize = rzc_chaxun_vm.data.temp_pageSize;
      var pageCount = rzc_chaxun_vm.data.temp_pageCount;
      
      pageNum = (pageNum === pageCount) ? pageNum : pageNum + 1;
      queryTemporaryData(fileSquence,zoningCode,errorStatus,typeCode,pageNum,pageSize);
      rzc_chaxun_vm.data.temp_pageNum = pageNum;
    },

    /**
     * 向上翻页
     * 文件上传展示
     */
    getPreviousDate: function () {
      var date = rzc_chaxun_vm.data.date;
      var zoningCode = rzc_chaxun_vm.data.zoningCode;
      var pageNum = rzc_chaxun_vm.data.pageNum;
      var pageSize = rzc_chaxun_vm.data.pageSize;

      pageNum = (pageNum === 1) ? pageNum : pageNum - 1;
      console.log(pageNum);
      queryFocusChangeFileInfo(pageNum, pageSize, date, zoningCode);
      rzc_chaxun_vm.data.pageNum = pageNum;
    },

    /**
     * 临时变更数据
     */
    getPreviousTempDate: function(){
      var fileSquence = rzc_chaxun_vm.data.temp_fileSquence;
      var zoningCode  = rzc_chaxun_vm.data.temp_zoningCode;
      var errorStatus = rzc_chaxun_vm.data.temp_errorStatus;
      var typeCode = rzc_chaxun_vm.data.temp_typeCode;
      var pageNum = rzc_chaxun_vm.data.temp_pageNum;
      var pageSize = rzc_chaxun_vm.data.temp_pageSize;
      
      pageNum = (pageNum === 1) ? pageNum : pageNum - 1;
      queryTemporaryData(fileSquence,zoningCode,errorStatus,typeCode,pageNum,pageSize);
      rzc_chaxun_vm.data.temp_pageNum = pageNum;     
    },

    /**
     * 获取点击当前页数据
     * 文件上传展示
     */
    getCurrentDate: function () {
      var date = rzc_chaxun_vm.data.date;
      var zoningCode = rzc_chaxun_vm.data.zoningCode;
      var pageSize = rzc_chaxun_vm.data.pageSize;
      var pageNum = Number($(this).text());
      queryFocusChangeFileInfo(pageNum, pageSize, date, zoningCode);
      rzc_chaxun_vm.data.pageNum = pageNum;
      rzc_chaxun_vm.data.zoningCode = zoningCode;
    },

    /**
     * 临时变更数据
     */
    getCurrentTempDate: function(){
      var fileSquence = rzc_chaxun_vm.data.temp_fileSquence;
      var zoningCode = rzc_chaxun_vm.data.temp_zoningCode;
      var errorStatus = rzc_chaxun_vm.data.temp_errorStatus;
      var typeCode = rzc_chaxun_vm.data.temp_typeCode;
      var pageSize = rzc_chaxun_vm.data.temp_pageSize;
      var pageNum = Number($(this).text());

      queryTemporaryData(fileSquence,zoningCode,errorStatus,typeCode,pageNum,pageSize);
      rzc_chaxun_vm.data.temp_pageNum = pageNum;
      rzc_chaxun_vm.data.temp_zoningCode = zoningCode;
    },

    getZoningCode: function(){
      rzc_chaxun_vm.data.temp_zoningCode = $('input[type=radio]:checked').data('zoningcode');
      console.log()
    },

    /**
     * 查询查询变更临时数据
     */
    getTemporaryData: function(){

      rzc_chaxun_vm.data.temp_fileSquence = $('input[type=radio]:checked').data('filesquence');
      rzc_chaxun_vm.data.temp_zoningCode = $('input[type=radio]:checked').data('zoningcode');
      
      var fileSquence = rzc_chaxun_vm.data.temp_fileSquence;
      var zoningCode = rzc_chaxun_vm.data.temp_zoningCode;
      var errorStatus = rzc_chaxun_vm.data.temp_errorStatus;
      var typeCode = rzc_chaxun_vm.data.temp_typeCode;
      var pageNum = rzc_chaxun_vm.data.temp_pageNum;
      var pageSize = rzc_chaxun_vm.data.temp_pageSize;

      if(fileSquence){
        queryTemporaryData(fileSquence,zoningCode,errorStatus,typeCode,pageNum,pageSize);
        rzc_chaxun_vm.data.temp_fileSquence = fileSquence;
        rzc_chaxun_vm.data.temp_zoningCode = zoningCode;
        rzc_chaxun_vm.data.temp_errorStatus = errorStatus;
        rzc_chaxun_vm.data.temp_typeCode = typeCode;
      }else{
        alert('欧尼酱,要选择一个文件才能查询呀!');
      }
 
      
    },

    /**
     * 下载文件信息
     */
    getDownloadChangeDat: function(){
      var fileSquence = rzc_chaxun_vm.data.temp_fileSquence;
      var errorStatus = rzc_chaxun_vm.data.temp_errorStatus;
      downloadChangeData(fileSquence,errorStatus);
      console.log(111);
    },

    /**
     * 校验并入库
     */
    getcheckAndImport: function(){
      var fileSquence = $('input[type=radio]:checked').data('filesquence');
      var typeCode = rzc_chaxun_vm.data.typeCode;

      //  修改文件状态 并刷新数据
      modifyTypeCode(fileSquence, typeCode);
      console.log(rzc_chaxun_vm.data.zoningCode,33333)
    },

    /**
     * 错误标志变更显示
     */
    errorSignToggle: function () {
      var status = "";
      var temp_errorStatus = rzc_chaxun_vm.data.temp_errorStatus;

      rzc_chaxun_vm.data.error_sign = $(this).text();
      status = $(this).data('value');

      switch(status){
        case 1:  temp_errorStatus = "Y"
        break;
        case 2:  temp_errorStatus = "N"
        break;
        default:
        temp_errorStatus = ""; 
      }

      rzc_chaxun_vm.data.temp_errorStatus = temp_errorStatus;
      rzc_chaxun_vm.data.temporaryData = [];
    },

    /**
     * 调整类型变更显示
     */
    adjustmentTypeToggle: function () {
      rzc_chaxun_vm.data.adjustment_type = $(this).text();
      rzc_chaxun_vm.data.temp_typeCode = $(this).data('value');
    },

  })


  //  初始加载,显示已上传文件信息
  queryFocusChangeFileInfo(rzc_chaxun_vm.data.pageNum, rzc_chaxun_vm.data.pageSize,rzc_chaxun_vm.data.date,rzc_chaxun_vm.data.zoningCode);


  /**
   * 分页器函数
   * @param {number} totalCount 总条数
   * @param {number} pageSize 每页显示数
   */
  function pagination(totalCount,pageSize) {

    var pageCount;  //  总页数
    totalCount = Number(totalCount);
    pageCount = Math.ceil(totalCount / pageSize);
    return pageCount;
  };

  /**
   * 上传文件查询接口  
   * @param {string} pageNum 当前页码
   * @param {string} pageSize 每页显示条数
   * @param {number} date 日期（8位）
   * @param {number} zoningCode 区划代码（6位）
   */
  function queryFocusChangeFileInfo(pageNum, pageSize, date, zoningCode) {
    var date = date || "";
    var zoningCode = zoningCode || "000000";
    $.ajax({
      url: 'http://'+ ip +':'+ port +'/zoning/query/fileInfo?zoningCode='+ zoningCode 
      +'&date=' + date + '&pageSize=' + pageSize + '&pageNum=' + pageNum,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        if (res.rtnCode === "000000") {
          console.log(res, "文件查询:" + res.rtnMessage);
          rzc_chaxun_vm.data.upload = res.responseData;
          rzc_chaxun_vm.data.totalCount = res.rtnMessage;

          //  调用分页器
          rzc_chaxun_vm.data.pageCount=pagination(rzc_chaxun_vm.data.totalCount,rzc_chaxun_vm.data.pageSize);
          
        }
      }
    })
  }

  /**
   * 查询变更临时数据接口
   * @param {number} fileSquence 文件序号
   * @param {string} zoningCode 区划代码
   * @param {string} errorStatus 错误标志
   * @param {string} typeCode 变更类型
   * @param {number} pageNum 当前页码
   * @param {number} pageSize 每页显示条数
   */
  function queryTemporaryData(fileSquence,zoningCode,errorStatus,typeCode,pageNum,pageSize){
    $.ajax({
      url: 'http://'+ ip +':'+ port +'/queryTemp/query/checkedData?fileSquence=' + fileSquence
        + '&zoningCode=' + zoningCode + '&errorStatus=' + errorStatus + '&typeCode=' + typeCode
        + '&pageNum=' + pageNum + '&pageSize=' + pageSize,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        console.log(res , "查询临时变更数据");

        if(res.rtnCode === "000000"){
          rzc_chaxun_vm.data.temporaryData = res.responseData;
          rzc_chaxun_vm.data.temp_totalCount = res.rtnMessage;

          //  调用分页器
          rzc_chaxun_vm.data.temp_pageCount = 
          pagination(rzc_chaxun_vm.data.temp_totalCount,rzc_chaxun_vm.data.temp_pageSize);
          // console.log(rzc_chaxun_vm.data.temp_totalCount,rzc_chaxun_vm.data.temp_pageSize,rzc_chaxun_vm.data.temp_pageCount);
        }
      }
    })
  }

  /**
   * 校验并导入接口
   * @param {number} fileSquence 文件序号
   */
  function checkAndImport(fileSquence){
    $.ajax({
      url:'http://'+ ip +':'+ port +'/importTemp/code/checkAndImport?fileSquence='+fileSquence,
      type: 'GET',
      success: function(da){
        var res = JSON.parse(da);
          console.log(res);
          queryFocusChangeFileInfo(rzc_chaxun_vm.data.pageNum, rzc_chaxun_vm.data.pageSize,rzc_chaxun_vm.data.date,rzc_chaxun_vm.data.zoningCode);            
      }
    })
  }

  /**
   * 文件状态修改接口
   * @param {number} fileSquence 文件序号
   * @param {string} typeCode 调整类型状态码
   */
  function modifyTypeCode(fileSquence, typeCode) {
    $.ajax({
      url: 'http://'+ ip +':'+ port +'/zoning/update/modifyTypeCode?fileSquence='+fileSquence
      +'&typeCode='+typeCode,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        console.log(res, "文件状态修改" + res.rtnMessage);
        if(res.rtnCode === "000000"){
          //  如果修改成功  刷新数据显示 
          console.log(rzc_chaxun_vm.data.zoningCode);
          queryFocusChangeFileInfo(rzc_chaxun_vm.data.pageNum, rzc_chaxun_vm.data.pageSize,rzc_chaxun_vm.data.date,rzc_chaxun_vm.data.zoningCode);

          //  校验并导入
          checkAndImport(fileSquence);
        }
      }
    })
  }

  /**
   * 下载文件信息接口
   * @param {number} fileSquence 文件序号
   * @param {string} errorStatus 错误标志状态码
   */
  function downloadChangeData(fileSquence,errorStatus){
      window.location.href = 'http://'+ ip +':'+ port +'/queryTemp/query/downloadData?fileSquence='+fileSquence
      +'&errorStatus='+errorStatus
  }
  avalon.scan(document.body);
})

