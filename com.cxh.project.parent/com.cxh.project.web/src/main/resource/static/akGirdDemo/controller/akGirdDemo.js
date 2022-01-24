define([ 'akui','common' ], 
		function( ak, common) {
	var me = {};	
	
	var zg = "";
	var zt = "";
	var xl = "";
	me.render = function() {
		ak.parse();
		_init();
	};

	function _init() {
		//获取上级页面传过来的参数
		_getUrlPath();
		_initCombox();
		//获取表格数据
		_initData();
	};	
	
	
	//获取上级页面传过来的参数
	var _getUrlPath = function() {
		var context = location.search;
     	var params = common.parseUrl(context);
		if(params.age){
			
     	}
    	if(params.name){
    		
     	}
		
	};
	
	//初始化加载表格数据
	function _initData(){
		var param = {
				"zg":zg,
				"zt":zt,
				"xl":xl
		};
		common.refreshGird("/akController/initPage",param,"datagrid");
	};
	
	
	
	$("#queryBtn").click(function(){
		if (ak.formatDate(ak("str").value,"yyyy-MM-dd") != "" && ak.formatDate(ak("end").value,"yyyy-MM-dd") != "") {
			if (ak.formatDate(ak("end").value,"yyyy-MM-dd").replaceAll("-", "") < ak.formatDate(ak("str").value,"yyyy-MM-dd").replaceAll("-", "")) {
				common.showTips("开始时间不可大于结束时间!", "info");
				return;
			}
		}
		zg = ak("zg").value;
		zt = ak("zt").value;
		xl = ak("xl").value;
		_initData();
	})
	
	
	
	
	//初始化下拉框数据
	function _initCombox(){
		param = {	
		};
		var data = common.invokeAjax("/akController/initSelect",param);
		if(data.successful){
			ak("zg").setData(data.filterData.zg);
			ak("zt").setData(data.filterData.zt);
			ak("xl").setData(data.filterData.xl);
		}
	}
	
	
	//初始化事件
	$("#clear").click(function () {
		common.clear() ;
	})
	
	
	
	//导出按钮点击事件
	$("#exportBtn").click(function(){
		var grid=ak("datagrid");
		var columns = common.getColumns(grid
				.getBottomColumns());
		if (ak.formatDate(ak("str").value,"yyyy-MM-dd") != "" && ak.formatDate(ak("end").value,"yyyy-MM-dd") != "") {
			if (ak.formatDate(ak("end").value,"yyyy-MM-dd").replaceAll("-", "") < ak.formatDate(ak("str").value,"yyyy-MM-dd").replaceAll("-", "")) {
				common.showTips("开始时间不可大于结束时间!", "info");
				return;
			}
		}
		stationLine = ak("belongStation").value;
		hazardGrade = ak("hazardGradeType").value; 
		hazardState = ak("hazardStateType").value;
		hazardType = ak("Cause").value;
//		strTime = "";
//		endTime =""; //初始化时间控件    运行工况总览调用时注释掉，并放开_initDate

		strTime = ak.formatDate(ak("str").value,"yyyy-MM-dd");
		endTime = ak.formatDate(ak("end").value,"yyyy-MM-dd");
		var param = {
				stationLine:stationLine,
				hazardGrade:hazardGrade,
//				yearLineCode:yearLineCode,
				ifRemove:ifRemove,
				monthBarCode:monthBarCode,
				deviceType:deviceType,
				hazardCase:hazardCase,
				hazardType : hazardType,
				hazardState:hazardState,
				strTime:strTime,
				endTime:endTime,
				maintemanceUnit:maintemanceUnit,
				voltageLevel:voltageLevel,
				excelName : "隐患分析导出详情",
				columns : JSON.stringify(columns)
			};
		common.exportPagesDataToExcel(grid,  appRoot+'/hazardGridPl/exportExcel', param);
	})
	return me;
});