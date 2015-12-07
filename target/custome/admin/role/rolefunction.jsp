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
<title>用户功能管理</title>
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
	src="<%=path %>/scripts/role/rolefunction.js?11231114"></script>
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
		<td>上级ID</td>
		<td id="father"><select name="fatherId" id="fatherId" onchange="getChildren('fatherId','fatherId1')">
		                <option value="0">请选择....</option>
		               </select>
		               <select name="fatherId1" id="fatherId1" onchange="getChildren('fatherId1','fatherId2')">
		               <option value="0">请选择....</option></select>
		               <select name="fatherId2" id="fatherId2">
		               <option value="0">请选择....</option>
		               </select>
		                </td>
	</tr>
	<tr>
		<td>角色名称(<font color="red">*</font>)</td>
		<td><input id="name" name="name" value="" required="true" /></td>
	</tr>
	<tr>
		<td>功能路径</td>
		<td><input id="functionUrl" name="functionUrl" value=""/></td>
	</tr>
	<tr>
		<td>状态</td>
		<td><select name="status" id="status">
		     <option value="" selected="selected">--请选择--</option>
			<option value="0">可用</option>
			<option value="1">不可用</option>
		</select>
		</td>
	</tr>
</table>
</form>
</div>
</body>
</html>
