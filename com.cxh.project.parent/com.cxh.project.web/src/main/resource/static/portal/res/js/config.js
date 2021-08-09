require.config({
	　　　　paths: {
			"portal": "controller/portal",
	　　　　　  "jquery": "/common/scripts/lib/jquery/jquery-3.3.1"
	　　　　}
	});

require(['portal'],function(my){
	my.render();
});



