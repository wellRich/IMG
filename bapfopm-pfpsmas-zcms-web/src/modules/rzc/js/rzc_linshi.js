define(['avalon', 'jquery', 'bootstrap', 'text!./rzc_linshi.js'], function (avalon, $, _rzc_linshi) {
  avalon.templateCache._rzc_linshi = _rzc_linshi;

  var rzc_linshi_vm = avalon.define({
    $id: "rzc_linshi",
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
          imgPath: "./src/modules/rzc/img/blue.png"
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
      upload: [],
      isShow: "",   //  信息展示框(无数据展示)显示隐藏开关

      //  查询信息
      totalCount: "", //  总条数
      pageSize: 4,  //  每页条数
      pageCount: 0,  //  总页数
      pageNum: 1,   //  当前页数

      //  文件状态修改
      typeCode: "22", //  22 --- 导入临时表处理中
    },

    //  向下翻页
    getNextDate: function () {
      var pageNum = rzc_linshi_vm.data.pageNum;
      var pageSize = rzc_linshi_vm.data.pageSize;
      var pageCount = rzc_linshi_vm.data.pageCount;
      // console.log(pageNum,pageSize,pageCount)

      pageNum = (pageNum === pageCount) ? pageNum : pageNum + 1;
      console.log(pageNum);
      queryFocusChangeFileInfo(pageNum, pageSize);
      rzc_linshi_vm.data.pageNum = pageNum;
    },

    //  向上翻页
    getPreviousDate: function () {
      var pageNum = rzc_linshi_vm.data.pageNum;
      var pageSize = rzc_linshi_vm.data.pageSize;
      // console.log(pageNum,pageSize);

      pageNum = (pageNum === 1) ? pageNum : pageNum - 1;
      console.log(pageNum);
      queryFocusChangeFileInfo(pageNum, pageSize);
      rzc_linshi_vm.data.pageNum = pageNum;
    },

    //  获取点击当前页数据
    getCurrentDate: function () {
      var pageSize = rzc_linshi_vm.data.pageSize;
      var pageNum = Number($(this).text());
      queryFocusChangeFileInfo(pageNum, pageSize);
      rzc_linshi_vm.data.pageNum = pageNum;
    },

    //  导入临时表(先修改文件状态 已上传==>导入临时表处理中)
    importTemporaryTables: function () {
      var fileSquence = $('input[type=radio]:checked').data('filesquence');
      var typeCode = $('input[type=radio]:checked').data('statuscode');
      console.log(typeCode);
      
      //  判断文件状态  是否为已上传
      if(typeCode === "10" || typeCode === 10){
        //  修改文件状态 并刷新数据
        modifyTypeCode(fileSquence, rzc_linshi_vm.data.typeCode);
      }else{
        alert("请选择正确的文件进行导入");
      }     
    }
  })

  avalon.scan(document.body);

  //  初始加载,就应显示已上传文件信息
  queryFocusChangeFileInfo(rzc_linshi_vm.data.pageNum, rzc_linshi_vm.data.pageSize);

  //  分页器函数
  function pagination() {
    var totalCount = rzc_linshi_vm.data.totalCount; //  总条数
    var pageCount = rzc_linshi_vm.data.pageCount; //  总页数
    var pageSize = rzc_linshi_vm.data.pageSize; //  每页显示数

    totalCount = Number(totalCount);
    pageCount = Math.ceil(totalCount / pageSize);
    return rzc_linshi_vm.data.pageCount = pageCount;
  };
  pagination();

  //  上传文件查询接口  
  function queryFocusChangeFileInfo(pageNum, pageSize) {
    $.ajax({
      url: 'http://localhost:8251/zoning/query/fileInfo?zoningCode=370000&&date=&&pageSize=' + pageSize + '&&pageNum=' + pageNum,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        if (res.rtnCode === "000000") {
          console.log(res, "文件查询:" + res.rtnMessage);
          rzc_linshi_vm.data.upload = res.responseData;
          rzc_linshi_vm.data.totalCount = res.rtnMessage;
          pagination();
        }
      }
    })
  }

  //  文件状态修改接口
  function modifyTypeCode(fileSquence, typeCode) {
    $.ajax({
      url: 'http://localhost:8251/zoning/update/modifyTypeCode?fileSquence='+fileSquence+'&typeCode='+typeCode,
      type: 'GET',

      success: function (da) {
        var res = JSON.parse(da);
        console.log(res, "文件状态修改" + res.rtnMessage);
        if(res.rtnCode === "000000"){
          //  如果修改成功  刷新数据显示 
          queryFocusChangeFileInfo(rzc_linshi_vm.data.pageNum, rzc_linshi_vm.data.pageSize);
          //  导入临时表
          importTemporary(fileSquence);
        }
      }
    })
  }

  //  导入临时表接口
  function importTemporary(fileSquence) {
    $.ajax({
      url: 'http://localhost:8251/importTemp/code/decompression?instructionCode=1&fileSquence=' + fileSquence,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        console.log(res, "导入临时表" + res.rtnMessage);
        if(res.rtnCode === "000000"){
          queryFocusChangeFileInfo(rzc_linshi_vm.data.pageNum, rzc_linshi_vm.data.pageSize);
        }
      }
    })
  }

  avalon.scan(document.body);
})