define(['akui', 'common'], function (ak, common) {
	ak.parse();
	var my = {};
	my.render = function () {

		textcount();
	}



	function textcount() {
		$("#fiveCount").text(100);
	}


	//近五年点击事件
	$("#fiveCount").click(function () {
		var name = "陈行昊"
		var pathurl = "/akGirdDemo/index.html?age=17&name="+name;
		common.showWindows("demo详情页面", pathurl)
	})
	return my;
});