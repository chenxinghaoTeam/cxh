require.config({
	　　　　paths: {
			"upload": "controller/upload",
			"jquery": "/common/scripts/lib/jquery/jquery-3.3.1",
		    "echarts": "/common/scripts/lib/echarts/echarts.min",
		    "common":"/common/js/common",
		    "bootstrap": "/common/scripts/lib/bootstrap/bootstrap.min",
	　　　　}
	});

require(['upload'],function(my){
	my.render();
});



