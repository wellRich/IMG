define(['avalon', 'jquery', 'bootstrap', 'text!./rzc_luru.js'], function (avalon, _rzc_luru) {
    avalon.templateCache._rzc_luru = _rzc_luru;

    var ZC = {};
    var rzc_luru_vm = avalon.define({
        $id: "rzc_luru",
        data: {
            navbar: [{
                name: "建立变更对照表",
                routerPath: "#!/rzc/rzc_jianli",
                imgPath: "./src/modules/rzc/img/gray.png"
            },
                {
                    name: "录入变更明细",
                    routerPath: "#!/rzc/rzc_luru",
                    imgPath: "./src/modules/rzc/img/blue.png"
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

            //  省,市,县,乡,村,组 各级区划
            codeRank: {
                "province": [],
                "city": [],
                "county": [],
                "township": [],
                "village": [],
                "group": [],
            },

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
        },

        /**
         * 获取子级区划代码
         */
        getSubordinateZoning: function () {
            rzc_luru_vm.data.subZoningCode = $(this).data('zoningcode');
            rzc_luru_vm.data.subAssigningCode = $(this).data('assigningcode');

            var assigningCode = rzc_luru_vm.data.assigningCode;
            var subAssigningCode = rzc_luru_vm.data.subAssigningCode;
            // console.log("用户自身级次代码:" + assigningCode, "点击的级次代码:"+ subAssigningCode);

            //  用户权限判定 只能操作下级数据
            if (subAssigningCode > assigningCode) {
                rzc_luru_vm.data.originalZoningName = $(this).data('originalzoningname');
                subordinateZoning(rzc_luru_vm.data.subZoningCode);
            }

        },

        /**
         * 调整类型变更显示
         */
        adjustmentTypeToggle: function () {
            rzc_luru_vm.data.adjustment_type = $(this).text();
            rzc_luru_vm.data.changeType = $(this).data('value');
            var subAssigningCode = rzc_luru_vm.data.subAssigningCode;
            // console.log(rzc_luru_vm.data.changeType);

            //  初始化现区划组样式及读写状态
            $('input[data-level]').css('color', '#999');
            $('input[data-level]').attr('readonly', true);
            rzc_luru_vm.data.iconToggle = false;

            //  判断区划变更类型  之后可以提取出来
            switch (rzc_luru_vm.data.changeType) {
                case 11:
                    $('[data-level=' + (subAssigningCode + 1) + ']').attr('readonly', false)
                        .css('color', '#fff')
                    break;
                case 21:
                    $('[data-level=' + (subAssigningCode) + ']').attr('readonly', false)
                        .css('color', '#fff')
                    break;
                case 31:
                    rzc_luru_vm.data.iconToggle = true
                    break;
                case 41:
                    rzc_luru_vm.data.iconToggle = true
                    break;
                default:
                    break;
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
        getSaveChangeDetail: function () {
            codeTransformation();
            if (rzc_luru_vm.data.targetZoningCode !== "" && rzc_luru_vm.data.targetZoningName !== ""
                && rzc_luru_vm.data.changeType !== "") {
                var checkResult = ZC.checkAdd();
                if(checkResult.success){
                    saveChangeDetail();
                }else {
                    alert(checkResult.msg);
                }
            } else {
                alert('欧尼酱,完善变更明细数据后才能保存哦!');
            }
        },

        /**
         * 提交区划变更对照明细
         */
        getSubmitDetails: function () {
            var group = JSON.stringify(rzc_luru_vm.data.group.$model);
            var details = JSON.stringify(rzc_luru_vm.data.details.$model);

            var zoningCode = rzc_luru_vm.data.zoningCode;
            console.log(group, details, zoningCode);

            submitDetails(group, details, zoningCode);

            rzc_luru_vm.data.group = {};
            rzc_luru_vm.data.details = [];
        },
        maxLengths: [2, 2, 2, 3, 3, 3]
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
     * 根据键值将数据放置对应的省市县区域代码中
     * @param {Object} obj 区划变更明细对照数据录入界面接口返回数据
     */
    function placeData(obj) {
        for (var key in obj) {
            // console.log(typeof key, obj[key]);
            switch (key) {
                case "1":
                    rzc_luru_vm.data.codeRank.province = obj[key]
                    break;
                case "2":
                    rzc_luru_vm.data.codeRank.city = obj[key]
                    break;
                case "3":
                    rzc_luru_vm.data.codeRank.county = obj[key]
                    break;
                case "4":
                    rzc_luru_vm.data.codeRank.township = obj[key]
                    break;
                case "5":
                    rzc_luru_vm.data.codeRank.village = obj[key]
                    break;
                case "6":
                    rzc_luru_vm.data.codeRank.group = obj[key]
                    break;

                default:
                    break;
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
            url: 'http://localhost:8251/zoningChangeManager/buildRequest',
            type: 'GET',
            success: function (da) {
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
            url: 'http://localhost:8251/zoningChangeManager/initAddDetails?zoningCode=' + zoningCode,
            type: 'GET',
            success: function (da) {
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
        // rzc_luru_vm.data.zoningCode = zoningCode;
        rzc_luru_vm.data.originalZoningCode = zoningCode;
        //  调用切分函数
        sliceCode();
        rzc_luru_vm.data.targetZoningCodeArray = rzc_luru_vm.data.originalZoningCodeArray;
        rzc_luru_vm.data.targetZoningName = rzc_luru_vm.data.originalZoningName;

        $.ajax({
            url: 'http://localhost:8251/zoningChangeManager/getSubordinateZoning?zoningCode=' + zoningCode,
            type: 'GET',
            success: function (da) {
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
            url: 'http://localhost:8251/zoningChangeManager/getBrothersZoning?zoningCode=' + zoningCode,
            type: 'GET',
            success: function (da) {
                var res = JSON.parse(da);
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
            url: 'http://localhost:8251/zoningChangeManager/saveDetails',
            type: 'POST',
            data: {
                group: group,
                details: details,
                zoningCode: zoningCode,
            },
            success: function (da) {
                var res = JSON.parse(da);
                console.log(res);
            }
        })
    }


    /**
     * 点击保存明细时触发的校验
     * @param 区划信息 changeInfo
     */
    ZC.checkAdd = function () {
        var changeInfo = {
            originalZoningCode: rzc_luru_vm.data.originalZoningCode,
            originalZoningName: rzc_luru_vm.data.originalZoningName,
            targetZoningCode: rzc_luru_vm.data.targetZoningCode,
            targetZoningName: rzc_luru_vm.data.targetZoningName,
            changeType: rzc_luru_vm.data.changeType,
            notes: rzc_luru_vm.data.notes,
            level: rzc_luru_vm.data.level,
            ringFlag: rzc_luru_vm.data.ringFlag,
        }, req = "";
        if(!/^3(\d){14}/.test(changeInfo.targetZoningCode)){
            return {
                msg:'现区划代码不是符合规范的15位阿拉伯数字',
                success: false
            }
        }else {
            //获取同级区划
            switch (changeInfo.changeType.toString()) {
                //新增区划
                case '11':
                //return ZC.checkNameAndCode(changeInfo);
                //变更
                case '21':
                //return ZC.checkNameAndCode(changeInfo);

                //迁移
                case '41':
                    return ZC.checkNameAndCode(changeInfo);
                //并入
                case '31'://可以在选择并入对象时时做校验
                default:
                    break;
            }
        }
    };

    /**
     *  校验区划代码与区划名称
     * @param changeInfo
     * @returns {{success: boolean, msg: string}}
     */
    ZC.checkNameAndCode = function (changeInfo) {

        //返回的结果
        var checkResult = {success: true, msg: ''},
            targetZoningCode = changeInfo.targetZoningCode,
            targetZoningName = changeInfo.targetZoningName;
        if(targetZoningCode === changeInfo.originalZoningCode){
            checkResult.success = false;
            checkResult.msg = '';
            return  checkResult;
        }else {
            ZC.getZoningByAssCode(ZC.getAssigningCode(targetZoningCode)).forEach(function (e) {

                //排除区划自身
                if (e.originalZoningCode !== changeInfo.originalZoningCode) {

                    //区划校验
                    if (e.targetZoningCode === targetZoningCode) {
                        checkResult.success = false;
                        checkResult.msg = checkResult.msg + '区划代码[' + targetZoningCode + ']已经存在！';

                    }

                    //名称校验
                    if (e.targetZoningName === targetZoningName) {
                        checkResult.success = false;
                        checkResult.msg = checkResult.msg + '区划名称[' + targetZoningName + ']已经存在！';
                    }
                }
            });
        }

        return checkResult;
    };

    /**
     * 根据级次代码取得区划数据
     * @param assigningCode
     * @returns {detail...}
     */
    ZC.getZoningByAssCode = function (assigningCode) {
        var translateAssigningCodes = ["province",
            "city",
            "county",
            "township",
            "village",
            "group"];
        return rzc_luru_vm.data.codeRank[translateAssigningCodes[Number(assigningCode)]];
    };

    ZC.getSubZoning = function (assigningCode) {
        var subAssCode = Number(assigningCode) + 1;
        return ZC.getZoningByAssCode(subAssCode);
    };

    /**
     * @description 获取该行政区划的上级行政区划
     * @method  getSuperiorZoningCode
     * @params [xzqh_dm：行政区划代码]
     * @return java.lang.String：上级行政区划代码
     */
    ZC.getSuperiorZoningCode = function (zoningCode) {

        if (zoningCode == null || zoningCode === "" || zoningCode.length != 15) {
            return "";
        }
        var superiorZoningCode = "";
        if (zoningCode.substring(0, 2)===("00")) {
            return "";
        } else if (zoningCode.substring(2, 4)===("00")) {
            superiorZoningCode = "000000000000000";
        } else if (zoningCode.substring(4, 6)===("00")) {
            superiorZoningCode = zoningCode.substring(0, 2) + "0000000000000";
        } else if (zoningCode.substring(6, 9)===("000")) {
            superiorZoningCode = zoningCode.substring(0, 4) + "00000000000";
        } else if (zoningCode.substring(9, 12)===("000")) {
            superiorZoningCode = zoningCode.substring(0, 6) + "000000000";
        } else if (zoningCode.substring(12, 15)===("000")) {
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
    ZC.getAssigningCode = function (zoningCode) {

        if (zoningCode == null || zoningCode===("") || zoningCode.length != 15) {
            return "";
        }
        var assigningCode = "";
        if (zoningCode.substring(0, 2)===("00")) {
            return "0";
        } else if (zoningCode.substring(2, 4)===("00")) {
            assigningCode = "1";
        } else if (zoningCode.substring(4, 6)===("00")) {
            assigningCode = "2";
        } else if (zoningCode.substring(6, 9)===("000")) {
            assigningCode = "3";
        } else if (zoningCode.substring(9, 12)===("000")) {
            assigningCode = "4";
        } else if (zoningCode.substring(12, 15)===("000")) {
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
    ZC.getLevelCode = function (zoningCode) {
        if (zoningCode == null || zoningCode===("") || zoningCode.length != 15) {
            return "";
        }
        var levelCode = "";
        if (zoningCode.substring(0, 2)===("00")) {
            return "";
        } else if (zoningCode.substring(2, 4)===("00")) {
            levelCode = zoningCode.substring(0, 2);
        } else if (zoningCode.substring(4, 6)===("00")) {
            levelCode = zoningCode.substring(0, 4);
        } else if (zoningCode.substring(6, 9)===("000")) {
            levelCode = zoningCode.substring(0, 6);
        } else if (zoningCode.substring(9, 12)===("000")) {
            levelCode = zoningCode.substring(0, 9);
        } else if (zoningCode.substring(12, 15)===("000")) {
            levelCode = zoningCode.substring(0, 12);
        } else {
            levelCode = zoningCode;
        }
        return levelCode;
    };

})