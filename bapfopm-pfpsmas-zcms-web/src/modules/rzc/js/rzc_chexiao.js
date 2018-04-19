define(['avalon', 'jquery', 'bootstrap', 'text!./rzc_chexiao.js'], function (avalon, _rzc_chexiao) {
  avalon.templateCache._rzc_chexiao = _rzc_chexiao;

  var rzc_chexiao_vm = avalon.define({
    $id: "rzc_chexiao",
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
          imgPath: "./src/modules/rzc/img/gray.png"
        },
        {
          name: "撤销变更对照表",
          routerPath: "#!/rzc/rzc_chexiao",
          imgPath: "./src/modules/rzc/img/blue.png"
        },
      ],

      changeControl: [],  //申请变更单存放位置

      //  用户申请单信息查询
      zoningCode: "",
      requestSeq: "",
      status: "",

      pageSize: 5,  //  每页条数
      pageIndex: 1, //  当前页
      totalRecord: 0, //  总条数
      totalPage: 1, //  总页数
    },

    /**
     * 向上翻页
     * 申请单展示
     */
    getPreviousDate: function () {
      var zoningCode = rzc_chexiao_vm.data.zoningCode;
      var pageIndex = rzc_chexiao_vm.data.pageIndex;
      var totalRecord = rzc_chexiao_vm.data.totalRecord;
      var pageSize = rzc_chexiao_vm.data.pageSize;

      pageIndex = (pageIndex === 1) ? pageIndex : pageIndex - 1;
      console.log(pageIndex);
      zoningChangeRequestList(zoningCode, pageSize, pageIndex, totalRecord);
      rzc_chexiao_vm.data.pageIndex = pageIndex;
    },

    /**
     * 向下翻页
     * 申请单展示
     */
    getNextDate: function () {
      var zoningCode = rzc_chexiao_vm.data.zoningCode;
      var pageIndex = rzc_chexiao_vm.data.pageIndex;
      var pageSize = rzc_chexiao_vm.data.pageSize;
      var totalPage = rzc_chexiao_vm.data.totalPage;
      var totalRecord = rzc_chexiao_vm.data.totalRecord;

      pageIndex = (pageIndex === totalPage) ? pageIndex : pageIndex + 1;
 
      zoningChangeRequestList(zoningCode, pageSize, pageIndex, totalRecord);
      rzc_chexiao_vm.data.pageIndex = pageIndex;
    },

    /**
     * 获取点击当前页数据
     * 申请单展示
     */
    getCurrentDate: function () {
      var zoningCode = rzc_chexiao_vm.data.zoningCode;
      var pageSize = rzc_chexiao_vm.data.pageSize;
      var totalRecord = rzc_chexiao_vm.data.totalRecord;
      var pageIndex = Number($(this).text());

      zoningChangeRequestList(zoningCode, pageSize, pageIndex, totalRecord);
      rzc_chexiao_vm.data.pageIndex = pageIndex;
      rzc_chexiao_vm.data.zoningCode = zoningCode;
    },

    /**
     * 撤销变更申请单
     */
    getRevokeToCheck: function () {
      var requestSeq = rzc_chexiao_vm.data.requestSeq = $('input[type=radio]:checked').data('requestseq');
      var status = rzc_chexiao_vm.data.status = $('input[type=radio]:checked').data('status');
      if(status === 11){
        console.log(requestSeq)
        revokeToCheck(requestSeq);
      }
    }
  })

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
          rzc_chexiao_vm.data.levelCode = res.responseData.levelCode;
          rzc_chexiao_vm.data.zoningCode = res.responseData.zoningCode;
          zoningChangeRequestList(rzc_chexiao_vm.data.zoningCode, rzc_chexiao_vm.data.pageSize, rzc_chexiao_vm.data.pageIndex, rzc_chexiao_vm.data.total);
        }
      }
    })
  }
  buildRequest();

  /**
   * 用户申请单信息查询接口
   * @param 区划代码 zoningCode 
   * @param 每个页面数据条数 pageSize 
   * @param  当前页面 pageIndex 
   * @param 总条数 total 
   */
  function zoningChangeRequestList(zoningCode, pageSize, pageIndex, total) {
    console.log(zoningCode);
    total = total || 0;
    $.ajax({
      url: 'http://localhost:8251/zoningChangeManager/ZoningChangeRequestList?zoningCode=' + zoningCode
        + '&pageSize=' + pageSize + '&pageIndex=' + pageIndex + '&total=' + total,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        console.log(res);

        if (res.rtnCode === "000000") {
          rzc_chexiao_vm.data.changeControl = res.responseData.dataList;
          rzc_chexiao_vm.data.totalPage = res.responseData.totalPage;
        }
      }
    })
  }

  /**
   * 撤销申请单接口
   * @param {number} requestSeq 申请单序号
   */
  function revokeToCheck(requestSeq) {
    $.ajax({
      url: 'http://localhost:8251/zoningChangeManager/revokeToCheck?seq=' + requestSeq,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        console.log(res);
        if(res.rtnCode === "000000"){
          zoningChangeRequestList(rzc_chexiao_vm.data.zoningCode, rzc_chexiao_vm.data.pageSize, rzc_chexiao_vm.data.pageIndex, rzc_chexiao_vm.data.total);          
        }
      }
    })
  }

})