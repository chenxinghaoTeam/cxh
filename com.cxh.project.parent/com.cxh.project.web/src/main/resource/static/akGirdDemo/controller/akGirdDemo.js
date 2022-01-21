define([ 'akui','common' ], 
		function( ak, common) {
	var me = {};	
	
	me.render = function() {
		ak.parse();
		_init();
	};

	function _init() {
		//获取上级页面传过来的参数
		_getUrlPath();
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
				stationLine:"111"
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
	})
	
	
	
	
	//初始化下拉框数据
	function _initCombox(){
		param = {	
		};
		var data = common.invokeAjax("",param);
		if(data.successful){
			ak("belongStation").setData(data.filterData.substationName);
		}
	}
	
	
	
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