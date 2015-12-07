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
		pageList : [ 10,20,30 ],
		url : './findAll.do',
		idField : 'id',
		onLoadSuccess:function(data){
		},
		columns : [  [/* {
			field : 'ck',
			checkbox : true
		},*/{
			title : '订单号',
			field : 'id',
			width : 60
		},{
			title:'房间',
			field:'roomId',
			width:50,
            formatter:function(val,row,index){
                if(row.room){
                    return row.room.name;
                }
                return val;
            }
			
		}, {
			title:'人数',
			field:'persionNum',
			width:30
        },{
			title:'结账',
			field:'jieZhang',
			width:50,
			formatter:function(val,row,index){
				if(val==1){
					return '<span style="color: cadetblue;"> 已结账 </span>';
				}else{
					return '<span style="color:red;">未结账</span>';
				}
				return val;
			}
		},{
			title:'应收金额',
			field:'totalPrice',
			width:70,
			formatter:function(val,row){
				return "￥"+val;
			}
		},{
            title:'实收金额',
            field:'gather_price',
            width:70,
            formatter:function(val,row){
                if(val && val>0){
                    return '<span style="color: green">￥'+val+'</span>';
                }else{
                    return '';
                }
            }

        },{
            title:'服务<span style="float: right">保健师</span>',
            field:'services',
            width:190,
            formatter:function(val,row){
                var str='';
                for(var i=0;i<val.length;i++){
                    if(val[i].baoJianShi){
                        str+='<span title="服务项">'+val[i].serviceName+'</span>' +
                            '<span style="float: right" title="保健师">'+val[i].baoJianShi.employeNo+'</span><br/>';
                    }else{
                        str+='<span>'+val[i].serviceName+'</span><span style="float: right">--</span><br/>';
                    }
                }
                return str;
            }
        },{
            title:'套餐<span style="float: right;">保健师</span>',
            field:'taocans',
            width:190,
            formatter:function(val,row){
                var str='';
                for(var i=0;i<val.length;i++){
                    if(val[i].baoJianShi){
                        str+='<span title="套餐">'+val[i].taocanName+'</span>' +
                            '<span title="保健师" style="float: right">'+val[i].baoJianShi.employeNo+'</span><br/>';
                    }else{
                        str+='<span>'+val[i].taocanName+'</span><span style="float: right">--</span><br/>';
                    }
                }
                return str;
            }
        },{
            title:'消费商品',
            field:'products',
            width:100,
            formatter:function(val,row){
                var str='';
                for(var i=0;i<val.length;i++){
                    for(var j=0;j<val[i].num;j++){
                        str+='<span title="消费商品">'+(i+j+1)+'. '+val[i].productName+'</span><br/>';
                    }
                }
                return str;
            }
        }
        ,{
            title:'进入时间',
            field:'enterTime',
            width:130,
            formatter:function(val,row){
                return DateUtils.parserNumberToFormatDate(val,'yyyy-MM-dd HH:mm:ss');
            }
        }
        ,{
			title:'离开时间',
			field:'leaveTime',
			width:130,
			formatter:function(val,row){
				if(val!=null){
					return DateUtils.parserNumberToFormatDate(val,'yyyy-MM-dd HH:mm:ss');
				}
			}
		}
        ,{
			title:'操作',
			field:'operator',
			width:240,
			formatter:function(val,row){
                if(row.jieZhang==1){
                    return '<span style="color: grey">添加服务|添加套餐|选择保健师|添加商品</span>';
                }else{
                    var str="";
                    str+='<a href="javascript:openServiceWin('+row.id+')">添加服务</a>';
                    str+='|<a href="javascript:openTaocanWin('+row.id+')">添加套餐</a>';
                    str+='|<a href="javascript:openBjsWin('+row.id+')">选择保健师</a>'
                    str+='|<a href="javascript:openProductWin('+row.id+')">添加商品</a>';
                    return str;
                }
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
		}
        ]
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
    $('.window')[0].style.width='400px'
    $('.window-header')[0].style.width='398px';
    $("#win")[0].style.width='398px';
    $("#win")[0].style.height='300px';
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
        $(".icon-save").remove();
        if(row.jieZhang==1){
            $.messager.alert('ERROR', '结束订单无法修改', 'error');
            return;
        }
        Easy.loadAllRoom(function(){
            win.window('open');
            win.css('display','block');
            $('#id').val(row.id);
            $('#roomId').val(row.roomId);
            $("#persionNum").val(row.persionNum)
            $("#roomId").attr('disabled','disabled');
//            $("#gather_price").val(row.totalPrice);
        });
        $("#totalPrice").attr('value',row.totalPrice);
        $("#gather_price").attr('disabled','');
        $("span[name=brightText]").attr('style','');
        $("#submit").unbind();
        $("#submit").text('结账')
        $("#submit").bind('click',function(event){
            event=event||window.event;
            event.preventDefault();
            var gather_price=$("#gather_price").val();
            if(!gather_price || gather_price.length==0){
                $.messager.alert('ERROR', '请输入支付金额。', 'error');
                return;
            }
            if(!/^\d+$/.test(gather_price)){
                $.messager.alert('ERROR', '请正确输入实际支付金额。', 'error');
                return;
            }else{
                if(parseInt(gather_price)>parseInt(row.totalPrice)){
                    $.messager.alert('ERROR', '实际支付金额不能大于应收金额。', 'error');
                    return;
                }else{
                    if(parseInt(gather_price)<parseInt(row.totalPrice)){
                        $.messager.confirm('确认提示','实际支付金额小于应收金额，您确定要提交吗？',
                            function(bool){
                                if(bool){
                                    $("#jieZhang").val(1);
                                    saveItem();
                                }
                            });
                    }else{
                        $("#jieZhang").val(1);
                        saveItem();
                    }
                }
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
 * 添加
 * 
 * @return
 */
function createItem() {
	win.window('open');
	win.css('display','block');
	$('#id').attr("value", "0");
	$('#roomId').attr("value", "");
    $("#persionNum").attr('value',"")
    $("#jieZhang").attr('value',"0");
    $("#roomId").attr('disabled','');
    $("#gather_price").attr('value','');
    $("#totalPrice").attr('value','');
    $(".icon-save").remove();
    $("span[name=brightText]").attr('style','color:gray');
    $("#gather_price").attr('disabled','disabled');
    $("#submit").unbind();
    $("#submit").bind('click',function(event){
        event=event||window.event;
        event.preventDefault();
        saveItem();

    });
    Easy.loadRoom();
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
                if(data.status.code==0){
                    $.messager.show( {
                        title : '提示',
                        msg : '处理成功',
                        timeout : 3000,
                        showType : 'slide'
                    });
                    grid.datagrid('reload');
                    win.window('close');
                    grid.datagrid('clearSelections');
                }else{
                    $.messager.alert('ERROR', data.status.description, 'error');
                }
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
$( $("tr[datagrid-row-index]")[0] ).attr('class','datagrid-row-over datagrid-row-selected');