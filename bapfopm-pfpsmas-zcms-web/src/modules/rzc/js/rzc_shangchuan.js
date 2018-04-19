define(['avalon', 'jquery', 'bootstrap', 'text!./rzc_shangchuan.js'], function (avalon, $, _rzc_shangchuan) {
  avalon.templateCache._rzc_shangchuan = _rzc_shangchuan;

  var rzc_shangchuan_vm = avalon.define({
    $id: "rzc_shangchuan",
    data: {
      navbar: [
        {
          name: "区划数据上传",
          routerPath: "#!/rzc/rzc_shangchuan",
          imgPath: "./src/modules/rzc/img/blue.png"
        },
        {
          name: "导入临时表",
          routerPath: "#!/rzc/rzc_linshi",
          imgPath: "./src/modules/rzc/img/black.png"
        },
        {
          name: "导入数据查询",
          routerPath: "#!/rzc/rzc_chaxun",
          imgPath: "./src/modules/rzc/img/black.png"
        },
        {
          name: "正式表",
          routerPath: "#!/rzc/rzc_zhengshi",
          imgPath: "./src/modules/rzc/img/black.png"
        },
      ],
      upload: [], //  文件查询信息放置数组
      fileName: "", //  上传文件名 

      zoningCode: "000000", //  区划代码
      date: "", //  日期

      //  查询信息
      totalCount: "", //  总条数
      pageSize: 4,  //  每页条数
      pageCount: 0,  //  总页数
      pageNum: 1,   //  当前页数

      //  显隐开关
      maskToggle: false,  //  遮罩层
      uploadToggle: false,  //  文件上传提示框
      deleteFileToggle: false,  //  文件删除提示框
      deleteRuleToggle: false, // 删除约束
    },


    //  上传数据选中状态更改(复选框才需要)
    activeToggleChange: function (index) {
      rzc_shangchuan_vm.data.upload[index].isChecked =
        !rzc_shangchuan_vm.data.upload[index].isChecked;
    },

    /**
     * 获取上传文件名
     */
    getFileName: function () {
      rzc_shangchuan_vm.data.fileName = $("#upload_file")[0].files[0].name;
    },

    /**
     * 上传文件
     */
    sendFile: function () {
      //  只能兼容到ie10
      if(rzc_shangchuan_vm.data.fileName) {
        var fileName = rzc_shangchuan_vm.data.fileName;
        //    QHDM_370000_20180211.zip
        var rex = /QHDM\_\d{6}\_\d{8}\.zip/;
        if (!rex.test(fileName)) {
          alert('请选择正确文件');
        }else{
          codeUpload("#form");
          rzc_shangchuan_vm.data.fileName = "";
        }
      }else{
        alert("欧妮桑,先选择一个文件才能点击我上传哟!")
      }
    },

    /**
     * 删除上传文件
     */
    deleteUploadFile: function () {
      var fileSquence = $('input[type=radio]:checked').data('filesquence');
      console.log(fileSquence);

      //  是否选择文件
      if(fileSquence){
        //  调用文件状态判断接口
        checkFileStatus(fileSquence);
      }else{
        alert('亲，请选择好一个文件宝宝才能进行删除哟！');
      }

      // 操作dom 让剩下的复选框移除选中状态
      $('input[type=radio]').prop("checked", false);
    },

    /**
     * 向下翻页
     */
    getNextDate: function () {
      var zoningCode = rzc_shangchuan_vm.data.zoningCode;
      var date = rzc_shangchuan_vm.data.date;
      var pageNum = rzc_shangchuan_vm.data.pageNum;
      var pageSize = rzc_shangchuan_vm.data.pageSize;
      var pageCount = rzc_shangchuan_vm.data.pageCount;

      pageNum = (pageNum === pageCount) ? pageNum : pageNum + 1;
      queryFocusChangeFileInfo(zoningCode, date, pageNum, pageSize);
      rzc_shangchuan_vm.data.pageNum = pageNum;
    },

    /**
     * 向上翻页
     */
    getPreviousDate: function () {
      var zoningCode = rzc_shangchuan_vm.data.zoningCode;
      var date = rzc_shangchuan_vm.data.date;
      var pageNum = rzc_shangchuan_vm.data.pageNum;
      var pageSize = rzc_shangchuan_vm.data.pageSize;

      pageNum = (pageNum === 1) ? pageNum : pageNum - 1;
      queryFocusChangeFileInfo(zoningCode, date, pageNum, pageSize);
      rzc_shangchuan_vm.data.pageNum = pageNum;
    },

    /**
     * 获取点击当前页数据
     */
    getCurrentDate: function () {
      var zoningCode = rzc_shangchuan_vm.data.zoningCode;
      var date = rzc_shangchuan_vm.data.date;
      var pageSize = rzc_shangchuan_vm.data.pageSize;
      var pageNum = Number($(this).text());

      queryFocusChangeFileInfo(zoningCode, date, pageNum, pageSize);
      rzc_shangchuan_vm.data.pageNum = pageNum;
    },

    /**
     * 隐藏提示框
     */
    hiddenPrompt: function () {
      rzc_shangchuan_vm.data.maskToggle = false;
      rzc_shangchuan_vm.data.uploadToggle = false;
      rzc_shangchuan_vm.data.deleteFileToggle = false;
      rzc_shangchuan_vm.data.deleteRuleToggle = false;
    },

    /**
     * 显示提示框
     */
    showPrompt: function () {
      // rzc_shangchuan_vm.data.maskToggle = true;
      // rzc_shangchuan_vm.data.uploadToggle = true;
      // rzc_shangchuan_vm.data.deleteFileToggle = true;
      // console.log(rzc_shangchuan_vm.data.deleteFileToggle);
      rzc_shangchuan_vm.data.deleteRuleToggle = !rzc_shangchuan_vm.data.deleteRuleToggle;
    },

    showDeletePrompt: function(){
      rzc_shangchuan_vm.data.deleteFileToggle = true;
    }
  })

  //  初始加载,就应显示已上传文件信息
  queryFocusChangeFileInfo(rzc_shangchuan_vm.data.zoningCode,rzc_shangchuan_vm.data.date,rzc_shangchuan_vm.data.pageNum, rzc_shangchuan_vm.data.pageSize);


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
  pagination(rzc_shangchuan_vm.data.totalCount, rzc_shangchuan_vm.data.pageSize);


  /**
   * 文件上传接口
   * @param {string} formId 表单ID
   */
  function codeUpload(formId) {
    var formObj = $(formId)[0];
    var formData = new FormData(formObj);
    $.ajax({
      url: 'http://localhost:8251/zoning/upload/zipFile',
      type: 'POST',
      data: formData,
      // 告诉jQuery不要去处理发送的数据
      processData: false,
      // 告诉jQuery不要去设置Content-Type请求头
      contentType: false,
      success: function (res) {
        console.log(res, "文件上传:" + res.rtnMessage);
        if (res.rtnCode === "000000") {
          rzc_shangchuan_vm.data.uploadToggle = true;
          //  重新刷新数据
          queryFocusChangeFileInfo(rzc_shangchuan_vm.data.zoningCode,rzc_shangchuan_vm.data.date,rzc_shangchuan_vm.data.pageNum, rzc_shangchuan_vm.data.pageSize);    
        }
      }
    })
  }


  /**
   * 上传文件查询接口
   * @param {number} zoningCode 区划代码
   * @param {number} pageNum 当前页码
   * @param {number} pageSize 每页显示条数
   */
  function queryFocusChangeFileInfo(zoningCode, date, pageNum, pageSize) {
    date = date || "";
    $.ajax({
      url: 'http://localhost:8251/zoning/query/fileInfo?zoningCode=' + zoningCode + '&date='+ date +'&pageSize='
        + pageSize + '&&pageNum=' + pageNum,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        if (res.rtnCode === "000000") {
          console.log(res, "文件查询:" + res.rtnMessage);
          rzc_shangchuan_vm.data.upload = res.responseData;

          //  给上传数据增添一个选中状态变量(复选框才需要)
          // rzc_shangchuan_vm.data.upload.map(function (el) {
          //   el.isChecked = false;
          // });
          rzc_shangchuan_vm.data.totalCount = res.rtnMessage;

          rzc_shangchuan_vm.data.pageCount = pagination(rzc_shangchuan_vm.data.totalCount, rzc_shangchuan_vm.data.pageSize);
        }
      }
    })
  }

  /**
   * 查询文件状态接口,  判断是否能删除
   * @param {number} fileSquence 上传文件序号
   */
  function checkFileStatus(fileSquence) {
    $.ajax({
      url: 'http://localhost:8251/zoning/query/fileStatusCode?fileSquence=' + fileSquence,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        console.log(res);
        //  如果文件类型判断为可以删除,调用删除上传文件接口
        if (res.rtnCode === "000000") {
          if (res.responseData === true) {
            console.log(res, "文件能否被删除:" + res.rtnMessage);
            deleteFileInfo(fileSquence);
          }
        }else{
          alert("欧尼酱,想不到我不能被删除吧,哈哈哈!");
        }
      }
    })
  }

  /**
   * 删除上传文件接口
   * @param {number} fileSquence 上传文件序号
   */
  function deleteFileInfo(fileSquence) {
    $.ajax({
      url: 'http://localhost:8251/zoning/delete/zipFile?fileSquence=' + fileSquence,
      type: 'GET',
      success: function (da) {
        res = JSON.parse(da);
        console.log(res, "文件删除:" + res.rtnMessage);

        if (res.rtnCode === "000000") {
          //  重新刷新数据
          queryFocusChangeFileInfo(rzc_shangchuan_vm.data.zoningCode,rzc_shangchuan_vm.data.date,rzc_shangchuan_vm.data.pageNum, rzc_shangchuan_vm.data.pageSize);              
        }
      }
    })
  }

  avalon.scan(document.body);
})

