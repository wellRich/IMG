define(['avalon', 'jquery', 'bootstrap', 'text!./rzc_tijiao.js'], function (avalon, _rzc_tijiao) {
  avalon.templateCache._rzc_tijiao = _rzc_tijiao;

  var rzc_tijiao_vm = avalon.define({
    $id: "rzc_tijiao",
    data: {
      navbar: [
        {
          name: "建立变更对照表",
          routerPath: "#!/rzc/rzc_jianli",
          imgPath: "./src/modules/rzc/img/gray.png"
        },
        {
          name: "录入变更明细",
          routerPath: "#!/rzc/rzc_luru",
          imgPath: "./src/modules/rzc/img/gray.png"
        },
        {
          name: "维护变更对照表",
          routerPath: "#!/rzc/rzc_weihu",
          imgPath: "./src/modules/rzc/img/gray.png"
        },
        {
          name: "提交变更对照表",
          routerPath: "#!/rzc/rzc_tijiao",
          imgPath: "./src/modules/rzc/img/blue.png"
        },
        {
          name: "撤销变更对照表",
          routerPath: "#!/rzc/rzc_chexiao",
          imgPath: "./src/modules/rzc/img/black.png"
        },
      ],

      
      changeControl: [],  //  申请单存放位置

      requestSeq: "",

      //  用户申请单信息查询
      zoningCode: "",

      pageSize: 5,  //  每页条数
      pageIndex: 1, //  当前页
      totalRecord: 0, //  总条数
      totalPage: 0, //  总页数
    },

    /**
     * 向上翻页
     * 文件上传展示
     */
    getPreviousDate: function () {
      var zoningCode = rzc_tijiao_vm.data.zoningCode;
      var pageIndex = rzc_tijiao_vm.data.pageIndex;
      var totalRecord = rzc_tijiao_vm.data.totalRecord;
      var pageSize = rzc_tijiao_vm.data.pageSize;

      pageIndex = (pageIndex === 1) ? pageIndex : pageIndex - 1;
      console.log(pageIndex);
      zoningChangeRequestList(zoningCode, pageSize, pageIndex, totalRecord);
      rzc_tijiao_vm.data.pageIndex = pageIndex;
    },

    /**
     * 向下翻页
     * 文件上传展示
     */
    getNextDate: function () {
      var zoningCode = rzc_tijiao_vm.data.zoningCode;
      var pageIndex = rzc_tijiao_vm.data.pageIndex;
      var pageSize = rzc_tijiao_vm.data.pageSize;
      var totalPage = rzc_tijiao_vm.data.totalPage;
      var totalRecord = rzc_tijiao_vm.data.totalRecord;

      pageIndex = (pageIndex === totalPage) ? pageIndex : pageIndex + 1;
 
      zoningChangeRequestList(zoningCode, pageSize, pageIndex, totalRecord);
      rzc_tijiao_vm.data.pageIndex = pageIndex;
    },

    /**
     * 获取点击当前页数据
     * 文件上传展示
     */
    getCurrentDate: function () {
      var zoningCode = rzc_tijiao_vm.data.zoningCode;
      var pageSize = rzc_tijiao_vm.data.pageSize;
      var totalRecord = rzc_tijiao_vm.data.totalRecord;
      var pageIndex = Number($(this).text());

      zoningChangeRequestList(zoningCode, pageSize, pageIndex, totalRecord);
      rzc_tijiao_vm.data.pageIndex = pageIndex;
      rzc_tijiao_vm.data.zoningCode = zoningCode;
    },

    /**
     * 提交申请单
     */
    getSubmitToCheck: function(){
      console.log($('input[type=radio]:checked'));
      rzc_tijiao_vm.data.requestSeq = $('input[type=radio]:checked').data('requestseq');
      submitToCheck(rzc_tijiao_vm.data.requestSeq);
    },

    /**
     * 数据刷新
     */
    getRefresh: function(){
      zoningChangeRequestList(rzc_tijiao_vm.data.zoningCode, rzc_tijiao_vm.data.pageSize, rzc_tijiao_vm.data.pageIndex, rzc_tijiao_vm.data.total);
    }
  })

  buildRequest();

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
   * 获取区划代码接口   
   * 默认值为 370102000000000
   */
  function buildRequest() {
    $.ajax({
      url: 'http://localhost:8251/zoningChangeManager/buildRequest',
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        // console.log(res);

        if (res.rtnCode === "000000") {
          rzc_tijiao_vm.data.zoningCode = res.responseData.zoningCode;
          zoningChangeRequestList(rzc_tijiao_vm.data.zoningCode, rzc_tijiao_vm.data.pageSize, rzc_tijiao_vm.data.pageIndex, rzc_tijiao_vm.data.total);
        }
      }
    })
  }

  /**
   * 用户申请单信息查询接口
   * @param 区划代码 zoningCode 
   * @param 每个页面数据条数 pageSize 
   * @param  当前页面 pageIndex 
   * @param 总条数 total 
   */
  function zoningChangeRequestList(zoningCode, pageSize, pageIndex, total){
    total = total || 0;
    $.ajax({
      url: 'http://localhost:8251/zoningChangeManager/ZoningChangeRequestList?zoningCode=' + zoningCode
        + '&pageSize=' + pageSize + '&pageIndex=' + pageIndex + '&total=' + total,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        console.log(res);

        if (res.rtnCode === "000000") {
          rzc_tijiao_vm.data.changeControl = res.responseData.dataList;
        }
      }
    })
  }

  /**
   * 提交申请单接口
   * @param {number} requestSeq 申请单序号 
   */
  function submitToCheck(requestSeq){
    $.ajax({
      url: 'http://localhost:8251/zoningChangeManager/submitToCheck?seq='+requestSeq,
      type: 'GET',
      success: function(da){
        var res = JSON.parse(da);
        console.log(res);
        if(res.rtnCode === "000000"){
          buildRequest();
        }
      }
    })
  }

})