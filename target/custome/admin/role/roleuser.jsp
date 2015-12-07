<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户信息管理</title>
<link rel="stylesheet" type="text/css"
	href="<%=path %>/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/themes/icon.css">
<script type="text/javascript"
	src="<%=path %>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript"
	src="<%=path %>/scripts/jquery.json-2.2.min.js"></script>
<script type="text/javascript"
	src="<%=path %>/scripts/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=path %>/scripts/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="<%=path %>/scripts/role/roleuser.js?123123"></script>
<script type="text/javascript"
	src="<%=path %>/scripts/custome.base.js"></script>

</head>

<body>

<table id="tt"></table>
<div id="win" style="display:none">
<form id="objForm" method="POST">
<table align="center">
	<tr>
		<td></td>
		<td><input id="id" name="id" value="0" type="hidden" /></td>
	</tr>
	<tr>
		<td>用户名(<font color="red">*</font>)</td>
		<td><input id="userName" name="userName" maxlength="16" value="" required="true" /></td>
	</tr>
	<tr>
		<td>用户密码</td>
		<td><input type="password" id="passWord" maxlength="16" name="passWord" value=""/></td>
	</tr>
	<tr>
		<td>昵称</td>
		<td><input id="nickName" name="nickName" maxlength="16" value=""/></td>
	</tr>
	
</table>
</form>
</div>

<div id="win1" style="display:none">
	<table align="center" id="groups">
	</table>
</div>
<div id="win2" style="display:none">
	<table align="center" >
	<tr>
		<td>昵称</td>
		<td id="countName"></td>
	</tr>
	<tr>
		<td>本日处理量</td>
		<td id="dayCount"></td>
	</tr>
	<tr>
		<td>本月处理量</td>
		<td id="monthCount"></td>
	</tr>
	<tr>
		<td>总计处理量</td>
		<td id="totleCount"></td>
	</tr>
	</table>
</div>
</body>
</html>
