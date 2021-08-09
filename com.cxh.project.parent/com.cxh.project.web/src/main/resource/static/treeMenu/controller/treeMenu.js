define([ 'jquery' ], function($) {

	var my = {};
	var data = [
		{
		  name:'换流',
		  child:[
			{name:'编辑语言',child:[{name:'java'},{name:'c#/.net'},{name:'python'},{name:'前端开发'}]},
			{name:'移动开发',child:[{name:'android开发'},{name:'IOS开发'}]},
			{name:'游戏开发',child:[{name:'phaser游戏开发'},{name:'webGL游戏开发',child:[{name:'3D游戏'},{name:'2D游戏'}]}]}
		  ]
		},
		{
		  name:'设计创作',
		  child:[{name:'平面设计',child:[{name:'电商美工'},{name:'综合平面设计'},{name:'摄影后期'}]},
				{name:'UI设计',child:[{name:'交互设计'},{name:'webUI设计'},{name:'游戏UI设计'}]},
				{name:'软件设计'}]
		},
		{
		  name:'升学考研',
		  child:[{name:'考研'},{name:'大学'},{name:'高中'},{name:'初中'}]
		},
		{
		  name:'职企考证',
		  child:[{name:'公务员',child:[{name:'教师考试'},{name:'建筑工程'}]}]
		}];
	
	my.render = function() {
	}

	
	function tree(data){
		var str = "<ul class=l_tree>";
		for(var i=0;i<data.length;i++){
			str+='<li class="l_tree_branch"><span>-</span>'+data[i].name;
			if(data[i].child){
				str+=tree(data[i].child);
			}
			str+="</li>";
		}
		str += "</ul>";
		return str;
	};
	
	
	$(".lists").html(tree(data));

	//绑定事件
	$(".lists ul li").click(function(event){
		event.stopPropagation(); //阻止事件冒泡
		if($(this).find("ul").is(":visible")){
			//隐藏
			$(this).find("ul").hide();
			$(this).find("span").text("+");
		}else {
			//显示
			$(this).find("ul").show();
			$(this).find("span").text("-");
		}
	});
	
	return my;
});