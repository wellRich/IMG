define(['avalon', 'jquery', 'bootstrap', 'ajaxConfig', 'text!./rozc_shenhe.js'], function (avalon, _rozc_shenhe) {
  avalon.templateCache._rozc_shenhe = _rozc_shenhe;

  var rozc_shenhe_vm = avalon.define({
    $id: "rozc_shenhe",
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
          imgPath: "./src/modules/rzc/img/blue.png"
        },
        {
          name: "省级确认",
          routerPath: "#!/rozc/rozc_queren",
          imgPath: "./src/modules/rzc/img/black.png"
        },
      ],

      changeControl: [],  //  申请变更单存放位置
      details: [],  // 变更对照明细数据存放位置

      isShowPrompt: false, //  审批意见添加提示框显隐开关

      //  用户申请单信息查询
      zoningCode: "", //  区划代码
      requestSeq: "", //  申请单序号
      status: "", //  申请单状态

      pageSize: 10,  //  每页条数
      pageIndex: 1, //  当前页
      totalRecord: 0, //  总条数
      totalPage: 1, //  总页数

      //  省级审核
      requestSeq: "", //  申请单序号
      isPassed: "", //  审核通过 true false
      msg: "审核通过!",  //  审批意见  默认为 "审核通过!"
    },

    /**
     * 向上翻页
     * 申请单展示
     */
    getPreviousDate: function () {
      var zoningCode = rozc_shenhe_vm.data.zoningCode;
      var pageIndex = rozc_shenhe_vm.data.pageIndex;
      var totalRecord = rozc_shenhe_vm.data.totalRecord;
      var pageSize = rozc_shenhe_vm.data.pageSize;

      pageIndex = (pageIndex === 1) ? pageIndex : pageIndex - 1;
      console.log(pageIndex);
      listOfSubmittedZCCReq(zoningCode, pageSize, pageIndex, totalRecord);
      rozc_shenhe_vm.data.pageIndex = pageIndex;
    },

    /**
     * 向下翻页
     * 申请单展示
     */
    getNextDate: function () {
      var zoningCode = rozc_shenhe_vm.data.zoningCode;
      var pageIndex = rozc_shenhe_vm.data.pageIndex;
      var pageSize = rozc_shenhe_vm.data.pageSize;
      var totalPage = rozc_shenhe_vm.data.totalPage;
      var totalRecord = rozc_shenhe_vm.data.totalRecord;

      pageIndex = (pageIndex === totalPage) ? pageIndex : pageIndex + 1;

      listOfSubmittedZCCReq(zoningCode, pageSize, pageIndex, totalRecord);
      rozc_shenhe_vm.data.pageIndex = pageIndex;
    },

    /**
     * 获取点击当前页数据
     * 申请单展示
     */
    getCurrentDate: function () {
      var zoningCode = rozc_shenhe_vm.data.zoningCode;
      var pageSize = rozc_shenhe_vm.data.pageSize;
      var totalRecord = rozc_shenhe_vm.data.totalRecord;
      var pageIndex = Number($(this).text());

      listOfSubmittedZCCReq(zoningCode, pageSize, pageIndex, totalRecord);
      rozc_shenhe_vm.data.pageIndex = pageIndex;
      rozc_shenhe_vm.data.zoningCode = zoningCode;
    },

    getCheckDetails: function(){
      var requestSeq = rozc_shenhe_vm.data.requestSeq = "";
      if($('input[type=checkbox]:checked').length === 0){
        alert('欧妮桑,请选择一个申请单查询哟!');
      }else if($('input[type=checkbox]:checked').length > 1){
        alert('欧妮桑,你太贪心了,只能选择一个申请单,哼唧哼唧!')
      }else{
        requestSeq = $('input[type=checkbox]:checked').data('requestseq');
        console.log(requestSeq);
        checkDetails(requestSeq);
      }
    },

    getSelectAll: function(){
      selectAll();
    },

    getIsSelectAll: function(){
      isSelectAll();
    },

    /**
     * 隐藏提示框
     */
    hiddenPrompt: function () {
      rozc_shenhe_vm.data.isShowPrompt = false;
    },

    /**
     * 显示提示框
     */
    showPrompt: function () {
      var aCheckbox = $('input[type=checkbox]:checked');

      var flag = true;
      for(var i = 0 ; i < aCheckbox.length ; i++){
        if($(aCheckbox[i]).data('status') === undefined){
          continue;
        }else if($(aCheckbox[i]).data('status') !== 11){
           flag = false;
           break;
        }
      }
      console.log(flag);

      if (aCheckbox.length === 0) { //  是否选择申请单
        alert('欧尼酱,先选择申请单,才能点击我哟!');
      } else if(flag === false){  //  申请单状态是否全是 已上传
        alert('欧尼酱,我生气啦,申请单只能全部是已上传状态,才能点击我,哼!')
      }else {
        rozc_shenhe_vm.data.isShowPrompt = true;
        rozc_shenhe_vm.data.isPassed = $(this).data('ispassed');
      }
    },

    /**
     * 省级审核
     */
    getProvincialCheck: function () {
      var seqStr = "";
      var isPassed = rozc_shenhe_vm.data.isPassed;
      var msg = rozc_shenhe_vm.data.msg;      
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
      // console.log(seqStr);
      // console.log(msg);

      provincialCheck(seqStr,isPassed,msg);
    },

    /**
     * 导出导出变更明细对照数据
     */
    getExportExcel: function(){
      rozc_shenhe_vm.data.requestSeq = $('input[type=checkbox]:checked').data('requestseq');
      console.log(rozc_shenhe_vm.data.requestSeq);

        //  是否要查询有明细数据

        exportExcel(rozc_shenhe_vm.data.requestSeq);   
    },
  })

  /**
   * 全选功能实现
   */
  function selectAll(){
    var aCheckbox = $('input[name=fileCheck]');
    var flag = false; //  默认未选中 ,只要有一个未选中 就变成true  点击全选  自然都为true

    for(var i = 0 ; i < aCheckbox.length ; i++){
      if(!$(aCheckbox[i]).prop('checked')){
        flag = true;
        break;
      }
    }
  
    for(var k = 0 ; k < aCheckbox.length ; k++){
      $(aCheckbox[k]).prop('checked',flag);
    }
  }

  /**
   * 判断是否为全选状态
   */
  function isSelectAll(){
    var aCheckbox = $('input[name=fileCheck]');
    var flag = false; //  默认不是全选, 必须都选中,才变为 true

    for(var i = 0 ; i < aCheckbox.length ; i++){      
      if($(aCheckbox[i]).prop('checked')){
        flag = true;
      }else{
        flag = false;
        break;
      }
    }
    $('#fileSquenceAll').prop('checked', flag);
  }

  /**
   * 获取区划代码接口   
   * 默认值为 370102000000000
   */
  function buildRequest() {
    $.ajax({
      url: 'http://'+ ip +':'+ port +'/zoningChangeManager/buildRequest',
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        // console.log(res);

        if (res.rtnCode === "000000") {
          rozc_shenhe_vm.data.levelCode = res.responseData.levelCode;
          rozc_shenhe_vm.data.zoningCode = res.responseData.zoningCode;
          listOfSubmittedZCCReq(rozc_shenhe_vm.data.zoningCode, rozc_shenhe_vm.data.pageSize, rozc_shenhe_vm.data.pageIndex, rozc_shenhe_vm.data.totalRecord);
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
  function listOfSubmittedZCCReq(zoningCode, pageSize, pageIndex, total) {
    // console.log(zoningCode);
    total = total || 0;
    $.ajax({
      url: 'http://'+ ip +':'+ port +'/zoningChangeManager/listOfSubmittedZCCReq?zoningCode=' + zoningCode
        + '&pageSize=' + pageSize + '&pageIndex=' + pageIndex + '&total=' + total,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        // console.log(res);
        if (res.rtnCode === "000000") {
          rozc_shenhe_vm.data.changeControl = res.responseData.dataList;
          rozc_shenhe_vm.data.totalPage = res.responseData.totalPage;
        }
      }
    })
  }

  /**
   * 查看变更对照明细数据接口
   * @param 区划变更申请单序号 requestSeq 
   */
  function checkDetails(requestSeq){
    rozc_shenhe_vm.data.details = [];
    $.ajax({
      url: 'http://'+ ip +':'+ port +'/zoningChangeManager/checkDetails?seq=' + requestSeq,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        console.log(res);

        if (res.rtnCode === "000000") {
          if(res.responseData.dataList === null){
            alert("欧尼酱,我看不到数据!!!")
          }else{
            rozc_shenhe_vm.data.details = res.responseData.dataList;
            console.log(rozc_shenhe_vm.data.details);
          } 
        }
      }
    })
  }

  /**
   * 省级审核接口
   * @param {string} seqStr 若干申请单序号
   * @param {boolean} isPassed 是否通过
   * @param {string} msg 审批意见
   */
  function provincialCheck(seqStr, isPassed, msg) {
    msg = msg || "审核通过!";
    $.ajax({
      url: 'http://'+ ip +':'+ port +'/zoningChangeManager/provincialCheck?seqStr=' + seqStr
        + '&isPassed=' + isPassed + '&msg=' + msg,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        console.log(res);

        //  这里曾出现一个状态码显示 rtnCode:999999 点击打印对象 rtnCode: 000000 的bug
        if(res.rtnCode === "000000"){
          rozc_shenhe_vm.data.isShowPrompt = false;
          listOfSubmittedZCCReq(rozc_shenhe_vm.data.zoningCode, rozc_shenhe_vm.data.pageSize, rozc_shenhe_vm.data.pageIndex, rozc_shenhe_vm.data.totalRecord);
        }else if(res.rtnCode === "999999"){
          alert("欧尼酱,审批出现异常,嘤嘤嘤@_@");
        }
      }
    })
  }

  /**
   * 导出变更明细对照数据接口  excel表格式
   * @param 申请单序号 requestSeq 
   */
  function exportExcel(requestSeq){
    window.location.href = 'http://'+ ip +':'+ port +'/zoningChangeManager/exportExcel?seq='+requestSeq 
  }
})