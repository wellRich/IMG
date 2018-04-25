define(['avalon', 'jquery', 'bootstrap', 'ajaxConfig', 'text!./rozc_queren.js'], function (avalon, _rozc_queren) {
  avalon.templateCache._rozc_queren = _rozc_queren;

  var rozc_queren_vm = avalon.define({
    $id: "rozc_queren",
    data: {
      navbar: [
        {
          name: "区划代码预览",
          routerPath: "#!/rozc/rozc_yulan",
          imgPath: "./src/modules/rzc/img/gray.png"
        },
        {
          name: "省级审核",
          routerPath: "#!/rozc/rozc_shenhe",
          imgPath: "./src/modules/rzc/img/gray.png"
        },
        {
          name: "省级确认",
          routerPath: "#!/rozc/rozc_queren",
          imgPath: "./src/modules/rzc/img/blue.png"
        },
      ],
      changeControl: [],  //  变更申请单存放位置

      isShowPrompt: false, //  审批意见添加提示框显隐开关

      //  用户申请单信息查询
      zoningCode: "", //  区划代码

      pageSize: 5,  //  每页条数
      pageIndex: 1, //  当前页
      totalRecord: 0, //  总条数
      totalPage: 0, //  总页数

      //  省级确认
      requestSeq: "", //  申请单序号
      isPassed: "", //  确认 true 驳回 false
      msg: "审核确认通过!",  //  审批意见  默认为 "审核确认通过!"
    },

    /**
     * 隐藏提示框
     */
    hiddenPrompt: function () {
      rozc_queren_vm.data.isShowPrompt = false;
    },

    /**
     * 显示提示框
     */
    showPrompt: function () {
      var aCheckbox = $('input[type=checkbox]:checked');

      var flag = true;
      for (var i = 0; i < aCheckbox.length; i++) {
        if ($(aCheckbox[i]).data('status') === undefined) { //  排除全选按钮
          continue;
        } else if ($(aCheckbox[i]).data('status') !== 20) {  //  判断申请单状态是否为 审核通过
          flag = false;
          break;
        }
      }
      // console.log(flag);

      if (aCheckbox.length === 0) { //  是否选择申请单
        alert('欧尼酱,先选择申请单,才能点击我哟!');
      } else if (flag === false) {  //  申请单状态是否全是 已上传
        alert('欧尼酱,我生气啦,申请单只能全部是"审核通过"状态,才能点击我,哼!')
      } else {
        rozc_queren_vm.data.isShowPrompt = true;
        rozc_queren_vm.data.isPassed = $(this).data('ispassed');
        console.log(rozc_queren_vm.data.isPassed);
      }
    },

    /**
     * 全选实现
     */
    getSelectAll: function () {
      selectAll();
    },

    /**
     * 判断是否全选
     */
    getIsSelectAll: function () {
      isSelectAll();
    },

    getProvincialConfirm: function () { 
      var seqStr = "";
      var isPassed = rozc_queren_vm.data.isPassed;
      var msg = rozc_queren_vm.data.msg;      
      // console.log(isPassed);
      var aCheckbox = $('input[type=checkbox]:checked');

      //  获取的是一个伪数组  map,reduce不适用,后面再优化
      for (var i = 0; i < aCheckbox.length; i++) {
        //  排除全选状态时获取到全选按钮的undefined
        if ($(aCheckbox[i]).data('requestseq')) {
          seqStr += $(aCheckbox[i]).data('requestseq') + ","
        }
      }
      seqStr = seqStr.substr(0, seqStr.length - 1);
      console.log(seqStr);
      console.log(msg);

      provincialConfirm(seqStr,isPassed,msg);
    },

    getRefresh: function(){
      listOfApprovedZCCReq(rozc_queren_vm.data.zoningCode, rozc_queren_vm.data.pageSize, rozc_queren_vm.data.pageIndex, rozc_queren_vm.data.total);
    }
  })

  /**
   * 全选功能实现
   */
  function selectAll() {
    var aCheckbox = $('input[name=fileCheck]');
    var flag = false; //  默认未选中 ,只要有一个未选中 就变成true  点击全选  自然都为true

    for (var i = 0; i < aCheckbox.length; i++) {
      if (!$(aCheckbox[i]).prop('checked')) {
        flag = true;
        break;
      }
    }

    for (var k = 0; k < aCheckbox.length; k++) {
      $(aCheckbox[k]).prop('checked', flag);
    }
  }

  /**
   * 判断是否为全选状态
   */
  function isSelectAll() {
    var aCheckbox = $('input[name=fileCheck]');
    var flag = false; //  默认不是全选, 必须都选中,才变为 true

    for (var i = 0; i < aCheckbox.length; i++) {
      if ($(aCheckbox[i]).prop('checked')) {
        flag = true;
      } else {
        flag = false;
        break;
      }
    }
    $('#fileSquenceAll').prop('checked', flag);
  }

  /**
   * 获取区划代码接口
   */
  function buildRequest() {
    $.ajax({
      url: 'http://' + ip + ':' + port + '/zoningChangeManager/buildRequest',
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        // console.log(res);

        if (res.rtnCode === "000000") {
          rozc_queren_vm.data.zoningCode = res.responseData.zoningCode;
          listOfApprovedZCCReq(rozc_queren_vm.data.zoningCode, rozc_queren_vm.data.pageSize, rozc_queren_vm.data.pageIndex, rozc_queren_vm.data.total);
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
  function listOfApprovedZCCReq(zoningCode, pageSize, pageIndex, total) {
    // console.log(zoningCode);
    total = total || 0;
    $.ajax({
      url: 'http://' + ip + ':' + port + '/zoningChangeManager/listOfApprovedZCCReq?zoningCode=' + zoningCode
        + '&pageSize=' + pageSize + '&pageIndex=' + pageIndex + '&total=' + total,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        console.log(res);
        rozc_queren_vm.data.changeControl = res.responseData.dataList;
      }
    })
  }

  /**
   * 省级确认驳回接口
   * @param {string} seqStr 
   * @param {boolean  } isPassed 
   * @param {string} msg 
   */
  function provincialConfirm(seqStr, isPassed, msg) {
    msg = msg || "审核确认通过";
    $.ajax({
      url: 'http://' + ip + ':' + port + '/zoningChangeManager/provincialConfirm?seqStr=' + seqStr
        + '&isPassed=' + isPassed + '&msg=' + msg,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        console.log(res);
        if(res.rtnCode === "000000"){
          rozc_queren_vm.data.isShowPrompt = false;
          listOfApprovedZCCReq(rozc_queren_vm.data.zoningCode, rozc_queren_vm.data.pageSize, rozc_queren_vm.data.pageIndex, rozc_queren_vm.data.total);

        }
      }
    })
  }
})