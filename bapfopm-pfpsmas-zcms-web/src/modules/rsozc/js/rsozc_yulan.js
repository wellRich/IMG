define(['avalon', 'jquery', 'bootstrap', 'ajaxConfig', 'text!./rsozc_yulan.js'], function (avalon, _rsozc_yulan) {
  avalon.templateCache._rsozc_yulan = _rsozc_yulan;

  var rsozc_yulan_vm = avalon.define({
    $id: "rsozc_yulan",
    data: {
      navbar: [
        {
          name: "区划代码预览",
          routerPath: "#!/rsozc/rsozc_yulan",
          imgPath: "./src/modules/rzc/img/blue.png"
        },
        {
          name: "国家审核",
          routerPath: "#!/rsozc/rsozc_shenhe",
          imgPath: "./src/modules/rzc/img/black.png"
        },
        {
          name: "发布区划",
          routerPath: "#!/rsozc/rsozc_fabu",
          imgPath: "./src/modules/rzc/img/black.png"
        },
      ],

      zoningCode: "370000000000000", //  用户区划代码

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
      rsozc_yulan_vm.data.previewZoningCode = $(this).data('previewzoningcode');
      rsozc_yulan_vm.data.previewZoningName = $(this).data('previewzoningname');
      rsozc_yulan_vm.data.previewAssigningcode = $(this).data('previewassigningcode');

      clearData(rsozc_yulan_vm.data.previewAssigningcode, rsozc_yulan_vm.data.codeRankPreview);
      subordinateZoning(rsozc_yulan_vm.data.previewZoningCode);
    },

    getCheckFormalZoning: function () {
      rsozc_yulan_vm.data.formalZoningCode = $(this).data('formalzoningcode');
      rsozc_yulan_vm.data.formalZoningName = $(this).data('formalzoningname');
      rsozc_yulan_vm.data.formalwAssigningcode = $(this).data('formalwassigningcode');

      clearData(rsozc_yulan_vm.data.formalwAssigningcode, rsozc_yulan_vm.data.codeRankFormal);
      checkFormalZoning(rsozc_yulan_vm.data.formalZoningCode);
    }
  })

  initPreviewZoningData(rsozc_yulan_vm.data.zoningCode);
  initFormalZoningData(rsozc_yulan_vm.data.zoningCode);

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
  function clearData(levelCode, codeRank) {
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
      url: 'http://'+ ip +':'+ port +'/queryZoningData/initPreviewZoningData?zoningCode=' + zoningCode,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        // console.log(res);
        if (res.rtnCode === "000000") {
          var dataCode = res.responseData;
          placeData(dataCode, rsozc_yulan_vm.data.codeRankPreview);
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
      url: 'http://'+ ip +':'+ port +'/zoningChangeManager/getSubordinateZoning?zoningCode=' + zoningCode,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        console.log(res);
        if (res.rtnCode === "000000") {
          var dataCode = res.responseData;
          placeData(dataCode, rsozc_yulan_vm.data.codeRankPreview);
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
      url: 'http://'+ ip +':'+ port +'/queryZoningData/initFormalZoningData?zoningCode=' + zoningCode,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        // console.log(res);
        if (res.rtnCode === "000000") {
          var dataCode = res.responseData;
          placeData(dataCode, rsozc_yulan_vm.data.codeRankFormal);
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
      url: 'http://'+ ip +':'+ port +'/queryZoningData/checkFormalZoning?zoningCode=' + zoningCode,
      type: 'GET',
      success: function (da) {
        var res = JSON.parse(da);
        console.log(res);
        if (res.rtnCode === "000000") {
          var dataCode = res.responseData;
          placeData(dataCode, rsozc_yulan_vm.data.codeRankFormal);
        }
      }
    })
  }
})