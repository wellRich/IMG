//  require 配置
require.config({
    baseUrl: "./src/",
    paths: {
        jquery: 'js/jquery.min',
        bootstrap: 'js/bootstrap.min',

        bootstrapDatetimepicker: 'js/bootstrap-datetimepicker',
        bootstrapDatetimepickerZHCN: 'js/bootstrap-datetimepicker.zh-CN',
        scroll: 'js/scroll.min',
        feeds: 'js/feeds.min',

        ztreeCore: 'js/vendor/ztree/jquery.ztree.core',

        zTreeCheck: 'js/vendor/ztree/jquery.ztree.excheck.min',

        zTreeSearch: 'js/vendor/ztree/jquery.ztree.search',

        functions: 'js/functions',

        ajaxConfig: 'js/ajax.config',
        avalonCommon: 'js/avalon.common',

        avalon: ["js/vendor/avalon/avalon"],//必须修改源码，禁用自带加载器，或直接删提AMD加载器模块
        mmHistory: 'js/vendor/avalon/mmHistory',
        mmRouter: 'js/vendor/avalon/mmRouter',
        text: ['js/vendor/require/text'],
        domReady: 'js/vendor/require/domReady',
    },
    priority: ['text'],
    shim: {
        'bootstrap': {
            deps: ['jquery',]
        },
        'bootstrapDatetimepicker': {
            deps: ['jquery', 'bootstrap']
        },
        'bootstrapDatetimepickerZHCN': {
            deps: ['jquery', 'bootstrap', 'bootstrapDatetimepicker']
        },
        'scroll': {
            deps: ['jquery']
        },
        'feeds': {
            deps: ['jquery']
        },
        'ztreeCore': {
            deps: ['jquery']
        },
        'zTreeCheck': {
            deps: ['ztreeCore']
        },
        'zTreeSearch': {
            deps: ['ztreeCore']
        }
    }
});

require(['avalon', 'avalonCommon', 'functions', 'bootstrapDatetimepicker', 'bootstrapDatetimepickerZHCN', 'ztreeCore', 'mmRouter', "domReady!"], function (mmRouter, domReady) {
// require(['mmRouter', "domReady!"], function (mmRouter, domReady) {

    avalon.log("引入avalon");

    var model = avalon.define({
        $id: "root",
        pageUrl: "",

        //  主页数据存放
        data: {
            profileMenu: [{name: '用户管理'}, {name: '菜单管理'}, {name: '角色管理'}],

            sideMenu: [
                {name: '生育和家庭服务管理', nickname: '人口计生', className: 'sa-side-home', subMenu: []},
                {name: '流动人口服务管理', nickname: '流动人口', className: 'sa-side-typography', subMenu: []},
                {name: '内部综合管理', nickname: '内部综合', className: 'sa-side-widget', subMenu: []},
                {name: '监测分析', nickname: '监测分析', className: 'sa-side-table', subMenu: []},

                {
                    name: '区划代码管理', nickname: '区划代码', className: 'sa-side-form', subMenu: [
                        {
                            name: '区划代码接收', subMenu: [
                                {name: '集中上报', routerName: '#!/rzc/rzc_shangchuan'},
                                {name: '在线上报', routerName: '#!/rzc/rzc_jianli'},
                                {name: '接口上报', routerName: '#!/rzc/rzc_jiekou'},
                                {name: '导入民政区划', routerName: '#!/rzc/rzc_daoru'}
                            ]
                        },
                        {
                            name: '区划代码更新', subMenu: [
                                {name: '区划代码预览', routerName: '#!/rozc/rozc_yulan'},
                                {name: '省级审核', routerName: '#!/rozc/rozc_shenhe'},
                                {name: '省级确认', routerName: '#!/rozc/rozc_queren'},
                            ]
                        },
                        {
                            name: '区划代码发布', subMenu: [
                                {name: '区划代码预览', routerName: '#!/rsozc/rsozc_yulan'},
                                {name: '国家审核', routerName: '#!/rsozc/rsozc_shenhe'},
                                {name: '发布区划', routerName: '#!/rsozc/rsozc_fabu'},
                            ]
                        },
                        {
                            name: '区划变更批复文件管理', subMenu: [
                                {name: '批复文件上传', routerName: '#!/zccrfm/zccrfm_shangchuan'},
                                {name: '上传情况统计', routerName: '#!/zccrfm/zccrfm_tongji'},
                                {name: '批复文件管理', routerName: '#!/zccrfm/zccrfm_guanli'},
                            ]
                        },
                        {
                            name: '区划接口', subMenu: [
                                {name: '实时接口查询', routerName: '#!/zcif/zcif_chaxun'},
                                {name: '发布区划下载', routerName: '#!/zcif/zcif_xiazai'},
                            ]
                        },

                    ]
                },
            ],
            delNav: [],
            routerPath: "",
        },
        getRouterName: function () {
            model.data.delNav.push(this.lastElementChild.innerText);
            model.data.delNav = [...new Set(model.data.delNav)];
        },
        showToggleChange: function () {
            // console.log(this.previousElementSibling.innerText);
            sIndex = model.data.delNav.indexOf(this.previousElementSibling.innerText);
            model.data.delNav.splice(sIndex, 1);
        },
        getRouterPath: function () {
            //  设置一个data-router 属性   获取路由地址
            var router = document.querySelector(".scroll-window").firstElementChild.getAttribute("data-router");
            model.data.routerPath = router;
            console.log(model.data.routerPath);
        }
    })


    //导航回调
    function callback() {
        var jspath = "modules";                //  这个是js路径的变量
        var pagepath = "./src/modules";       //  这个是网页的变量

        if (this.path === "/") {
            jspath = "";
            pagepath = "";
        } else {
            var paths = this.path.split("/");
            for (var i = 0; i < paths.length; i++) {
                if (paths[i] != "") {
                    jspath += "/" + paths[i];
                    pagepath += "/" + paths[i];
                    if (i === paths.length - 2) {
                        jspath += "/js";
                        pagepath += "/html";
                    }
                }
            }
        }

        // console.log(jspath);
        // console.log(pagepath);

        require([jspath], function (page) {
            //  根据路由判断初始加载页面
            avalon.vmodels.root.pageUrl = (pagepath !== "") ? (pagepath + ".html") : "";
        });
    }

    //  配置路由规则
    avalon.router.get("/*path", callback);

    avalon.history.start({
        basepath: "/avalon"
    })
    avalon.scan()
    $('.second-menu').hover(function () {
        $('#ascrail2000').css('z-index', 1)

    })
});

document.documentElement.style.overflowY = 'hidden';

