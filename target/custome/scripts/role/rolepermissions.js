var grid;
var win;
var form;

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
		url : './role/rolepermissionss.do',
		idField : 'id',
		columns : [  [ {
			field : 'ck',
			checkbox : true
		}, {
			title : '编号',
			field : 'id',
			width : 40
		}, {
			title : '角色名称',
			field : 'roleName',
			width : 100
		}, {
			title : '状态',
			field : 'status',
			width : 50
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
		width : 400,
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
		$("#roleName").val(row.roleName);
		$("#status").val(row.status);
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
	$('#roleName').attr("value", "");
	$('#status').attr("value", "");
}
function delItem() {
	var row = grid.datagrid('getSelected');
	if (row) {
		$.messager.confirm('警告', '[ID' + row.id + '],确认删除??', function(r) {
			if (r) {
				form.url = './role/deleterolepermissions.do?id=' + row.id;
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
	form.url = './role/rolepermissionsupdate.do';
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

