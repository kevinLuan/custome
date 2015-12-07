var grid;
var win;
var form;
var win1;
var win2;


$(function() {
	grid = $('#tt').datagrid( {
		iconCls : 'icon-save',
		loadMsg : '数据加载中，请稍后......',
		nowrap : false,
		striped : true,
		rownumbers : true,
		singleSelect : true,
		pagination : true,
		pageList : [ 20, 30, 50 ],
		url : './role/roleusers.do',
		idField : 'id',
		columns : [  [ {
			field : 'ck',
			checkbox : true
		},/* {
			title : '编号',
			field : 'id',
			width : 60
		},*/ {
			title : '用户昵称',
			field : 'nickName',
			width : 100
		}, {
			title : '用户名',
			field : 'userName',
			width : 100
		} , {
		    title : '操作',
		    field : 'opt',
		    width : 200,
		    align : 'center', 
			formatter:function(value,rec){
		       return "<span style='color:red'><a href='javascript:addRole("+rec.id+");'>添加权限</a>";
		    }
		 }] ],
		toolbar : [ {
			text : '添加',
			iconCls : 'icon-add',
			handler : createItem
		}, '-', {
			text : '修改',
			iconCls : 'icon-edit',
			handler : editItem
		}, '-', {
			text : '删除',
			iconCls : 'icon-remove',
			handler : delItem
		} ]
	});
	win = $('#win').window( {
		title : '添加用户窗口',
		draggable : false,
		minimizable : false,
		maximizable : false,
		resizable : false,
		collapsible : false,
		collapsed : true,
		closed : true,
		modal : true,
		left : 200,
		top : 50,
		height : 350,
		width : 400,
		tools : [ {
			iconCls : 'icon-save',
			handler : saveItem
		} ]
	});
	win1 = $('#win1').window( {
		title : '用户权限管理窗口',
		draggable : false,
		minimizable : false,
		maximizable : false,
		resizable : false,
		collapsible : false,
		collapsed : true,
		closed : true,
		modal : true,
		left : 200,
		top : 50,
		height : 350,
		width : 400,
		tools : [ {
			iconCls : 'icon-save',
			handler : saveUerRole
		} ]
	});
	win2 = $('#win2').window( {
	    title : '统计查看',
		draggable : false,
		minimizable : false,
		maximizable : false,
		resizable : false,
		collapsible : false,
		collapsed : true,
		closed : true,
		modal : true,
		left : 200,
		top : 50,
		height : 350,
		width : 400
	});
	form = win.find('#objForm');
});
/**
 * 编辑数据
 * 
 * @return
 */
function editItem() {
	var row = grid.datagrid('getSelected');
	if (row) {
		win.window('open');
		win.css('display','block');
		$("#id").val(row.id);
		$("#userName").val(row.userName);
		$("#passWord").val(row.passWord);
		$("#nickName").val(row.nickName);
	} else {
		$.messager.show( {
			title : '警告',
			msg : '请选择!',
			timeout : 5000,
			showType : 'slide'
		});
	}
}
/**
 * 添加
 * 
 * @return
 */
function createItem() {
	win.window('open');
	win.css('display','block');
	$('#id').attr("value", "0");
	$('#userName').attr("value", "");
	$('#passWord').attr("value", "");
	$('#nickName').attr("value", "");
}
function delItem() {
	var row = grid.datagrid('getSelected');
	if (row) {
		$.messager.confirm('警告', '[ID' + row.id + '],确认删除??', function(r) {
			if (r) {
				form.url = './role/deleteroleuser.do?id=' + row.id;
				jQuery.ajax( {
					type : 'POST',
					contentType : 'application/json',
					url : form.url,
					data : "",
					dataType : 'json',
					success : function(data) {
						grid.datagrid('reload');
						grid.datagrid('clearSelections');
					},
					error : function() {
						alert('错误');
					}
				});
			}
		});
	} else {
		$.messager.show( {
			title : '警告',
			msg : '请选择!',
			timeout : 5000,
			showType : 'slide'
		});
	}
}
/**
 * 提交表单数据
 * 
 * @return
 */
