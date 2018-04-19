define(['avalon', 'jquery', 'bootstrap', 'text!./rzc_zhengshi.js'], function (avalon, _rzc_zhengshi) {
  avalon.templateCache._rzc_zhengshi = _rzc_zhengshi;

  var rzc_zhengshi_vm = avalon.define({
    $id: "rzc_zhengshi",
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
          imgPath: "./src/modules/rzc/img/gray.png"
        },
        {
          name: "正式表",
          routerPath: "#!/rzc/rzc_zhengshi",
          imgPath: "./src/modules/rzc/img/blue.png"
        },
      ],
      upload: [], //  上传文件数据

      //  查询信息
      totalCount: "", //  总条数
      pageSize: 4,  //  每页条数
      pageCount: 0,  //  总页数
      pageNum: 1,   //  当前页数
      date: "",
      zoningCode: "000000",

      //  文件状态修改
      typeCode: 32,

    },

    /**
     * 查询上传文件信息
     */
    getqueryFocusChangeFileInfo: function(){
      var pageNum = rzc_zhengshi_vm.data.pageNum = 1;
      var pageSize = rzc_zhengshi_vm.data.pageSize;
      var date = rzc_zhengshi_vm.data.date;
      var zoningCode = rzc_zhengshi_vm.data.zoningCode;

      date = $("#date").val();
      zoningCode = $("#zoningCode").val();
      queryFocusChangeFileInfo(pageNum, pageSize, date, zoningCode);
      
      rzc_zhengshi_vm.data.date = date; 
      rzc_zhengshi_vm.data.zoningCode = zoningCode;
    },

    /**
     * 向下翻页
     * 文件上传展示
     */
    getNextDate: function () {
      var date = rzc_zhengshi_vm.data.date;
      var zoningCode = rzc_zhengshi_vm.data.zoningCode;
      var pageNum = rzc_zhengshi_vm.data.pageNum;
      var pageSize = rzc_zhengshi_vm.data.pageSize;
      var pageCount = rzc_zhengshi_vm.data.pageCount;

      pageNum = (pageNum === pageCount) ? pageNum : pageNum + 1;
      queryFocusChangeFileInfo(pageNum, pageSize, date, zoningCode);
      rzc_zhengshi_vm.data.pageNum = pageNum;
    },

    /**
     * 向上翻页
     * 文件上传展示
     */
    getPreviousDate: function () {
      var date = rzc_zhengshi_vm.data.date;
      var zoningCode = rzc_zhengshi_vm.data.zoningCode;
      var pageNum = rzc_zhengshi_vm.data.pageNum;
      var pageSize = rzc_zhengshi_vm.data.pageSize;

      pageNum = (pageNum === 1) ? pageNum : pageNum - 1;
      console.log(pageNum);
      queryFocusChangeFileInfo(pageNum, pageSize, date, zoningCode);
      rzc_zhengshi_vm.data.pageNum = pageNum;
    },

    /**
     * 获取点击当前页数据
     * 文件上传展示
     */
    getCurrentDate: function () {
      var date = rzc_zhengshi_vm.data.date;
      var zoningCode = rzc_zhengshi_vm.data.zoningCode;
      var pageSize = rzc_zhengshi_vm.data.pageSize;
      var pageNum = Number($(this).text());
      queryFocusChangeFileInfo(pageNum, pageSize, date, zoningCode);
      rzc_zhengshi_vm.data.pageNum = pageNum;
      rzc_zhengshi_vm.data.zoningCode = zoningCode;
    },

    /**
     * 校验并入库
     */
    getcheckAndImport: function () {
      var fileSquence = $('input[type=radio]:checked').data('filesquence');
      var typeCode = rzc_zhengshi_vm.data.typeCode;

      //  修改文件状态 并刷新数据
      modifyTypeCode(fileSquence, typeCode);
    },

  })

  queryFocusChangeFileInfo(rzc_zhengshi_vm.data.pageNum, rzc_zhengshi_vm.data.pageSize, rzc_zhengshi_vm.data.date, rzc_zhengshi_vm.data.zoningCode);


  /**
   * 分页器函数
   * @param {number} totalCount 总条数
   * @param {number} pageSize 每页显示数
   */
  function pagination(totalCount, pageSize) {

    var pageCount;
    totalCount = Number(totalCount);
    pageCount = Math.ceil(totalCount / pageSize);
    return pageCount;
  };


  /**
   * 校验并导入接口
   * @param {number} fileSquence 文件序号
   */
  function checkAndImport(fileSquence) {
    $.ajax({
      url: 'http://localhost:8251/importTemp/code/checkAndImport?fileSquence=' + fileSquence,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        console.log(res);
        queryFocusChangeFileInfo(rzc_zhengshi_vm.data.pageNum, rzc_zhengshi_vm.data.pageSize, rzc_zhengshi_vm.data.date, rzc_zhengshi_vm.data.zoningCode);
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
      url: 'http://localhost:8251/zoning/update/modifyTypeCode?fileSquence=' + fileSquence
        + '&typeCode=' + typeCode,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        console.log(res, "文件状态修改" + res.rtnMessage);
        if (res.rtnCode === "000000") {
          //  如果修改成功  刷新数据显示 
          console.log(rzc_zhengshi_vm.data.zoningCode);
          queryFocusChangeFileInfo(rzc_zhengshi_vm.data.pageNum, rzc_zhengshi_vm.data.pageSize, rzc_zhengshi_vm.data.date, rzc_zhengshi_vm.data.zoningCode);

          //  校验并导入
          checkAndImport(fileSquence);
        }
      }
    })
  }

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
      url: 'http://localhost:8251/zoning/query/fileInfo?zoningCode=' + zoningCode 
      + '&date=' + date + '&pageSize=' + pageSize + '&pageNum=' + pageNum,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        if (res.rtnCode === "000000") {
          console.log(res, "文件查询:" + res.rtnMessage);
          rzc_zhengshi_vm.data.upload = res.responseData;
          rzc_zhengshi_vm.data.totalCount = res.rtnMessage;

          //  调用分页器
          rzc_zhengshi_vm.data.pageCount = pagination(rzc_zhengshi_vm.data.totalCount, rzc_zhengshi_vm.data.pageSize);

        }
      }
    })
  }

  /**
 * 校验并导入接口
 * @param {number} fileSquence 文件序号
 */
  function checkAndImport(fileSquence) {
    $.ajax({
      url: 'http://localhost:8251/importTemp/code/checkAndImport?fileSquence=' + fileSquence,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        console.log(res);
        queryFocusChangeFileInfo(rzc_zhengshi_vm.data.pageNum, rzc_zhengshi_vm.data.pageSize, rzc_zhengshi_vm.data.date, rzc_zhengshi_vm.data.zoningCode);
      }
    })
  }
})