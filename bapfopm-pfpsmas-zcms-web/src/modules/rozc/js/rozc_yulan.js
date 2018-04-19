define(['avalon', 'jquery', 'bootstrap', 'text!./rozc_yulan.js'], function (avalon, _rozc_yulan) {
  avalon.templateCache._rozc_yulan = _rozc_yulan;

  var rozc_yulan_vm = avalon.define({
    $id: "rozc_yulan",
    data: {
      navbar: [
        {
          name: "区划代码预览",
          routerPath: "#!/rozc/rozc_yulan",
          imgPath: "./src/modules/rzc/img/blue.png"
        },
        {
          name: "省级审核",
          routerPath: "#!/rozc/rozc_shenhe",
          imgPath: "./src/modules/rzc/img/black.png"
        },
        {
          name: "省级确认",
          routerPath: "#!/rozc/rozc_queren",
          imgPath: "./src/modules/rzc/img/black.png"
        },
      ],

      zoningCode: "000000000000000", //  用户区划代码

      //  获取下级区划预览数据
      previewZoningCode: "", //  本级预览区划代码
      previewZoningName: "", //  本级预览区划名称
      previewAssigningcode: "", //  本级预览级次代码

      //  获取下级区划正式数据
      formalZoningCode: "", //  本级正式区划代码
      formalZoningName: "", //  本级正式区划名称
      formalAssigningcode: "", //  本级正式级次代码

      //  省,市,县,乡,村,组   各级区划预览数据存放
      codeRankPreview: {
        "province": [],
        "city": [],
        "county": [],
        "township": [],
        "village": [],
        "group": [],
      },

      //  省,市,县,乡,村,组   各级区划正式数据存放
      codeRankFormal: {
        "province": [],
        "city": [],
        "county": [],
        "township": [],
        "village": [],
        "group": [],
      },
    },

    getSubordinateZoning: function () {
      rozc_yulan_vm.data.previewZoningCode = $(this).data('previewzoningcode');
      rozc_yulan_vm.data.previewZoningName = $(this).data('previewzoningname');
      rozc_yulan_vm.data.previewAssigningcode = $(this).data('previewassigningcode');
      
      clearData(rozc_yulan_vm.data.previewAssigningcode, rozc_yulan_vm.data.codeRankPreview);
      subordinateZoning(rozc_yulan_vm.data.previewZoningCode);
    },

    getCheckFormalZoning: function () {
      rozc_yulan_vm.data.formalZoningCode = $(this).data('formalzoningcode');
      rozc_yulan_vm.data.formalZoningName = $(this).data('formalzoningname');
      rozc_yulan_vm.data.formalwAssigningcode = $(this).data('formalwassigningcode');

      clearData(rozc_yulan_vm.data.formalwAssigningcode, rozc_yulan_vm.data.codeRankFormal);
      checkFormalZoning(rozc_yulan_vm.data.formalZoningCode);
    }
  })

  initPreviewZoningData(rozc_yulan_vm.data.zoningCode);
  initFormalZoningData(rozc_yulan_vm.data.zoningCode);

  /**
   * 根据键值将数据放置对应的省市县区域代码中
   * @param {Object} obj 区划变更明细对照数据录入界面接口返回数据 
   * @param {Object} codeRank 各级区划存放的数组对象 
   */
  function placeData(obj, codeRank) {
    for (var key in obj) {
      // console.log(typeof key, obj[key]);
      switch (key) {
        case "1":
          codeRank.province = obj[key]
          break;
        case "2":
          codeRank.city = obj[key]
          break;
        case "3":
          codeRank.county = obj[key]
          break;
        case "4":
          codeRank.township = obj[key]
          break;
        case "5":
          codeRank.village = obj[key]
          break;
        case "6":
          codeRank.group = obj[key]
          break;
        default:
          break;
      }
    }
  }

  /**
   * 清空子级以下的区划数据
   * @param {string} levelCode 本级级次代码
   * @param {*} codeRank 各级区划存放的数组对象
   */
  function clearData(levelCode, codeRank){
    switch (levelCode) {
      case 1: codeRank.county = codeRank.township = codeRank.village = codeRank.group = []
        break;
      case 2: codeRank.township = codeRank.village = codeRank.group = []
        break; 
      case 3: codeRank.village = codeRank.group = []
        break;
      case 4: codeRank.group = []
        break; 
      default: break;
    }
  }

  /**
   * 初始化区划预览数据接口
   * @param {string} zoningCode 登录人区划代码
   */
  function initPreviewZoningData(zoningCode) {
    // console.log(zoningCode);
    $.ajax({
      url: 'http://localhost:8251/queryZoningData/initPreviewZoningData?zoningCode=' + zoningCode,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        // console.log(res);
        if (res.rtnCode === "000000") {
          var dataCode = res.responseData;
          placeData(dataCode, rozc_yulan_vm.data.codeRankPreview);
        } else {
          alert(res.rtnMessage);
        }
      }
    })
  }

  /**
   * 获取下级区划预览数据接口
   * @param {string} zoningCode 区划代码
   */
  function subordinateZoning(zoningCode) {
    $.ajax({
      url: 'http://localhost:8251/zoningChangeManager/getSubordinateZoning?zoningCode=' + zoningCode,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        console.log(res);
        if (res.rtnCode === "000000") {
          var dataCode = res.responseData;
          placeData(dataCode, rozc_yulan_vm.data.codeRankPreview);
        }
      }
    })
  }

  /**
   * 初始化区划正式数据接口
   * @param {string} zoningCode 登录人区划代码
   */
  function initFormalZoningData(zoningCode) {
    $.ajax({
      url: 'http://localhost:8251/queryZoningData/initFormalZoningData?zoningCode=' + zoningCode,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        // console.log(res);
        if (res.rtnCode === "000000") {
          var dataCode = res.responseData;
          placeData(dataCode, rozc_yulan_vm.data.codeRankFormal);
        } else {
          alert(res.rtnMessage);
        }
      }
    })
  }

  /**
   * 获取下级区划正式数据
   * @param {string} zoningCode 区划代码
   */
  function checkFormalZoning(zoningCode) {
    $.ajax({
      url: 'http://localhost:8251/queryZoningData/checkFormalZoning?zoningCode=' + zoningCode,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        console.log(res);
        if (res.rtnCode === "000000") {
          var dataCode = res.responseData;
          placeData(dataCode, rozc_yulan_vm.data.codeRankFormal);
        }
      }
    })
  }
})