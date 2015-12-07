<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>客服管理系统</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/themes/icon.css">
		<script type="text/javascript"
			src="<%=path%>/scripts/jquery-1.4.2.min.js"></script>
		<script type="text/javascript"
			src="<%=path%>/scripts/jquery.easyui.min.js"></script>
		<script type="text/javascript"
			src="<%=path%>/scripts/custome.login.js"></script>
	    

	</head>
	<body class="easyui-layout">
		<div region="south" split="true"
			style="height: 50px; padding: 0px; background: #efefef;">
			<p align="center">
				Copyright © 1900-2012 飞信客服管理系统
			</p>
		</div>
		<div region="center">
		     <div id="win" title="客服系统登录" class="easyui-window" style="cursor:default;padding:40px;" split="true" align="center">
			<form id="objForm" style="padding: 5px 5px 3px 3px;" action="./login.do?a=login" method="post">
				<table border="0" bordercolor="red"  style="font-size:10pt">
					<tr>
						<td align="right">
							用户名
						</td>
						<td align="left">
							<input id="userName" name="username" class="easyui-validatebox"
								required="true">
						</td>
					</tr>
					<tr>
						<td align="right">
							密&nbsp;&nbsp;码
						</td>
						<td align="left">
							<input id="passWord" name="password" class="easyui-validatebox" type="password"
								required="true">
						</td>
					</tr>
					
					
					<tr>
						<td align="center" colspan="2">
							<input type="button" id="_login" value="登&nbsp;&nbsp;录" lang="80"/>
						</td>
					</tr>
				</table>
			</form>
			<div style="padding: 5px; text-align: center;">
			</div>
		</div>
		</div>
	</body>
</html>
