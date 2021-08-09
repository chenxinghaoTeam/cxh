require.config({
	　　　　paths: {
			"treeMenu": "controller/treeMenu",
	　　　　　  "jquery": "/common/scripts/lib/jquery/jquery-3.3.1"
	　　　　}
	});

require(['treeMenu'],function(my){
	my.render();
});



