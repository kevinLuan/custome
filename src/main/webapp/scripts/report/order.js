var grid;
var win;
var form;
var win1;

$(function() {
    reload_grid=function(url,startDate,endDate){
        if(startDate && endDate){
            url+='?startDate='+startDate+'&endDate='+endDate;
        }

        grid = $('#tt').datagrid( {
            iconCls : 'icon-save',
            loadMsg : '数据加载中，请稍后......',
            nowrap : false,
            striped : true,
            rownumbers : true,
            singleSelect : true,
            pagination : false,
            pageList : [ 20,30,40 ],
            url : url,
            idField : 'id',
            columns : [  [
                {
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
                    width:50,
                    formatter:function(val,row){
                        return "￥"+val;
                    }
                },{
                    title:'实收金额',
                    field:'gather_price',
                    width:50,
                    formatter:function(val,row){
                        return '￥'+val;
                    }
                },{
                    title:'服务<span style="float: right">保健师</span>',
                    field:'services',
                    width:160,
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
                    width:160,
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
            ] ],
            toolbar : [ ]
        });
        loadMenu(startDate,endDate);
    },
    loadMenu=function(startDate,endDate){
        if(endDate==undefined){
        		endDate='';
        }
        if(startDate==undefined){
        		startDate='';
        }
        $(".datagrid-toolbar").append(
            '<div>' +
            '开始日期：<input type="text" id="startDate" name="startDate" value="'+startDate+'" maxlength="10" readonly="readonly" onclick="WdatePicker({lang:\'zh-cn\',isShowClear:false,dateFmt:\'yyyy-MM-dd\',minDate:\'2015-10-01\',maxDate:\'\'}  )" dataType="Require" msg="*"/>' +
            '结束日期：<input type="text" id="endDate" name="endDate" value="'+endDate+'" maxlength="10" readonly="readonly" onclick="WdatePicker({lang:\'zh-cn\',isShowClear:false,dateFmt:\'yyyy-MM-dd\',minDate:\'2015-10-01\',maxDate:\'\'}  )" dataType="Require" msg="*"/>'+
            '<button id="submit">查询</button><button id="exportHtml">导出Excel</button>'+
            '</div>' +
            '');
        $("#submit").bind('click',function(){
            var startDate=$("#startDate").val();
            var endDate= $("#endDate").val();
            if(startDate.length==0){
                $.messager.alert('ERROR', '请选择开始日期', 'error');
                return;
            }
            if(endDate.length==0){
                $.messager.alert('ERROR', '请选择结束日期', 'error');
                return;
            }
            reload_grid('./findOrderAll.do',$("#startDate").val(),$("#endDate").val());
        })
        $("#exportHtml").bind('click',function(){
            var startDate=$("#startDate").val();
            var endDate= $("#endDate").val();
            if(startDate.length==0){
                $.messager.alert('ERROR', '请选择开始日期', 'error');
                return;
            }
            if(endDate.length==0){
                $.messager.alert('ERROR', '请选择结束日期', 'error');
                return;
            }
            window.location.href='exportOrder.html?startDate='+startDate+'&endDate='+endDate+'&downloadFileName=Order.xls';
        })
    }
    reload_grid('./findOrderAll.do');
});