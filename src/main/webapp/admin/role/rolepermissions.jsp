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
<title>用户组管理</title>
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
	src="<%=path %>/scripts/role/rolepermissions.js?1222"></script>
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
		<td>角色名称(<font color="red">*</font>)</td>
		<td><input id="roleName" name="roleName" value="" required="true" /></td>
	</tr>
	<tr>
		<td>状态(<font color="red">只能输入数字</font>)</td>
		<td><input id="status" name="status" value=""/></td>
	</tr>
</table>
</form>
</div>
</body>
</html>
