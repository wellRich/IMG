define(['avalon', 'jquery', 'bootstrap', 'text!./rzc_weihu.js'], function (avalon, _rzc_weihu) {
  avalon.templateCache._rzc_weihu = _rzc_weihu;

  var rzc_weihu_vm = avalon.define({
    $id: "rzc_weihu",
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
          imgPath: "./src/modules/rzc/img/blue.png"
        },
        {
          name: "提交变更对照表",
          routerPath: "#!/rzc/rzc_tijiao",
          imgPath: "./src/modules/rzc/img/black.png"
        },
        {
          name: "撤销变更对照表",
          routerPath: "#!/rzc/rzc_chexiao",
          imgPath: "./src/modules/rzc/img/black.png"
        },
      ],
      changeControl: [], //  用户申请单存放位置
      details: [],  // 变更对照明细数据存放位置 


      isShowPrompt: false,

      //  用户申请单信息查询
      zoningCode: "",

      pageSize: 5,  //  每页条数
      pageIndex: 1, //  当前页
      totalRecord: 0, //  总条数
      totalPage: 0, //  总页数

      //  变更对照明细数据查询
      requestSeq: "",

      detailPageSize: 5,  //  明细表_每页条数
      detailPageIndex: 1, //  明细表_当前页
      detailTotalRecord: 0, //  明细表_总条数
      detailTotalPage: 0, //  明细表_总页数

      //  修改申请单
      requestSeq: "", //  文件申请单序号
      requestName: "", //  申请单名称
      notes: "",  //  申请单备注
      status: "", //  申请单状态
    },

    /**
     * 获取变更对照明细数据 
     */
    getCheckDetails: function () {
      var requestSeq = rzc_weihu_vm.data.requestSeq = $('input[type=radio]:checked').data('requestseq');
      if(requestSeq){
        checkDetails(requestSeq);
      }else{
        alert("欧尼酱,想要查询明细,必须先选择申请单才行,哼唧哼唧!");
      }
    },

    /**
     * 申请单修改
     */
    getUpdateZCCRequest: function () {
      updateZCCRequest(rzc_weihu_vm.data.requestSeq,rzc_weihu_vm.data.requestName,rzc_weihu_vm.data.notes);
    },

    /**
     * 修改提示框数据自动填充
     */
    getData: function(){
      rzc_weihu_vm.data.requestName = $(this).data('requestname');
      rzc_weihu_vm.data.notes = $(this).data('notes');
    },

    /**
     * 导出导出变更明细对照数据
     */
    getExportExcel: function(){
      rzc_weihu_vm.data.requestSeq = $('input[type=radio]:checked').data('requestseq');
      exportExcel(rzc_weihu_vm.data.requestSeq);
    },

    /**
     * 提示框隐藏
     */
    hiddenPrompt: function () {
      rzc_weihu_vm.data.isShowPrompt = false;
    },

    /**
     * 提示框显示
     */
    showPrompt: function () {
      rzc_weihu_vm.data.status = $('input[type=radio]:checked').data('status');
      rzc_weihu_vm.data.requestSeq = $('input[type=radio]:checked').data('requestseq');
      // rzc_weihu_vm.data.requestName = $('input[type=radio]:checked').data('requestname');     
      // rzc_weihu_vm.data.notes = $('input[type=radio]:checked').data('notes');

      var status = rzc_weihu_vm.data.status;
   
      if (status === 10 || status === 21) {
        rzc_weihu_vm.data.isShowPrompt = true;
      }else{
        alert('欧尼酱,申请单的状态不对啦,必须是"未提交"或者"审核不通过"才能进行修改!');
      }
    },

    /**
     * 数据刷新
     */
    getRefresh: function(){
      ZoningChangeRequestList(rzc_weihu_vm.data.zoningCode, rzc_weihu_vm.data.pageSize, rzc_weihu_vm.data.pageIndex, rzc_weihu_vm.data.total);
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
          rzc_weihu_vm.data.zoningCode = res.responseData.zoningCode;
          // console.log(rzc_weihu_vm.data.zoningCode);

          ZoningChangeRequestList(rzc_weihu_vm.data.zoningCode, rzc_weihu_vm.data.pageSize, rzc_weihu_vm.data.pageIndex, rzc_weihu_vm.data.total);
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
  function ZoningChangeRequestList(zoningCode, pageSize, pageIndex, total) {
    total = total || 0;
    $.ajax({
      url: 'http://localhost:8251/zoningChangeManager/ZoningChangeRequestList?zoningCode=' + zoningCode
        + '&pageSize=' + pageSize + '&pageIndex=' + pageIndex + '&total=' + total,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        console.log(res);

        if (res.rtnCode === "000000") {
          rzc_weihu_vm.data.changeControl = res.responseData.dataList;
        }
      }
    })
  }

  /**
   * 查看变更对照明细数据接口
   * @param 区划变更申请单序号 requestSeq 
   */
  function checkDetails(requestSeq) {
    console.log(requestSeq);
    $.ajax({
      url: 'http://localhost:8251/zoningChangeManager/checkDetails?seq=' + requestSeq,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        console.log(res);

        if (res.rtnCode === "000000") {
          if(res.responseData.dataList){
            rzc_weihu_vm.data.details = res.responseData.dataList;
            console.log(rzc_weihu_vm.data.details);
          }else{

          }
        }
      }
    })
  }

  /**
   * 修改申请单接口
   * @param 申请单序号 requestSeq 
   * @param 申请单名称 name 
   * @param 备注 notes 
   */
  function updateZCCRequest(requestSeq, requestName, notes) {
    $.ajax({
      url: 'http://localhost:8251/zoningChangeManager/updateZCCRequest?seq=' + requestSeq +
        '&name=' + requestName + '&notes=' + notes,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        console.log(res);

        if (res.rtnCode === "000000") {
          rzc_weihu_vm.data.isShowPrompt = false;
          buildRequest();
        }
      }

    })
    avalon.scan(document.body);
  }

  /**
   * 导出变更明细对照数据接口  excel表格式
   * @param 申请单序号 requestSeq 
   */
  function exportExcel(requestSeq){
    window.location.href = 'http://localhost:8251/zoningChangeManager/exportExcel?seq='+requestSeq 
  }
})