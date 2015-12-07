var grid;
var win;
var form;
var win1;

$(function() {
	    grid = $('#tt').datagrid( {
		iconCls : 'icon-save',
		loadMsg : '数据加载中，请稍后......',
		nowrap : false,
		striped : true,
		rownumbers : false,
		singleSelect : true,
		pagination : true,
		pageList : [ 20, 30, 50 ],
		url : './role/rolegroups.do',
		idField : 'id',
		columns : [  [ {
			field : 'ck',
			checkbox : true
		}, {
			title : '编号',
			field : 'id',
			width : 40
		}, {
			title : '组名称',
			field : 'name',
			width : 100
		}, {
			title : '状态',
	   	    field : 'status',
	   	    width : 100,
			formatter:function(val,rec) {
				if(val==0) {
					return "可用";
				}else {
					return "不可用";
				}
			}
		}, {
		    title : '操作',
		    field : 'opt',
		    width : 100,
		    align : 'center', 
			formatter:function(value,rec){
		       return '<span style="color:red"><a href="javascript:addGroup('+rec.id+');">添加</a> </span>';
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
		title : '<font color="red">*</font>为必输入项',
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
		width:400,
		tools : [ {
			iconCls : 'icon-save',
			handler : saveItem
		} ]
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
		$("#name").val(row.name);
		$("#status").empty();
	  if(row.status==1){
	    $("#status").append('<option value="0" >可用</option>');
        $("#status").append('<option value="1" selected>不可用</option>');
	  }else{
	    $("#status").append('<option value="0" >可用</option>');
        $("#status").append('<option value="1" >不可用</option>');
	  }
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
	$('#name').attr("value", "");
	$("#status").empty();
    $("#status").append('<option value="0" >可用</option>');
    $("#status").append('<option value="1">不可用</option>');
}

function addGroup(divId){
   win1 = $('#win1').window( {
		title : '添加权限',
		zIndex:9000,
		draggable : false,
		minimizable : false,
		maximizable : false,
		resizable : false,
		collapsible : false,
		collapsed : true,
		modal : true,
		left : 200,
		top : 50,
		height : 600,
		width:400,
		tools : [ {
			iconCls : 'icon-save',
			handler : saveGroupRole
		} ]
	});
    win1.window('open');
    win1.css('display','block');
    form.url = './role/getTrees.do?id='+divId;
	$('#win1tt').tree({
	            checkbox: true ,
	            cascadeCheck: false,
	            url: form.url
	});
    
	
 }
function saveGroupRole(){
	var row = grid.datagrid('getSelected');
	var nodes = $('#win1tt').tree('getChecked');
	var s='';
	for(var i=0; i<nodes.length; i++){
		if (s != '') s += ',';
		s += nodes[i].id;
	}
	 var url = './role/groupRoleUpdate.do?groupId='+row.id+'&roleIds='+s;          
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



function saveItem() {
	var jsonObj = $.toJSON(form.serializeObject());
	form.url = './role/rolegroupupdate.do';
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
function delItem() {
	var row = grid.datagrid('getSelected');
	if (row) {
		$.messager.confirm('警告', '[ID' + row.id + '],确认删除??', function(r) {
			if (r) {
				form.url = './role/deleterolegroup.do?id='+ row.id;
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
	form.url = './role/rolegroupupdate.do';
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

