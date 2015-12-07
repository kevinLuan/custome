<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
			src="<%=path%>/scripts/custome.base.js"></script>
			 <script type="text/javascript"
			src="<%=path%>/scripts/excanvas.js"></script>
		<script type="text/javascript"
			src="<%=path%>/scripts/user.roles.js"></script>
	   <script type="text/javascript"
            src="<%=path%>/scripts/highcharts.js"></script>		
       <script type="text/javascript" 
            src="<%=path%>/scripts/exporting.js"></script>
       <script type="text/javascript" src="/custome/scripts/validate.js"></script>     
        <style type="text/css">

            .box{
                width:100px;
                height:60px;
                border:1px solid #dce9ff;
                text-align: center;
                /*margin-top: auto;*/
                float: left;
                margin-left: 10px;
                margin-top: 10px;
                cursor: pointer;
            }
        </style>
	</head>
	<body class="easyui-layout" id="lout">
		
		<div region="south" split="true"
			style="height: 50px; padding: 0px; background: #efefef;">
			<p align="center">
				Copyright © 2015-2020 管理系统
			</p>
		</div>

		<div region="west"  title="菜单"
			style="width: 280px; padding:1px; overflow: hidden;" >
			<div id="mytree" style="width:280px;"></div>
			
		</div>
		<div region="center" style="overflow: hidden;">
			<div id="tabs" class="easyui-tabs" fit="true" border="false">
				<div title="主页">
					<div id="bodyWin"></div>
					<div id="house" style="margin-top: 5px;width: 950px;height:80%;border:1px solid #FFF;padding:  10px 10px 10px 10px;">
                        <!--
                        <fieldset>
                            <legend>一层</legend>
                            <div class="box">
                                <h2>1000X</h2>
                                <p>空闲中 </p>
                            </div>
                            <div class="box">
                                <h2>1000X</h2>
                                <p>空闲中 </p>
                            </div>
                        </fieldset>
                        -->
					</div>

				</div>
			</div>
		</div>
	
	<!-- LOGIN win start -->
	<textarea id="loginWin">
	<div region="center">
	<div id="win" title="客服系统登录" class="easyui-window" style="cursor:default;padding:40px;" split="true" align="center">
			<form id="loginForm" style="padding: 5px 5px 3px 3px;" method="post">
				<table border="0" bordercolor="red"  style="font-size:10pt">
					<tr>
						<td align="right">
							用户名
						</td>
						<td align="left">
							<input type="text" id="username" name="username" />
						</td>
					</tr>
					<tr>
						<td align="right">
							密&nbsp;&nbsp;码
						</td>
						<td align="left">
							<input type="password" id="password" name="password"/>
						</td>
					</tr>
					
					
					<tr>
						<td align="center" colspan="2">
							<input type="button" onclick="ajaxLogin()" value="登&nbsp;&nbsp;录" lang="80"/>
						</td>
					</tr>
				</table>
			</form>
			<div style="padding: 5px; text-align: center;">
			</div>
		</div>
		</div>
	</textarea>
	
	<!-- LOGIN win done -->

    <script type="text/javascript">
        function loadRoom(){
            jQuery.ajax( {
                type : 'POST',
                contentType : 'application/json',
                url : '/custome/room/findAll.do',
                data : "",
                dataType : 'json',
                success : function(data) {
                    var map={};
                    for(var i=0;i<data.rows.length;i++){
                        if(!map[data.rows[i].floor]){
                            map[data.rows[i].floor]=[];
                        }
                        map[data.rows[i].floor].push(data.rows[i]);
                    }
                    var house= $("#house");
                    for(var i=0;i<house.children().length;i++){
                        house.children().remove();
                    }
                    for(var level in map){
                        var rooms= map[level];
                        var html="<fieldset><legend>"+level+"层</legend>";
                        for(var i=0;i<rooms.length;i++){
                            if(rooms[i].status==1){
                                html+='<div class="box" style="background-color: red;" title="房间使用中"><h2>'+rooms[i].name+'</h2><p>使用中 </p></div>';
                            }else{
                                html+='<div class="box" style="background-color: green;" title="房间空闲中"><h2>'+rooms[i].name+'</h2><p>空闲中 </p></div>';
                            }

                        }
                        html+="</fieldset>";
                        $("#house").append(html)
                    }
                },
                error : function() {
                }
            });
        }
        loadRoom();
        setInterval(loadRoom,2000);
    </script>
	<script type="text/javascript">
	var win;
    var isShowLogin=false;
		function ajaxLogin(){
			var username=$("#username").val();
			var password= $("#password").val();
			if(username.length==0){
                $.messager.alert('ERROR', '请输入用户名!', 'error');
				return;
			}
			if(password.length==0){
                $.messager.alert('ERROR', '请输入密码!', 'error');
				return;
			}
			jQuery.ajax( {
                type : 'POST',
                contentType : 'application/json',
                url : '/custome/ajaxLogin.do?username='+username+'&password='+password,
                data : "",
                dataType : 'json',
                success : function(data) {
                   if(data.status.code==0){
                	   	win.window('close');
                	   	if($("#shade_div")){
        	                $("#shade_div").remove();
        	            }
                       window.location.reload();
                   }else{
                       $.messager.alert('ERROR', data.status.description, 'error');
                   }
                },
                error : function() {
                    $.messager.show( {
	            			title : '警告',
	            			msg : '登陆出错',
	            			timeout : 5000,
	            			showType : 'slide'
            			});
                }
            })

        }
		
		function show_shade(id,text) {
	        var coverEl = document.createElement('div');
	        coverEl.id = id || 'shade_div';
	        coverEl.style.width = '100%';
	        coverEl.style.height = '100%';
	        coverEl.style.position = 'absolute';
	        coverEl.style.left = '0';
	        coverEl.style.top = '0';
	        coverEl.style.background = 'rgba(0,0,0,0.5)';
	        coverEl.style.zIndex = '20';
	        if(text){
	            coverEl.style.textAlign='center';
	            coverEl.textContent=text;
	            coverEl.style.color='#FFF';
	        }
	        document.body.appendChild(coverEl);
	    };
	   	function showLogin(){
            show_shade('shade_div','请重新登录...');
            $("#bodyWin")[0].innerHTML=$("#loginWin").val();

            $(function() {
                $('#_login').click(function() {
                    $('#objForm').submit();
                });
                win = $('#win').window({
                    draggable : false,
                    minimizable : false,
                    maximizable : false,
                    resizable : false,
                    collapsible : false,
                    left : 460,
                    top : 180,
                    width:360,
                    height:235
                });
                win.window('open');
            });
            $(".panel-tool").remove()
            window.isShowLogin=true;
        }

             /*ping 验证是否登陆会话失败*/
            setInterval(function(){
                if(window.isShowLogin){
                    return;
                }
                jQuery.ajax( {
                    type : 'POST',
                    contentType : 'application/json',
                    url : '/custome/ping.do',
                    data : "",
                    dataType : 'json',
                    success : function(data) {
                        if(!data.result){
                            showLogin();
                        }
                    },
                    error : function() {
	                    	$.messager.show( {
	                			title : '警告',
	                			msg : 'ping error',
	                			timeout : 5000,
	                			showType : 'slide'
	                		});
                    }
                })
            },5000);
		</script>
	</body>
</html>