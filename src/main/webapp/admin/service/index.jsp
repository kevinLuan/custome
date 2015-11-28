<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>服务项目管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="/custome/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/custome/themes/icon.css">

    <script type="text/javascript" src="/custome/scripts/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/custome/scripts/jquery.json-2.2.min.js"></script>
    <script type="text/javascript" src="/custome/scripts/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/custome/scripts/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/custome/scripts/service/index.js?v=12332"></script>
    <script type="text/javascript" src="/custome/scripts/custome.base.js"></script>
    <script type="text/javascript" src="/custome/scripts/validate.js"></script>
    <style type="text/css">
       #showData span{
            cursor: hand;
            border:gray solid 1px;
            border-color: skyblue;
           font-size: 10px;
        }
        .btn{
            box-shadow: 1 2 3 4;border: gray solid 1px;
        }
        /* input,select{
        		width:300px;
        		height:30px;
        		font-size:24px;
        } */
    </style>
</head>
<body style="margin-top: 2px">
<table id="tt"></table>
<div id="win" style="display:none">

<form id="objForm" method="POST" enctype="multipart/form-data" method="post">
<table align="center">
    <tr>
        <td></td>
        <td><input type="hidden" id="id" name="id" value="0"/></td>
    </tr>
    <tr>
    	<td>服务名称</td>
    	<td><input id="name" name="name" value="" maxlength="10" required="true" dataType="Require" msg="*" /></td>
    </tr>
    <tr>
	    	<td>价格</td>
	    	<td>
	    		<input id="price" name="price" value="" maxlength="10" required="true" dataType="Require" msg="*" />
	    	</td>
    </tr>
    <tr>
    		<td>状态</td>
    		<td>
    			<select id="status" name="status" required="true" dataType="Require" msg="*" >
    				<option value="">--请选择--</option>
    				<option value="0">无效</option>
    				<option value="1">有效</option>
    			</select>
    		</td>
    </tr>
    <tr>
    		<td>服务描述</td>
    		<td>
    			<textarea rows="5" cols="40" id="description" name="description"></textarea>
    		</td>
    </tr>
</table>
</form>
</div>
<div id="win1"  title="添加" style="display:none">
     <ul id="win1tt"></ul>
</div>
</body>
</html>