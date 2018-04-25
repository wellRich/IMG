define(['avalon', 'jquery', 'zTreeCheck', 'zTreeSearch', 'bootstrap', 'ajaxConfig', 'text!./rzc_luru.js'], function(avalon, _rzc_luru, ztree) {
    avalon.templateCache._rzc_luru = _rzc_luru;

    //gyk的函数命名空间
    var ZC = {};
    ZC.ADD = '11', ZC.CHANGE = '21', ZC.MERGE = '31', ZC.MOVE = '41';
    ZC.changeTypes = {
        '11': {value: ZC.ADD, label: '新增', display: 'black'},
        '21': {value: ZC.CHANGE, label: '变更', display: 'black'},
        '31': {value: ZC.MERGE, label: '并入'},
        '41': {value: ZC.MOVE, label: '迁移'}
    };

    var rzc_luru_vm = avalon.define({
        $id: "rzc_luru",
        data: {
            navbar: [{
                name: "建立变更对照表",
                routerPath: "#!/rzc/rzc_jianli",
                imgPath: "./src/modules/rzc/img/gray.png"
            }, {
                name: "录入变更明细",
                routerPath: "#!/rzc/rzc_luru",
                imgPath: "./src/modules/rzc/img/blue.png"
            }, {
                name: "维护变更对照表",
                routerPath: "#!/rzc/rzc_weihu",
                imgPath: "./src/modules/rzc/img/black.png"
            }, {
                name: "提交变更对照表",
                routerPath: "#!/rzc/rzc_tijiao",
                imgPath: "./src/modules/rzc/img/black.png"
            }, {
                name: "撤销变更对照表",
                routerPath: "#!/rzc/rzc_chexiao",
                imgPath: "./src/modules/rzc/img/black.png"
            }, ],
            //每组区划对应的长度
            maxLengths: [2, 2, 2, 3, 3, 3],

            //  省,市,县,乡,村,组 各级区划
            codeRank: {
                "province": [],
                "city": [],
                "county": [],
                "township": [],
                "village": [],
                "group": [],
            },

            translateAssigningCodes:["province",
                "city",
                "county",
                "township",
                "village",
                "group"
            ],

            //  变更明细数据存放位置
            details: [],
            group: {},

            zoningCode: "370102000000000", //  用户区划代码
            assigningCode: "", //  用户本身级次代码

            //  获取下级区划代码
            subZoningCode: "", //  本级区划代码
            subAssigningCode: "", //  用户选择地区的级次代码,用户只能查看下级区划代码

            //  变更明细数据
            originalZoningCodeArray: [], //  原区划代码分组展示的存放位置
            originalZoningCode: "", //  原区划代码
            originalZoningName: "", //  原区划名称
            targetZoningCodeArray: ["", "", "", "", "", ""], //  现区划代码分组展示的存放位置
            targetZoningCode: "", //   现区划代码
            targetZoningName: "", //  现区划名称

            notes: "", //  备注
            requestSeq: "", //  区划变更申请单序号
            requestName: "", //  变更组名称&&调整说明

            adjustment_type: "--请选择--", //  变更类型展示
            changeType: "", //  变更类型

            level: 0, //  变更明细的级别代码
            ringFlag: 0, //  环状标志

            //  显隐开关
            iconToggle: false,

            isShowDialog: false, //选择目标区划的模态框的显隐开头

            searchValue: "",//用于对区划树进行模糊匹配的值

            zTreeObj: {}, //选择目标区划的zTree实例对象

            /**
             * 用于控制变更类型下拉框中的待选项的显隐
             * 初始状态只能选择“新增”，默认新增登录人所属区划的下级区划
             */
            changeTypeMap: {
                '11': {value: ZC.ADD, label: '新增', display: true},
                '21': {value: ZC.CHANGE, label: '变更', display: false},
                '31': {value: ZC.MERGE, label: '并入', display: false},
                '41': {value: ZC.MOVE, label: '迁移', display: false}
            }
        },

        /**
         * 获取子级区划代码
         */
        getSubordinateZoning: function() {

            //重置变更类型
            rzc_luru_vm.data.adjustment_type = "--请选择--";


            rzc_luru_vm.data.subZoningCode = $(this).data('zoningcode');
            rzc_luru_vm.data.subAssigningCode = $(this).data('assigningcode');

            var assigningCode = rzc_luru_vm.data.assigningCode;
            var subAssigningCode = rzc_luru_vm.data.subAssigningCode;

            console.log("用户自身级次代码:" + assigningCode, "点击的级次代码:"+ subAssigningCode);
            //  用户权限判定 只能操作下级数据
            if (subAssigningCode > assigningCode) {
                rzc_luru_vm.data.originalZoningName = $(this).data('originalzoningname');
                subordinateZoning(rzc_luru_vm.data.subZoningCode);

                // 根据用户区划对应的级次、与选中区划的级次，判断是否能够进行迁移操作，
                // 从而隐藏或者显示变更类型待选项“迁移”
                if(Number(subAssigningCode) - Number(assigningCode) < 2){
                    var changeTypeMap = rzc_luru_vm.data.changeTypeMap;
                    for(var type in changeTypeMap){
                        if(type === ZC.MOVE){
                            changeTypeMap[type].display = false;
                        }else {
                            changeTypeMap[type].display = true;
                        }
                    }
                }
                //将全部的变更类型设置为可选（可见）
                else {
                    var changeTypeMap = rzc_luru_vm.data.changeTypeMap;
                    for(var type in changeTypeMap){
                        changeTypeMap[type].display = true;
                    }
                }
            }

        },

        /**
         * 调整类型变更显示
         */
        adjustmentTypeToggle: function() {
            rzc_luru_vm.data.adjustment_type = $(this).text();
            rzc_luru_vm.data.changeType = $(this).data('value');
            var subAssigningCode = rzc_luru_vm.data.subAssigningCode,
                target = $("#targetZoningName");
            // console.log(rzc_luru_vm.data.changeType);

            //  初始化现区划组样式及读写状态
            $('input[data-level]').css('color', '#999');
            $('input[data-level]').attr('readonly', true);
            rzc_luru_vm.data.iconToggle = false;
            console.log("adjustmentTypeToggle.changeType------> " , rzc_luru_vm.data.changeType);
            //  判断区划变更类型  之后可以提取出来
            switch (rzc_luru_vm.data.changeType.toString()) {
                case ZC.ADD:
                    rzc_luru_vm.data.targetZoningCodeArray = sliceSpecifiedCode(rzc_luru_vm.data.zoningCode);
                    subAssigningCode = Number(ZC.getAssigningCode(rzc_luru_vm.data.zoningCode));
                    $('[data-level=' + (subAssigningCode + 1) + ']').attr('readonly', false)
                        .css('color', '#fff');
                    target.attr("readonly", false);
                    break;
                case ZC.CHANGE:
                    $('[data-level=' + (subAssigningCode) + ']').attr('readonly', false)
                        .css('color', '#fff');
                    target.attr("readonly", false);
                    break;
                case ZC.MERGE:

                    //显示打开区划选择器的按钮
                    rzc_luru_vm.data.iconToggle = true;

                    //清空目标区划代码
                    rzc_luru_vm.data.targetZoningCodeArray =  ["", "", "", "", "", ""];

                    target.attr("readonly", true);

                    //清空目标区划名称
                    target.val("");
                   /* $('[data-level=' + (subAssigningCode) + ']').attr('readonly', false)
                        .css('color', '#fff');*/
                    break;
                case ZC.MOVE:

                    //显示打开区划选择器的按钮
                    rzc_luru_vm.data.iconToggle = true;


                    //设置目标区划名称不可修改
                    target.attr("readonly", true);

                    //清空目标区划代码
                    rzc_luru_vm.data.targetZoningCodeArray =  ["", "", "", "", "", ""];

                    //清空目标区划名称
                    target.val("");
                    break;
                default:
                    break;
            }

        },

        /**
         * 重用变更
         * 动态调整可选的变更类型，
         * 重用变更时变更类型只能选择“变更”
         * @param n dom对象
         */
        reusing: function(n){
            console.log("n -----> ", n.checked);

            //1 重置变更类型选择框已经选择的值为"--请选择--"
            rzc_luru_vm.data.adjustment_type = "--请选择--";

            //2 设置可选值
            if(n.checked){
                console.log("'选择 重用' -----> ", '选择 重用');
                rzc_luru_vm.data.ringFlag = 1;
                var changeTypes = rzc_luru_vm.data.changeTypeMap;
                for(var e in changeTypes){
                    var changeType = changeTypes[e];
                    if(changeType.value !== ZC.CHANGE){
                        changeType.display = false;
                    }
                }
            }else {
                console.log("'取消 重用' -----> ", '取消 重用');
                rzc_luru_vm.data.ringFlag = 0;
                var changeTypes = rzc_luru_vm.data.changeTypeMap;
                for(var e in changeTypes){
                    changeTypes[e].display = true;
                }
            }
            console.log("changeTypeMap--> ", JSON.stringify(rzc_luru_vm.data.changeTypeMap));
        },

        //在区划树中查找、定位区划
        // 查询条件为：通过区划名称（可以考虑增加区划代码为查询条件）区划，支持模糊匹配
        findInTree: function(){

            // 重写了zTreeSearch中的查询方法
            // 清除高亮
            var value = rzc_luru_vm.data.searchValue, zTreeObj = rzc_luru_vm.data.zTreeObj, allNodes = zTreeObj.transformToArray(zTreeObj.getNodes());
            console.log("value -----> ", value);
            for (var i in allNodes) {
                allNodes[i].highlight = false;
                zTreeObj.updateNode(allNodes[i]);
            }
            console.log("allNodes -----> ", allNodes);
            if (value == '') return; // input '' wipe highlight


            // 查询节点
            var nodes = zTreeObj.getNodesByParamFuzzy('name', value);

            console.log("nodes -----> ", nodes);

            // 高亮显示匹配的节点，并展开
            for (var i in nodes) {
                nodes[i].highlight = true;
                zTreeObj.updateNode(nodes[i]);
                zTreeObj.expandNode(nodes[i].getParentNode(),true);

                //定位到匹配的第一个节点
                if (i == 0) {
                    zTreeObj.selectNode(nodes[0]);
                }
            }
        },

        //选择一个区划作为并入或者迁移的目标
        selectTargetZoning: function(){

            // 1 获取选中的区划
            var vmData = rzc_luru_vm.data, checkedZoning = vmData.zTreeObj.getCheckedNodes(true);

            console.log(" selectTargetZoning.checkedZoning-----> ", checkedZoning);
            if(checkedZoning.length > 0){

                // 2 获取目标区划
                // 区划树是单选的，所以取第一个即可
                var targetZoning = checkedZoning[0],
                    changeType = vmData.changeType;

                if(changeType.toString() === ZC.MOVE){

                    // 3 填充现区划名称
                    vmData.targetZoningName = vmData.originalZoningName;

                    // 4 填充现区划代码
                    //4.1 拼凑新的区划代码，将原区划代码中的父级级别代码替换为新的父级区划级别代码
                    var oldSuperiorLevelCode = ZC.getLevelCode(ZC.getSuperiorZoningCode(vmData.originalZoningCode)),
                        newSuperiorLevelCode = ZC.getLevelCode(targetZoning.zoningCode);
                    console.log("checkedZoning---------> ", checkedZoning);
                    console.log("oldSuperiorLevelCode---------> ", oldSuperiorLevelCode);
                    console.log("newSuperiorLevelCode---------> ", newSuperiorLevelCode);
                    vmData.targetZoningCodeArray = sliceSpecifiedCode(vmData.originalZoningCode.replace(oldSuperiorLevelCode, newSuperiorLevelCode));

                }else if(changeType.toString() === ZC.MERGE){

                    // 3 填充现区划名称
                    vmData.targetZoningName = targetZoning.divisionName;

                    // 4 填充现区划代码
                    vmData.targetZoningCodeArray = sliceSpecifiedCode(targetZoning.zoningCode);
                }



                // 5 隐藏选择器
                rzc_luru_vm.hiddenDialog();

            }else {
                rzc_luru_vm.hiddenDialog();
            }
        },

        //加载待选择的并入、迁移的目标区划
        findZoning: function(){
            var checkedZoningCode =  rzc_luru_vm.data.originalZoningCode;
            console.log('findZoning.code----->', checkedZoningCode);
            if(checkedZoningCode !== '' && checkedZoningCode !== null){
                // 变更类型不一样，展示的内容也不同
                // 并入，获取同级区划，都是亲兄弟
                if(rzc_luru_vm.data.changeType.toString() === ZC.MERGE){

                    var subZoning = ZC.getSubZoning(ZC.getAssigningCode(checkedZoningCode));
                    if(subZoning.length > 0){
                        alert("将要进行并入操作的区划[" + checkedZoningCode + "]有下级区划，请将子级区划全部迁移至目标区划，再做本区划的并入操作！");
                    }else {

                        //1 获取操作区划、父级区划的级次代码
                        var assigningCode = ZC.getAssigningCode(checkedZoningCode),

                            //2 通过级次代码获取兄弟区划
                            brothers = ZC.getZoningByAssCode(assigningCode).filter(function (e) {
                                return e.zoningCode !== checkedZoningCode;
                            }).map(function (e) {
                                return ZC.translateZoningData(e);
                            }).sort(function(a, b) {
                                return a["ownCode"] - b["ownCode"];
                            });


                        //3 创建树
                        ZC.buildZTree(brothers);

                        var testData = [
                            {
                                id:1000,
                                name: '老大',
                                zoningCode: '69',
                                children: brothers
                            },
                            {
                                id:2000,
                                name: '老二',
                                zoningCode: '49',
                                chkDisabled: true,
                                children: [
                                    {
                                        id:1,
                                        name: '燕京',
                                        zoningCode: '1'
                                    },
                                    {
                                        id:2,
                                        name: '南京',
                                        zoningCode: '2'
                                    },
                                    {
                                        id:3,
                                        name: '东京',
                                        zoningCode: '3'
                                    }
                                ]
                            },{
                                id: 3000,
                                name: '孤家寡人',
                                zoningCode: '999'
                            }
                        ];
                        //ZC.buildZTree(testData);

                        //4 显示区划选择框
                        rzc_luru_vm.showDialog();
                    }
                }
                //迁移，获取叔伯区划，同根的（根——登录用户所在区划）
                else if(rzc_luru_vm.data.changeType.toString() === ZC.MOVE){
                    ZC.getTowLevelTree(checkedZoningCode);

                    //显示区划选择框
                    rzc_luru_vm.showDialog();
                }
            }else {
               alert("请先选择将要操作的区划");
            }
        },

        /**
         * 变更组名称&&调整说明
         * ms-duplex 对中文变更不适用,可能需要setTimeout 进行同步 (已实现,后期更改)
         * 暂时先用dom绑定
         */
        // changeNotes: function () {
        //   rzc_luru_vm.data.notes = $('#notes').val();
        // },

        /**
         * 备注变更
         * ms-duplex 对中文变更不适用,可能需要setTimeout 进行同步 (已实现,后期更改)
         * 暂时先用dom绑定
         */
        // changeRequestName: function () {
        //   rzc_luru_vm.data.requestName = $('#requestName').val();
        // },

        /**
         * 保存变更明细
         */
        getSaveChangeDetail: function() {
            codeTransformation();
            if (rzc_luru_vm.data.targetZoningCode !== "" && rzc_luru_vm.data.targetZoningName !== "" &&
                rzc_luru_vm.data.changeType !== "") {
                var checkResult = ZC.checkAdd();
                if (checkResult.success) {
                    saveChangeDetail();
                } else {
                    alert(checkResult.msg);
                }
            } else {
                alert('欧尼酱,完善变更明细数据后才能保存哦!');
            }
        },

        /**
         * 提交区划变更对照明细
         */
        getSubmitDetails: function() {
            var group = JSON.stringify(rzc_luru_vm.data.group.$model);
            var details = JSON.stringify(rzc_luru_vm.data.details.$model);

            var zoningCode = rzc_luru_vm.data.zoningCode;
            console.log(group, details, zoningCode);

            submitDetails(group, details, zoningCode);

            rzc_luru_vm.data.group = {};
            rzc_luru_vm.data.details = [];
        },
        /**
         * 隐藏模态框
         */
        hiddenDialog: function() {
            rzc_luru_vm.data.isShowDialog = false;
        },

        /**
         * 显示模态框
         */
        showDialog: function() {
            rzc_luru_vm.data.isShowDialog = true;
        }
    })

    initAddDetails(rzc_luru_vm.data.zoningCode); //  页面数据初始化
    sliceCode(); //  页面初始化调用切分函数

    /**
     * 区划代码分组切分
     * 区划代码15位  前三级各2位  后三级各3位  共6级
     */
    function sliceCode() {
        rzc_luru_vm.data.originalZoningCodeArray = [];
        rzc_luru_vm.data.subZoningCode = "" + rzc_luru_vm.data.subZoningCode;
        rzc_luru_vm.data.originalZoningCode = rzc_luru_vm.data.subZoningCode;

        for (var i = 0; i < 6; i++) {
            if (i < 3) {
                rzc_luru_vm.data.originalZoningCodeArray.push(rzc_luru_vm.data.originalZoningCode.substring(i * 2, (i + 1) * 2));
            } else {
                rzc_luru_vm.data.originalZoningCodeArray.push(rzc_luru_vm.data.originalZoningCode.substring((i - 1) * 3, i * 3));
            }
        }
        return rzc_luru_vm.data.originalZoningCodeArray;
    }

    /**
     * 切分指定的区划代码
     * @param zoningCode
     * @returns {Array}
     */
    function sliceSpecifiedCode(zoningCode) {
        var zoningCodeArray = [];
        for (var i = 0; i < 6; i++) {
            if (i < 3) {
                zoningCodeArray.push(zoningCode.substring(i * 2, (i + 1) * 2));
            } else {
                zoningCodeArray.push(zoningCode.substring((i - 1) * 3, i * 3));
            }
        }
        return zoningCodeArray;
    }

    /**
     * 根据键值将数据放置对应的省市县区域代码中
     * @param {Object} obj 区划变更明细对照数据录入界面接口返回数据
     */
    function placeData(obj) {
        var codeRank = rzc_luru_vm.data.codeRank;
        translateAssigningCodes = rzc_luru_vm.data.translateAssigningCodes;
        for (var key in obj) {
            // console.log(typeof key, obj[key]);
            switch (key) {
                case "1":
                    codeRank.province = obj[key];
                    break;
                case "2":
                    codeRank.city = obj[key];
                    break;
                case "3":
                    codeRank.county = obj[key];
                    break;
                case "4":
                    codeRank.township = obj[key];
                    break;
                case "5":
                    codeRank.village = obj[key];
                    break;
                case "6":
                    codeRank.group = obj[key];
                    break;

                default:
                    break;
            }

            for(var i = 1 ; i <= 6; i ++){
                if(Number(key) < i){
                    console.log("come in ");
                    codeRank[translateAssigningCodes[i - 1]] = [];
                }
            }

        }
    }

    /**
     * 现区划代码分组转化成 完整的现区划代码
     */
    function codeTransformation() {
        rzc_luru_vm.data.targetZoningCode = rzc_luru_vm.data.targetZoningCodeArray.join("");
    }

    /**
     * 存放变更明细数据
     */
    function saveChangeDetail() {
        rzc_luru_vm.data.details.push({
            originalZoningCode: rzc_luru_vm.data.originalZoningCode,
            originalZoningName: rzc_luru_vm.data.originalZoningName,
            targetZoningCode: rzc_luru_vm.data.targetZoningCode,
            targetZoningName: rzc_luru_vm.data.targetZoningName,
            changeType: rzc_luru_vm.data.changeType,
            notes: rzc_luru_vm.data.notes,
            level: rzc_luru_vm.data.level,
            ringFlag: rzc_luru_vm.data.ringFlag,
        })

        rzc_luru_vm.data.targetZoningCodeArray = ["", "", "", "", "", ""];
        rzc_luru_vm.data.targetZoningCode = "";
        rzc_luru_vm.data.targetZoningName = "";
        rzc_luru_vm.data.notes = "";

        rzc_luru_vm.data.group.$model.requestSeq = rzc_luru_vm.data.requestSeq;
        rzc_luru_vm.data.group.$model.name = rzc_luru_vm.data.requestName;

        //  判断 变更组名称&&调整说明 是否变更  变更则刷新
        // if (rzc_luru_vm.data.group.length === 0) {
        //   rzc_luru_vm.data.group.push({
        //     requestSeq: rzc_luru_vm.data.requestSeq,
        //     name: rzc_luru_vm.data.requestName,
        //   })
        // } else if (rzc_luru_vm.data.group[0].name !== rzc_luru_vm.data.requestName) {
        //   rzc_luru_vm.data.group = [];
        //   rzc_luru_vm.data.group.push({
        //     requestSeq: rzc_luru_vm.data.requestSeq,
        //     name: rzc_luru_vm.data.requestName,
        //   })
        // }

        // console.log(rzc_luru_vm.data.group);
        console.log(rzc_luru_vm.data.group);
    }

    /**
     * 获取用户区划代码
     */
    function buildRequest() {
        $.ajax({
            url: 'http://' + ip + ':' + port + '/zoningChangeManager/buildRequest',
            type: 'GET',
            success: function(da) {
                var res = JSON.parse(da);
                rzc_luru_vm.data.zoningCode = res.responseData.zoningCode;
            }
        })
    }

    /**
     * 初始化区划变更明细对照数据录入界面接口
     * @param 登录人区划代码 zoningCode
     */
    function initAddDetails(zoningCode) {
        // console.log(zoningCode);
        $.ajax({
            url: 'http://' + ip + ':' + port + '/zoningChangeManager/initAddDetails?zoningCode=' + zoningCode,
            type: 'GET',
            success: function(da) {
                var res = JSON.parse(da);
                console.log(res);
                if (res.rtnCode === "000000") {
                    var dataCode = res.responseData.previewData;
                    rzc_luru_vm.data.assigningCode = res.responseData.assigningCode;
                    rzc_luru_vm.data.requestSeq = res.responseData.seq;
                    placeData(dataCode);
                } else {
                    alert(res.rtnMessage);
                }

            }
        })
    }

    /**
     * 获取子级区划接口
     * @param 区划代码 zoningCode
     */
    function subordinateZoning(zoningCode) {

        console.log("subordinateZoning---> ", zoningCode);
        // rzc_luru_vm.data.zoningCode = zoningCode;
        rzc_luru_vm.data.originalZoningCode = zoningCode;
        //  调用切分函数
        sliceCode();
        rzc_luru_vm.data.targetZoningCodeArray = rzc_luru_vm.data.originalZoningCodeArray;
        rzc_luru_vm.data.targetZoningName = rzc_luru_vm.data.originalZoningName;

        $.ajax({
            url: 'http://' + ip + ':' + port + '/zoningChangeManager/getSubordinateZoning?zoningCode=' + zoningCode,
            type: 'GET',
            success: function(da) {
                var res = JSON.parse(da);
                console.log(res.responseData);
                var dataCode = res.responseData;

                placeData(dataCode);
            }
        })
    }

    /**
     * 获取同级区划接口
     * @param 区划代码 zoningCode
     */
    function getBrothersZoning(zoningCode) {
        $.ajax({
            url: 'http://' + ip + ':' + port + '/zoningChangeManager/getBrothersZoning?zoningCode=' + zoningCode,
            type: 'GET',
            success: function(da) {
                var translateZoningData = ZC.translateZoningData;
                ZC.buildZTree(JSON.parse(da).responseData.map(function (e) {
                    return translateZoningData(e)
                }));
            }
        })
    }



    /**
     * 提交区划变更对照明细接口
     * @param 存放文件序号,变更组名称 group
     * @param 存放变更明细数据 details
     * @param 区划代码 zoningCode
     */
    function submitDetails(group, details, zoningCode) {
        $.ajax({
            url: 'http://' + ip + ':' + port + '/zoningChangeManager/saveDetails',
            type: 'POST',
            data: {
                group: group,
                details: details,
                zoningCode: zoningCode,
            },
            success: function(da) {
                var res = JSON.parse(da);
                console.log(res);
                if(res.rtnCode === "999999"){
                    alert("变更失败，" + res.rtnMessage);
                }
            },
            error: function (da) {
               console.error(da);
            }
        })
    }


    /**
     * 点击保存明细时触发的校验
     * @param 区划信息 changeInfo
     */
    ZC.checkAdd = function() {
        var changeInfo = {
                originalZoningCode: rzc_luru_vm.data.originalZoningCode,
                originalZoningName: rzc_luru_vm.data.originalZoningName,
                targetZoningCode: rzc_luru_vm.data.targetZoningCode,
                targetZoningName: rzc_luru_vm.data.targetZoningName,
                changeType: rzc_luru_vm.data.changeType,
                notes: rzc_luru_vm.data.notes,
                level: rzc_luru_vm.data.level,
                ringFlag: rzc_luru_vm.data.ringFlag,
            },
            changeType = changeInfo.changeType.toString();
        if (!/^3(\d){14}/.test(changeInfo.targetZoningCode)) {
            return {
                msg: '现区划代码不是符合规范的15位阿拉伯数字',
                success: false
            }
        } else {

            if (changeType === ZC.ADD) {
                return ZC.checkNameAndCode(changeInfo);
            } else if (changeType === ZC.CHANGE) {
                if (changeInfo.targetZoningCode === changeInfo.originalZoningCode && changeInfo.targetZoningName === changeInfo.originalZoningName) {
                    return {
                        success: false,
                        msg: '无效的变更，原区划代码、原区划名称与现区划代码、现区划名称完全一致！'
                    };
                } else {
                    return ZC.checkNameAndCode(changeInfo);
                }
            } else if (changeType === ZC.MERGE) {
                //并入，可以在点击“选择并入对象”时，校验对象是否有子级区划
                var targetAssCode = ZC.getAssigningCode(changeInfo.targetZoningCode),
                    originalAssCode = ZC.getAssigningCode(changeInfo.originalZoningCode);

                //比较区划级次
                if (targetAssCode === originalAssCode) {
                    return {
                        success: true,
                        msg: '操作成功！'
                    }
                } else {
                    return {
                        success: false,
                        msg: '并入的目标区划与原区划级次不一样！'
                    };
                }
            } else if (changeType === ZC.MOVE) {

                //迁移在前台其实不能做多少校验
                if(ZC.getAssigningCode(changeInfo.targetZoningCode) === ZC.getAssigningCode(changeInfo.originalZoningCode)){
                    return {
                        success: true,
                        msg: '操作成功'
                    };
                }else {
                    return {
                        success: false,
                        msg: '迁移的目标区划级次不对！'
                    };
                }
            }
        }
    };


    //建立区划树，异步加载，做二级树，叶子节点是单选框
    // 数据量可能有些大，需要做个分页
    ZC.buildZTree = function (data, setting) {
        console.log("buildZTree.data -----> ", data);
        var zTreeTool = avalon.templateCache._rzc_luru.fn.zTree,
            $search = $('#searchZoning');

        //销毁可能存在的树
        zTreeTool.destroy('treeDemo');

        //新建树
        rzc_luru_vm.data.zTreeObj = zTreeTool.init($("#treeDemo"), setting || {
            check: {
                enable: true,
                chkStyle: "radio",
                radioType: "all"
            }
        }, data);

        //在搜索框，绑定enter键盘事件
        $search.keyup(function(e) {
            if(e.keyCode === 13){
                rzc_luru_vm.findInTree();
            }
        });
    };

    ZC.getTowLevelTree = function (originalZoningCode) {
        $.ajax({
            url: 'http://' + ip + ':' + port + '/zoningChangeManager/findByAssigningCodesAndRootZoning?originalZoningCode=' + originalZoningCode + "&ownZoningCode=" + rzc_luru_vm.data.zoningCode,
            type: 'GET',
            success: function(da) {
                var source = JSON.parse(da)['responseData'],
                    treeData = [],
                    grandPaes = {},
                    farthers = {},
                    assigningcode = Number(ZC.getAssigningCode(originalZoningCode)),
                    grandPaAssCode = assigningcode - 2 + "",
                    fartherAssCode = assigningcode - 1 + "",
                    translate = ZC.translateZoningData;

                     //1 装起来
                    source.forEach(function(e) {
                        if(e['assigningCode'] === grandPaAssCode ){
                            grandPaes[e['zoningCode']] = translate(e);
                        }else{
                            var superiorZoningCode = e['superiorZoningCode'];
                            if(farthers[superiorZoningCode]){
                                farthers[superiorZoningCode].push(translate(e));
                            }else{
                                farthers[superiorZoningCode] = [translate(e)];
                            }
                        }
                    });

                     //2 各找各爹
                     for(var info in farthers){

                        //2.1 获取祖父
                        var grandPa = translate(grandPaes[info]);
                        if(grandPa){
                            grandPa["chkDisabled"] = true;
                            grandPa["children"] = farthers[info].sort(function(a, b) {
                                return a['ownCode'] - b['ownCode'];
                            });
                            treeData.push(grandPa);
                        }

                     }
                console.log("getTowLevelTree-----------> ", treeData);
                ZC.buildZTree(treeData);
            }
        })
    };

    /**
     * 将区划预览数据转化成ztree需要的格式
     * @param  {obj} e   区划预览数据
     * @return {obj}     {ownCode: 本级区划部分，name:拼凑出的标题，id: index, zoningCode: zoningCode, divisionName: divisionName}
     */
    ZC.translateZoningData = function(e) {
        if (e) {
            var ownCode = ZC.getOwnZoningCode(e.zoningCode);
            return {
                ownCode: Number(ownCode),
                name: e.divisionName + " " + ownCode,
                divisionName: e.divisionName,
                id: e.index,
                zoningCode: e.zoningCode
            }
        } else {
            return null;
        }
    };


    /**
     * 获取本级区划代码
     */
    ZC.getOwnZoningCode = function(zoningCode){
        var assigningCode = ZC.getAssigningCode(zoningCode);
        if(assigningCode === ""){
            return "";
        }else if(assigningCode === "1"){
            return zoningCode.substring(0, 2);
        }else if(assigningCode === "2"){
            return zoningCode.substring(2, 4);
        }else if(assigningCode === "3"){
            return zoningCode.substring(4, 6);
        }else if(assigningCode === "4"){
            return zoningCode.substring(6, 9);
        }else if(assigningCode === "5"){
            return zoningCode.substring(9, 12);
        }else if(assigningCode === "6"){
            return zoningCode.substring(12);
        }else {
            return "";
        }
    };


    /**
     *  校验区划代码与区划名称
     * @param changeInfo
     * @returns {{success: boolean, msg: string}}
     */
    ZC.checkNameAndCode = function (changeInfo) {

        //返回的结果
        var checkResult = {
                success: true,
                msg: ''
            },
            targetZoningCode = changeInfo.targetZoningCode,
            targetZoningName = changeInfo.targetZoningName,
            zoningData = ZC.getZoningByAssCode(ZC.getAssigningCode(targetZoningCode));
        console.log("zoningData -----> ", zoningData);
        zoningData.forEach(function (e) {
            //排除区划自身
            if (e.zoningCode !== changeInfo.originalZoningCode) {

                //区划校验
                if (e.zoningCode === targetZoningCode) {
                    checkResult.success = false;
                    checkResult.msg = checkResult.msg + '区划代码[' + targetZoningCode + ']已经存在！';

                }

                //名称校验
                if (e.divisionName === targetZoningName) {
                    checkResult.success = false;
                    checkResult.msg = checkResult.msg + '区划名称[' + targetZoningName + ']已经存在！';
                }
            }
        });
        return checkResult;
    };

    /**
     * 根据级次代码取得区划数据
     * @param assigningCode
     * @returns {detail...}
     */
    ZC.getZoningByAssCode = function(assigningCode) {
        return rzc_luru_vm.data.codeRank[rzc_luru_vm.data.translateAssigningCodes[Number(assigningCode) - 1]];
    };

    ZC.getSubZoning = function(assigningCode) {
        var subAssCode = Number(assigningCode) + 1;
        return ZC.getZoningByAssCode(subAssCode);
    };

    /**
     * @description 获取该行政区划的上级行政区划
     * @method  getSuperiorZoningCode
     * @params [xzqh_dm：行政区划代码]
     * @return java.lang.String：上级行政区划代码
     */
    ZC.getSuperiorZoningCode = function(zoningCode) {

        if (zoningCode == null || zoningCode === "" || zoningCode.length != 15) {
            return "";
        }
        var superiorZoningCode = "";
        if (zoningCode.substring(0, 2) === ("00")) {
            return "";
        } else if (zoningCode.substring(2, 4) === ("00")) {
            superiorZoningCode = "000000000000000";
        } else if (zoningCode.substring(4, 6) === ("00")) {
            superiorZoningCode = zoningCode.substring(0, 2) + "0000000000000";
        } else if (zoningCode.substring(6, 9) === ("000")) {
            superiorZoningCode = zoningCode.substring(0, 4) + "00000000000";
        } else if (zoningCode.substring(9, 12) === ("000")) {
            superiorZoningCode = zoningCode.substring(0, 6) + "000000000";
        } else if (zoningCode.substring(12, 15) === ("000")) {
            superiorZoningCode = zoningCode.substring(0, 9) + "000000";
        } else {
            superiorZoningCode = zoningCode.substring(0, 12) + "000";
        }
        return superiorZoningCode;
    };



    /**
     *  根据行政区划代码获取相应级次代码
     * @method  getAssigningCode
     * @param  区划代码  zoningCode
     * @return java.lang.String
     */
    ZC.getAssigningCode = function(zoningCode) {

        if (zoningCode == null || zoningCode === ("") || zoningCode.length != 15) {
            return "";
        }
        var assigningCode = "";
        if (zoningCode.substring(0, 2) === ("00")) {
            return "0";
        } else if (zoningCode.substring(2, 4) === ("00")) {
            assigningCode = "1";
        } else if (zoningCode.substring(4, 6) === ("00")) {
            assigningCode = "2";
        } else if (zoningCode.substring(6, 9) === ("000")) {
            assigningCode = "3";
        } else if (zoningCode.substring(9, 12) === ("000")) {
            assigningCode = "4";
        } else if (zoningCode.substring(12, 15) === ("000")) {
            assigningCode = "5";
        } else {
            assigningCode = "6";
        }
        return assigningCode;
    };

    /**
     *  获取区划代码中的级别代码
     * @param zoningCode 区划代码
     * @return 级别代码
     */
    ZC.getLevelCode = function(zoningCode) {
        if (zoningCode == null || zoningCode === ("") || zoningCode.length != 15) {
            return "";
        }
        var levelCode = "";
        if (zoningCode.substring(0, 2) === ("00")) {
            return "";
        } else if (zoningCode.substring(2, 4) === ("00")) {
            levelCode = zoningCode.substring(0, 2);
        } else if (zoningCode.substring(4, 6) === ("00")) {
            levelCode = zoningCode.substring(0, 4);
        } else if (zoningCode.substring(6, 9) === ("000")) {
            levelCode = zoningCode.substring(0, 6);
        } else if (zoningCode.substring(9, 12) === ("000")) {
            levelCode = zoningCode.substring(0, 9);
        } else if (zoningCode.substring(12, 15) === ("000")) {
            levelCode = zoningCode.substring(0, 12);
        } else {
            levelCode = zoningCode;
        }
        return levelCode;
    };

})