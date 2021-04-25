require.config({
	　　　　paths: {
			"testView": "controller/testView",
	　　　　　  "jquery": "/common/scripts/lib/jquery/jquery-3.3.1",
	       	"echarts": "/common/scripts/lib/echarts/echarts.min",
	       	"common":"/common/js/common"
	　　　　}
	});

require(['testView'],function(my){
	my.render();
});



