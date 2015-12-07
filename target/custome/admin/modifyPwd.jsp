<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
<link rel="stylesheet" type="text/css"
	href="<%=path%>/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/themes/icon.css">
<script type="text/javascript"
	src="<%=path%>/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/scripts/jquery.easyui.min.js"></script>
	<style type="text/css">
		.panel-tool{
			display: none;
		}
		.btn{
		    width: 100px;
		    height: 30px;
		    border: 1px solid #83C7F7;
		    background-color: #8DB2E3;
		    cursor: pointer;
		}
	</style>
</head>
<body>
	<div id="win" title="更改密码" class="easyui-window"
		style="cursor: default; padding: 40px; overflow: hidden;" split="true"
		align="center">
		<form id="objForm" style="padding: 5px 5px 3px 3px;"
			action="./modifyPwd.do" method="post">

			<table border="0" bordercolor="red" style="font-size: 10pt">
				<tr>
					<td align="right">原始密码:</td>
					<td align="left">
					<input id="oldPwd" name="oldPwd"
						class="easyui-validatebox" maxlength="20" style="width: 150px"
						type="password" required="required" validType="length[1,15]"
						value="" onkeydown="eventEnterKey()"></td>
				</tr>
				<tr>
					<td align="right">变更密码:</td>
					<td align="left"><input id="newPwd" name="newPwd"
						class="easyui-validatebox" maxlength="20" style="width: 150px"
						type="password" required="required" validType="length[1,15]"
						value="" onkeydown="eventEnterKey()"></td>
				</tr>
				<tr>
					<td align="right">确认密码:</td>
					<td align="left"><input id="re_newPwd" name="re_newPwd"
						class="easyui-validatebox" maxlength="20" style="width: 150px"
						type="password" required="required" validType="equals['#newPwd']"
						value="" onkeydown="eventEnterKey()"></td>
				</tr>
				<tr>
					<td align="center" colspan="2">
					<input type="submit" class="btn" id="submit" value="修改密码" onclick="submitForm(event)" lang="80" />
					</td>
				</tr>
			</table>
		</form>
		
	</div>
	<script type="text/javascript">
    $.extend($.fn.validatebox.defaults.rules, {
        equals: {
            validator: function(value,param){
                return value == $(param[0]).val();
            },
            message: 'Field do not match.'
        }/*,
        maxLength: {
            validator: function(value, param){
                return value.length > param[0];
            },
            message: 'this characters are too large.'
        }*/

    });
    function eventEnterKey(){
        if(window.event.keyCode==13){
            $("#objForm")[0].submit();
        }
    }
    function submitForm(event){
        event= event || window.event
        event.preventDefault();
        var url = "<%=path%>/modifyPwd.do";
      	var oldPwd = $("#oldPwd").val(), newPwd = $("#newPwd").val(), re_newPwd = $("#re_newPwd").val();

			if (oldPwd == "" || newPwd == "" || re_newPwd != newPwd){
				$.messager.alert("ERROR", "输入数据不合法", "error");
				return;
			}
			/* if (newPwd.replace(/\d+/g, "").replace(/[a-z]+/ig, "").length == 0
					|| newPwd.length < 8) {
				alert('密码太过于简单了。 密码格式必须包含：数字+字母+符号 并且大于7位')
				return false;
			} */
			jQuery.ajax({
				type : "POST",
				url : url,
				data : {
					"oldPwd" : oldPwd,
					"newPwd" : newPwd
				},
				dataType : "json",
				success : function(data) {
					if (data.status == true)
						$.messager.alert("SUCCESS", "修改密码成功!");
					else
						$.messager.alert("FALSE", data.desc);
				},
				error : function() {
					$.messager.alert("ERROR", "修改密码失败!", "error");
				}
			});

		}
	</script>
</body>
</html>