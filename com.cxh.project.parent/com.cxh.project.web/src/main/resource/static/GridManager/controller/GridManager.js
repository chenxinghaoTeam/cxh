define([ 'jquery', 'echarts','GM','common',], function($, echarts,GM,common) {

	var my = {};
	my.render = function() {
		gird();
	}
	
	
	function gird(){
		document.querySelector('table').GM({
			gridManagerName: 'test',
            width: '100%',
            height: '100%',
            // 是否使用无总条数模式
            // useNoTotalsMode: true,
            // 是否开启分页
            supportAjaxPage: true,
            // 排序模式，single(升降序单一触发) overall(升降序整体触发)
            sortMode: 'single',

             supportAdjust: true,
            // 是否开启配置功能
             supportConfig: true,

            // 是否开启导出
             supportExport: true,

            // 是否开启打印
             supportPrint: true,

            // 右键菜单
            supportMenu: true,
            menuHandler: list => {
                list.unshift({
                    content: '自定义菜单',
                    line: true,
                    onClick: _ => {
                        alert(111);
                    }
                });
                return list;
            },
            // 使用单元格触焦高亮样式
            useCellFocus: true,
            // 行移动
            supportMoveRow: true,
            moveRowConfig: {
                key: 'priority',
                useSingleMode: true,
                fixed: 'left',
                handler: (list, tableData) => {
                    console.log(list, tableData);
                }
            },
            summaryHandler: function(data){
                let readNumber = 0;
                data.forEach(item => {
                    readNumber += item.readNumber;
                });
                return {
                    pic: '共计',
                    readNumber
                };
            },

            // 禁用缓存
            disableCache: false,
            ajaxData: function (settings, params) {
                document.querySelector('[name="type"]').value = params.type || -1;
                return 'https://www.lovejavascript.com/blogManager/getBlogList';
            },

            // 导出配置
            exportConfig: {
                fileName: query => {
                    const date = new Date();
                    let fileName = `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`;
                    for (let key in query) {
                        fileName = `${fileName}-${key}=${query[key]}`;
                    }
                    return fileName;
                },
                suffix: 'xls'
            },
            ajaxType: 'POST',

            // 选择事件执行前事件
            checkedBefore: function (checkedList, isChecked, row) {
                console.log('checkedBefore==', checkedList, isChecked, row);
                if (row && row.id === 90) {
                    alert('该节点在checkedBefore中配置为不可选');
                }
                return row && row.id !== 90;
            },

            // 选择事件执行后事件
            checkedAfter: function (checkedList, isChecked, row) {
                console.log('checkedAfter==', checkedList, isChecked, row);
            },

            // 全选事件执行前事件
            checkedAllBefore: function (checkedList, isChecked) {
                console.log('checkedAllBefore==', checkedList, isChecked);
                //
                // if (isChecked) {
                //     alert('不能取消全选');
                // }
                // return !isChecked;
            },

            // 全选事件执行后事件
            checkedAllAfter: function (checkedList, isChecked) {
                console.log('checkedAllAfter==', checkedList, isChecked);
            },

            // 执行排序前事件
            sortingBefore: function (query) {
                console.log('sortingBefore', query);
            },

            // 排行排序后事件
            sortingAfter: function (query) {
                console.log('sortingAfter', query);
            },

            // AJAX请求前事件函数
            ajaxBeforeSend: function (promise) {
                console.log('ajaxBeforeSend');
            },
            // AJAX成功事件函数
            ajaxSuccess: function (response) {
                console.log('ajaxSuccess');
            },

            // AJAX失败事件函数
            ajaxError: function (errorInfo) {
                console.log('ajaxError');
            },

            // AJAX结束事件函数
            ajaxComplete: function (complete) {
                console.log('ajaxComplete');
            },
            adjustBefore: eve => {
                console.log('adjustBefore=>', eve);
            },
            adjustAfter: eve => {
                console.log('adjustAfter=>', eve);
            },

            // 执行请求后执行程序
            responseHandler: res => {
                res.data.forEach(item => {
                    // 用id模拟优先级字段
                    item.priority = item.id;
                });
                return res;
            },

            // 单行数据渲染时执行程序
            rowRenderHandler: (row, index) => {
                // if (row.id === 90) {
                //     row.gm_checkbox = true;
                // }

                // 指定第92行不可选中
                if (row.id === 92) {
                        // row.gm_checkbox = true;
                    row.gm_checkbox_disabled = true;
                    row.gm_row_class_name = 'test-row-class';
                }
                return row;
            },

            emptyTemplate: settings => {
                return `<div style="text-align: center;">${settings.query.title ? '搜索为空' : '暂无数据'}</div>`;
            },
            // 单个td的click事件
            // cellClick: (row, rowIndex, colIndex) => {
            //     console.log(row, rowIndex, colIndex);
            //     return {
            //         text: '这里有个提示',
            //         position: 'left'
            //     };
            // },
            // rowHover: (a, b, c) => {
            //     return {
            //         text: '这里有个提示',
            //         position: 'right'
            //     };
            // },
            // useWordBreak: true,
            columnData: [
                {
                    key: 'pic',
                    remind: {
                        text: '这一列显示了缩略图，可以通过点击跳转至对应的博客地址',
                        style: {
                            'color': 'yellow'
                        }
                    },
                    width: '110px',
                    align: 'center',
                    text: '缩略图',
                    disableMoveRow: true,
                    // 使用函数返回 dom node
                    template: function (pic, row) {
                        var picNode = document.createElement('a');
                        picNode.setAttribute('href', `https://www.lovejavascript.com/#!zone/blog/content.html?id=${row.id}`);
                        picNode.setAttribute('title', row.title);
                        picNode.setAttribute('target', '_blank');
                        picNode.title = `点击阅读[${row.title}]`;
                        picNode.style.display = 'block';
                        picNode.style.height = '58.5px';

                        var imgNode = document.createElement('img');
                        imgNode.style.width = '90px';
                        imgNode.style.margin = '0 auto';
                        imgNode.alt = row.title;
                        imgNode.src = `https://www.lovejavascript.com/${pic}`;

                        picNode.appendChild(imgNode);
                        return picNode;
                    }
                }, {
                    key: 'title',
                    remind: 'the title',
                    text: '标题',
                    sorting: '',
                    disableMoveRow: true,
                    // 使用函数返回 dom node
                    template: function (title, row) {
                        var titleNode = document.createElement('a');
                        titleNode.setAttribute('href', `https://www.lovejavascript.com/#!zone/blog/content.html?id=${row.id}`);
                        titleNode.setAttribute('title', title);
                        titleNode.setAttribute('target', '_blank');
                        titleNode.innerText = title;
                        titleNode.title = `点击阅读[${row.title}]`;
                        titleNode.classList.add('plugin-action');
                        return titleNode;
                    }
                }, {
                    key: 'readNumber',
                    text: '阅读量'
                }, {
                    key: 'type',
                    remind: {
                        text: '[HTML/CSS, nodeJS, javaScript, 前端鸡汤, PM Coffee, 前端框架, 前端相关]',
                        style: {
                            width: '300px',
                            'text-align': 'left'
                        }
                    },
                    text: '博文分类',
                    align: 'left',
                    width: '150px',
                    sorting: '',
                    disableMoveRow: true,
                    // 表头筛选条件, 该值由用户操作后会将选中的值以{key: value}的形式覆盖至query参数内。非必设项
                    filter: {
                        // 筛选条件列表, 数组对象。格式: [{value: '1', text: 'HTML/CSS'}],在使用filter时该参数为必设项。
                        option: [
                            {value: '1', text: 'HTML/CSS'},
                            {value: '2', text: 'nodeJS'},
                            {value: '3', text: 'javaScript'},
                            {value: '4', text: '前端鸡汤'},
                            {value: '5', text: 'PM Coffee'},
                            {value: '6', text: '前端框架'},
                            {value: '7', text: '前端相关'}
                        ],
                        // 筛选选中项，字符串, 未存在选中项时设置为''。 在此设置的选中的过滤条件将会覆盖query
                        selected: '3',
                        // 否为多选, 布尔值, 默认为false。非必设项
                        isMultiple: false
                    },
                    template: function (type, row) {
                        return TYPE_MAP[type];
                    }
                }, {
                    key: 'info',
                    remind: 'the info',
                    width: '100px',
                    text: '简介',
                    disableMoveRow: true
                }, {
                    key: 'username',
                    remind: 'the username',
                    align: 'center',
                    width: '100px',
                    text: '作者',
                    disableMoveRow: true,
                    template: (username, row) => {
                        return `<a class="plugin-action" href="https://github.com/baukh789" target="_blank" ${row.id} title="去看看${username}的github">${username}</a>`;
                    }
                }, {
                    key: 'createDate',
                    width: '130px',
                    text: '创建时间',
                    sorting: 'DESC',
                    align: 'left',
                    // 使用函数返回 htmlString
                    template: (createDate, row) => {
                        return new Date(createDate).toLocaleDateString();
                    }
                }, {
                    key: 'lastDate',
                    width: '130px',
                    text: '最后修改时间',
                    merge: 'text',
                    sorting: '',
                    // 使用函数返回 htmlString
                    template: function (lastDate, row) {
                        return new Date(lastDate).toLocaleDateString();
                    }
                },
                {
                    key: 'priority',
                    text: '优先级',
                    // fixed: 'right',
                    align: 'right',
                    width: '100px'
                },
                {
                    key: 'action',
                    remind: 'the action',
                    width: '100px',
                    align: 'center',
                    fixed: 'right',
                    disableMoveRow: true,
                    disableRowCheck: true,
                    text: '<span style="color: red">操作</span>',
                    // 直接返回 通过函数返回
                    template: (action, row) => {
                        return `<span class="plugin-action" data-id="${row.id}" onclick="demo1.editRowData(this)">修改</span>`;
                    }
                }
            ]
        }, query => {
            // 渲染完成后的回调函数
            console.log('渲染完成后的回调函数:', query);
		});
	}

	
	
	return my;
});