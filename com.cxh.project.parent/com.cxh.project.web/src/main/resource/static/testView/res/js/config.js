require.config({
	　　　　paths: {
			"testView": "controller/testView",
	　　　　　  "jquery": "/common/scripts/lib/jquery/jquery-3.3.1",
	       	"echarts": "/common/scripts/lib/echarts/echarts.min",
	       	"common":"/common/js/common"
	　　　　}
	});

require(['testView','jquery','echarts','common'],function(my){
	my.render();
});



