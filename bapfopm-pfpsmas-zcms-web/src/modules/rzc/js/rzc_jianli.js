define(['avalon','jquery','bootstrap', 'text!./rzc_jianli.js'], function (avalon,$,_rzc_jianli) {
  avalon.templateCache._rzc_jianli = _rzc_jianli;

  var rzc_jianli_vm = avalon.define({
    $id: "rzc_jianli",
    data: {
      navbar: [
        {
          name: "建立变更对照表",
          routerPath: "#!/rzc/rzc_jianli",
          imgPath: "./src/modules/rzc/img/blue.png"
        },
        {
          name: "录入变更明细",
          routerPath: "#!/rzc/rzc_luru",
          imgPath: "./src/modules/rzc/img/black.png"
        },
        {
          name: "维护变更对照表",
          routerPath: "#!/rzc/rzc_weihu",
          imgPath: "./src/modules/rzc/img/black.png"
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
      change_control: [],
      isShowPrompt: false,

      //  用户申请单信息查询
      zoningCode: "",

      pageSize: 5,  //  每页条数
      pageIndex: 1, //  当前页
      totalRecord: 0, //  总条数
      totalPage: 0, //  总页数

      //  建立建立申请单
      name: "",  //  申请单名称
      notes: "",  //  备注
      levelCode: "370102",  // 区划代码
    },

    
    /**
     * 建立申请单
     */
    getAddZoningChangeRequest: function(){
      addZoningChangeRequest(rzc_jianli_vm.data.name,rzc_jianli_vm.data.levelCode,rzc_jianli_vm.data.notes);
    },

    
    /**
     * 隐藏提示框
     */
    hiddenPrompt: function(){
      rzc_jianli_vm.data.isShowPrompt = false;
    },

    /**
     * 显示提示框
     */
    showPrompt: function(){
      rzc_jianli_vm.data.isShowPrompt = true;
    }
    
  })

  /**
   * 获取区划代码接口   
   * 默认值为 370102000000000
   */
  function buildRequest(){
    $.ajax({
      url: 'http://localhost:8251/zoningChangeManager/buildRequest',
      type: 'GET',
      success: function(da){
        var res = JSON.parse(da);
        // console.log(res);

        if(res.rtnCode === "000000"){
          rzc_jianli_vm.data.levelCode = res.responseData.levelCode;
          rzc_jianli_vm.data.zoningCode = res.responseData.zoningCode;
          ZoningChangeRequestList(rzc_jianli_vm.data.zoningCode, rzc_jianli_vm.data.pageSize, rzc_jianli_vm.data.pageIndex, rzc_jianli_vm.data.total);
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
    // console.log(zoningCode);
    total = total || 0;
    $.ajax({
      url: 'http://localhost:8251/zoningChangeManager/ZoningChangeRequestList?zoningCode='+zoningCode
      +'&pageSize='+pageSize+'&pageIndex='+pageIndex+'&total='+total,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);  
        // console.log(res);
        rzc_jianli_vm.data.change_control = res.responseData.dataList; 
      }
    })
  }

  /**
   * 建立申请单接口
   * @param 申请单名字 name 
   * @param 上报区划的级别代码 levelCode 
   * @param 备注 notes 
   */
  function addZoningChangeRequest(name,levelCode,notes){
    $.ajax({
      url: 'http://localhost:8251/zoningChangeManager/addZoningChangeRequest',
      type: 'POST',
      data: {
        name: name,
        levelCode: levelCode,
        notes: notes,
      },
      success: function(da){
        var res = JSON.parse(da);
        console.log(res);

        if(res.rtnCode === "999999"){
          alert(res.rtnMessage+"---存在未经过审核的区划变更申请单，不可继续添加区划变更申请单！");
        }

        buildRequest();
      }
    })
  }
})