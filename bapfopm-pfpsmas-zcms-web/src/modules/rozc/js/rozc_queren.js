define(['avalon','jquery','bootstrap', 'ajaxConfig', 'text!./rozc_queren.js'], function (avalon,_rozc_queren) {
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

      
    }
  })

})