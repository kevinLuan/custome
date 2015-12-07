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
		url : './role/rolefunctions.do',
		idField : 'id',
		columns : [  [
		{
			field : 'ck',
			checkbox : true
		}, {
			title : 'id',
			field : 'id',
			width : 60
		}, {
			title : '角色名称',
			field : 'name',
			width : 100
		}, {
			title : '功能路径',
			field : 'functionUrl',
			width : 400
		}, {
			title : '上级Id',
			field : 'fatherId',
			width : 50
		} ,{
			title : '编码',
			field : 'code',
			width : 50
		} , {
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
		} ] ],
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
    loadFatherOne();
	var row = grid.datagrid('getSelected');
	if (row) {
		win.window('open');
		win.css('display','block');
		$("#id").val(row.id);
		$("#name").val(row.name);
		$("#functionUrl").val(row.functionUrl);
		$("#fatherId").val(row.fatherId);
		$("#code").val(row.code);
		$("#status").empty();
		if(row.status==1){
		$("#status").append('<option value="0">可用</option>');
		$("#status").append('<option value="1" selected>不可用</option>');
		}else{
		$("#status").append('<option value="0">可用</option>');
		$("#status").append('<option value="1">不可用</option>');
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
    loadFatherOne();
	win.window('open');
	win.css('display','block');
	$('#id').attr("value", "0");
	$('#functionUrl').attr("value", "");
	$('#name').attr("value", "");
    $("#status").empty();
    $("#status").append('<option value="0" >可用</option>');
    $("#status").append('<option value="1">不可用</option>');
	
}
function loadFatherOne(){
  var fatherId= document.getElementById('fatherId');
  clearSelect(fatherId);
  fatherId.options.add(new Option("请选择...", "0"));
  var fatherId1= document.getElementById('fatherId1');
  clearSelect(fatherId1);
  fatherId1.options.add(new Option("请选择...", "0"));
  var fatherId2= document.getElementById('fatherId2');
  clearSelect(fatherId2);
  fatherId2.options.add(new Option("请选择...", "0"));
  jQuery.ajax( {
		type : 'POST',
		contentType : 'application/json',
		url :'./role/getrolefunctions.do?code=0001',
		dataType : 'json',
		success : function(data) {
			$.each(data,function(entryIndex,entry){ 
			     fatherId.options.add(new Option(entry.name, entry.id));
             }); 
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
				form.url = './role/deleterolefunction.do?id=' + row.id;
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
	form.url = './role/rolefunctionupdate.do';
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

function getChildren(divId,divId1){
 var v = $('#'+divId+' option:selected').val();
 var objecJson ='[{"id":"'+v+'"}]';
 var fatherId= document.getElementById(divId1);
  clearSelect(fatherId);
  fatherId.options.add(new Option("请选择...", "0"));
  if(v!=0){
  jQuery.ajax( {
		type : 'POST',
		contentType : 'application/json',
		url :'./role/getrolefunctions.do?id='+v,
		dataType : 'json',
		success : function(data) {
			$.each(data,function(entryIndex,entry){ 
			     fatherId.options.add(new Option(entry.name, entry.id));
             }); 
		},
		error : function() {
			$.messager.alert('ERROR', '操作失败!', 'error');
		}
	});
	}
}


function clearSelect(selectElement){
 selectElement.options.length=0;
 }
