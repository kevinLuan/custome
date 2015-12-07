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
		rownumbers : true,
		singleSelect : true,
		pagination : true,
		pageList : [ 15,20,30 ],
		url : './findAll.do',
		idField : 'id',
		columns : [  [ {
			field : 'ck',
			checkbox : true
		}, /*{
			title : 'ID',
			field : 'id',
			width : 100
		},*/ {
			title:'名称',
			field:'name',
			width:300
        },{
			title:'价格',
			field:'price',
			width:100
		},{
            title:'状态',
            field:'status',
            width:150,
            formatter:function(val,row){
            		if(val==0){
            			return '<span style="color: grey">无效</span>';
            		}else if(val==1){
            			return '<span style="color: green">正常</span>';
            		}
                return val;
            }
        }
        ] ],
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
	resizeWin();
});
function resizeWin(){
    $('.window')[0].style.width='500px'
    $('.window-header')[0].style.width='498px';
    $("#win")[0].style.width='498px';
    $("#win")[0].style.height='200px';
}
function getNameByTypeId(val){
    var type=$('#type');
    for(var i=0;i<type[0].length;i++){
        if(parseInt(type[0][i].value)==val){
            return type[0][i].text;
        }
    }
    return val;
}
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
        $("#price").val(row.price);
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
	$('#name').attr("value", "");
    $("#status").attr('value',"")
    $("#price").attr('value',"");
}

function delItem() {
	var row = grid.datagrid('getSelected');
	if (row) {
		$.messager.confirm('警告', '您确认要删除商品：“' + row.name + '”吗?', function(r) {
			if (r) {
				form.url = './deleteById.do?id='+ row.id;
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
    if(Validator.Validate(form[0],3) && checkData()){
        form.url = './addOrUpdateItem.do';
        jQuery.ajax( {
            type : 'POST',
            contentType : 'application/json',
            url : form.url,
            data : jsonObj,
            dataType : 'json',
            success : function(data) {
//                if(data.status){
                    $.messager.show( {
                        title : '提示',
                        msg : data.msg,
                        timeout : 3000,
                        showType : 'slide'
                    });
                    grid.datagrid('reload');
                    win.window('close');
                    grid.datagrid('clearSelections');
//                }else{
//                    $.messager.alert('ERROR', data.msg, 'error');
//                }
            },
            error : function() {
                $.messager.alert('ERROR', '操作失败!', 'error');
            }
        });
    }
}

function checkData(){
    return true;
}
