require.config({
	　　　　paths: {
			"GridManager": "controller/GridManager",
			"jquery": "/common/scripts/lib/jquery/jquery-3.3.1",
		    "echarts": "/common/scripts/lib/echarts/echarts.min",
		    "GM":"/common/scripts/lib/GridManager/gm",
		    "common":"/common/js/common"
	　　　　}
	});

require(['GridManager'],function(my){
	my.render();
});



