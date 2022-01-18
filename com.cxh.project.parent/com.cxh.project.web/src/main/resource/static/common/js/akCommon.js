define(
		['akui'],
		function(ak) {
			var app = {};
			app.appname = "";
			var me = {};
			// ak初始化
			ak.parse();
			me.color = [ "#009391", "#30BFE1", "#EA9B02", "#AA8DE0", "#45B969" ];

				
			 /**
			 * 执行ak调用后台（适用于异步调用）
			 * @param pathUrl 调用地址
			 * @param params 传入的参数
			 * @returns
			 */
			 me.invokeAjaxAsync = function(pathUrl,param,callback){
			    	
			 me.send(pathUrl,
			 "GET",
			 {jsonParam:JSON.stringify(param)},
			 true,
			 callback
			 );
			 }
				
			 
			 me.invokeAjaxAsync2 = function(pathUrl,param,callback){
			    	
				 me.send2(pathUrl,
				 "GET",
				 param,
				 true,
				 callback
				 );
				 }
			
			 /**
			 * 填充表格
			 * @param pageIndex
			 * @param pageSize
			 * @param dataResult
			 * @param grid
			 * @returns
			 */
			 me.fillData = function(pageIndex, pageSize, dataResult, grid){
			 var data = dataResult;
			 var totalCount = dataResult.length;
			 var arr = [];
			 var start = pageIndex * pageSize;
			 var end = start + pageSize;
			 for (var i = start, l = end; i < l; i++) {
			 var record = data[i];
			 if (!record) continue;
			 arr.push(record);
			 }
			 grid.setTotalCount(totalCount);
			 grid.setPageIndex(pageIndex);
			 grid.setPageSize(pageSize);
			 grid.setData(arr);
					
			 grid.on("beforeload", function (e) {
			 e.cancel = true;
			 var pageIndex = e.data.pageIndex;
			 var pageSize = e.data.pageSize;
			 me.fillData(pageIndex, pageSize, data, grid);
			 });
			 }
				
			 function getComBoxName(id) {
					var a = ak(id)
					var text = a.getText()
					if (!text) return ''
					var name = $(a.el).prev().text()
					if (!name) throw new Error('id为:' + id + '的字段名称不能为空！')
					return name + '[' + text + '],'
				}
			 me.getComBoxName = getComBoxName
			

			/**
			 * 初始化ak
			 */
			me.init = function(akParam) {
				ak = akParam;
			}

			/**
			 * 加载swiper，使页面自适应
			 */
			me.initSwiper = function() {
				var clientWidth = document.documentElement.clientWidth;
				var as = 3;
				if (clientWidth > 1200) {
					as = 4;
				}
				var swiper = new Swiper('.swiper-container', {
					pagination : '.swiper-pagination',
					nextButton : '.swiper-button-next',
					prevButton : '.swiper-button-prev',
					spaceBetween : 10,
					slidesPerView : as,
					slidesPerGroup : as
				});
			}

			/**
			 * 使用pi6000中的ak发送请求，访问后台
			 * 
			 * @param p_path
			 *            访问路径
			 * @param p_method
			 *            请求方式（GET或者POST请求）
			 * @param p_data
			 *            参数对象
			 * @param p_async
			 *            同步或者异步调用（true异步或者false同步）
			 * @param p_callback
			 *            回调函数（后台返回数据结果集）
			 */
			me.send = function(p_path, p_method, p_data, p_async, p_callback) {
				ak.send(app.appname + p_path, p_method, p_data, p_async,
						p_callback);
			};
			
			me.send2 = function(p_path, p_method, p_data, p_async, p_callback) {
				ak.send(p_path, p_method, p_data, p_async,p_callback);
			};

			/**
			 * 执行ak调用后台（适用于同步调用）
			 * 
			 * @param pathUrl
			 *            调用地址
			 * @param params
			 *            传入的参数
			 * @returns
			 */
			me.invokeAjax = function(pathUrl, param) {
				var dateValue = null;
				me.send(pathUrl, "GET", {
					jsonParam : JSON.stringify(param)
				}, false, function(data) {
					dateValue = data;
				});
				return dateValue;
			}

			/**
			 * 显示窗口
			 * 
			 * @param title
			 *            标题
			 * @param pathUrl
			 *            完整地址
			 * @returns
			 */
			me.showWinView = function(title, pathUrl, callback, widthValue,
					heightValue) {
				var localUrl = pathUrl;
				if (widthValue == null) {
					widthValue = "87%";
				}
				if (heightValue == null) {
					heightValue = "93%";
				}
				// 弹出窗
				var win = ak.open({
					url : localUrl , // 页面地址
					title : title, // 标题
					allowResize : true, // 允许尺寸调节
					allowDrag : true, // 允许拖拽位置
					showCloseButton : true, // 显示关闭按钮
					showMaxButton : true, // 显示最大化按钮
					showModal : true, // 显示遮罩
					width : widthValue, // 宽度
					height : heightValue, // 高度
					ondestroy : callback
				});
				win.showAtPos("206px", "55px");
			}
			
			
			/**
			 * 显示窗口
			 * 无localUrl
			 */
			me.showWinViewForPms = function (title,pathUrl,widthValue,heightValue){
				if(widthValue == null){
					widthValue = "87%";
		    	}
		    	if(heightValue == null){
		    		heightValue = "93%";
		    	}
		    	// 弹出窗
				var win = ak.open({
		            url: pathUrl,   //页面地址
				    title: title,      //标题
				    allowResize: true,       //允许尺寸调节
				    allowDrag: true,         //允许拖拽位置
				    showCloseButton: true,   //显示关闭按钮
				    showMaxButton: true,     //显示最大化按钮
				    showModal: true,        //显示遮罩
				    width: widthValue,      //宽度
				    height: heightValue     //高度
		        });
				win.showAtPos("206px", "55px");
			}

			/**
			 * 显示加载提示信息
			 * 
			 * @param message
			 *            需要显示的信息
			 * @param state
			 *            提示信息的类型，默认（success）常用（success，info，danger）
			 * @param destroyTime
			 *            销毁时间（可不传），默认（2000）
			 */
			me.showTips = function(message, state, destroyTime) {
				if (message == null) {
					message = "";
				}
				var _b = "";
				if (state == "info") {
					_b = "提示";
				} else if (state == "danger") {
					_b = "错误";
				} else if (state == "default") {
					_b = "默认";
				} else if (state == "warning") {
					_b = "警告";
				} else {
					state = "success";
					_b = "成功";
				}

				if (destroyTime == null) {
					destroyTime = 2000;
				}

				ak.showTips({
					content : "<b>" + _b + "</b> <br/>" + message,
					state : state, // default|success（成功）|info（信息）|warning|danger(错误)
					x : "center", // left|center|right
					y : "center", // top|center|bottom
					timeout : destroyTime
				// 自动消失间隔时间。默认2000（2秒）。
				})
			}

			/**
			 * 显示窗口
			 * 
			 * @param title
			 *            标题
			 * @param pathUrl
			 *            链接地址
			 * @returns
			 */
			me.showWindows = function(title, pathUrl, callback, widthParam,
					heightParam) {
				var url = app.appname + pathUrl;
				me.showWinView(title, url, callback, widthParam, heightParam);
			}
			
			me.showWindows2 = function(title, pathUrl, callback, widthParam,
					heightParam) {
				var url = pathUrl;
				me.showWinView(title, url, callback, widthParam, heightParam);
			}
			/**
			 * 解析带参数的页面地址 引用位置：defectRecordDetail,
			 */
			me.parseUrl = function(urlVal) {
				var paramsVal = null;
				var splitParamsArr = null;

				var urlObj = {};
				if (urlVal.indexOf("?") != -1) {
					splitParamsArr = [];
					paramsVal = urlVal.slice(1, urlVal.length);

					if (paramsVal.indexOf("&") != -1) {
						splitParamsArr = paramsVal.split("&");
					} else {
						splitParamsArr.push(paramsVal);
					}

					// splitParamsArr不为空
					if (splitParamsArr.length != 0) {
						for (var i = 0; i < splitParamsArr.length; i++) {
							if (splitParamsArr[i].indexOf("=") != -1) {
								var tempName = splitParamsArr[i].split("=")[0];
								// var tempVal = splitParamsArr[i].split("=")[1];
								//中文转码
								var tempVal = decodeURI(splitParamsArr[i].split("=")[1], "utf-8");
								urlObj[tempName] = tempVal;
							}
						}
					}
				}
				return urlObj;
			}

			/**
			 * 刷新表格
			 * 
			 * @param urlPath
			 *            后台地址
			 * @param param
			 *            参数
			 * @param gridId
			 *            表格id
			 * @param callBack
			 *            数据获取完成时的回调函数
			 * @returns
			 */
			me.refreshGird = function(urlPath, param, gridId, callBack) {
				var grid = ak(gridId);
				grid.setUrl(app.appname + urlPath);
				if (callBack == null) {
					grid.load(param);
				} else {
					grid.load(param, callBack);
				}
			}
			/**
			 * 重置按钮
			 */
			me.clear = function() {
				$(".ak-combobox.ak-comboboxwhite").each(function(index, value) {
					ak(value.id).selects(-1)
				});
				$(".ak-datepicker").each(function(index, value) {
					ak(value.id).setValue()
				});
			}

			/**
			 * 没有数据时显示
			 */
			me.notData = function(divId) {
				$("#" + divId).empty();
				var notDatadiv = $("<div class='noData'>无数据</div>");
				notDatadiv.css({
					"width" : "100%",
					"height" : "100%",
					"transform" : "translateY(50%)",
					"text-align" : "center",
					"font-size" : "18px"
				});
				$("#" + divId).append(notDatadiv);
			}
			/**
			 * 获取grid列
			 * 
			 */
			me.getColumns = function(columns) {
				var returnColums = [];
				for (var i = 0; i < columns.length; i++) {
					var column = columns[i];
					if (!column.visible) {
						continue;
					}
					if (!column.field) {
						continue;
					} else {
						var c = {
							header : column.header,
							field : column.field
						};
						returnColums.push(c);
					}
				}
				return returnColums;
			}
			/**
			 * 导出Excel
			 * 
			 */
		me.exportPagesDataToExcel = function(grid, url, json) {
		        var totalCount = ak(grid).totalCount;
				if(totalCount == 0){
					ak.showTips({
				            content: "<b>提示</b> <br/>当前表格无数据!",
				            state: "info",
				            x: "center",
				            y: "center",
				            timeout: 2000
				        });
					return;
				} 
				var excelName = json.excelName;
	            ak.mask({
	                el: document.body,
	                cls: 'ak-mask-loading',
	                html: ''
	            });
	            var xhr = new XMLHttpRequest();
	            xhr.open('post', url, true);
	            window.top.tokenArrays = window.top.tokenArrays.filter(function(element,index,self){
	            		return self.indexOf(element) === index;
	            });

	            var formData = "excelData="+JSON.stringify(json);
	            
	            xhr.send(formData);
 			}
			/**
			 * 饼图无数据样式
			 */
			me.pieNoNum = function(data, chartId) {

				if (!data.seriesData[0][0] || data.seriesData[0][0].name == "无数据") {
					var noNumOption = {
						color : [ "#e8e8e8" ],
						xAxis : {
							show : false
						},
						yAxis : {
							show : false
						},
						series : [ {
							itemStyle : {
								normal : {
									shadowBlur : 11,
									shadowOffsetX : 0,
									shadowColor : '#e8e8e8',
									show : false,
									textStyle : {
										color : 'black'
									}
								}
							}
						} ]
					};
					var obj = {
						"legendData" : [ {
							"name" : "无数据",
							"code" : "N/A"
						} ],
						"seriesData" : [ [ {
							"name" : "无数据",
							"value" : 0
						} ] ]
					}
					ak(chartId).setDisplayModel(noNumOption);
					ak(chartId).setViewModel(obj);
				} else {
					ak(chartId).setViewModel(data);
				}

			}

			/**
			 * 获取当前时间
			 */
			me.getNowTimes = function() {
				var year = null;
				var month = null;
				var nowDay = null;
				var monthMaxDays = null;
				var date = new Date();
				year = date.getFullYear();
				month = date.getMonth() + 1;
				nowDay = date.getDate();
				monthMaxDays = me.getMonthMaxDays(year, month);
				month = addZero(month);
				nowDay = addZero(nowDay);
				var timesObj = {
					"year" : year.toString(),
					"month" : month.toString(),
					"nowDay" : nowDay.toString(),
					"monthMaxDays" : monthMaxDays.toString()
				};

				return timesObj;
			}
			
			function formatDate(date){
				var myyear = date.getFullYear();
				var mymonth = date.getMonth()+1;
				var myweekday = date.getDate();
				if(mymonth<10){
					mymonth = "0"+mymonth;
				}
				if(myweekday<10){
					myweekday = "0"+myweekday;
				}
				return myyear+"-"+mymonth+"-"+myweekday;
			}
			/**
			 * 本周开始时间
			 */
			me.getWeekStartDate = function(){
				var now = new Date;//当前日期
				var nowDayOfWeek=now.getDay();//本周第几天
				var nowDay = now.getDate();//当前日
				var nowMonth = now.getMonth();//当前月
				var nowYear = now.getYear();//当前年
				nowYear+=(nowYear<2000)?1900:0;
				var weekStartDate = new Date(nowYear,nowMonth,nowDay-nowDayOfWeek+1);
				return formatDate(weekStartDate);
			}
			/**
			 * 本周结束时间
			 */
			me.getWeekEndDate = function(){
				var now = new Date;//当前日期
				var nowDayOfWeek=now.getDay();//本周第几天
				var nowDay = now.getDate();//当前日
				var nowMonth = now.getMonth();//当前月
				var nowYear = now.getYear();//当前年
				nowYear+=(nowYear<2000)?1900:0;
				var weekEndDate = new Date(nowYear,nowMonth,nowDay+(6-nowDayOfWeek+1));
				return formatDate(weekEndDate);
			}
			
			
			
			
			
			/**
			 * 本周结束时间
			 */
			
			 /**
			 * 获取当前月的最大天数
			 */
			 me.getMonthMaxDays = function(year,month){
				 var days = new Date(year,month,0).getDate();
				 return days;
			 }
			 
			 /**
			 * 当值小于10时前面补0
			 * @param num 小于10的数
			 * @returns num 数值补0后返回
			 */
			 function addZero(num) {
				 if (num < 10) {
				 num = "0" + num;
				 }
				 return num;
			 }
			 /**
			  * data为网省表格的数据源；
			  * callBackClick(id,name)
			  * id为网省id
			  * name为网省名称
			  */
			 me.makeProvinceTable = function(data,callBackClick){
				 $("#provinceDiv").empty();
				 var buttext = "";
				 buttext += "<span class='provinceSpan'>所属网省</span>"+
					"<img class='chooseImg' id='downClass' src='res/images/down.png'></img>"+
					"<img class='chooseImg active' id='upClass' src='res/images/up.png'></img>";
				 $("#provinceDiv").append(buttext);
				 
				 $(".chooseImg").click(function(){
						if(this.id=="downClass"){
							$("#downClass").addClass("active");
							$("#upClass").removeClass("active");
							$(".provinceFont").removeClass("active");
						}else if(this.id=="upClass"){
							$("#upClass").addClass("active");
							$("#downClass").removeClass("active");
							$(".provinceFont").addClass("active");
						}
					})
					$(".provinceFont").empty();
					var text = "";
					text +="<table class='provinceTable' >"+
						"<tr><td class='tdFont' id='all'>全部</td><td class=''></td><td class=''></td></tr>";
					text += "<tr>";
					for (var i = 0;  i< data.length; i++) {
						text +="<td class='tdFont' id='"+data[i].id+"'>"+data[i].text+"</td>";
							if( i!=0 && i!= data.length && i%3 == 2){
								text +="</tr><tr>";
							}
					} 
					text +="</tr>"
					text += "</table>"
					$(".provinceFont").append(text);
					$(".tdFont").click(function(){
						return callBackClick(this.id,this.innerText);
					})
					$(".tdFont").bind("mouseover", function(e) {
						$(this).css("background","#EB9F0D");
					});
					$(".tdFont").bind("mouseout", function(e) {
						$(this).css("background","#424979");
					});
					
				};
				 /**
				 * 检验字符串
				 */
				 me.checkParams = function(param){
					 if(param == "all" || param == "cn" || param == null || param == ""
						 || param == "undefined"){
						 return true;
					 }else{
						 return false;
					 }
				 }
				 /**
				 * 在使用swiper时，根据数据的总个数和每小页显示个数取得swiper的页数
				 */
				 me.swiperNumber = function (dataLength,everyPageShowNum){
					 if(dataLength == null || dataLength == 0 || everyPageShowNum == null || everyPageShowNum == 0){
						 return 0;
					 }
					 //根据长度取得分页数
					 var pageNum = 0;
					 if(dataLength % everyPageShowNum == 0){
						pageNum = dataLength/everyPageShowNum;
					 }else{
						pageNum = parseInt(dataLength/everyPageShowNum) + 1;
					 }
					 return pageNum;
				 }
				 
				 me.timeFormat = function (time){
					 var date = new Date(time);
					 var year = date.getFullYear(),
					 	month = date.getMonth()+1,
					 	day = date.getDate();
					 return year+'-'+month+'-'+day
				 }
				 /**
			     * 显示加载
			     * @param pathUrl 调用地址
			     * @param params 传入的参数
			     * @returns
			     */
				me.mask = function(pathUrl,param,callback){
					ak.mask({
				        el: document.body,
				        cls: 'ak-mask-loading',
				        html: '拼命加载中，请耐心等待…'
				    });
			    }
				/**
			     * 取消加载
			     * @param pathUrl 调用地址
			     * @param params 传入的参数
			     * @returns
			     */
				me.unmask = function(pathUrl,param,callback){
					ak.unmask(document.body);
			    } 
				
				
				
				/**
				 * 环图无数据样式
				 */
				me.ringNoNum = function(data, chartId,id) {
					if (data.seriesData[0][0].name == "无数据") {
						var legendAdd = {
								 title: {
								        text: 0 + '\n\n'+id,
								        x: 'center',
								        y: 'center',
								        textStyle : {
								            fontSize : 14,
								            fontFamily: "微软雅黑 Light",
							            	fontWeight : 'normal'	
								        }
								    },
								    
								    yAxis:{
								    	show:false
								    },
								    xAxis:{
								    	show:false
								    },
								color: ["#e8e8e8"],
							    legend: {
							    	y : 'bottom',
									x : "center",
							        show: true
							    },
							    series: [{
						            label: {
						                normal: {
						                    show: true
						                }
						            },
						            x : '0%', // for funnel
						            y : '0%', // for funnel
						            center: ['50%','50%'],
								    radius : ['45%', "65%"],
								    
							    }]
							}
						ak(chartId).setDisplayModel(legendAdd);
						ak(chartId).setViewModel(data);
					} else {
						ak(chartId).setViewModel(data);
					}

				}
				
				//跳转页面
				me.pageJumping = function(o){
					var n = {}
					n.p = {
						id:"page_id",
						parentContainer:$("body"),
						returnPageRefresh:false,
						title:null,
						url:"",
						iframeName:"iframe_pageName",
						iframeId:"iframe_id",
						prev:".ak-prevPage",
						next:".ak-nextPage",
						ifParent:false,
						nextButton:true,
						onload:function(){
						},
						defaultBut:true
					}
					n.c = function(){
						for(var a in o){
							this.p[a] = o[a];
						}
						if(this.p.defaultBut){
							this.b();
						}
					}
					n.move = function(e){
						var y = e.clientY,
							x = e.clientX,
							_t = $(e.target).parent(),
							oy = _t.offset().top,
							ox = _t.offset().left,
							_f = function(){
							$(document).off("mousemove.d");
							$(document).off("mouseup.d");
							_t.find(".pageJ-shadow").remove();
						}
						if(e.type == "mousedown"){
							_t.append("<div class='pageJ-shadow'></div>");
							$(document).on("mousemove.d",function(f){
								var lx = (ox+(f.clientX-x)),
									ty = (oy+(f.clientY-y));
								if(lx<10){
									lx = 0;
								}else if(lx>$(window).width()-20){
									lx = $(window).width()-20;
								}else if(ty<10){
									ty = 10;
								}else if(ty>$(window).height()-20){
									ty = $(window).height()-20
								}else{
									_t.css({
										top:ty,left:lx
									})
								}
							})
							$(document).on("mouseup.d",function(f){
								_f();
							})
						}else if(e.type == "mouseup"){
							_f();
						}
					}
					n.b = function(){
						var a_next = "<div class='ak-nextPage ak-active'><span style='position:absolute;left:0;right:0;top:0;bottom:0;'></span><a class='morePagej_a' title='拖拽' href='javascript:;'></a></div>";
						if(this.p.nextButton){
							this.p.parentContainer.append(a_next);
							this.p.parentContainer.find(".ak-nextPage").on("mousedown mouseup","a",function(e){
								n.move(e);
								e.stopPropagation;
							})
						}
					}
					n.f = function(fn){
						$(this.p.next).hide();
						var thisTitle = this.p.title,parentTitle = window.top.$("#curMenuTitle"),parentTitleText = parentTitle.text();
						var box = "<div id='"+this.p.id+"' class='page_box'>" +
								"<div class='ak-prevPage ak-active'><span style='position:absolute;left:0;right:0;top:0;bottom:0;'></span><a class='morePagej_a' title='拖拽' href='javascript:;'></a></div>"+
								"<iframe id='"+this.p.iframeId+"' name='"+this.p.iframeName+"' src='"+this.p.url+"' class='page_iframe'></iframe>"+
							"</div>";
						var p_box = this.p.parentContainer;
						if(p_box.find('#'+this.p.id).length){
							p_box.find('#'+n.p.id).show();
							$(parentTitle).text(thisTitle||document.getElementById(n.p.iframeId).contentWindow.document.title);
						}else{
							this.p.parentContainer.append(box);
						}
						if(this.p.ifParent){
							if(thisTitle != null){
								$(parentTitle).text(thisTitle);	
							}
						}else{
							var _that = this;
							window[this.p.iframeName].onload = function(e){
								thisTitle = thisTitle||document.getElementById(n.p.iframeId).contentWindow.document.title;
								$(parentTitle).text(thisTitle);
								_that.p.onload();
							};
						}
						p_box.find(this.p.prev).on("click","span",function(){
							n.p.callback();
							parentTitle.text(parentTitleText);
							$(n.p.next).show();
							n.p.returnPageRefresh?p_box.find('#'+n.p.id).remove():p_box.find('#'+n.p.id).hide();
						})
						if(typeof(fn) == "function"){
							fn(p_box.find(this.p.prev));
						}
						this.p.parentContainer.on("mousedown mouseup","a",function(e){
							n.move(e);
							e.stopPropagation;
						})
					}
					n.c(); 
					
					return n;
				}	
				
/****************************************气象***********************************************/				
				
				me.sliderTime = function(str,lx,close,items,number,pu,pt){
					var sliderTrue = str,sliderLxTrue = lx;
					if(items == undefined || items == null || items == ""){
						ak.showTips({
				            content: "<b>提示</b><br/>今日无《"+lx+"数据》！",
				            state: "info",
				            x: "left",
				            y: "bottom",
				            timeout: 4000
				        });
						return ;
					}
					 if($(".TimeS").length == 0){
						 ak.showTips({
					        content: "<b>错误提示</b> <br/>检测到页面没有填写class='TimeS'容器，请在GIS容器内添加后再试！",
							state: "warning",
							x: "left",
							y: "bottom",
					        timeout: 4000
					    });
						return false;
					}
					//动态创建html
					var _div = 	"<div class='TimeSlider'>"+
								"<p class='p_yblx'>预报类型：<b class='slider_lx'>--</b>"+
								"<div class='div_slider'><div class='div_slider_button'>"+
								"<button id='play' title='播放'></button>"+
								"<button id='stop' title='暂停'></button>"+
								"<button id='refresh' title='复位'></button>"+
								"<button id='close' title='关闭'>×</button>"+
								"</div>"+
								"<div class='div_slider_kz'>"+
								"<span class='div_slider_s1'></span>"+
								"<a href='javascript:;' class='div_slider_s2'>"+
								"<span>Time</span>"+
								"<b></b>"+
								"</a>"+
								"<span class='div_slider_s3'></span>"+
								"</div>"+
								"<p class='p_ybsj'>预报时间：<b class='slider_yubao'>--</b></p>"+
								"</div>" +
								"</div>";
					if((sliderTrue != bvr && bvr !=undefined)||(sliderLxTrue != blx && blx !=undefined)){
						me.sliderTimeClose();
					}
					if(sliderTrue == bvr || sliderLxTrue == blx){
						ak.showTips({
				            content: "<b>提示</b> <br/>正在播放《"+lx+"数据》，请勿重复操作！",
				            state: "info",
				            x: "center",
				            y: "center",
				            timeout: 3000
				        });
						return;
					}
					// else if(myIframe_2D.window.Gis2d_weatherLayer == undefined){ 重要：调用二维方法显示图片
						// 	setTimeout(slider,1000);
					// }
					else{
						$(".TimeS").append(_div);
						setTimeout(function(){$(".TimeSlider").css("bottom","4px")},500);
						if(close == 1){
							$("#close").hide();		
						}
						$("#play").attr("disabled",true);
						$("#stop").attr("disabled",false);
						bvr = str;
						blx = lx;
						var sliderArrayT = items;//方法返回值
						var sliderArray = [];
						var sLength = sliderArrayT.length;
						var startButton = true;//起始
						var sliderX,//鼠标位置
						sliderLx = 0,//移动按钮起始位置
						sliderDiv = $(".div_slider"),//容器
						sliderDivButton = sliderDiv.find(".div_slider_s2"),//移动按钮
						sliderDivLoad = sliderDiv.find(".div_slider_s3"),//蓝色条
						sliderWidth = sliderDiv.width(),//容器总宽度
						sliderTime,//定时器
						sliderClientX,//滑动位置
						sliderTLeft = 0,//按钮位置
						sliderDivButtonIndex = 0,
						sliderArrayIndex = 0;//数组索引值
						var sliderLine = $("<div class='div_slider_line'><p class='slidertr'></p></div>");
						for(i=0;i<sliderArrayT.length;i++){
							var sliderNumber = number||3;
							sliderArray[i] = sliderArrayT[i].split('_')[sliderNumber];//截取
							sliderYear = sliderArray[i].substr(0,4); //年
							sliderMonth = sliderArray[i].substr(4,2); //月
							sliderdate = sliderArray[i].substr(6,2);//日
							sliderhh = sliderArray[i].substr(8,2); //时
							sliderArray[i] = sliderYear+"/"+sliderMonth+"/"+sliderdate+"/ "+sliderhh+":00";
						}
						var sL = {"start":sliderArray[0],"end":sliderArray[sLength-1]};//获取开始时间和终止时间
						$(".slider_yubao").text(sliderArray[0]);
						$(".slider_lx").text(lx);
						sliderDiv.append(sliderLine);
						for(i=0;i<sliderArray.length-1;i++){
							sliderLine.find(".slidertr").append("<span style='width:"+(100/(sliderArrayT.length-1))+"%'></span>");
						}
						var spanWidth = sliderLine.find(".slidertr span").width();
						sliderDivButton.mousedown(function(f){
								sliderX = f.clientX;//鼠标位置
								sliderLx = sliderDivButton.position().left;//按钮距容器left值
								clearTimeout(sliderTime);
								sliderDiv.removeClass("div_sliderActive");//删掉缓冲动画
								$(document).on('mousemove.d',function(event){
									sliderClientX = event.clientX;//获取鼠标滑动像素值
									slider();//调用方法
								})			
							})
						sliderDivButton.mouseup(function(){//鼠标UP事件
							startButton = false;
							$(document).off('mousemove.d');
							sliderUpMath = sliderArrayIndex*(sliderWidth/(sliderArrayT.length-1));//获取鼠标UP事件移动的距离
							sliderDivButton.css("left",sliderUpMath.toFixed(0)+"px");//移动
							sliderDivLoad.css("width",sliderUpMath.toFixed(0)+"px");//控制蓝色条宽度
							sliderTLeft = sliderUpMath;
							sliderArrayIndex = (((sliderArrayT.length-1)/100)*sliderDivButtonIndex).toFixed(0);
							$("#play").attr("disabled",false);
							$("#stop").attr("disabled",true);
						})
						function slider(){//移动方法
							sliderDivButton.css("left",(sliderLx+(sliderClientX-sliderX)));//移动
								if(sliderDivButton.position().left<0){//判断左侧溢出
									sliderDivButton.css("left","0");
									$(document).off('mousemove.d');
									}
								if(sliderDivButton.position().left>sliderWidth){//判断右侧溢出
									sliderDivButton.css("left",sliderWidth.toFixed(0)+"px");
									$(document).off('mousemove.d');
									}			
								sliderTLeft = sliderDivButton.position().left;//移动传值
								sliderMover();//调用方法
						}
						function sliderMover(Left,res){//控制按钮移动和进度距离
							startButton = false;
							var L = Left?Left:sliderDivButton.position().left;
							sliderDivButtonIndex = (L/sliderWidth)*100;//算出对应index值
							sliderArrayIndex = (((sliderArray.length-1)/100)*sliderDivButtonIndex).toFixed(0);//计算拖动位置对应显示图片index
							var I = res?0:sliderArrayIndex;
							sliderDivButton.find("span").text(sliderArray[I])//赋值计算拖动位置对应显示图片名称
							sliderDivLoad.css("width",sliderTLeft+"px");//控制蓝色条宽度
							sliderDivButton.css("left",sliderTLeft+"px");//移动距离
							var weatherPlayerPath = pu + "/riskWarning/imagePlayer?playTime=" + pt + "&fileName=" + sliderArrayT[sliderArrayIndex] + "&dataType=" + bvr;
							me.weatherPlayer(weatherPlayerPath);
						}
						
						
						function sliderPlay(){//自动播放
							sliderDiv.addClass("div_sliderActive");//删掉缓冲动画
							startButton?sliderTLeft = 0:sliderTLeft = sliderDivButton.position().left+(sliderWidth/(sliderArrayT.length-1));//自动播放left赋值
				
							sliderMover(sliderTLeft);//按钮移动和进度
							sliderTime = setTimeout(sliderPlay,1000);//递归
							length = sliderArray.length-2;//计算数组个数	
							sliderDiv.addClass("div_sliderActive")//增加播放按钮移动缓冲动画
							if(sliderArrayIndex>length){//判断循环调用超出数组个数
								$("#play").attr("disabled",true);
								$("#stop").attr("disabled",true);
								clearTimeout(sliderTime);
								return;
							}
						}
						$("#play").on(click,function() {
							sliderPlay();
							$("#play").attr("disabled",true);
							$("#stop").attr("disabled",false);
						});
						$("#stop").on(click,function() {
							clearTimeout(sliderTime);
							$("#play").attr("disabled",false);
							$("#stop").attr("disabled",true);
						});
						$("#refresh").on(click,function() {
							reclose();
							sliderMover(sliderTLeft,true);//按钮移动和进度
							var weatherPlayerPath = pu + "/riskWarning/imagePlayer?playTime=" + pt + "&fileName=" + sliderArrayT[sliderArrayIndex] + "&dataType=" + bvr;
							me.weatherPlayer(weatherPlayerPath);
						});	
						$("#close").on(click,function() {
							sliderDiv.parent().remove();
							bvr = false;
							bxl = false;
							reclose();
							//myIframe_2D.window.Gis2d_WeatherLjClose(); 重要：调用二维方法显示图片关闭二维地图显示
						});	
						var reclose = function (){
							sliderTrue = true;
							sliderLxTrue = true;
							startButton = true;
							sliderLx = 0;//移动按钮起始位置
							sliderTLeft = 0;//按钮位置
							sliderDivButtonIndex = 0;
							sliderArrayIndex = 0;//数组索引值
							setTimeout(function(){
								$("#play").attr("disabled",false);
							}, 2000);
							clearTimeout(sliderTime);
						}
					}
					sliderPlay();
					$("#stop").trigger("click");
					return sL;//返回开始时间和结束
				}
				/*功能结束*/			
				
				
			me.securityMeasure =  function(){
				
				$('.ak-textbox .ak-textbox-input').on('input',function(){
			        var val = this.value;
			        var mess = ak.isValid(val, ' ./()（）、_，,-0123456789±ⅠⅡⅢⅣⅤⅥⅦⅧⅨⅩ', ['en', 'cn' , 'nu']);
			        if (val.match(/^\s*$/) && val != "") {
			            $(this).val('');
			            ak.showTips({
			                content: "输入内容不可包含非法字符！",
			                state: 'info',
			                x: 'center',
			                y: 'center',
			                timeout: 3000
			            });
			        }
			        if (!mess){
			            ak.showTips({
			                content: "输入内容不可包含非法字符！",
			                state: 'info',
			                x: 'center',
			                y: 'center',
			                timeout: 3000
			            });
			            var reg = /[\u4e00-\u9fa5a-zA-Z0-9ⅠⅡ ±  .×/()（）、_，,-]/;
			            var arr = val.split('');
			            var str = '';
			            for (var i = 0; i < arr.length; i++) {
			                if (reg.test(arr[i])) {
			                    str += arr[i];
			                }
			            }
			            this.value = str;
			            return false;
			        }
			        if (val.length > 1000) {
			            ak.showTips({
			                content: "输入内容不得超过1000个字符！",
			                state: 'info',
			                x: 'center',
			                y: 'center',
			                timeout: 3000
			            });
			            var str = val.substring(0, 1000);
			            this.value = str;
			            return false;
			        }
			    });	
			}
				 
			return me;
		})