define(['jquery','echarts'],function($,echarts) {

	var me = {};
	
	/**
	 * ajax请求
	 */
	me.invokeAjax = function (pathUrl, param) {
		var dateValue = null;
		$.ajax({
			type : "POST",
			url : pathUrl,
			async : false,
			data : {
				// 把参数序列化成字符串
				jsonParm : JSON.stringify(param)
			},
			dataType : "json",
			success : function(data) {
				dateValue = data;
			},
			error : function(e) {
				layer.msg('error');
			}
		});
		return dateValue;
	}
	
	
	/**
	 * 文件上传ajax请求
	 * id为form表单id
	 */
	me.uploadAjax = function(pathUrl, id) {
		var dateValue = null;
		var form = new FormData(document.getElementById(id));
		$.ajax({
			type : "POST",
			url : pathUrl,
			async : false,
			data : form,
			dataType : "json",//返回的格式必须和controller返回的一致
			processData : false,
			contentType : false,
			success : function(data) {
				dateValue = data;
			},
            error:function(e){
                alert("错误！！");
            }
		});
		return dateValue;
	}

	/**
	 * 解析url传入的参数
	 */
	me._getUrlPath = function () {
		// 获得传过来的login与在数据库中对应的表单
		var paras = location.search; // search获得地址中的参数，内容为'?itemId=12'
		var result = paras.match(/[^\?&]*=[^&]*/g); // match是字符串中符合的字段一个一个取出来，result中的值为['login=xx','table=admin']
		paras = {}; // 让paras变成没有内容的json对象
		for (i in result) {
			var temp = result[i].split('='); // split()将一个字符串分解成一个数组,两次遍历result中的值分别为['itemId','xx']
			paras[temp[0]] = decodeURI(temp[1], "utf-8");// 中文乱码使用decodeURI()
		}
		return paras;
	}
	
	// 提示框
	me.showMessage = function(message, type, time) {
		let str = ''
		switch (type) {
		case 'success':
			str = '<div class="success-message" style="width: 300px;height: 40px;text-align: center;background-color:#daf5eb;;color: rgba(59,128,58,0.7);position: fixed;left: 43%;top: 35%;line-height: 40px;border-radius: 5px;z-index: 9999">\n'
					+ '    <span class="mes-text">' + message + '</span></div>'
			break;
		case 'error':
			str = '<div class="error-message" style="width: 300px;height: 40px;text-align: center;background-color: #f5f0e5;color: rgba(238,99,99,0.8);position: fixed;left: 43%;top: 35%;line-height: 40px;border-radius: 5px;;z-index: 9999">\n'
					+ '    <span class="mes-text">' + message + '</span></div>'
		}
		$('body').append(str)
		setTimeout(function() {
			$('.' + type + '-message').remove()
		}, time)
	}
	
	
	me.getRing = function (chartsId) {
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById(chartsId));
		// 绘制图表
		myChart.setOption({
			title : {
				text : '环形图'
			},
			tooltip : {
				trigger : 'item',
				formatter : '{a} <br/>{b}: {c} ({d}%)'
			},
			legend : {
				orient : 'vertical',
				left : 'right',
				data : [ '直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎' ]
			},
			series : [ {
				name : '访问来源',
				type : 'pie',
				radius : [ '50%', '70%' ],
				avoidLabelOverlap : false,
				label : {
					show : false,
					position : 'center'
				},
				emphasis : {
					label : {
						show : true,
						fontSize : '30',
						fontWeight : 'bold'
					}
				},
				labelLine : {
					show : false
				},
				data : [ {
					value : 335,
					name : '直接访问'
				}, {
					value : 310,
					name : '邮件营销'
				}, {
					value : 234,
					name : '联盟广告'
				}, {
					value : 135,
					name : '视频广告'
				}, {
					value : 1548,
					name : '搜索引擎'
				} ]
			} ]
		});
	}
	return me;
})