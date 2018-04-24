define(['avalon', 'jquery', 'bootstrap', 'ajaxConfig', 'text!./rsozc_shenhe.js'], function (avalon, _rsozc_shenhe) {
  avalon.templateCache._rsozc_shenhe = _rsozc_shenhe;

  var rsozc_shenhe_vm = avalon.define({
    $id: "rsozc_shenhe",
    data: {
      navbar: [
        {
          name: "区划代码预览",
          routerPath: "#!/rsozc/rsozc_yulan",
          imgPath: "./src/modules/rzc/img/gray.png"
        },
        {
          name: "国家审核",
          routerPath: "#!/rsozc/rsozc_shenhe",
          imgPath: "./src/modules/rzc/img/blue.png"
        },
        {
          name: "发布区划",
          routerPath: "#!/rsozc/rsozc_fabu",
          imgPath: "./src/modules/rzc/img/black.png"
        },
      ],

      //  申请单查询
      zoningCode: "000000000000000",

      pageSize: 5,  //  每页条数
      pageIndex: 1, //  当前页
      totalRecord: 0, //  总条数
      totalPage: 0, //  总页数

      //  国家审核
      requestSeq: "", //  申请单序号
      isPassed: "", //  审核通过 true false
      msg: "审核通过!",  //  审批意见  默认为 "审核通过!"

      isShowPrompt: false,
      
    },

    getNationalCheck: function(){

    },

    /**
     * 隐藏提示框
     */
    hiddenPrompt: function () {
      rsozc_shenhe_vm.data.isShowPrompt = false;
    },

    /**
     * 显示提示框
     */
    showPrompt: function(){
      rsozc_shenhe_vm.data.isShowPrompt = true;
    },
  })

  //  初始化申请单数据信息
  zoningChangeRequestList(rsozc_shenhe_vm.data.zoningCode, rsozc_shenhe_vm.data.pageSize, rsozc_shenhe_vm.data.pageIndex, rsozc_shenhe_vm.data.totalRecord);

  /**
   * 用户申请单查询接口
   * @param {string} zoningCode 区划代码
   * @param {number} pageSize 每页显示数
   * @param {number} pageIndex 当前页码
   * @param {number} total 总条数
   */
  function zoningChangeRequestList(zoningCode, pageSize, pageIndex, total) {
    $.ajax({
      url: 'http://' + ip + ':' + port + '/zoningChangeManager/ZoningChangeRequestList?zoningCode='
        + zoningCode + '&pageSize=' + pageSize + '&pageIndex=' + pageIndex + '&total=' + total,
      type: 'GET',
      success: function(da){
        var res = JSON.parse(da);
        console.log(res);
      }
    })
  }

  /**
   * 国家审核接口
   * @param {string} seqStr 若干申请单序号,以“,”分隔
   * @param {boolean} isPassed 是否通过 
   * @param {string} msg 审批意见
   */
  function nationalCheck(seqStr, isPassed, msg) {
    msg = msg || "审核通过!";
    $.ajax({
      url: 'http://' + ip + ':' + port + '/zoningChangeManager/nationalCheck?seqStr=' + seqStr
        + '&isPassed=' + isPassed + '&msg=' + msg,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
      }
    })
  }

})