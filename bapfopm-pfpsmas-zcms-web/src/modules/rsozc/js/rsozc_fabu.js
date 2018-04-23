define(['avalon','jquery','bootstrap', 'ajaxConfig', 'text!./rsozc_fabu.js'], function (avalon,_rsozc_fabu) {
  avalon.templateCache._rsozc_fabu = _rsozc_fabu;

  var rsozc_fabu_vm = avalon.define({
    $id: "rsozc_fabu",
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
          imgPath: "./src/modules/rzc/img/gray.png"
        },
        {
          name: "发布区划",
          routerPath: "#!/rsozc/rsozc_fabu",
          imgPath: "./src/modules/rzc/img/blue.png"
        },
      ],
    }
  })

})