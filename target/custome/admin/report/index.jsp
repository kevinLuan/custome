<%@page import="java.util.Set"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>报表</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css"
	href="/custome/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/custome/themes/icon.css">

<script type="text/javascript"
	src="/custome/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript"
	src="/custome/scripts/jquery.json-2.2.min.js"></script>
<script type="text/javascript"
	src="/custome/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="/custome/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/custome/scripts/custome.base.js"></script>
<script type="text/javascript" src="/custome/scripts/validate.js"></script>
<script type="text/javascript" src="/custome/scripts/ichart.latest.min.js"></script>
<script type="text/javascript">
	$(function() {
		<%
		List<Float> flow= (List<Float>)request.getAttribute("flow");
		StringBuilder builder=new StringBuilder();
		for(int i=0;i<flow.size();i++){
		  if(i==0){
		    builder.append("[");
		  }
		  builder.append(flow.get(i));
		  if(i<flow.size()-1){
		    builder.append(",");
		  }
		  if(i==flow.size()-1){
		    builder.append("]");
		  }
		}
		
		
		Set<String> lable= (Set<String>)request.getAttribute("lable");
		StringBuilder lableBuilder=new StringBuilder();
		Object[]array= lable.toArray();
		for(int i=0;i<array.length;i++){
		  if(i==0){
		    lableBuilder.append("[");
		  }
		  lableBuilder.append("'"+array[i].toString().substring(8)+"'");
		  if(i<array.length-1){
		    lableBuilder.append(",");
		  }
		  if(i==array.length-1){
		    lableBuilder.append("]");
		  }
		}
		%>
		var flow =<%=builder.toString() %>;
		/* for (var i = 0; i < 25; i++) {
			flow.push(Math.floor(Math.random() * (10 + ((i % 16) * 5))) + 10);
		} */

		var data = [ {
			name : 'PV',
			value : flow,
			color : '#ec4646',
			line_width : 2
		} ];

		var labels = <%=lableBuilder.toString() %>;

		var chart = new iChart.LineBasic2D(
				{
					render : 'canvasDiv',
					data : data,
					align : 'center',
					title : {
						text : '近一个月的统计数据',
						font : '微软雅黑',
						fontsize : 24,
						color : '#b4b4b4'
					},
					subtitle : {
						text : '统计${start}~${last}的营业额',
						font : '微软雅黑',
						color : '#b4b4b4'
					},
					footnote : {
						text : 'ichartjs.com',
						font : '微软雅黑',
						fontsize : 11,
						fontweight : 600,
						padding : '0 28',
						color : '#b4b4b4'
					},
					width : 800,
					height : 400,
					shadow : true,
					shadow_color : '#202020',
					shadow_blur : 8,
					shadow_offsetx : 0,
					shadow_offsety : 0,
					background_color : '#2e2e2e',
					tip : {
						enable : true,
						shadow : true,
						listeners : {
							//tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
							parseText : function(tip, name, value, text, i) {
								return "<span style='color:#005268;font-size:12px;'>"
										+ labels[i]
										+ ":营业额<br/>"
										+ "</span><span style='color:#005268;font-size:20px;'>"
										+ value + "元</span>";
							}
						}
					},
					crosshair : {
						enable : true,
						line_color : '#ec4646'
					},
					sub_option : {
						smooth : true,
						label : false,
						hollow : false,
						hollow_inside : false,
						point_size : 8
					},
					coordinate : {
						width : 640,
						height : 260,
						striped_factor : 0.18,
						grid_color : '#4e4e4e',
						axis : {
							color : '#252525',
							width : [ 0, 0, 4, 4 ]
						},
						scale : [ {
							position : 'left',
							start_scale : 0,
							end_scale : ${end_scale},
							scale_space : ${scale_space},
							scale_size : 2,
							scale_enable : false,
							label : {
								color : '#9d987a',
								font : '微软雅黑',
								fontsize : 11,
								fontweight : 600
							},
							scale_color : '#9f9f9f'
						}, {
							position : 'bottom',
							label : {
								color : '#9d987a',
								font : '微软雅黑',
								fontsize : 11,
								fontweight : 600
							},
							scale_enable : false,
							labels : labels
						} ]
					}
				});
		//利用自定义组件构造左侧说明文本
		chart.plugin(new iChart.Custom(
						{
							drawFn : function() {
								//计算位置
								var coo = chart.getCoordinate(), 
								x = coo.get('originx'), y = coo.get('originy'), w = coo.width, h = coo.height;
								//在左上侧的位置，渲染一个单位的文字
								chart.target.textAlign('start').textBaseline(
										'bottom').textFont('600 11px 微软雅黑')
										.fillText('最近一个月总营业额${totalMoney}元', x - 40, y - 12,
												false, '#9d987a').textBaseline(
												'top').fillText('(时间)',
												x + w + 12, y + h + 10, false,
												'#9d987a');

							}
						}));
		//开始画图
		chart.draw();
	});
</script>
<style type="text/css">
#showData span {
	cursor: hand;
	border: gray solid 1px;
	border-color: skyblue;
	font-size: 10px;
}

.btn {
	box-shadow: 1 2 3 4;
	border: gray solid 1px;
}
</style>
</head>
<body style="margin-top: 2px;">
	<div id='canvasDiv'></div>
	<div id='ichartjs_result'></div>
</body>
</html>