function saveItem() {
	  var jsonObj = $.toJSON(form.serializeObject());
	form.url = './role/userupdate.do';
	jQuery.ajax( {
		type : 'POST',
		contentType : 'application/json',
		url : form.url,
		data : jsonObj,
		dataType : 'json',
		success : function(data) {
			$.messager.show( {
				title : '提示',
				msg : '操作成功!',
				timeout : 3000,
				showType : 'slide'
			});
			grid.datagrid('reload');
			win.window('close');
			grid.datagrid('clearSelections');
		},
		error : function() {
			$.messager.alert('ERROR', '操作失败!', 'error');
		}
	});
}
function addRole(userId){
    win1.window('open');
    win1.css('display','block');
    var groupTable = $('#groups');
    groupTable.html('');
    jQuery.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : './role/getUserGroups.do?userId='+userId,
		dataType : 'json',
		success : function(data) {
			$.each(data,function(entryIndex,entry){ 
			    if(entry.checked=='true'){
			    groupTable.append('<tr><td><input type="checkbox" name="groupId" value="'+entry.id+'" checked></td><td>'+entry.name+'</td></tr>');
			    }else{
			     groupTable.append('<tr><td><input type="checkbox" name="groupId" value="'+entry.id+'" ></td><td>'+entry.name+'</td></tr>');
			    }
             });
			
		},
		error : function() {
			$.messager.alert('ERROR', '操作失败!', 'error');
		}
	});
    
}
function saveUerRole(){
	var str='';
	var checkBoxs = document.getElementsByName("groupId");
    var row  =   grid.datagrid('getSelected'); 
	for (var i = 0; i < checkBoxs .length; i++) {
	   if (checkBoxs[i].checked){
	     str += checkBoxs[i].value + ',';  
	   }
	}
	var url ='./role/updateUserRoles.do?userId='+row.id+'&groupIds='+str;
	jQuery.ajax( {
		type : 'POST',
		contentType : 'application/json',
		url : url,
		dataType : 'json',
		success : function(data) {
			$.messager.show( {
				title : '提示',
				msg : '操作成功!',
				timeout : 3000,
				showType : 'slide'
			});
			grid.datagrid('reload');
			win1.window('close');
			grid.datagrid('clearSelections');
		},
		error : function() {
			$.messager.alert('ERROR', '操作失败!', 'error');
		}
	});
}
function editRole(){
	var row = grid.datagrid('getSelected');
	if (row) {
		$.messager.confirm('警告', '[ID' + row.id + '],确认删除??', function(r) {
			if (r) {
				form.url = './role/deleteuserrole.do?userId='+ row.id;
				jQuery.ajax( {
					type : 'POST',
					contentType : 'application/json',
					url : form.url,
					data : "",
					dataType : 'json',
					success : function(data) {
						grid.datagrid('reload');
						grid.datagrid('clearSelections');
					},
					error : function() {
						alert('错误');
					}
				});
			}
		});
	} else {
		$.messager.show( {
			title : '警告',
			msg : '请选择!',
			timeout : 5000,
			showType : 'slide'
		});
	}
}  
function getCounts(id,name){
	 win2.window('open');
	 win2.css('display','block');
	 var url='./custome/getCountForUser.do?id='+id ;
	 $('#countName').html('');
	 $('#countName').html(name);
	 $('#dayCount').html('');
	 $('#monthCount').html('');
	 $('#totleCount').html('');
     jQuery.ajax( {
		type : 'POST',
		contentType : 'application/json',
		url : url,
		dataType : 'json',
		success : function(data) {
			$.each(data,function(entryIndex,entry){ 
			    if(entry.type==0){
			     $('#totleCount').html(entry.staticCount);
			    }
			    if(entry.type==1){
			     $('#dayCount').html(entry.staticCount);
			    }
			    if(entry.type==2){
			    $('#monthCount').html(entry.staticCount);
			    }
			    
             });
			grid.datagrid('clearSelections');
		},
		error : function() {
			$.messager.alert('ERROR', '操作失败!', 'error');
		}
	});
}  
