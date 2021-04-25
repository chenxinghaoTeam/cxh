define(['jquery','echarts','common'], function($,echarts,common){
	
	var my = {};
	my.render=function(){
		var data = common._getUrlPath();
		common.getRing('ring');
	}    
	
	
	return my;
});