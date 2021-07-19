define([ 'jquery', 'echarts', 'common'], function($, echarts, common) {

	var my = {};
	var fileText;
	var fileStatus;
	my.render = function() {
		getFileData();
		initClickBtn();
	}
	
	
	function initClickBtn(){
		 $(".tableCLS tbody").on("click","tr",function (e) {
			 if(e.target.textContent == '下载'){
				 var filename = $(this).find('td').eq(3).text();
		         window.location.href = "/download?filename="+filename;
			 }else if(e.target.textContent == '删除'){
				 param = {
					"id" : $(this).find('td').eq(0).text(),
					"filename" : $(this).find('td').eq(3).text()
				 };
				 var data = common.invokeAjax("/deleteFile",param);
				 if(data.successful){
					 common.showMessage("删除成功","success","2000")
					 getFileData();
				 }
			 }else{
				 return
			 }
     })
	}
	
	function getFileData(){
		var text ="";
		$("#fileTable").text("")
		param = {};
		var data = common.invokeAjax("/getAllFile",param);
		if(data){
			for (var i = 0; i < data.length; i++) {
				text += "<tr>" +
				"<td style='display:none'>"+data[i].ID+"</td>"+
				"<td>"+(i+1)+"</td>"+
				"<td>"+data[i].TITLE+"</td>"+
				"<td>"+data[i].FILENAME+"</td>"+
				"<td>"+data[i].UPLOADDATE+"</td>"+
				"<td class='download'>下载</td>"+
				"<td class='delete'>删除</td>"+
				"</tr>"
			}
		}
		$("#fileTable").append(text);
	}
	

	
	$("#uploadBtn").click(function() {
		var data = common.uploadAjax("/uploadFile","form1");
		if(data.successful){
			getFileData();
		}
		
	})
	
	return my;
});