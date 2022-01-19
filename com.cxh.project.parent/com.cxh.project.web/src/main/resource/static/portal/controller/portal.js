define([ 'jquery' ], function($) {

	var my = {};
	
	my.render = function() {
		navList();
	}
	
	$("#nav_dot li").click(function(e){
		var text = $(this)[0].innerText;
		var url = "";
		if(text == "树行菜单"){
			url = "/treeMenu/index.html"
		}else if(text == "文件上传"){
			url = "/upload/index.html"
		}else if(text == "word导出"){
			url = "/testView/index.html"
		}else if(text == "表格展示"){
			url = "/GridManager/index.html"
		}else if(text == ""){
			url
		}else if(text == ""){
			url
		}
		$("#myiframe").attr("src",url)
	})

	//导航菜单
	function navList(id) {
	    var $obj = $("#nav_dot"), $item = $("#J_nav_" + id);
	    $item.addClass("on").parent().removeClass("none").parent().addClass("selected");
	    $obj.find("h4").hover(function () {
	        $(this).addClass("hover");
	    }, function () {
	        $(this).removeClass("hover");
	    });
	    $obj.find("p").hover(function () {
	        if ($(this).hasClass("on")) { return; }
	        $(this).addClass("hover");
	    }, function () {
	        if ($(this).hasClass("on")) { return; }
	        $(this).removeClass("hover");
	    });
	    $obj.find("h4").click(function () {
	        var $div = $(this).siblings(".list-item");
	        if ($(this).parent().hasClass("selected")) {
	            $div.slideUp(600);
	            $(this).parent().removeClass("selected");
	        }
	        if ($div.is(":hidden")) {
	            $("#nav_dot li").find(".list-item").slideUp(600);
	            $("#nav_dot li").removeClass("selected");
	            $(this).parent().addClass("selected");
	            $div.slideDown(600);

	        } else {
	            $div.slideUp(600);
	        }
	    });
	}
	return my;
});