define([ 'jquery', 'echarts', 'common' ], function($, echarts, common) {

	var my = {};
	my.render = function() {
		common.getRing('ring');
		initPage();
	}

	$("#word").click(function(){
		window.location.href = "/ExportWord/exportMillCerttificateWord"
	})
	
	
	// 列表数据
	function initPage() {
		param = {

		}
		var data = common.invokeAjax("/controller/initPage", param);
		if (data.successful) {
			if (data) {
				$("#name").text(data);
			}
		}
	}

	return my;
});