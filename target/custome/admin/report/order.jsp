<%@page import="java.util.Set"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>订单详细列表</title>
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
<script type="text/javascript" src="/custome/scripts/report/order.js?v=12331112"></script>
<script type="text/javascript" src="/custome/scripts/My97DatePicker/WdatePicker.js?v=1"></script>
<script type="text/javascript" src="/custome/scripts/DateUtils.js?123"></script>
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
<table id="tt"></table>
</body>
</html>